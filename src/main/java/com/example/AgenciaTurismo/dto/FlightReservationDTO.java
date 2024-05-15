package com.example.AgenciaTurismo.dto;

import com.example.AgenciaTurismo.dto.request.FlightConsultDTO;
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
public class FlightReservationDTO {
    @JsonProperty("date_from")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate DateFrom;
    @JsonProperty("date_to")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate DateTo;
    private String Origin;
    private String Destination;
    @JsonProperty("flight_number")
    private String flightNumber;
    private String seats;
    @JsonProperty("seat_type")
    private String seatType;
    @JsonProperty("people")
    private List<PeopleDTO> peopleDTO;
    @JsonProperty("payment_method")
    private PaymentMethodDTO paymentMethodDTO;



}