package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.model.Flight;
import com.example.AgenciaTurismo.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntFlightRepository extends JpaRepository<Flight, Long> {

//    @Query("DELETE FROM Hotel h WHERE h.id = :hotelCode")
//    void deleteByCode(@Param("hotelCode") String hotelCode);
}
