package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.model.Hotel;
import com.example.AgenciaTurismo.repository.IHotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService implements IHotelService{
    @Autowired
    private IHotelRepository hotelRepository;

    public List<Hotel> listHotels() {
        return hotelRepository.findAll().stream()
                .map(hotel -> new Hotel(
                        hotel.getHotelCode(),
                        hotel.getHotelName(),
                        hotel.getDestination(),
                        hotel.getRoomType(),
                        hotel.getPriceForNight(),
                        hotel.getDateFrom(),
                        hotel.getDateTo(),
                        hotel.getReserved()
                )).toList();
    }
}
