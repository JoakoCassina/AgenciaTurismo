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
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/flights")
public class JpaFlightController {

    @Autowired
    IJpaFlightService service;

    @GetMapping
    public ResponseEntity<List<Flight>> traerTodosLosVuelos() {
        return new ResponseEntity<>(service.listarFlight(), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<ResponseDTO> crearFlight(@RequestBody FlightDTO flightDTO) {
        return new ResponseEntity<>(service.createFlight(flightDTO), HttpStatus.CREATED);
    }

    //UPDATE
    @PostMapping("/edit/{id}")
    public ResponseEntity<ResponseDTO> updateFlight(@PathVariable @NotNull Long id, @RequestBody FlightDTO flightDTO) {
        return new ResponseEntity<>(service.updateFlight(id, flightDTO), HttpStatus.OK);
    }

    //DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteFlights(@PathVariable @NotNull Long id) {
        return new ResponseEntity<>(service.deleteFlight(id), HttpStatus.OK);
    }

    @DeleteMapping("/borrar/{flightCode}")
    public ResponseEntity<ResponseDTO> deleteFlights(@PathVariable @NotNull String flightCode) {
        try {
            ResponseDTO response = service.eliminarPorCode(flightCode);
            return ResponseEntity.ok(response); // Devuelve 200 OK si la eliminación fue exitosa
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build(); // Devuelve 404 Not Found si no se encontró el vuelo
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Devuelve 500 Internal Server Error en otros casos}
        }
    }
}
