package com.example.AgenciaTurismo.dto.response;

import com.example.AgenciaTurismo.dto.request.FinalFlightReservationDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TotalFlightReservationDTO {
    private Double amount;
    private Double interest;
    private Double total;
    private FinalFlightReservationDTO finalFlightReservationDTO;
}
