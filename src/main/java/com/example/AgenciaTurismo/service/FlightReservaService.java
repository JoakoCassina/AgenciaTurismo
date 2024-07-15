package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.FlightDTO;
import com.example.AgenciaTurismo.dto.FlightReservationDTO;
import com.example.AgenciaTurismo.dto.PaymentMethodDTO;
import com.example.AgenciaTurismo.dto.PeopleDTO;
import com.example.AgenciaTurismo.dto.request.FinalFlightReservationDTO;
import com.example.AgenciaTurismo.dto.request.FlightConsultDTO;
import com.example.AgenciaTurismo.dto.response.ResponseDTO;
import com.example.AgenciaTurismo.dto.response.StatusCodeDTO;
import com.example.AgenciaTurismo.dto.response.TotalFlightReservationDTO;
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
public class FlightReservaService implements IFlightReservaService {
    @Autowired
    IFlightReservaRepository flightReservaRepository;

    @Autowired
    IPeopleRepository peopleRepository;
    @Autowired
    IPaymentMethodRepository paymentMethodRepository;
    @Autowired
    IFlightRepository flightRepository;
    @Autowired
    IFlightService serviceFlight;

    @Autowired
    IClientRepository clientRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public List<FinalFlightReservationDTO> listarReservas() { //Listar Todas las reservasd de Vuelos
        List<ReservarFlight> reservasList = flightReservaRepository.findAll();
        return this.mapearReservas(reservasList);
    }


