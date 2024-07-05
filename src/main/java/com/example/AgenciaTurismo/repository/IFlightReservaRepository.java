package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.model.ReservarFlight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFlightReservaRepository extends JpaRepository<ReservarFlight, Long> {

}
