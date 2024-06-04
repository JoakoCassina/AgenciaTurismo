package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.HotelDTO;
import com.example.AgenciaTurismo.dto.HotelReservationDTO;
import com.example.AgenciaTurismo.dto.HotelReservedDTO;
import com.example.AgenciaTurismo.dto.request.FinalHotelReservationDTO;
import com.example.AgenciaTurismo.dto.request.HotelConsultDTO;
import com.example.AgenciaTurismo.dto.response.HotelAvailableDTO;
import com.example.AgenciaTurismo.dto.response.ResponseDTO;
import com.example.AgenciaTurismo.dto.response.TotalHotelReservationDTO;

import java.time.LocalDate;
import java.util.List;

public interface IHotelService {

    List<HotelDTO> listHotelsDTO();

    HotelAvailableDTO hotelesDisponibles (HotelConsultDTO hotelConsultDTO);

    Double calcInterest(Double amount, Integer dues, String type);

    TotalHotelReservationDTO reserved(FinalHotelReservationDTO finalHotelReservationDTO);

    Boolean reserveSaved(FinalHotelReservationDTO finalHotelReservationDTO);

    List<HotelReservedDTO> hotelSaved();


    //CREATE
    ResponseDTO createHotel(HotelDTO hotelDTO);

    //UPDATE
    ResponseDTO updateHotel(String hotelCode, HotelDTO hotelDTO);

    //DELETE
    ResponseDTO deleteHotel(String hotelCode);



    //METODOS PARA VALIDAR

    Boolean roomCapacity(HotelReservationDTO reservation);

    List<HotelDTO> validarHotelesDisponibles(HotelConsultDTO hotelConsultDTO);

    Boolean dateValid(LocalDate dateFrom, LocalDate dateTo);

    Boolean destinationValid(String destination);

}
