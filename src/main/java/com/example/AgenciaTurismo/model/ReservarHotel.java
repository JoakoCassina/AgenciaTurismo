package com.example.AgenciaTurismo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "reservar_hotels")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservarHotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "people_amount")
    private Double peopleAmount;

    @OneToMany(mappedBy = "reservationHotel", cascade = CascadeType.ALL)
    private List<People> people;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotels_id", unique = true)
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "paquete_turistico_id")
    private TouristPackage paqueteTuristico;

}
