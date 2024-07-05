package com.example.AgenciaTurismo.service;


import com.example.AgenciaTurismo.dto.FlightReservedDTO;
import com.example.AgenciaTurismo.dto.request.FinalFlightReservationDTO;
import com.example.AgenciaTurismo.dto.response.ResponseDTO;
import com.example.AgenciaTurismo.dto.response.TotalFlightReservationDTO;

import java.util.List;

public interface IFlightReservaService {
    List<FlightReservedDTO> listarReservas();
    ResponseDTO createReserva(FinalFlightReservationDTO finalFlightReservationDTO);
    ResponseDTO uptateReserva(Long id, FinalFlightReservationDTO finalFlightReservationDTO);
    ResponseDTO deleteReserva(Long id);
}
