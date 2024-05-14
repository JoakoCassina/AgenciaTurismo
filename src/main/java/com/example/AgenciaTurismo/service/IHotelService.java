package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.HotelDTO;
import com.example.AgenciaTurismo.dto.request.HotelConsultDTO;
import com.example.AgenciaTurismo.dto.response.HotelAvailableDTO;

import java.util.List;

public interface IHotelService {

    List<HotelDTO> listHotelsDTO();

    HotelAvailableDTO hotelesDisponibles (HotelConsultDTO hotelConsultDTO);




}
