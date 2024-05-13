package com.example.AgenciaTurismo.repository;



import com.example.AgenciaTurismo.model.Flight;

import java.util.List;

public interface IFlightRepository {
    public List<Flight> findAll();
}
