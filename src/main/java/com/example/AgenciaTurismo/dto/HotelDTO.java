package com.example.AgenciaTurismo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelDTO {
    @JsonProperty("hotel_code")
    private String hotelCode;
    @JsonProperty("hotel_name")
    private String hotelName;
    private String destination;
    @JsonProperty("room_type")
    private String roomType;
    @JsonProperty("price_for_night")
    private Integer priceForNight;
    @JsonFormat()
    @JsonProperty("date_from")
    private Date dateFrom;
    @JsonFormat()
    @JsonProperty("date_to")
    private Date dateTo;
    private Boolean reserved;

}
