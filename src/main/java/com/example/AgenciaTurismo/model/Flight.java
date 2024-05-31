package com.example.AgenciaTurismo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flight {
    private String flightCode;
    private String origin;
    private String destination;
    private String seatType;
    private Integer price;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateFrom;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateTo;
}
