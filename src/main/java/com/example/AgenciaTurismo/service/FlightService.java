package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.*;
import com.example.AgenciaTurismo.dto.request.FinalFlightReservationDTO;
import com.example.AgenciaTurismo.dto.request.FlightConsultDTO;
import com.example.AgenciaTurismo.dto.response.FlightAvailableDTO;
import com.example.AgenciaTurismo.dto.response.TotalFlightReservationDTO;
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
    public FinalFlightReservationDTO processFlightReservation(FinalFlightReservationDTO request) {

        //Calcular el total
        double amount = 4000.50;
        double interest = 4.5;
        double total = amount * (1 + (interest / 100));

        //Obtener detalles de las personas
        List<PersonDetailsDTO> personDetails = new ArrayList<>();
        for(PeopleDTO person : request.getFlightReservationDTO().getPeopleDTO()){
            PersonDetailsDTO details = new PersonDetailsDTO();
            details.setDni(person.getDni());
            details.setName(person.getName());
            details.setLastName(person.getLastName());
            details.setEmail(person.getEmail());
            details.setBirthDate(person.getBirthDate());
            personDetails.add(details);
        }

        //Construyo la respuesta
        FinalFlightReservationDTO response = new FinalFlightReservationDTO();
        response.setUserName(request.getUserName());
        response.setAmount(amount);
        response.setInterest(interest);
        response.setTotal(total);
        response.setFlightReservationDTO(request.getFlightReservationDTO());
        response.setStatusCode(new StatusCodeDTO(201, "El proceso terminó satisfactoriamente"));
        return response;
    }
}
