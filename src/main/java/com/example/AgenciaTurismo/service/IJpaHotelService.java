package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.HotelDTO;
import com.example.AgenciaTurismo.dto.response.ResponseDTO;
import com.example.AgenciaTurismo.model.Hotel;

import java.util.List;

public interface IJpaHotelService {

    List<Hotel> listarHotels();

    // CREATE
    ResponseDTO createHotel(HotelDTO hotelDTO);

    // UPDATE
//    ResponseDTO updateHotel(HotelDTO hotelDTO);
//
//    ResponseDTO deleteHotel(Long id);


}
