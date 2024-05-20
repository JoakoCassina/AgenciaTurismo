package com.example.AgenciaTurismo.controller;

import com.example.AgenciaTurismo.dto.FlightDTO;
import com.example.AgenciaTurismo.dto.request.FinalFlightReservationDTO;
import com.example.AgenciaTurismo.dto.request.FlightConsultDTO;
import com.example.AgenciaTurismo.dto.response.ResponseDTO;
import com.example.AgenciaTurismo.dto.response.TotalFlightReservationDTO;
import com.example.AgenciaTurismo.service.IFlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1")
public class FlightController {

    @Autowired
    private IFlightService flightService;

    //US 0004:
    @GetMapping("/listFlights") //
    public ResponseEntity<?> listFlightsDTO(){
        return new ResponseEntity<>(flightService.listFlightsDTO(), HttpStatus.OK);
    }

    //US 0005:
    @GetMapping("/flights")
    public ResponseEntity<?> vuelosDisponibles(@DateTimeFormat(pattern = "dd-MM-yyyy") @RequestParam LocalDate dateFrom,
                                               @DateTimeFormat(pattern = "dd-MM-yyyy") @RequestParam LocalDate dateTo,
                                               @RequestParam String origin,
                                               @RequestParam String destination){
        FlightConsultDTO datos = new FlightConsultDTO(dateFrom, dateTo, origin, destination);
        return new ResponseEntity<>(flightService.vuelosDisponibles(datos), HttpStatus.OK);
    }

    //US 0006
    @PostMapping("/flight-reservation")
    public TotalFlightReservationDTO reserveFlight(@RequestBody FinalFlightReservationDTO finalFlightReservationDTO) {
        return flightService.reserved(finalFlightReservationDTO);
    }
    //VUELOS RESERVADOS
    @GetMapping("/reservedFlights")
    public ResponseEntity<?> flightsSaved() {
        return new ResponseEntity<>(flightService.flightSaved(), HttpStatus.OK);
    }

    //CREATE
    @PostMapping("/createFlight")
    public ResponseEntity<ResponseDTO> createFlight(@RequestBody FlightDTO flightDTO) {

        return new ResponseEntity<>(flightService.createFlight(flightDTO), HttpStatus.CREATED);
    }

    //UPDATE
    @PutMapping("/updateFlight/{flightCode}")
    public ResponseEntity<ResponseDTO> updateFlight(@PathVariable String flightCode, @RequestBody FlightDTO flightDTO) {

        return new ResponseEntity<>(flightService.updateFlight(flightDTO), HttpStatus.OK);
    }

    //DELETE
    @DeleteMapping("/deleteFlight/{flightCode}")
    public ResponseEntity<ResponseDTO> deleteFlight(@PathVariable String flightCode) {
        return new ResponseEntity<>(flightService.deleteFlight(flightCode), HttpStatus.OK);
    }

}
