package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.FlightDTO;
import com.example.AgenciaTurismo.dto.response.ResponseDTO;
import com.example.AgenciaTurismo.model.Flight;

import java.util.List;

public interface IJpaFlightService {

    List<Flight> listarFlight();

    // CREATE
    ResponseDTO createFlight(FlightDTO flightDTO);

    // UPDATE
    ResponseDTO updateFlight(Long id, FlightDTO flightDTO);
    //DELETE
    ResponseDTO deleteFlight (Long id);



}
