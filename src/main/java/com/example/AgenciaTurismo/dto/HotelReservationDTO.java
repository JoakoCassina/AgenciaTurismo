package com.example.AgenciaTurismo.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelReservationDTO {
    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonProperty("date_from")
    private LocalDate dateFrom;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonProperty("date_to")
    private LocalDate dateTo;
    private String destination;
    @JsonProperty("hotel_code")
    private String hotelCode;
    @JsonProperty ("people_amount")
    private Double peopleAmount;
    @JsonProperty("room_type")
    private String roomType;
    @JsonProperty("people")
    private List<PeopleDTO> peopleDTO;
    @JsonProperty("payment_method")
    private PaymentMethodDTO paymentMethodDTO;
}
