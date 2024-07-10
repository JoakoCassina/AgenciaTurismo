package com.example.AgenciaTurismo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "reservar_flights")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservarFlight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double seats;

    @OneToMany(mappedBy = "reservationFlight", cascade = CascadeType.ALL)
    private List<People> people;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL)
    private Flight flights;
}
