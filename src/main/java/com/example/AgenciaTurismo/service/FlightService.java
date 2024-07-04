//package com.example.AgenciaTurismo.service;
//
//import com.example.AgenciaTurismo.dto.*;
//
//import com.example.AgenciaTurismo.dto.request.FinalFlightReservationDTO;
//import com.example.AgenciaTurismo.dto.request.FlightConsultDTO;
//import com.example.AgenciaTurismo.dto.response.FlightAvailableDTO;
//import com.example.AgenciaTurismo.dto.response.ResponseDTO;
//import com.example.AgenciaTurismo.dto.response.StatusCodeDTO;
//import com.example.AgenciaTurismo.dto.response.TotalFlightReservationDTO;
//import com.example.AgenciaTurismo.model.Flight;
//import com.example.AgenciaTurismo.repository.IFlightRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class FlightService implements IFlightService {
//
//    @Autowired
//    private IFlightRepository flightRepository;
//
//    private List<FlightReservedDTO> flightReserve = new ArrayList<>();
//
//
//    @Override
//    public List<FlightDTO> listFlightsDTO() {
//        return flightRepository.findAll().stream()
//                .map(flight -> new FlightDTO(
//                        flight.getFlightCode(),
//                        flight.getOrigin(),
//                        flight.getDestination(),
//                        flight.getSeatType(),
//                        flight.getPrice(),
//                        flight.getDateFrom(),
//                        flight.getDateTo()
//                )).toList();
//    }
//
//    @Override
//    public FlightAvailableDTO vuelosDisponibles(FlightConsultDTO flightConsultDTO) {
//        //LLAMAMOS AL METODO VALIDAR VUELOS DISPONIBLES
//        List<FlightDTO> availableFlight = this.validarVuelosDisponibles(flightConsultDTO);
//        //CREAMOS OBJ TIPO FLIGHTAVAILABLEDTO
//        FlightAvailableDTO flightAvailable = new FlightAvailableDTO();
//        flightAvailable.setAvailableFlightDTO(availableFlight);
//
//        return flightAvailable;
//    }
//
//    @Override
//    public TotalFlightReservationDTO reserved(FinalFlightReservationDTO finalFlightReservationDTO) {
//
//        //SE CREA VARIABLE PARA REDUCIR LINEAS DE CODIGO
//        FlightReservationDTO vueloReserva = finalFlightReservationDTO.getFlightReservationDTO();
//
//        if (this.reserveSaved(finalFlightReservationDTO)) {
//            throw new IllegalArgumentException("La reserva ya está realizada.");
//        }
//
//        FlightConsultDTO vueloBuscados = new FlightConsultDTO(
//                vueloReserva.getDateFrom(),
//                vueloReserva.getDateTo(),
//                vueloReserva.getOrigin(),
//                vueloReserva.getDestination());
//
//        List<FlightDTO> availableFlight = this.validarVuelosDisponibles(vueloBuscados);
//
//
//        FlightDTO flightToReserved = null;
//        for (FlightDTO flight : availableFlight) {
//            if(flight.getOrigin().equalsIgnoreCase(vueloReserva.getOrigin())
//                    && flight.getDestination().equalsIgnoreCase(vueloReserva.getDestination())
//                    && flight.getDateFrom().equals(vueloReserva.getDateFrom())
//                    && flight.getDateTo().equals(vueloReserva.getDateTo())) {
//                flightToReserved = flight;
//                break;
//            }
//        }
//        if (flightToReserved == null) {
//            throw new IllegalArgumentException("No se encontró ningún vuelo que coincida con los criterios de reserva.");
//        }
//
//        if (vueloReserva.getSeats() != vueloReserva.getPeopleDTO().size()) {
//            throw new IllegalArgumentException("La cantidad de asientos debe ser igual que la cantidad de personas.");
//        }
//
//        Double amount = flightToReserved.getPrice() * finalFlightReservationDTO.getFlightReservationDTO().getSeats();
//
//        Double interest = this.calcInterest(amount,
//                                        finalFlightReservationDTO.getFlightReservationDTO().getPaymentMethodDTO().getDues(),
//                                        finalFlightReservationDTO.getFlightReservationDTO().getPaymentMethodDTO().getType());
//
//        Double priceFinal = amount + interest;
//
//        TotalFlightReservationDTO totalFlightReservationDTO = new TotalFlightReservationDTO();
//        totalFlightReservationDTO.setAmount(amount);
//        totalFlightReservationDTO.setInterest(interest);
//        totalFlightReservationDTO.setTotal(priceFinal);
//        totalFlightReservationDTO.setFinalFlightReservationDTO(finalFlightReservationDTO);
//        totalFlightReservationDTO.setStatusCode(new StatusCodeDTO(201, "El proceso terminó satisfactoriamente"));
//
//        flightReserve.add(new FlightReservedDTO(totalFlightReservationDTO));
//
//        return totalFlightReservationDTO;
//    }
//
//                                    //CRUD
//    //CREATE
//    @Override
//    public ResponseDTO createFlight(FlightDTO flightDTO) {
//        Flight flight = new Flight();
//        flight.setFlightCode(flightDTO.getFlightCode());
//        flight.setOrigin(flightDTO.getOrigin());
//        flight.setDestination(flightDTO.getDestination());
//        flight.setSeatType(flightDTO.getSeatType());
//        flight.setPrice(flightDTO.getPrice());
//        flight.setDateFrom(flightDTO.getDateFrom());
//        flight.setDateTo(flightDTO.getDateTo());
//        flightRepository.save(flight);
//
//        return new ResponseDTO("Vuelo creado con éxito");
//    }
//
//    //UPDATE
//    @Override
//    public ResponseDTO updateFlight(Long id, FlightDTO flightDTO) {
//        Flight flight = new Flight(
//                id,
//                flightDTO.getFlightCode(),
//                flightDTO.getOrigin(),
//                flightDTO.getDestination(),
//                flightDTO.getSeatType(),
//                flightDTO.getPrice(),
//                flightDTO.getDateFrom(),
//                flightDTO.getDateTo()
//        );
//        Flight updatedFlight = flightRepository.update(flight);
//        if (updatedFlight != null) {
//            return new ResponseDTO("Vuelo actualizado con éxito");
//        } else {
//            return new ResponseDTO("Vuelo no encontrado");
//        }
//    }
//
//    //DELETE
//    @Override
//    public ResponseDTO deleteFlight(String flightCode) {
//        Flight flight = flightRepository.deleteFlight(flightCode);
//        if(flight != null){
//            return new ResponseDTO("Vuelo eliminado con éxito");
//        }else {
//            return new ResponseDTO("No se encontro el vuelo a eliminar");
//        }
//
//    }
//
//                            //METODOS PARA VALIDAR
//    @Override
//    public Boolean reserveSaved(FinalFlightReservationDTO finalFlightReservationDTO) {
//        for (FlightReservedDTO reservaGuardada : flightReserve){
//            FinalFlightReservationDTO reservaExistente = reservaGuardada.getFlightReserved().getFinalFlightReservationDTO();
//            if (reservaExistente.equals(finalFlightReservationDTO)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public List<FlightReservedDTO> flightSaved() {
//        return flightReserve;
//    }
//
//    @Override
//    public Double calcInterest(Double priceTotal, Integer dues, String type) {
//        if (type.equalsIgnoreCase("Debit") || type.equalsIgnoreCase("Credit")) {
//            if (type.equalsIgnoreCase("Debit") && dues > 1) {
//                throw new IllegalArgumentException("No puede pagar en cuotas con tarjeta de debito.");
//            } else
//                switch (dues) {
//                    case 1:
//                        return 0.0;
//                    case 2, 3:
//                        return priceTotal * 0.05;
//                    case 4, 5, 6:
//                        return priceTotal * 0.10;
//                    case 7, 8, 9, 10, 11, 12:
//                        return priceTotal * 0.20;
//                    default:
//                        throw new IllegalArgumentException("Número de cuotas no válido.");
//                }
//        } else {
//            throw new IllegalArgumentException("Tipo de pago no válido.");
//        }
//    }
//
//    //VALIDACION DE DESTINO Y ORIGEN DE VUELOS
//    @Override
//    public List<FlightDTO> validarVuelosDisponibles(FlightConsultDTO flightConsultDTO) {
//        //LLAMAMOS AL METODO QUE VALIDA DESTINO Y ORIGEN
//        this.flightValid(flightConsultDTO.getOrigin(), flightConsultDTO.getDestination());
//        //LLAMAMOS AL METODO QUE VALIDA FECHAS
//        this.dateValid(flightConsultDTO.getDateFrom(), flightConsultDTO.getDateTo());
//
//
//        List<FlightDTO> listFlightDTO = this.listFlightsDTO();
//        //CREAMOS UNA LISTA DE VUELOS QUE COINCIDAN CON LA FECHA
//        List<FlightDTO> availableFlight = new ArrayList<>();
//        for (FlightDTO flight : listFlightDTO) {
//            if (flight.getDateFrom().equals(flightConsultDTO.getDateFrom())
//                    && flight.getDateTo().equals(flightConsultDTO.getDateTo())) {
//                availableFlight.add(flight);
//            }
//        }
//
//        if (availableFlight.isEmpty()) {
//            throw new IllegalArgumentException("No hay vuelos disponibles para las fechas y la ruta especificadas.");
//        }
//
//        return availableFlight;
//    }
//
//    @Override
//    public Boolean flightValid(String origin, String destination) {
//        List<String> validOrigin = listFlightsDTO().stream()
//                .map(FlightDTO::getOrigin)
//                .toList();
//        List<String> validDestination = listFlightsDTO().stream()
//                .map(FlightDTO::getDestination)
//                .toList();
//        if (validOrigin.contains(origin) && !validDestination.contains(destination)){
//            throw new IllegalArgumentException("El destino elegido no existe");
//        }else if (!validOrigin.contains(origin) && validDestination.contains(destination)){
//            throw new IllegalArgumentException("El origen elegido no existe");
//        }else if (!validOrigin.contains(origin) && !validDestination.contains(destination)){
//            throw new IllegalArgumentException("El origen y el destino elegido no existe");
//        }else {
//            return true; //LOS DOS ARGUMENTOS SON CORRECTOS, DESTINO Y ORIGEN EXISTEN
//        }
//    }
//
//    @Override
//    public Boolean dateValid(LocalDate dateFrom, LocalDate dateTo) {
//        if (!dateFrom.isBefore(dateTo)) {
//            throw new IllegalArgumentException("La fecha de entrada debe ser menor a la de salida");
//        } else
//            return true;
//    }
//
//
//
//}
