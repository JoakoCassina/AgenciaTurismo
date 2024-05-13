package com.example.AgenciaTurismo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Hotel {
    private String hotelCode;
    private String hotelName;
    private String destination;
    private String roomType;
    private Integer priceForNight;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private Boolean reserved;

}
