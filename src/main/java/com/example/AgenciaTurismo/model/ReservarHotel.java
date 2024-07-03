package com.example.AgenciaTurismo.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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

    @Column(name = "date_from")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateFrom;

    @Column(name = "date_to")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateTo;

    private String destination;

    @Column(name = "hotel_code")
    private String hotelCode;

    @Column(name = "people_amount")
    private Double peopleAmount;

    @Column(name = "room_type")
    private String roomType;

    @OneToMany(mappedBy = "reservationHotel")
    private List<People> people;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "paymentmethod_id")
    private PaymentMethod paymentMethod;

//    @OneToOne(mappedBy = "reservation")
//    private Hotel hotel;
}
