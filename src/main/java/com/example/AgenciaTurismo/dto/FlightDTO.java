package com.example.AgenciaTurismo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightDTO {

    @JsonProperty("flight_code")
    private String flightCode;
    private String origin;
    private String destination;
    @JsonProperty("seat_type")
    private String seatType;
    private Integer price;
    @JsonProperty("date_from")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateFrom;
    @JsonProperty("date_to")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateTo;

}
