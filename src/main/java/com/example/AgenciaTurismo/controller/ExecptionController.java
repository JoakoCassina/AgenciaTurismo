package com.example.AgenciaTurismo.controller;

import com.example.AgenciaTurismo.exception.InvalidReservationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExecptionController {

    @ExceptionHandler(value = {InvalidReservationException.class})
    public ResponseEntity<Object> handleInvalidFlightReservationException(InvalidReservationException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getErrorMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleException(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error en la ejecución del servicio");
    }

}