    @Override
    public ResponseDTO createReserva(FinalFlightReservationDTO finalFlightReservationDTO) { //Crear una reserva de vuelo

        Optional<Client> clienteExistente = clientRepository.findByUsername(finalFlightReservationDTO.getUserName());
        if(clienteExistente.isEmpty()) {
            return new ResponseDTO("Debes loguearte para poder crear una reserva!!"); //El ciente debe existir en nuestra base de datos!!
        }

        Client clienteEncontrado = clienteExistente.get(); //Guardo el cliente en una variable

        this.reserveSaved(finalFlightReservationDTO); //Llamamos al método que verifica si la reserva no existe (en caso de existir lanza una excepcíon)

        if(finalFlightReservationDTO.getFlightReservationDTO().getSeats() !=
                finalFlightReservationDTO.getFlightReservationDTO().getPeopleDTO().size()) {
            throw new IllegalArgumentException("La cantidad de asientos debe coincidir con la cantidad de personas");
        } //La cantidad de asientos a contratar debe coincidir con la cantidad de personas.

        FlightConsultDTO vueloBuscado = new FlightConsultDTO(
                finalFlightReservationDTO.getFlightReservationDTO().getDateFrom(),
                finalFlightReservationDTO.getFlightReservationDTO().getDateTo(),
                finalFlightReservationDTO.getFlightReservationDTO().getOrigin(),
                finalFlightReservationDTO.getFlightReservationDTO().getDestination()); //Creamos una variable de tipo de dato FlightConsultDTO

        List<FlightDTO> availableFlights = serviceFlight.validarVuelosDisponibles(vueloBuscado); //Llamamos al método que verifica si el existe un vuelo con esos datos.

        FlightDTO flightToReserved = null;
        for (FlightDTO flight : availableFlights) {
            if (flight.getOrigin().equalsIgnoreCase(finalFlightReservationDTO.getFlightReservationDTO().getOrigin())
                    && flight.getDestination().equalsIgnoreCase(finalFlightReservationDTO.getFlightReservationDTO().getDestination())
                    && flight.getDateFrom().equals(finalFlightReservationDTO.getFlightReservationDTO().getDateFrom())
                    && flight.getDateTo().equals(finalFlightReservationDTO.getFlightReservationDTO().getDateTo())) {
                flightToReserved = flight;
                break;
                } //El método anterior me debuelve una lista de vuelos encontrados (selecciono el pedido específicamente)
            }
            if (flightToReserved == null) {
                throw new IllegalArgumentException("No se encontró ningún vuelo que coincida con los criterios de reserva.");
            }//En caso de no encontrar el vuelo retorna una excepcíon

        Double amount = (flightToReserved.getPrice() * finalFlightReservationDTO.getFlightReservationDTO().getSeats());
            //Realizamos el calculo de precio del vuelo por la cantidad de personas especificadas.

        Double interest = this.calcInterest(amount, finalFlightReservationDTO.getFlightReservationDTO().getPaymentMethodDTO().getDues(),
                finalFlightReservationDTO.getFlightReservationDTO().getPaymentMethodDTO().getType());
        //Llamamos al método que calcula el interes del total dependiendo el tipo de pago seleccionado

        Double total = amount + interest; //Sacamos el total final

        TotalFlightReservationDTO totalFlightReservationDTO = new TotalFlightReservationDTO();
        totalFlightReservationDTO.setAmount(amount);
        totalFlightReservationDTO.setInterest(interest);
        totalFlightReservationDTO.setTotal(total);
        totalFlightReservationDTO.setFinalFlightReservationDTO(finalFlightReservationDTO);
        totalFlightReservationDTO.setStatusCode(new StatusCodeDTO(201, "El proceso terminó satisfactoriamente"));
        //Armamos el tipo de respuesta que nos pedia en el sprint 2. (Ahora no lo utilizamos)

        List<PeopleDTO> persDeReserva = finalFlightReservationDTO.getFlightReservationDTO().getPeopleDTO();
        List<People> persAGuardar = new ArrayList<>();
        for (PeopleDTO peoples : persDeReserva) {
            People person = modelMapper.map(peoples, People.class);
            persAGuardar.add(person);
            peopleRepository.save(person);
        }//Mapeo la lista de personas al tipo de dato que necesito para armar mi respuesta.
        //y guardo esas personas en mi base de datos

        PaymentMethodDTO metodoPago = finalFlightReservationDTO.getFlightReservationDTO().getPaymentMethodDTO();
        PaymentMethod metodoPagoAGuardar = modelMapper.map(metodoPago, PaymentMethod.class);
        paymentMethodRepository.save(metodoPagoAGuardar);
        //Mapeo el metodo de pago al tipo de dato que necesito.
        //Guardo el metodo de pago en mi base de datos

        Random random = new Random();

        int[] diasPosibles = {10, 15, 20};
        int randomDay  = diasPosibles[random.nextInt(diasPosibles.length)];

        int randomMonth = random.nextInt(12 - 7 + 1) + 7;

        int year = 2024;

        LocalDate fechaCreacion = LocalDate.of(year, randomMonth, randomDay);

        //En este caso armamos una fecha de creacion para las Reservas (Utilizamos un random)
        //Para poder variar en la fecha de creacion y poder jugar con el Postman


        Flight flightExistente = flightRepository.findByFlightCode(flightToReserved.getFlightCode());
        if (flightExistente == null){
            throw new IllegalArgumentException("No se encontró el vuelo a reservar.");

        }
        flightExistente.setReserved(true); //Al vuelo a reservar le modificamos el atributo reserva(ya que pasa a estar reservado)

        ReservarFlight reservaFlightCreada = new ReservarFlight(); //Inicializamos una variable con el tipo de Variable a guardar en base de datos.
        reservaFlightCreada.setSeats(finalFlightReservationDTO.getFlightReservationDTO().getSeats()); //GUARDAMOS//cantidad de asientos
        reservaFlightCreada.setPeople(persAGuardar);//personas que van a viajar en el vuelo
        reservaFlightCreada.setPaymentMethod(metodoPagoAGuardar);//metodo de pago de la reserva
        reservaFlightCreada.setCliente(clienteEncontrado);//Al cliente que realiza la reserva, se le adiciona la reserva.
        reservaFlightCreada.setFlight(flightExistente);//El vuelo pasa a ser reservado
        reservaFlightCreada.setTotalAmount(total);//El monto total de la reserva
        reservaFlightCreada.setCreationDate(fechaCreacion);//La fecha en la que se crea la reserva
        flightReservaRepository.save(reservaFlightCreada);//Se guarda la reserva en la Base de datos.

        clienteEncontrado.setBookingQuantity(clienteEncontrado.getBookingQuantity()+1);//Al cliente encontrado le incrementamos la cantidad de reservas

        for (People peoples : persAGuardar) {
            peoples.setReservationFlight(reservaFlightCreada);
             peopleRepository.save(peoples);
        }//Una vez guardada la Reserva se las asignamos a las personas (para saber a que reserva pertenecen dichas personas)
        return new ResponseDTO("Reserva de vuelo dada de alta correctamente");
    }

