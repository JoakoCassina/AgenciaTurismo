package com.example.AgenciaTurismo.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {


    @JsonProperty("top_number")
    private Integer topNumber;
    private Integer year;
    @JsonProperty("booking_quantity")
    private Integer bookingQuantity;
    @JsonProperty("total_amount")
    private Double totalAmount;
    private Integer clienteId;
    @JsonProperty("client_name")
    private String firstName;
    @JsonProperty("client_lastname")
    private String lastName;

}
