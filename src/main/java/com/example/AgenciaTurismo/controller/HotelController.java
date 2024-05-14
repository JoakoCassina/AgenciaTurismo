package com.example.AgenciaTurismo.controller;

import com.example.AgenciaTurismo.dto.request.FlightConsultDTO;
import com.example.AgenciaTurismo.dto.request.HotelConsultDTO;
import com.example.AgenciaTurismo.service.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1")
public class HotelController {

    @Autowired
    private IHotelService hotelService;


    @GetMapping("/listarHoteles") //
    public ResponseEntity<?> listarHoteles(){
        return new ResponseEntity<>(hotelService.listHotelsDTO(), HttpStatus.OK);
    }

    @GetMapping("/hotels")
    public ResponseEntity<?> hotelesDisponibles(@DateTimeFormat(pattern = "dd-MM-yyyy") @RequestParam LocalDate dateFrom,
                                               @DateTimeFormat(pattern = "dd-MM-yyyy") @RequestParam LocalDate dateTo,
                                               @RequestParam String destination){
        HotelConsultDTO datos = new HotelConsultDTO(dateFrom, dateTo, destination);
        return new ResponseEntity<>(hotelService.hotelesDisponibles(datos), HttpStatus.OK);
    }

}
