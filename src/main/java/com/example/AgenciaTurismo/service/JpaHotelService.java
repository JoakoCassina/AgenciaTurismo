package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.HotelDTO;
import com.example.AgenciaTurismo.dto.response.ResponseDTO;
import com.example.AgenciaTurismo.model.Hotel;
import com.example.AgenciaTurismo.repository.IntHotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JpaHotelService implements  IJpaHotelService {

    @Autowired
    IntHotelRepository repository;

    @Override
    public List<Hotel> listarHotels() {
        List<Hotel> listHotel = repository.findAll();
        return listHotel;
    }


    @Override
    public ResponseDTO createHotel(HotelDTO hotelDTO) {
        Hotel hotel = new Hotel();
                hotel.setHotelCode(hotelDTO.getHotelCode());
                hotel.setHotelName(hotelDTO.getHotelName());
                hotel.setDestination(hotelDTO.getDestination());
                hotel.setRoomType(hotelDTO.getRoomType());
                hotel.setPriceForNight(hotelDTO.getPriceForNight());
                hotel.setDateFrom(hotelDTO.getDateFrom());
                hotel.setDateTo(hotelDTO.getDateTo());
                hotel.setReserved(hotelDTO.getReserved());

        repository.save(hotel);

        return new ResponseDTO("Hotel creado con éxito");
    }

    @Override
    public ResponseDTO updateHotel(Long id, HotelDTO hotelDTO) {

        Hotel hotel = new Hotel(
                id,
                hotelDTO.getHotelCode(),
                hotelDTO.getHotelName(),
                hotelDTO.getDestination(),
                hotelDTO.getRoomType(),
                hotelDTO.getPriceForNight(),
                hotelDTO.getDateFrom(),
                hotelDTO.getDateTo(),
                hotelDTO.getReserved()
        );
        repository.save(hotel);
        return new ResponseDTO("Hotel actualizado con éxito");


    }
    @Override
    public ResponseDTO deleteHotel(Long id) {
        repository.deleteById(id);
        return new ResponseDTO("Hotel eliminado con éxito");
    }
}
