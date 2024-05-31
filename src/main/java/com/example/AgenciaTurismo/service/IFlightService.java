package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.FlightDTO;
import com.example.AgenciaTurismo.dto.FlightReservedDTO;
import com.example.AgenciaTurismo.dto.request.FinalFlightReservationDTO;
import com.example.AgenciaTurismo.dto.request.FlightConsultDTO;
import com.example.AgenciaTurismo.dto.response.FlightAvailableDTO;
import com.example.AgenciaTurismo.dto.response.ResponseDTO;
import com.example.AgenciaTurismo.dto.response.TotalFlightReservationDTO;

import java.util.List;

public interface IFlightService {

    List<FlightDTO> listFlightsDTO();

    FlightAvailableDTO vuelosDisponibles(FlightConsultDTO flightConsultDTO);

    Double calcInterest(Double totalPrice, Integer dues);

    TotalFlightReservationDTO reserved(FinalFlightReservationDTO finalFlightReservationDTO);

    Boolean reserveSaved(FinalFlightReservationDTO finalFlightReservationDTO);

    List<FlightReservedDTO> flightSaved();


    //CREATE
    ResponseDTO createFlight(FlightDTO flightDTO);

    //UPDATE
    ResponseDTO updateFlight(String flightCode, FlightDTO flightDTO);

    //DELETE
    ResponseDTO deleteFlight(String flightCode);
}
