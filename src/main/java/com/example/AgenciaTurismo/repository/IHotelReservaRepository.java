package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.model.ReservarHotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IHotelReservaRepository extends JpaRepository<ReservarHotel, Long> {

    @Query ("SELECT r.totalAmount, r.id FROM ReservarHotel r WHERE r.cliente.id = :clientId")
    List<Object[]> findByClientId(Long clientId);

}
