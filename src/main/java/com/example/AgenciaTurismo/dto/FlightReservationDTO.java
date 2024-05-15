package com.example.AgenciaTurismo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightReservationDTO {
    @JsonProperty("date_from")
    private LocalDate DateFrom;
    @JsonProperty("date_to")
    private LocalDate DateTo;
    private String Origin;
    private String Destination;
    @JsonProperty("flight_number")
    private String flightNumber;
    private Double seats;
    @JsonProperty("seat_type")
    private String seatType;
    @JsonProperty("people")
    private List<PeopleDTO> peopleDTO;
    @JsonProperty("payment_method")
    private PaymentMethodDTO paymentMethodDTO;

}