package com.example.AgenciaTurismo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class flight_reservationDTO {
    @JsonProperty("date_from")
    private String DateFrom;
    @JsonProperty("date_to")
    private String DateTo;
    private String Origin;
    private String Destination;
    @JsonProperty("flight_number")
    private String flightNumber;
    private String seats;
    @JsonProperty("seat_type")
    private String seatType;
    private List<PeopleDTO> peopleDTO;
    private PaymentMethodDTO paymentMethodDTO;

}