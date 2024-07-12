package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.response.ListReservasDTO;
import com.example.AgenciaTurismo.repository.IFlightReservaRepository;
import com.example.AgenciaTurismo.repository.IHotelReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ListReservasService implements IListReservasService {

    @Autowired
    IFlightReservaRepository flightReservaRepository;
    @Autowired
    IHotelReservaRepository hotelReservaRepository;

    @Autowired
    IFlightReservaService flightReservaService;
    @Autowired
    IHotelReservaService hotelReservaService;


    @Override
    public ListReservasDTO listarReservas() {
        ListReservasDTO listaReservasDTO = new ListReservasDTO();
        listaReservasDTO.setMessage("Listado de Todas las Reservas");
        listaReservasDTO.setListaReservaHotels(hotelReservaService.listarReservas());
        listaReservasDTO.setListaReservaFlight(flightReservaService.listarReservas());
        return listaReservasDTO;
    }

    @Override
    public ListReservasDTO listarReservasPorDia(LocalDate dia) {
        ListReservasDTO listaReservasDTODia = new ListReservasDTO();
        listaReservasDTODia.setMessage("Listado de Reservas del Día " + dia);
        listaReservasDTODia.setListaReservaHotels(hotelReservaService.listarReservasDia(dia));
        listaReservasDTODia.setListaReservaFlight(flightReservaService.listarReservasDia(dia));
        return listaReservasDTODia;
    }

    @Override
    public ListReservasDTO listarReservasPorMes(Integer mes) {
        ListReservasDTO listaReservasDTOMes = new ListReservasDTO();
        listaReservasDTOMes.setMessage("Listado de Reservas del Mes " + mes);
        listaReservasDTOMes.setListaReservaHotels(hotelReservaService.listarReservasMes(mes));
        listaReservasDTOMes.setListaReservaFlight(flightReservaService.listarReservasMes(mes));
        return listaReservasDTOMes;
    }
}
