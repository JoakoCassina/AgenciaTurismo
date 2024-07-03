package com.example.AgenciaTurismo.model;

import com.example.AgenciaTurismo.dto.PaymentMethodDTO;
import com.example.AgenciaTurismo.dto.PeopleDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;
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

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "date_from")
    private LocalDate dateFrom;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "date_to")
    private LocalDate dateTo;
    private String origin;
    private String destination;
    @Column(name = "flight_number")
    private String flightNumber;
    private Double seats;
    @Column(name = "seat_type")
    private String seatType;

    @OneToMany(mappedBy = "reservationFlight")
    private List<People> people;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "paymentmethod_id")
    private PaymentMethod paymentMethod;

//    @OneToOne(mappedBy = "reservation")
//    private Flight flights;
}
