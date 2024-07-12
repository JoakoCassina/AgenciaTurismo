package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.model.ReservarFlight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IFlightReservaRepository extends JpaRepository<ReservarFlight, Long> {

    @Query("SELECT r FROM ReservarFlight r WHERE r.cliente.id = :clientId")
    List<ReservarFlight> findByClientId(Long clientId);

}
