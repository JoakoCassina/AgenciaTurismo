package com.example.AgenciaTurismo.service;


import com.example.AgenciaTurismo.dto.FlightDTO;
import com.example.AgenciaTurismo.dto.response.ResponseDTO;
import com.example.AgenciaTurismo.model.Flight;
import com.example.AgenciaTurismo.model.Hotel;
import com.example.AgenciaTurismo.repository.IntFlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JpaFlightService implements IJpaFlightService {

    @Autowired
    IntFlightRepository repository;

    @Override
    public List<Flight> listarFlight() {
        List<Flight> listFlight = repository.findAll();
        return listFlight;
    }


    @Override
    public ResponseDTO createFlight(FlightDTO flightDTO) {
        Flight flight = new Flight();
        flight.setFlightCode(flightDTO.getFlightCode());
        flight.setOrigin(flightDTO.getOrigin());
        flight.setDestination(flightDTO.getDestination());
        flight.setSeatType(flightDTO.getSeatType());
        flight.setPrice(flightDTO.getPrice());
        flight.setDateFrom(flightDTO.getDateFrom());
        flight.setDateTo(flightDTO.getDateTo());

        repository.save(flight);

        return new ResponseDTO("Vuelo creado con éxito");
    }

    @Override
    public ResponseDTO updateFlight(Long id, FlightDTO flightDTO) {

        if(!repository.existsById(id)){
            return new ResponseDTO("Vuelo no encontrado");
        }
        Flight flight = new Flight(
                id,
                flightDTO.getFlightCode(),
                flightDTO.getOrigin(),
                flightDTO.getDestination(),
                flightDTO.getSeatType(),
                flightDTO.getPrice(),
                flightDTO.getDateFrom(),
                flightDTO.getDateTo()
        );
        repository.save(flight);
        return new ResponseDTO("Vuelo actualizado con éxito");


    }
    @Override
    public ResponseDTO deleteFlight(Long id) {
        if(!repository.existsById(id)){
            return new ResponseDTO("Vuelo no encontrado");
        }

        repository.deleteById(id);
        return new ResponseDTO("Vuelo eliminado con éxito");
    }

    @Override
    public ResponseDTO eliminarPorCode(String flightCode) {
       repository.deleteByFlightCode(flightCode);
       return new ResponseDTO("Vuelo eliminado con éxito");
    }
}
