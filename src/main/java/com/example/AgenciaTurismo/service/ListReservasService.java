package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.request.FinalFlightReservationDTO;
import com.example.AgenciaTurismo.dto.request.FinalHotelReservationDTO;
import com.example.AgenciaTurismo.dto.response.ListReservasDTO;
import com.example.AgenciaTurismo.dto.response.ReservaDiaDTO;
import com.example.AgenciaTurismo.dto.response.ReservaMesDTO;
import com.example.AgenciaTurismo.model.ReservarFlight;
import com.example.AgenciaTurismo.model.ReservarHotel;
import com.example.AgenciaTurismo.repository.IFlightReservaRepository;
import com.example.AgenciaTurismo.repository.IHotelReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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


    @Override//Metodo que lista todas las reservas creadas (Tanto como de Hotel como de Flight)
    public ListReservasDTO listarReservas() {
        ListReservasDTO listaReservasDTO = new ListReservasDTO();
        listaReservasDTO.setMessage("Listado de Todas las Reservas");
        listaReservasDTO.setListaReservaHotels(hotelReservaService.listarReservas());
        listaReservasDTO.setListaReservaFlight(flightReservaService.listarReservas());
        return listaReservasDTO;
    }

    @Override//Devuele el monto total que se adquirido por una fecha específica
    public ReservaDiaDTO listarReservasPorDia(LocalDate dia) {
        List<ReservarHotel> listaReservasHotelDia = (hotelReservaService.listarReservasDia(dia));//Llamamos al meotod que lista las reservas realizadas tal fecha
         Double totalReservasHotel = 0.0;
        Double totalReservasVuelo = 0.0;
        for (ReservarHotel reserva : listaReservasHotelDia){//De la lista obtenida tomamos y sumamos sus TOTALES
            totalReservasHotel += reserva.getTotalAmount();
        }//Así obtenemos el total obtenido en las Reservas de hotel en una fecha específica
        List<ReservarFlight> listaReservasVueloDia = (flightReservaService.listarReservasDia(dia));
        for (ReservarFlight reserva : listaReservasVueloDia) {
            totalReservasVuelo += reserva.getTotalAmount();
        }
        ReservaDiaDTO totalReservasDia = new ReservaDiaDTO();//Creamos la respuesta final
        totalReservasDia.setDate(dia);//La fecha de consulta
        totalReservasDia.setTotal(totalReservasHotel + totalReservasVuelo);//Sumamos los montos adquiridos tanto con reservas de hoteles como de Flight para obtener el total final
        return totalReservasDia;
    }

    @Override
    public ReservaMesDTO listarReservasPorMes(Integer mes) {
        List<ReservarHotel> listaReservaHotelMes =hotelReservaService.listarReservasMes(mes);
        Double totalReservasHotel = 0.0;
        Double totalReservasVuelo = 0.0;
        for (ReservarHotel reserva : listaReservaHotelMes){
            totalReservasHotel += reserva.getTotalAmount();
        }
        List<ReservarFlight> listaReservaVueloMes = flightReservaService.listarReservasMes(mes);
        for (ReservarFlight reserva : listaReservaVueloMes) {
            totalReservasVuelo += reserva.getTotalAmount();
        }
        ReservaMesDTO totalReservasMes = new ReservaMesDTO();
        totalReservasMes.setMes(mes);
        totalReservasMes.setAnio(2024);
        totalReservasMes.setTotal(totalReservasHotel + totalReservasVuelo);
        return totalReservasMes;
    }
}
