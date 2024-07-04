package com.example.AgenciaTurismo.controller;

import com.example.AgenciaTurismo.dto.FlightDTO;
import com.example.AgenciaTurismo.dto.response.ResponseDTO;
import com.example.AgenciaTurismo.model.Flight;
import com.example.AgenciaTurismo.service.IJpaFlightService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class JpaFlightController {

    @Autowired
    IJpaFlightService service;

    @GetMapping("/listarTodosLosVuelos")
    public ResponseEntity<List<Flight>> traerTodosLosVuelos() {
        return new ResponseEntity<>(service.listarFlight(), HttpStatus.OK);
    }

    @PostMapping("/crearFlight")
    public ResponseEntity<ResponseDTO> crearFlight(@RequestBody FlightDTO flightDTO) {
        return new ResponseEntity<>(service.createFlight(flightDTO), HttpStatus.CREATED);
    }

    //UPDATE
    @PostMapping("/updateFlight/{id}")
    public ResponseEntity<ResponseDTO> updateFlight(@PathVariable @NotNull Long id, @RequestBody FlightDTO flightDTO) {
        return new ResponseEntity<>(service.updateFlight(id, flightDTO), HttpStatus.OK);
    }

    //DELETE
    @DeleteMapping("/deleteFlights/{id}")
    public ResponseEntity<ResponseDTO> deleteFlights(@PathVariable @NotNull Long id) {
        return new ResponseEntity<>(service.deleteFlight(id), HttpStatus.OK);
    }
}
