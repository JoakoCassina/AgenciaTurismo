package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.FlightReservedDTO;
import com.example.AgenciaTurismo.dto.request.FinalFlightReservationDTO;
import com.example.AgenciaTurismo.dto.response.ResponseDTO;
import com.example.AgenciaTurismo.repository.IFlightReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightReservaService implements IFlightReservaService {
    @Autowired
    IFlightReservaRepository repository;


    @Override
    public List<FlightReservedDTO> listarReservas() {
        return List.of();
    }

    @Override
    public ResponseDTO createReserva(FinalFlightReservationDTO finalFlightReservationDTO) {
        return null;
    }

    @Override
    public ResponseDTO uptateReserva(Long id, FinalFlightReservationDTO finalFlightReservationDTO) {
        return null;
    }

    @Override
    public ResponseDTO deleteReserva(Long id) {
        return null;
    }
}
