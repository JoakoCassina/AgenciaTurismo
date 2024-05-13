package com.example.AgenciaTurismo.controller;

import com.example.AgenciaTurismo.service.IFlightService;
import com.example.AgenciaTurismo.service.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlightController {

    @Autowired
    private IFlightService flightService;

    @GetMapping("/listarVuelos") //
    public ResponseEntity<?> listFlightsDTO(){
        return new ResponseEntity<>(flightService.listFlightsDTO(), HttpStatus.OK);
    }

}
