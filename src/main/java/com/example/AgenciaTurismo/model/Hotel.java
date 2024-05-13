package com.example.AgenciaTurismo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Date dateFrom;
    private Date dateTo;
    private Boolean reserved;

}
