package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.model.Hotel;

import java.util.List;

public interface IHotelRepository {
    public List<Hotel> findAll();

    //CREATE
    Hotel save(Hotel hotel);

    //UPDATE
    Hotel update(Hotel hotel);
}


