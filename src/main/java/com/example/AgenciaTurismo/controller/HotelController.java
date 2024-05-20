package com.example.AgenciaTurismo.controller;

import com.example.AgenciaTurismo.dto.HotelDTO;
import com.example.AgenciaTurismo.dto.request.FinalHotelReservationDTO;
import com.example.AgenciaTurismo.dto.request.HotelConsultDTO;
import com.example.AgenciaTurismo.dto.response.ResponseDTO;
import com.example.AgenciaTurismo.dto.response.TotalHotelReservationDTO;
import com.example.AgenciaTurismo.service.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1")
public class HotelController {

    @Autowired
    private IHotelService hotelService;

    //US 0001:
    @GetMapping("/listHotels") //
    public ResponseEntity<?> listarHoteles(){
        return new ResponseEntity<>(hotelService.listHotelsDTO(), HttpStatus.OK);
    }

    //US 0002:
    @GetMapping("/hotels")
    public ResponseEntity<?> hotelesDisponibles(@DateTimeFormat(pattern = "dd-MM-yyyy") @RequestParam LocalDate dateFrom,
                                               @DateTimeFormat(pattern = "dd-MM-yyyy") @RequestParam LocalDate dateTo,
                                               @RequestParam String destination){
        HotelConsultDTO datos = new HotelConsultDTO(dateFrom, dateTo, destination);
        return new ResponseEntity<>(hotelService.hotelesDisponibles(datos), HttpStatus.OK);
    }

    //US 0003:
    @PostMapping("/booking")
    public TotalHotelReservationDTO reserved(@RequestBody FinalHotelReservationDTO finalHotelReservationDTO) {
        return hotelService.reserved(finalHotelReservationDTO);
    }

    //HOTELES RESERVADOS
    @GetMapping("/reservedHotels")
    public ResponseEntity<?> hotelsSaved() {
        return new ResponseEntity<>(hotelService.hotelSaved(), HttpStatus.OK);
    }

    //CREATE
    @PostMapping("/createHotel")
    public ResponseEntity<ResponseDTO> createHotel(@RequestBody HotelDTO hotelDTO) {

        return new ResponseEntity<>(hotelService.createHotel(hotelDTO), HttpStatus.CREATED);
    }
    //UPDATE
    @PutMapping("/updateHotel/{hotelCode}")
    public ResponseEntity<ResponseDTO> updateHotel(@PathVariable String hotelCode, @RequestBody HotelDTO hotelDTO) {

        return new ResponseEntity<>(hotelService.updateHotel(hotelDTO), HttpStatus.OK);
    }

    //DELETE
    @DeleteMapping("/deleteHotel/{hotelCode}")
    public ResponseEntity<ResponseDTO> deleteHotel(@PathVariable String hotelCode) {
        return new ResponseEntity<>(hotelService.deleteHotel(hotelCode), HttpStatus.OK);
    }
}

