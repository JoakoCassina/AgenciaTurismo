package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.HotelReservationDTO;
import com.example.AgenciaTurismo.dto.HotelReservedDTO;
import com.example.AgenciaTurismo.dto.request.FinalHotelReservationDTO;
import com.example.AgenciaTurismo.dto.response.ResponseDTO;

import java.util.List;

public interface IHotelReservaService {

    List<HotelReservedDTO> listarReservas();

    //CREATE
    ResponseDTO createReserva(FinalHotelReservationDTO finalHotelReservationDTO);

    //UPDATE
    ResponseDTO uptateReserva(Long id, FinalHotelReservationDTO finalHoteltReservationDTO);

    //DELETE
    ResponseDTO deleteReserva(Long id);

    //METODOS PARA VALIDAR
    Boolean reserveSaved(FinalHotelReservationDTO finalHotelReservationDTO);

    Double calcInterest(Double amount, Integer dues, String type);

    Boolean roomCapacity(HotelReservationDTO reservation);
}
