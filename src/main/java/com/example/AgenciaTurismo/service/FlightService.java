package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.FlightDTO;
import com.example.AgenciaTurismo.dto.HotelDTO;
import com.example.AgenciaTurismo.repository.IFlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService implements IFlightService{

    @Autowired
    private IFlightRepository flightRepository;

    @Override
    public List<FlightDTO> listFlightsDTO() {
        return flightRepository.findAll().stream()
                .map(flight -> new FlightDTO(
                        flight.getFlightCode(),
                        flight.getOrigin(),
                        flight.getDestination(),
                        flight.getSeatType(),
                        flight.getPrice(),
                        flight.getDateFrom(),
                        flight.getDateTo()
                )).toList();
    }
}
