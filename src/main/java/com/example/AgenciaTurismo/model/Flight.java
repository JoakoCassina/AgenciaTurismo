package com.example.AgenciaTurismo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flight {
    private String flightCode;
    private String origin;
    private String destination;
    private String seatType;
    private Integer price;
    private LocalDate dateFrom;
    private LocalDate dateTo;
}
