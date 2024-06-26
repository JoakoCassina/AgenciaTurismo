package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.FlightDTO;
import com.example.AgenciaTurismo.dto.FlightReservedDTO;
import com.example.AgenciaTurismo.dto.request.FinalFlightReservationDTO;
import com.example.AgenciaTurismo.dto.request.FlightConsultDTO;
import com.example.AgenciaTurismo.dto.response.FlightAvailableDTO;
import com.example.AgenciaTurismo.dto.response.ResponseDTO;
import com.example.AgenciaTurismo.dto.response.TotalFlightReservationDTO;

import java.time.LocalDate;
import java.util.List;

public interface IFlightService {

    List<FlightDTO> listFlightsDTO();

    FlightAvailableDTO vuelosDisponibles(FlightConsultDTO flightConsultDTO);

    TotalFlightReservationDTO reserved(FinalFlightReservationDTO finalFlightReservationDTO);


    //CREATE
    ResponseDTO createFlight(FlightDTO flightDTO);

    //UPDATE
    ResponseDTO updateFlight(String flightCode, FlightDTO flightDTO);

    //DELETE
    ResponseDTO deleteFlight(String flightCode);


    //METODOS PARA VALIDAR
    Boolean reserveSaved(FinalFlightReservationDTO finalFlightReservationDTO);

    List<FlightReservedDTO> flightSaved();

    List<FlightDTO> validarVuelosDisponibles(FlightConsultDTO flightConsultDTO);

    Boolean flightValid(String origin, String destination);

    Boolean dateValid(LocalDate dateFrom, LocalDate dateTo);

    Double calcInterest(Double totalPrice, Integer dues, String type);
}
