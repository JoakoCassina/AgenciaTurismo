package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.model.ReservarHotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHotelReservaRepository extends JpaRepository<ReservarHotel, Long> {

}
