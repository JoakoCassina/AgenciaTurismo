package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.TouristicPackageDTO;
import com.example.AgenciaTurismo.dto.response.ResponseDTO;
import com.example.AgenciaTurismo.model.Client;
import com.example.AgenciaTurismo.model.ReservarFlight;
import com.example.AgenciaTurismo.model.ReservarHotel;
import com.example.AgenciaTurismo.model.TouristPackage;
import com.example.AgenciaTurismo.repository.IClientRepository;
import com.example.AgenciaTurismo.repository.IFlightReservaRepository;
import com.example.AgenciaTurismo.repository.IHotelReservaRepository;
import com.example.AgenciaTurismo.repository.ITouristicPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TouristicPackageService implements ITouristicPackageService{
    @Autowired
    ITouristicPackageRepository touristicPackageRepository;

    @Autowired
    IClientRepository clientRepository;
    @Autowired
    IHotelReservaRepository hotelReservaRepository;
    @Autowired
    IFlightReservaRepository flightReservaRepository;


    public List<Client> listarClientes(){
        return clientRepository.findAll();
    }


    @Override//Creamos un paquete turistico
    public ResponseDTO createPackageHotel(TouristicPackageDTO paquete) {
        Long clienteABuscar = paquete.getClienteId();//Obtenemos el ID  del cliente
        Long reservaDeClienteABuscar = paquete.getListaReservation().getId1(); //Obtenemos el ID de la reserva de hotel con la cual vamos armar un paquete Turistico
        Long segundaReservaDeClienteABuscar = paquete.getListaReservation().getId2();//Obtenemos otro segundo ID de la reserva de hotel con la cual vamos armar un paquete Turistico

        Optional<Client> clienteParaPaquete = clientRepository.findById(paquete.getClienteId());//Buscamos si el cliente existe en nuestra DB
        Client clienteEncontrado  = clienteParaPaquete.get();

        List<ReservarHotel> reservasDelClienteHotel = hotelReservaRepository.findByClientId(clienteABuscar);//Listamos todas las reservas que estan a nombre de un cliente (con el id que nos pasaron)
        Double totalAmountReserva1 = null;
        Double totalAmountReserva2 = null;

        // Iterar sobre las reservas del cliente para encontrar los totalAmounts de las reservas específicas
        for (ReservarHotel reserva : reservasDelClienteHotel) {
            Long idReserva = reserva.getId();//Obtenemos el ID de las reservas de un clliente
            Double totalAmount = reserva.getTotalAmount(); //Obtenemos el total amount de las reservas

            if (idReserva.equals(reservaDeClienteABuscar)) {//Si el ID de las reservas coincide con alguno de los recibimos para armar el paquete suma el Total.
                totalAmountReserva1 = totalAmount;
            } else if (idReserva.equals(segundaReservaDeClienteABuscar)) {
                totalAmountReserva2 = totalAmount;
            }
        }
        //Si los totalAmountReserva tiene un valor quiere decir que las Reservas del cliente existen y se puede pasar a crear el paquete turistico.
            if (totalAmountReserva1 != null && totalAmountReserva2 != null) {
                // Sumamos los totalAmounts y le realizamos el descuento del 10%
                Double total = ((totalAmountReserva1 + totalAmountReserva2) * 0.90);
                // Devolver el total como parte de la respuesta
                TouristPackage paqueteTuristico = new TouristPackage();//Armamos el tipo de respuesta esperado
                paqueteTuristico.setName(paquete.getName());//Nombre del paquete
                paqueteTuristico.setCreationDate(paquete.getCreationDate());//Fecha de creacion del paquete
                paqueteTuristico.setClientId(clienteEncontrado.getId());//le asignamos el id del cleinte al paquete turistico.
                touristicPackageRepository.save(paqueteTuristico);//Guardamos en nuestra DB el paquete Turistico.

                Optional<ReservarHotel> reservaDePaquete = hotelReservaRepository.findById(reservaDeClienteABuscar);
                ReservarHotel reservaEncontrada = reservaDePaquete.get();
                reservaEncontrada.setPaqueteTuristico(paqueteTuristico);
                hotelReservaRepository.save(reservaEncontrada);//Le asignamos el id del paquete tursitico a una de las reservas encontradas
                Optional<ReservarHotel> reservaDePaqueteDos = hotelReservaRepository.findById(segundaReservaDeClienteABuscar);
                ReservarHotel reservaEncontradaDos = reservaDePaqueteDos.get();
                reservaEncontradaDos.setPaqueteTuristico(paqueteTuristico);
                hotelReservaRepository.save(reservaEncontradaDos);//Le asignamos el id del paquete tursitico a la segunda reserva encontrada


                return new ResponseDTO("Paquete Turistico de hoteles creado correctamente. Total del paquete Turístico: " + total);
            }
        throw new IllegalArgumentException("No se encontraron todas las reservas especificadas para el cliente.");
    }


    @Override//Metodo para crear un paquete tursitico con 2 reservas de vuelos.
    public ResponseDTO createPackageVuelo(TouristicPackageDTO paquete) {
        Long clienteABuscar = paquete.getClienteId();
        Long reservaDeClienteABuscar = paquete.getListaReservation().getId1();
        Long segundaReservaDeClienteABuscar = paquete.getListaReservation().getId2();

        Optional<Client> clienteParaPaquete = clientRepository.findById(paquete.getClienteId());
        Client clienteEncontrado  = clienteParaPaquete.get();

        List<ReservarFlight> reservasDelClienteVuelo = flightReservaRepository.findByClientId(clienteABuscar);

        Double totalAmountReserva1 = null;
        Double totalAmountReserva2 = null;

        for (ReservarFlight reserva : reservasDelClienteVuelo) {
            Long idReserva = reserva.getId();
            Double totalAmount = reserva.getTotalAmount();

            if (idReserva.equals(reservaDeClienteABuscar)) {
                totalAmountReserva1 = totalAmount;
            } else if (idReserva.equals(segundaReservaDeClienteABuscar)) {
                totalAmountReserva2 = totalAmount;
            }
            if (totalAmountReserva1 != null && totalAmountReserva2 != null) {
                break;
            }
        }
        if (totalAmountReserva1 != null && totalAmountReserva2 != null) {
            // Sumar los totalAmounts
            Double total = ((totalAmountReserva1 + totalAmountReserva2) * 0.90);
            // Devolver el total como parte de la respuesta
            TouristPackage paqueteTuristico = new TouristPackage();
            paqueteTuristico.setName(paquete.getName());
            paqueteTuristico.setCreationDate(paquete.getCreationDate());
            paqueteTuristico.setClientId(clienteEncontrado.getId());
            touristicPackageRepository.save(paqueteTuristico);

            Optional<ReservarFlight> reservaDePaquete = flightReservaRepository.findById(reservaDeClienteABuscar);
            ReservarFlight reservaEncontrada = reservaDePaquete.get();
            reservaEncontrada.setPaqueteTuristico(paqueteTuristico);
            flightReservaRepository.save(reservaEncontrada);
            Optional<ReservarFlight> reservaDePaqueteDos = flightReservaRepository.findById(segundaReservaDeClienteABuscar);
            ReservarFlight reservaEncontradaDos = reservaDePaqueteDos.get();
            reservaEncontradaDos.setPaqueteTuristico(paqueteTuristico);
            flightReservaRepository.save(reservaEncontradaDos);
            return new ResponseDTO("Paquete Turistico de vuelos creado correctamente. Total del paquete Turístico: " + total);
        }
        throw new IllegalArgumentException("No se encontraron todas las reservas especificadas para el cliente.");
    }
    }


