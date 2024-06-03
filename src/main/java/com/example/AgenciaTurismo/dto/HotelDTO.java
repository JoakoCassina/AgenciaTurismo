package com.example.AgenciaTurismo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelDTO {
    @NotBlank(message = "el codigo no puede ser nulo")
    @JsonProperty("hotel_code")
    private String hotelCode;
    @Size(min = 3, max = 20, message = "El nombre debe tener entre 3 y 20 caracteres")
    @NotBlank(message = "El nombre no puede ser nulo")
    @JsonProperty("hotel_name")
    private String hotelName;
    @NotNull(message = "el destino debe ser una ciudad")
    private String destination;
    @JsonProperty("room_type")
    private String roomType;
    @JsonProperty("price_for_night")
    private Integer priceForNight;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonProperty("date_from")
    private LocalDate dateFrom;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonProperty("date_to")
    private LocalDate dateTo;
    private Boolean reserved;

}
