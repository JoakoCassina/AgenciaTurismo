package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.HotelDTO;
import com.example.AgenciaTurismo.dto.HotelReservationDTO;
import com.example.AgenciaTurismo.dto.HotelReservedDTO;
import com.example.AgenciaTurismo.dto.request.FinalHotelReservationDTO;
import com.example.AgenciaTurismo.dto.request.HotelConsultDTO;
import com.example.AgenciaTurismo.dto.response.HotelAvailableDTO;
import com.example.AgenciaTurismo.dto.response.ResponseDTO;
import com.example.AgenciaTurismo.dto.response.TotalHotelReservationDTO;
import com.example.AgenciaTurismo.model.Flight;
import com.example.AgenciaTurismo.model.Hotel;

import java.time.LocalDate;
import java.util.List;

public interface IJpaHotelService {

    List<HotelDTO> listarHotels();

    HotelAvailableDTO hotelesDisponibles (HotelConsultDTO hotelConsultDTO);

    TotalHotelReservationDTO reserved(FinalHotelReservationDTO finalHotelReservationDTO);

    // CREATE
    ResponseDTO createHotel(HotelDTO hotelDTO);

    // UPDATE
    ResponseDTO updateHotel(Long id, HotelDTO hotelDTO);
    //DELETE
    ResponseDTO deleteHotel (Long id);

    //METODOS PARA VALIDAR
    Boolean reserveSaved(FinalHotelReservationDTO finalHotelReservationDTO);

    List<HotelReservedDTO> hotelSaved();

    Double calcInterest(Double amount, Integer dues, String type);

    Boolean roomCapacity(HotelReservationDTO reservation);

    List<HotelDTO> validarHotelesDisponibles(HotelConsultDTO hotelConsultDTO);

    Boolean dateValid(LocalDate dateFrom, LocalDate dateTo);

    Boolean destinationValid(String destination);


}
