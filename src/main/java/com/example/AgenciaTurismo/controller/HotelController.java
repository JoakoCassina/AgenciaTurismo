package com.example.AgenciaTurismo.controller;

import com.example.AgenciaTurismo.dto.HotelDTO;
import com.example.AgenciaTurismo.dto.request.FinalHotelReservationDTO;
import com.example.AgenciaTurismo.dto.request.HotelConsultDTO;
import com.example.AgenciaTurismo.dto.response.ResponseDTO;
import com.example.AgenciaTurismo.dto.response.TotalHotelReservationDTO;
import com.example.AgenciaTurismo.service.IHotelService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1")
@Validated
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
    public ResponseEntity<?> hotelesDisponibles(@RequestParam  @Future(message = "La fecha de entrada debe ser en el futuro") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateFrom,
                                                @RequestParam @Future(message = "La fecha de salida debe ser en el futuro") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateTo,
                                               @RequestParam @NotBlank(message = "El destino no puede estar en blanco") String destination){
        HotelConsultDTO datos = new HotelConsultDTO(dateFrom, dateTo, destination);
        return new ResponseEntity<>(hotelService.hotelesDisponibles(datos), HttpStatus.OK);
    }

    //US 0003:
    @PostMapping("/booking")
    public ResponseEntity<?> reserved(@RequestBody @Valid FinalHotelReservationDTO finalHotelReservationDTO) {
        return new ResponseEntity<>(hotelService.reserved(finalHotelReservationDTO), HttpStatus.CREATED);
    }

    //HOTELES RESERVADOS
    @GetMapping("/reservedHotels")
    public ResponseEntity<?> hotelsSaved() {
        return new ResponseEntity<>(hotelService.hotelSaved(), HttpStatus.OK);
    }

    //CREATE
    @PostMapping("/createHotel")
    public ResponseEntity<ResponseDTO> createHotel(@RequestBody @Valid HotelDTO hotelDTO) {
        return new ResponseEntity<>(hotelService.createHotel(hotelDTO), HttpStatus.CREATED);
    }

    //UPDATE
    @PutMapping("/updateHotel/{id}")
    public ResponseEntity<ResponseDTO> updateHotel(@PathVariable @NotNull Long id, @RequestBody HotelDTO hotelDTO) {

        return new ResponseEntity<>(hotelService.updateHotel(id, hotelDTO), HttpStatus.OK);
    }

    //DELETE
    @DeleteMapping("/deleteHotel/{hotelCode}")
    public ResponseEntity<ResponseDTO> deleteHotel(@PathVariable @NotNull String hotelCode) {
        return new ResponseEntity<>(hotelService.deleteHotel(hotelCode), HttpStatus.OK);
    }
}

