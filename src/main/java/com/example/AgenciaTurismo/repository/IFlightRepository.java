package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface IFlightRepository extends JpaRepository<Flight, Long> {

//    @Transactional
//    @Modifying
//    @Query("DELETE FROM Flight f WHERE f.id = :flightCode")
     void deleteByFlightCode(@Param("flightCode") String flightCode);
}