    @Override
    public ResponseDTO updateReserva(Long id, FinalFlightReservationDTO finalFlightReservationDTO){
        Optional<ReservarFlight> optionalReservaFlight = flightReservaRepository.findById(id); //Buscamos si existe la reserva por ID

        if (optionalReservaFlight.isEmpty()) {
            throw new IllegalArgumentException("No se encontró la reserva a actualizar");
        }//Si no encontro la reserva devuelve una excepcion.

        ReservarFlight reservaExistente = optionalReservaFlight.get();

        // Actualizar campos simples directamente
        reservaExistente.setSeats(finalFlightReservationDTO.getFlightReservationDTO().getSeats());

        // Actualizar método de pago (paymentMethod)
        PaymentMethodDTO metodoPagoDTO = finalFlightReservationDTO.getFlightReservationDTO().getPaymentMethodDTO();
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
        flightReservaRepository.save(reservaExistente);

        return new ResponseDTO("Reserva actualizada correctamente");
    }

    @Override
    public ResponseDTO deleteReserva(Long id) {
        Optional<ReservarFlight> reservaABuscar = flightReservaRepository.findById(id);//Buscamos si existe la reserva
        if (reservaABuscar.isEmpty()) {
            return new ResponseDTO("No se encontró la reserva a eliminar");//En caso de no encotrarla, devuelve este ResponseDTO
        }
        ReservarFlight reservaAEliminar = reservaABuscar.get();//Si encontró la reserva la guardamos.
        Flight flightReservado = reservaAEliminar.getFlight();//De la reserva tomamos el vuelo
        flightReservaRepository.deleteById(id);//Eliminamos la reserva

        flightReservado.setReserved(false);//al vuelo lo actualizamos, ya que al eliminarse la reserva vuelve a estar disponible.
        flightRepository.save(flightReservado);//guardamos el vuelo actualizado.
        return new ResponseDTO("Reserva eliminada con éxito");
    }

    @Override
    public Boolean reserveSaved(FinalFlightReservationDTO finalFlightReservationDTO) {
        String flightCode = finalFlightReservationDTO.getFlightReservationDTO().getFlightCode();//Obtenemos el flightCode del vuelo
        Flight flightEncontrado = flightRepository.findByFlightCode(flightCode);//Buscamos el vuelo por su flightCode

        if (flightEncontrado.getReserved() == true) {
            throw new IllegalArgumentException("Este vuelo se encuentra reservado"); //Si el vuelo ya se encuentra reservado lanza la excepcion
        }

        return false;
    }

