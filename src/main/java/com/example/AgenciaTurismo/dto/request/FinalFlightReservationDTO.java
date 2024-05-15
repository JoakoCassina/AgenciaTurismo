package com.example.AgenciaTurismo.dto.request;


import com.example.AgenciaTurismo.dto.FlightReservationDTO;
import com.example.AgenciaTurismo.dto.PersonDetailsDTO;
import com.example.AgenciaTurismo.dto.StatusCodeDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinalFlightReservationDTO {
    @JsonProperty("user_name")
    private String userName;
    private double amount;
    private double interest;
    private double total;
    @JsonProperty("flight_reservation")
    private FlightReservationDTO FlightReservationDTO;
    private StatusCodeDTO statusCode;
}
