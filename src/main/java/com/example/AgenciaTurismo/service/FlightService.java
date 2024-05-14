package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.FlightDTO;
import com.example.AgenciaTurismo.dto.HotelDTO;
import com.example.AgenciaTurismo.dto.request.FinalReservationDTO;
import com.example.AgenciaTurismo.dto.request.FlightConsultDTO;
import com.example.AgenciaTurismo.dto.response.FlightAvailableDTO;
import com.example.AgenciaTurismo.dto.response.TotalReservationDTO;
import com.example.AgenciaTurismo.model.Flight;
import com.example.AgenciaTurismo.repository.IFlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public FlightAvailableDTO vuelosDisponibles(FlightConsultDTO flightConsultDTO) {
        List<FlightDTO> listFlightDTO = listFlightsDTO();

        List<FlightDTO> availableFlight = new ArrayList<>();
        for (FlightDTO flight : listFlightDTO) {
            if (flight.getOrigin().equals(flightConsultDTO.getOrigin())
                    && flight.getDestination().equals(flightConsultDTO.getDestination())
                    && flight.getDateFrom().equals(flightConsultDTO.getDateFrom())
                    && flight.getDateTo().equals(flightConsultDTO.getDateTo())) {
                availableFlight.add(flight);
            }
        }
        FlightAvailableDTO flightAvailable = new FlightAvailableDTO();
        flightAvailable.setAvailableFlightDTO(availableFlight);

        return flightAvailable;
    }

    @Override
    public TotalReservationDTO calcularTotal(FinalReservationDTO finalReservationDTO) {
        return null;
    }


}
