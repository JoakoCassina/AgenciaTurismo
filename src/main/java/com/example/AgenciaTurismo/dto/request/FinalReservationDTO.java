package com.example.AgenciaTurismo.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinalReservationDTO {
    private String userName;
    private FinalReservationDTO reservation;
}
