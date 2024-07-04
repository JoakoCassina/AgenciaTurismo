package com.example.AgenciaTurismo.controller;

import com.example.AgenciaTurismo.dto.HotelDTO;
import com.example.AgenciaTurismo.dto.response.ResponseDTO;
import com.example.AgenciaTurismo.model.Hotel;
import com.example.AgenciaTurismo.service.IJpaHotelService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class JpaHotelController {

    @Autowired
    IJpaHotelService service;

    @GetMapping("/listarTodosLosHoteles")
    public ResponseEntity<List<Hotel>> traerTodosLosHoteles() {
        return new ResponseEntity<>(service.listarHotels(), HttpStatus.OK);
    }

    @PostMapping("/crearHotel")
    public ResponseEntity<ResponseDTO> crearHotel(@RequestBody HotelDTO hotelDTO) {
        return new ResponseEntity<>(service.createHotel(hotelDTO), HttpStatus.CREATED);
    }

    //UPDATE
    @PostMapping("/updateHotel/{id}")
    public ResponseEntity<ResponseDTO> updateHotel(@PathVariable @NotNull Long id, @RequestBody HotelDTO hotelDTO) {
        return new ResponseEntity<>(service.updateHotel(id, hotelDTO), HttpStatus.OK);
    }

    //DELETE
    @DeleteMapping("/deleteHoteles/{id}")
    public ResponseEntity<ResponseDTO> deleteHotel(@PathVariable @NotNull Long id) {
        return new ResponseEntity<>(service.deleteHotel(id), HttpStatus.OK);
    }
}
