package com.example.AgenciaTurismo.repository;



import com.example.AgenciaTurismo.model.Flight;
import com.example.AgenciaTurismo.model.Hotel;

import java.util.List;

public interface IFlightRepository {
    public List<Flight> findAll();
    //CREATE
    Flight save(Flight flight);

    //UPDATE
    Flight update(Flight flight);

    //DELETE
    Flight deleteFlight(String flight);
}

