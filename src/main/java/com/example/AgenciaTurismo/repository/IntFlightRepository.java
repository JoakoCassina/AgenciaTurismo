package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.model.Flight;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IntFlightRepository extends JpaRepository<Flight, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Flight f WHERE f.id = :flightCode")
    void deleteByCode(@Param("flightCode") String flightCode);
}
