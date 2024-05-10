package com.example.AgenciaTurismo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flight {
    private String flight_code;
    private String origin;
    private String destination;
    private String seat_type;
    private Integer price;
    private Date date_from;
    private Date date_to;
}
