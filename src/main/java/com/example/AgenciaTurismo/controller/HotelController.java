package com.example.AgenciaTurismo.controller;

import com.example.AgenciaTurismo.dto.HotelDTO;
import com.example.AgenciaTurismo.dto.request.HotelConsultDTO;
import com.example.AgenciaTurismo.dto.response.ResponseDTO;
import com.example.AgenciaTurismo.service.IHotelService;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/hotels")
public class HotelController {

    @Autowired
    IHotelService service;

    @GetMapping
    public ResponseEntity<List<HotelDTO>> traerTodosLosHoteles() {
        return new ResponseEntity<>(service.listarHotels(), HttpStatus.OK);
    }

    //CREAR
    @PostMapping("/hotels/new")
    public ResponseEntity<ResponseDTO> crearHotel(@RequestBody HotelDTO hotelDTO) {
        return new ResponseEntity<>(service.createHotel(hotelDTO), HttpStatus.CREATED);
    }

    //UPDATE
    @PostMapping("/hotels/edit/{id}")
    public ResponseEntity<ResponseDTO> updateHotel(@PathVariable @NotNull Long id, @RequestBody HotelDTO hotelDTO) {
        return new ResponseEntity<>(service.updateHotel(id, hotelDTO), HttpStatus.OK);
    }

    //DELETE
    @DeleteMapping("hotels/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteHotel(@PathVariable @NotNull Long id) {
        return new ResponseEntity<>(service.deleteHotel(id), HttpStatus.OK);
    }
    //LISTADO DE HOTELES SEGUN FILTROS
    @GetMapping("/hotels-available")
    public ResponseEntity<?> hotelesDisponibles(@RequestParam (required = false) @Future(message = "La fecha de entrada debe ser en el futuro") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateFrom,
                                                @RequestParam (required = false) @Future(message = "La fecha de salida debe ser en el futuro") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateTo,
                                               @RequestParam (required = false) @NotBlank(message = "El destino no puede estar en blanco") String destination){
        HotelConsultDTO datos = new HotelConsultDTO(dateFrom, dateTo, destination);
        return new ResponseEntity<>(service.hotelesDisponibles(datos), HttpStatus.OK);
    }

}
