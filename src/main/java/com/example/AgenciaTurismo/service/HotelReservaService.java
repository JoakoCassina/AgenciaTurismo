package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.*;
import com.example.AgenciaTurismo.dto.request.FinalHotelReservationDTO;
import com.example.AgenciaTurismo.dto.request.HotelConsultDTO;
import com.example.AgenciaTurismo.dto.response.ResponseDTO;
import com.example.AgenciaTurismo.dto.response.StatusCodeDTO;
import com.example.AgenciaTurismo.dto.response.TotalHotelReservationDTO;
import com.example.AgenciaTurismo.model.*;
import com.example.AgenciaTurismo.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class HotelReservaService implements IHotelReservaService {
    @Autowired
    IHotelReservaRepository hotelReservaRepository;
    @Autowired
    IPeopleRepository peopleRepository;
    @Autowired
    IPaymentMethodRepository paymentMethodRepository;
    @Autowired
    IHotelRepository hotelRepository;
    @Autowired
    IHotelService serviceHotel;
    @Autowired
    ITouristicPackageRepository touristicPackageRepository;
    @Autowired
    IClientRepository clientRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override//Método que lista todas las reservas de Hotel cargadas en mi DB
    public List<FinalHotelReservationDTO> listarReservas() {
        List<ReservarHotel> reservasList = hotelReservaRepository.findAll();
        return mapearReservas(reservasList);

    }

    @Override//Método para crear una reserva de Hotel
    public ResponseDTO createReserva(FinalHotelReservationDTO finalHotelReservationDTO) {

        Optional<Client> clienteExistente = clientRepository.findByUsername(finalHotelReservationDTO.getUserName());
        if(clienteExistente.isEmpty()) {
            return new ResponseDTO("Debes loguearte para poder crear una reserva!!");//Validamos que sea un cliente existente el que realiza la reserva
        }
        Client clienteEncontrado = clienteExistente.get();

        this.reserveSaved(finalHotelReservationDTO);//Llamamos al método que valida si una reserva ya existe para evitar duplicados.

        this.roomCapacity(finalHotelReservationDTO.getHotelReservationDTO());//Llamamos al metodo que valida si la cantidad de personas coincide con el tipo de habitacíon solicitado.


        HotelConsultDTO hotelBuscado = new HotelConsultDTO( //Creamos una variable de tipo de dato HotelConsultDTO
                finalHotelReservationDTO.getHotelReservationDTO().getDateFrom(),
                finalHotelReservationDTO.getHotelReservationDTO().getDateTo(),
                finalHotelReservationDTO.getHotelReservationDTO().getDestination(),
                finalHotelReservationDTO.getHotelReservationDTO().getHotelCode());

        List<HotelDTO> availableHotel = serviceHotel.validarHotelesDisponibles(hotelBuscado); //Llamamos al service que tiene el metodo que valida si un Hotel existe.


        HotelDTO hotelToReserved = null;
        for (HotelDTO hotel : availableHotel) {
            if (hotel.getDestination().equalsIgnoreCase(finalHotelReservationDTO.getHotelReservationDTO().getDestination())
                    && hotel.getDateFrom().equals(finalHotelReservationDTO.getHotelReservationDTO().getDateFrom())
                    && hotel.getDateTo().equals(finalHotelReservationDTO.getHotelReservationDTO().getDateTo())) {
                hotelToReserved = hotel;
                break;
            }
        } //De la lista obtenida, tomo el hotel que coincide con el que se desea reservar
        if (hotelToReserved == null) {
            throw new IllegalArgumentException("No se encontró ningún hotel que coincida con los criterios de reserva.");
        }

        Double amount = (hotelToReserved.getPriceForNight() * finalHotelReservationDTO.getHotelReservationDTO().getPeopleAmount()); //Calculo el total

        Double interest = this.calcInterest(amount, finalHotelReservationDTO.getHotelReservationDTO().getPaymentMethodDTO().getDues(),
                finalHotelReservationDTO.getHotelReservationDTO().getPaymentMethodDTO().getType());
        //Llamamos al método que calcula el interes dependiendo el tipo de pago seleccionado

        Double total = amount + interest;//Obtenemos el total final a pagar.


        TotalHotelReservationDTO totalHotelReservationDTO = new TotalHotelReservationDTO();
        totalHotelReservationDTO.setAmount(amount);
        totalHotelReservationDTO.setInterest(interest);
        totalHotelReservationDTO.setTotal(total);
        totalHotelReservationDTO.setFinalHotelReservation(finalHotelReservationDTO);
        totalHotelReservationDTO.setStatusCode(new StatusCodeDTO(201, "El proceso terminó satisfactoriamente"));
        //Creamos el tipo de respuesta que nos pedia en el Sprint2


        List<PeopleDTO> persDeReserva = finalHotelReservationDTO.getHotelReservationDTO().getPeopleDTO();
        List<People> persAGuardar = new ArrayList<>();
        for (PeopleDTO peoples : persDeReserva) {
            People person = modelMapper.map(peoples, People.class);
            persAGuardar.add(person);
            peopleRepository.save(person);
        } //Mapeo la lista de personas y guardo esa lista mapeada en mi DB


        PaymentMethodDTO metodoPago = finalHotelReservationDTO.getHotelReservationDTO().getPaymentMethodDTO();
        PaymentMethod metodoPagoAGuardar = modelMapper.map(metodoPago, PaymentMethod.class);
        paymentMethodRepository.save(metodoPagoAGuardar);//Mapeo el método de pago y guardo el metodo de pago mapeado.

        Random random = new Random();

        int[] diasPosibles = {10, 15, 20};
        int randomDay  = diasPosibles[random.nextInt(diasPosibles.length)];

        int randomMonth = random.nextInt(12 - 7 + 1) + 7;

        int year = 2024;

        LocalDate fechaCreacion = LocalDate.of(year, randomMonth, randomDay);
        //Creamos un Random para asignar fechas random en la creacion de una reserva.

        Hotel hotelExistente = hotelRepository.findByHotelCode(hotelToReserved.getHotelCode());
        if (hotelExistente == null) {
            throw new IllegalArgumentException("No se encontró el hotel a reservar.");
        }//Buscamos el hotel que se desea reservar
        hotelExistente.setReserved(true);//Al hotel obtenido lo actualizamos, pasa a ser Reservado.

        ReservarHotel reservaHotelCreada = new ReservarHotel();//Creamos el tipo de dato a guardar
        reservaHotelCreada.setPeopleAmount(finalHotelReservationDTO.getHotelReservationDTO().getPeopleAmount());//Cantidad de personas
        reservaHotelCreada.setPeople(persAGuardar);//Cantidad de personas de la reserva
        reservaHotelCreada.setPaymentMethod(metodoPagoAGuardar);//Metodo de pago de la reserva
        reservaHotelCreada.setHotel(hotelExistente);//El hotel reservado
        reservaHotelCreada.setCliente(clienteEncontrado);//Le asignamos al cliente la reserva
        reservaHotelCreada.setTotalAmount(total);//Le asignamos el total a la reserva
        reservaHotelCreada.setCreationDate(fechaCreacion);//Le asignamos la fecha de creacion de dicha reserva.
        hotelReservaRepository.save(reservaHotelCreada);//Guardamos nuestra reserva en la DB

        clienteEncontrado.setBookingQuantity(clienteEncontrado.getBookingQuantity()+1);//Le incrementamos al cliente la cantidad de reservas

        for (People person : persAGuardar) {
            person.setReservationHotel(reservaHotelCreada);
            peopleRepository.save(person);
        }//Le asignamos el id de la reserva a las personas que pertenecen a la reserva.

        return new ResponseDTO("Reserva de hotel dada de alta correctamente");
    }

    @Override
    public ResponseDTO updateReserva(Long id, FinalHotelReservationDTO finalHotelReservationDTO) {
        Optional<ReservarHotel> optionalReservaHotel = hotelReservaRepository.findById(id);//Buscamos la reserva por ID en la DB

        if (optionalReservaHotel.isEmpty()) {
            throw new IllegalArgumentException("No se encontró la reserva a actualizar");
        }

        ReservarHotel reservaExistente = optionalReservaHotel.get();

        // Actualizar campos simples directamente
        reservaExistente.setPeopleAmount(finalHotelReservationDTO.getHotelReservationDTO().getPeopleAmount());

        // Actualizar método de pago (paymentMethod)
        PaymentMethodDTO metodoPagoDTO = finalHotelReservationDTO.getHotelReservationDTO().getPaymentMethodDTO();
        if (metodoPagoDTO != null) {
            // Obtener el método de pago existente de la reserva
            PaymentMethod metodoPagoExistente = reservaExistente.getPaymentMethod();

            // Verificar si hay un método de pago existente
            if (metodoPagoExistente != null) {
                // Actualizar los campos del método de pago existente con los del DTO
                modelMapper.map(metodoPagoDTO, metodoPagoExistente);
            } else {
                // Si no hay método de pago existente, mapear uno nuevo
                PaymentMethod metodoPago = modelMapper.map(metodoPagoDTO, PaymentMethod.class);
                reservaExistente.setPaymentMethod(metodoPago);
            }
        }
        // Guardar la reserva actualizada
        hotelReservaRepository.save(reservaExistente);

        return new ResponseDTO("Reserva actualizada correctamente");
    }

    @Override
    public ResponseDTO deleteReserva(Long id) {
        Optional<ReservarHotel> reservaABuscar = hotelReservaRepository.findById(id);//Buscamos si existe una reserva por ID en nuestra DB
        if(reservaABuscar.isEmpty()){
            return new ResponseDTO("No se encontro la reserva a eliminar");
        }
        ReservarHotel reservaAEliminar = reservaABuscar.get();
        Hotel hotelReservado = reservaAEliminar.getHotel();//Obtenemos el hotel de la reserva

        hotelReservaRepository.deleteById(id);//Eliminamos la reserva

        hotelReservado.setReserved(false);//Actualizamos el Hotel, ya que ahora pasa a NO estar reservado.
        hotelRepository.save(hotelReservado);//Guardamos el hotel actualizado

        return new ResponseDTO("Reserva eliminada con éxito");
    }

    //METODOS PARA REUTILIZAR
    @Override
    public Boolean reserveSaved(FinalHotelReservationDTO finalHotelReservationDTO) {
      String hotelCode = finalHotelReservationDTO.getHotelReservationDTO().getHotelCode();//Obtenemos el hotel de nuestra solicitud
      Hotel hotelEncontrado = hotelRepository.findByHotelCode(hotelCode);//Buscamos el hotel por hotelCode

      if (hotelEncontrado.getReserved() == true) {//Si está reservado lanza la excepcíon
          throw new IllegalArgumentException("Este hotel se encuentra reservado");
      }

        return false;
    }


    @Override//Calcula el interes dependiendo el tipo de pago y las cuotas seleccionadas por el cliente.
    public Double calcInterest(Double amount, Integer dues, String type) {

        if (type.equalsIgnoreCase("Debit") || type.equalsIgnoreCase("Credit")) {
            if (type.equalsIgnoreCase("Debit") && dues > 1) {
                throw new IllegalArgumentException("No puede pagar en cuotas con tarjeta de debito.");
            } else
                switch (dues) {
                    case 1:
                        return 0.0;
                    case 2, 3:
                        return amount * 0.05;
                    case 4, 5, 6:
                        return amount * 0.10;
                    case 7, 8, 9, 10, 11, 12:
                        return amount * 0.20;
                    default:
                        throw new IllegalArgumentException("Número de cuotas no válido.");
                }
        } else {
            throw new IllegalArgumentException("Tipo de pago no válido.");
        }

    }

    //Valida que la cantidad de personas coincida con el tipo de habitacion seleccionado
    public Boolean roomCapacity(HotelReservationDTO reservation) {
        Double people;


        switch (reservation.getRoomType()) {
            case "Single":
                people = 1D;
                break;
            case "Doble":
                people = 2D;
                break;
            case "Triple":
                people = 3D;
                break;
            case "Múltiple":
                people = 4D;
                break;
            default:
                people = 0D;
                break;
        }
        if (!people.equals(reservation.getPeopleAmount())) {
            throw new IllegalArgumentException("La cantidad de personas no coincide con el tipo de habitación.");
        } else if (people != reservation.getPeopleDTO().size()) {
            throw new IllegalArgumentException("El tipo de habitación seleccionada no coincide con la cantidad de personas que se alojarán en ella.");
        }
        return true;


    }

    @Override//Mapea ReservarHotel a FinalHotelReservationDTO
    public List<FinalHotelReservationDTO> mapearReservas(List<ReservarHotel> reservas) {
        List<FinalHotelReservationDTO> listAMotrar = new ArrayList<>();


        for (ReservarHotel reserva : reservas) { //Iteramos la lista de Reservas de la BBDD

            FinalHotelReservationDTO reservaFinal = new FinalHotelReservationDTO();

            Client clienteDTO = reserva.getCliente();
            if (clienteDTO != null) {
                reservaFinal.setUserName(clienteDTO.getUsername());
            } //Recuperamos el userName del cliente que realizo la reserva (obtenemos el primer campo del FinalHotelReservationDTO)

            HotelReservationDTO reservaGeneral = new HotelReservationDTO();
            //Inicializamos el segundo campo de FinalHotelReservationDTO (HotelReservatioDTO) y mapeamos todos los campos simples de HotelReservationDTO
            reservaGeneral.setHotelCode(reserva.getHotel().getHotelCode());
            reservaGeneral.setDateFrom(reserva.getHotel().getDateFrom());
            reservaGeneral.setDateTo(reserva.getHotel().getDateTo());
            reservaGeneral.setDestination(reserva.getHotel().getDestination());
            reservaGeneral.setRoomType(reserva.getHotel().getRoomType());
            reservaGeneral.setPeopleAmount(reserva.getPeopleAmount());


            PaymentMethod paymentMethodDeReserva = reserva.getPaymentMethod();
            PaymentMethodDTO pagoDTO = modelMapper.map(paymentMethodDeReserva, PaymentMethodDTO.class);
            //Mapeamos el Objeto PaymentMethod ==> PaymentMethodDTO
            reservaGeneral.setPaymentMethodDTO(pagoDTO); //le asignamos el campo a HotelReservationDTO

            List<People> peopleDeReserva = reserva.getPeople();
            List<PeopleDTO> peoplesDTO = new ArrayList<>();
            for (People peoples : peopleDeReserva) {
                PeopleDTO person = modelMapper.map(peoples, PeopleDTO.class);
                peoplesDTO.add(person);
            } //Mapeamos la lista de Peoples ==> peopleDTO
            reservaGeneral.setPeopleDTO(peoplesDTO); // Le asignamos la lista de peolesDTO al HotelReservationDTO

            reservaFinal.setHotelReservationDTO(reservaGeneral); //seteamos el HotelReservationDTO DEL FinalHotelReservationDTO con el HotelReservatioDTO creado
            listAMotrar.add(reservaFinal);
        } //guardo la lista de personas

        return listAMotrar;
    }

    @Override//Lista las reservas realizadas en un cierto día
    public List<ReservarHotel> listarReservasDia(LocalDate dia) {
        List<ReservarHotel> reservasListDia = hotelReservaRepository.findByDia(dia);
        return reservasListDia;
    }

    @Override//Lista las reservas realizadas en un cierto mes.
    public List<ReservarHotel> listarReservasMes(Integer mes) {
        List<ReservarHotel> reservasListMes = hotelReservaRepository.findByMes(mes);
        return reservasListMes;

    }


}
