package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.response.ListReservasDTO;

import java.time.LocalDate;

public interface IListReservasService {

    ListReservasDTO listarReservas();

    ListReservasDTO listarReservasPorDia(LocalDate dia);

    ListReservasDTO listarReservasPorMes(Integer mes);
}
