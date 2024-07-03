package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntHotelRepository extends JpaRepository<Hotel, Long> {
}
