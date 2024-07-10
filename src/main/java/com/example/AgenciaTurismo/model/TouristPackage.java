package com.example.AgenciaTurismo.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "paquete_turistico")

public class TouristPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "package_number")
    private Long packageNumber;


    @OneToMany(mappedBy = "paqueteTuristico", cascade = CascadeType.ALL)
    @Column(name = "reserva_hotel_id")
    private List<Long> reservaHotelId;

    @OneToMany(mappedBy = "paqueteTuristico", cascade = CascadeType.ALL)
    private List<Long> reservaFlightId;


}
