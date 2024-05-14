package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.FlightDTO;
import com.example.AgenciaTurismo.dto.request.FinalReservationDTO;
import com.example.AgenciaTurismo.dto.request.FlightConsultDTO;
import com.example.AgenciaTurismo.dto.response.FlightAvailableDTO;
import com.example.AgenciaTurismo.dto.response.TotalReservationDTO;

import java.util.List;

public interface IFlightService {

    List<FlightDTO> listFlightsDTO();

    FlightAvailableDTO vuelosDisponibles(FlightConsultDTO flightConsultDTO);


    TotalReservationDTO calcularTotal(FinalReservationDTO finalReservationDTO);

}