    //METODOS PARA REUTILIZAR
    @Override
    public Double calcInterest(Double amount, Integer dues, String type) {//Tomamos el total de la reserva, su metodo de pago y las cantidad de cuotas.
        if (type.equalsIgnoreCase("Debit") || type.equalsIgnoreCase("Credit")) {//Solo  aceptamos Debito y/o Credito
            if (type.equalsIgnoreCase("Debit") && dues > 1) {
                throw new IllegalArgumentException("No puede pagar en cuotas con tarjeta de débito.");//Con debito solo puede pagarse en un pago
            } else
                switch (dues) { //Le asignamos diferente intereses dependiendo la cantidad de cuotas seleccionadas
                    case 1:
                        return 0.0;
                    case 2, 3:
                        return amount * 0.05;
                    case 4, 5, 6:
                        return amount * 0.10;
                    case 7, 8, 9, 10, 11, 12:
                        return amount * 0.20;
                    default:
                        throw new IllegalArgumentException("Número de cuotas no válido.");//Si pasan un nummero de cuotas que no aceptamos lanza su excepcion.
                }
        } else {
            throw new IllegalArgumentException("Tipo de pago no válido.");
        }
    }

    @Override
    public List<ReservarFlight> listarReservasDia(LocalDate dia) {
        List<ReservarFlight> reservasListDia = flightReservaRepository.findByDia(dia);
        return reservasListDia; //Metodo que te devuelve la lista de reservas generada tal día.
    }

    @Override
    public List<ReservarFlight> listarReservasMes(Integer mes) {
        List<ReservarFlight> reservasListMes = flightReservaRepository.findByMes(mes);
        //Metodo que te devuelve la lista de reservas generadas por mes.
        return reservasListMes;

    }

    public List<FinalFlightReservationDTO> mapearReservas(List<ReservarFlight> listReservas) {
        List<FinalFlightReservationDTO> listAMotrar = new ArrayList<>();

        for (ReservarFlight reserva : listReservas) { //Iteramos la lista de Reservas de la BBDD

            FinalFlightReservationDTO reservaFinal = new FinalFlightReservationDTO();

            Client clienteDTO = reserva.getCliente();
            if (clienteDTO != null) {
                reservaFinal.setUserName(clienteDTO.getUsername());
            } //Recuperamos el userName del cliente que realizo la reserva (obtenemos el primer campo del FinalFlightReservationDTO)

            FlightReservationDTO reservaGeneral = new FlightReservationDTO();
            //Inicializamos el segundo campo de FinalFlightReservationDTO (FlighReservatioDTO) y mapeamos todos los campos simples de FlighReservationDTO
            reservaGeneral.setFlightCode(reserva.getFlight().getFlightCode());
            reservaGeneral.setDateFrom(reserva.getFlight().getDateFrom());
            reservaGeneral.setDateTo(reserva.getFlight().getDateTo());
            reservaGeneral.setOrigin(reserva.getFlight().getOrigin());
            reservaGeneral.setDestination(reserva.getFlight().getDestination());
            reservaGeneral.setSeatType(reserva.getFlight().getSeatType());

            PaymentMethod paymentMethodDeReserva = reserva.getPaymentMethod();
            PaymentMethodDTO pagoDTO = modelMapper.map(paymentMethodDeReserva, PaymentMethodDTO.class);
            //Mapeamos el Objeto PaymentMethod ==> PaymentMethodDTO
            reservaGeneral.setPaymentMethodDTO(pagoDTO); //le asignamos el campo a FlightReservationDTO

            List<People> peopleDeReserva = reserva.getPeople();
            List<PeopleDTO> peoplesDTO = new ArrayList<>();
            for (People peoples : peopleDeReserva) {
                PeopleDTO person = modelMapper.map(peoples, PeopleDTO.class);
                peoplesDTO.add(person);
            } //Mapeamos la lista de Peoples ==> peopleDTO
            reservaGeneral.setPeopleDTO(peoplesDTO); // Le asignamos la lista de peolesDTO al FlightReservationDTO

            reservaFinal.setFlightReservationDTO(reservaGeneral); //seteamos el Flight ReservationDTO DEL FinalFlightReservationDTO con el FlightReservatioDTO creado
            listAMotrar.add(reservaFinal);
        } //guardo la lista de personas

        return listAMotrar;
    }
    //Metodo que mapea mi ReservarFlight (mi resrva de la Base de Datos) al tipo de respuesta que trabajamos(FinalFlightReservationDTO).

}
