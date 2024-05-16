package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.FlightDTO;
import com.example.AgenciaTurismo.dto.request.FinalFlightReservationDTO;
import com.example.AgenciaTurismo.dto.request.FlightConsultDTO;
import com.example.AgenciaTurismo.dto.response.FlightAvailableDTO;
import com.example.AgenciaTurismo.dto.response.StatusCodeDTO;
import com.example.AgenciaTurismo.dto.response.TotalFlightReservationDTO;
import com.example.AgenciaTurismo.exception.InvalidReservationException;
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

        if (availableFlight.isEmpty()) {
            throw new InvalidReservationException("No hay vuelos disponibles para las fechas y la ruta especificadas.");
        }

        FlightAvailableDTO flightAvailable = new FlightAvailableDTO();
        flightAvailable.setAvailableFlightDTO(availableFlight);

        return flightAvailable;
    }


    @Override
    public Double calcInterest(Double priceTotal, Integer dues) {
        switch (dues) {
            case 1:
                return 0.0;
            case 3:
                return priceTotal * 0.05;
            case 6:
                return priceTotal * 0.15;
            case 12:
                return priceTotal * 0.30;
            default:
                throw new InvalidReservationException("Número de cuotas no válido.");
        }
    }


    @Override
    public TotalFlightReservationDTO reserved(FinalFlightReservationDTO finalFlightReservationDTO) {

        List<FlightDTO> listFlightDTO = listFlightsDTO();

        FlightDTO flightToReserved = null;
        for (FlightDTO flight : listFlightDTO) {
            if (flight.getOrigin().equalsIgnoreCase(finalFlightReservationDTO.getFlightReservationDTO().getOrigin())
                    && flight.getDestination().equalsIgnoreCase(finalFlightReservationDTO.getFlightReservationDTO().getDestination())
                    && flight.getDateFrom().equals(finalFlightReservationDTO.getFlightReservationDTO().getDateFrom())
                    && flight.getDateTo().equals(finalFlightReservationDTO.getFlightReservationDTO().getDateTo())) {
                flightToReserved = flight;
                break;
            }
        }
        if (flightToReserved == null) {
            throw new InvalidReservationException("No se encontró ningún vuelo que coincida con los criterios de reserva.");
        }

        Double amount = flightToReserved.getPrice() * finalFlightReservationDTO.getFlightReservationDTO().getSeats();

        Double interest = calcInterest(amount, finalFlightReservationDTO.getFlightReservationDTO().getPaymentMethodDTO().getDues());

        Double priceFinal = amount + interest;

        TotalFlightReservationDTO totalFlightReservationDTO = new TotalFlightReservationDTO();
        totalFlightReservationDTO.setAmount(amount);
        totalFlightReservationDTO.setInterest(interest);
        totalFlightReservationDTO.setTotal(priceFinal);
        totalFlightReservationDTO.setFinalFlightReservationDTO(finalFlightReservationDTO);
        totalFlightReservationDTO.setStatusCode(new StatusCodeDTO(201, "El proceso terminó satisfactoriamente"));
        return totalFlightReservationDTO;
    }



}
