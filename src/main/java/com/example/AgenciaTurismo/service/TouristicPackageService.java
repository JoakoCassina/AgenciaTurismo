package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.TouristicPackageDTO;
import com.example.AgenciaTurismo.dto.response.ResponseDTO;
import com.example.AgenciaTurismo.model.Client;
import com.example.AgenciaTurismo.model.TouristPackage;
import com.example.AgenciaTurismo.repository.IClientRepository;
import com.example.AgenciaTurismo.repository.IHotelReservaRepository;
import com.example.AgenciaTurismo.repository.ITouristicPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristicPackageService implements ITouristicPackageService{
    @Autowired
    ITouristicPackageRepository touristicPackageRepository;

    @Autowired
    IClientRepository clientRepository;
    @Autowired
    IHotelReservaRepository hotelReservaRepository;

    public List<Client> listarClientes(){
        return clientRepository.findAll();
    }



    @Override
    public ResponseDTO createPackage(TouristicPackageDTO paquete) {
        Long clienteABuscar = paquete.getClienteId();
        Long reservaDeClienteABuscar = paquete.getListaReservation().getId1();
        Long segundaReservaDeClienteABuscar = paquete.getListaReservation().getId2();

        List<Object[]> reservasDelCliente = hotelReservaRepository.findByClientId(clienteABuscar);

        Double totalAmountReserva1 = null;
        Double totalAmountReserva2 = null;

        // Iterar sobre las reservas del cliente para encontrar los totalAmounts de las reservas específicas
        for (Object[] reserva : reservasDelCliente) {
            Long idReserva = (Long) reserva[0];
            Double totalAmount = (Double) reserva[1];

            if (idReserva.equals(reservaDeClienteABuscar)) {
                totalAmountReserva1 = totalAmount;
            } else if (idReserva.equals(segundaReservaDeClienteABuscar)) {
                totalAmountReserva2 = totalAmount;
            }
        }
            // Si ambos totalAmounts se han encontrado, se puede salir del bucle
            if (totalAmountReserva1 != null && totalAmountReserva2 != null) {
                // Sumar los totalAmounts
                Double total = ((totalAmountReserva1 + totalAmountReserva2) - 0.10);
                // Devolver el total como parte de la respuesta
                return new ResponseDTO("Paquete Turistico de hoteles creado correctamente. Total de los totalAmounts: " + total);
            }
        throw new IllegalArgumentException("No se encontraron todas las reservas especificadas para el cliente.");

    }


}
