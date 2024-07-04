package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHotelRepository extends JpaRepository<Hotel, Long> {

//    @Query("DELETE FROM Hotel h WHERE h.id = :hotelCode")
//    void deleteByCode(@Param("hotelCode") String hotelCode);
}
