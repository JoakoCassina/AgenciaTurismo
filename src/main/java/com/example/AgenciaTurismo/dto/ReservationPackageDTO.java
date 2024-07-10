package com.example.AgenciaTurismo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReservationPackageDTO {
    @JsonProperty("book_res_id1")
    private Long id1;
    @JsonProperty("book_res_id2")
    private Long id2;
}
