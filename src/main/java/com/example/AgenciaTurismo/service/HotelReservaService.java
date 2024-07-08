package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.*;
import com.example.AgenciaTurismo.dto.request.FinalHotelReservationDTO;
import com.example.AgenciaTurismo.dto.request.HotelConsultDTO;
import com.example.AgenciaTurismo.dto.response.ResponseDTO;
import com.example.AgenciaTurismo.dto.response.StatusCodeDTO;
import com.example.AgenciaTurismo.dto.response.TotalHotelReservationDTO;
import com.example.AgenciaTurismo.model.Hotel;
import com.example.AgenciaTurismo.model.PaymentMethod;
import com.example.AgenciaTurismo.model.People;
import com.example.AgenciaTurismo.model.ReservarHotel;
import com.example.AgenciaTurismo.repository.IHotelReservaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelReservaService implements IHotelReservaService {
    @Autowired
    IHotelReservaRepository repository;

    @Autowired
    IHotelService serviceHotel;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<HotelReservedDTO> listarReservas() {
        List<HotelReservedDTO> hotelReserve = new ArrayList<>();
        List<ReservarHotel> reservas = repository.findAll();
        if (reservas.isEmpty()) {
            return hotelReserve;
        }

        for(ReservarHotel resrv : reservas) {
            HotelReservedDTO reservaDTO = modelMapper.map(resrv, HotelReservedDTO.class);
            hotelReserve.add(reservaDTO);
        }
        return hotelReserve;
    }

    @Override
    public ResponseDTO createReserva(FinalHotelReservationDTO finalHotelReservationDTO) {

        if (this.reserveSaved(finalHotelReservationDTO)) {
            throw new IllegalArgumentException("La reserva ya está realizada.");
        }

        this.roomCapacity(finalHotelReservationDTO.getHotelReservationDTO());


        HotelConsultDTO hotelBuscado = new HotelConsultDTO(
                finalHotelReservationDTO.getHotelReservationDTO().getDateFrom(),
                finalHotelReservationDTO.getHotelReservationDTO().getDateTo(),
                finalHotelReservationDTO.getHotelReservationDTO().getDestination());

        List<HotelDTO> availableHotel = serviceHotel.validarHotelesDisponibles(hotelBuscado);


        HotelDTO hotelToReserved = null;
        for (HotelDTO hotel : availableHotel) {
            if (hotel.getDestination().equalsIgnoreCase(finalHotelReservationDTO.getHotelReservationDTO().getDestination())
                    && hotel.getDateFrom().equals(finalHotelReservationDTO.getHotelReservationDTO().getDateFrom())
                    && hotel.getDateTo().equals(finalHotelReservationDTO.getHotelReservationDTO().getDateTo())) {
                hotelToReserved = hotel;
                break;
            }
        }
        if (hotelToReserved == null) {
            throw new IllegalArgumentException("No se encontró ningún hotel que coincida con los criterios de reserva.");
        }

        Double amount = (hotelToReserved.getPriceForNight() * finalHotelReservationDTO.getHotelReservationDTO().getPeopleAmount());

        Double interest = this.calcInterest(amount, finalHotelReservationDTO.getHotelReservationDTO().getPaymentMethodDTO().getDues(),
                finalHotelReservationDTO.getHotelReservationDTO().getPaymentMethodDTO().getType());

        Double total = amount + interest;

        TotalHotelReservationDTO totalHotelReservationDTO = new TotalHotelReservationDTO();
        totalHotelReservationDTO.setAmount(amount);
        totalHotelReservationDTO.setInterest(interest);
        totalHotelReservationDTO.setTotal(total);
        totalHotelReservationDTO.setFinalHotelReservation(finalHotelReservationDTO);
        totalHotelReservationDTO.setStatusCode(new StatusCodeDTO(201, "El proceso terminó satisfactoriamente"));

        List<PeopleDTO> persDeReserva = finalHotelReservationDTO.getHotelReservationDTO().getPeopleDTO();
        List<People> persAGuardar = new ArrayList<>();
        for (PeopleDTO peoples : persDeReserva) {
            People person = modelMapper.map(peoples, People.class);
            persAGuardar.add(person);
        } //guardo la lista de personas

        PaymentMethodDTO metodoPago = totalHotelReservationDTO.getFinalHotelReservation().getHotelReservationDTO().getPaymentMethodDTO();
        PaymentMethod metodoPagoAGuardar = modelMapper.map(metodoPago, PaymentMethod.class);

        HotelDTO hotelDTO = new HotelDTO();
        hotelDTO.setHotelCode(totalHotelReservationDTO.getFinalHotelReservation().getHotelReservationDTO().getHotelCode());
        hotelDTO.setHotelName(hotelToReserved.getHotelName());
        hotelDTO.setDestination(hotelToReserved.getDestination());
        hotelDTO.setRoomType(hotelToReserved.getRoomType());
        hotelDTO.setPriceForNight(hotelToReserved.getPriceForNight());
        hotelDTO.setDateFrom(hotelToReserved.getDateFrom());
        hotelDTO.setDateTo(hotelToReserved.getDateTo());
        hotelDTO.setReserved(hotelToReserved.getReserved());

        Hotel hotelAGuardar = modelMapper.map(hotelDTO, Hotel.class);

        ReservarHotel hotel = new ReservarHotel();
        hotel.setPeopleAmount(finalHotelReservationDTO.getHotelReservationDTO().getPeopleAmount());
        hotel.setPeople(persAGuardar);
        hotel.setPaymentMethod(metodoPagoAGuardar);
        hotel.setHotel(hotelAGuardar);

        repository.save(hotel);

        return new ResponseDTO("Reserva de hotel dada de alta correctamente");
    }

    @Override
    public ResponseDTO uptateReserva(Long id, FinalHotelReservationDTO finalHoteltReservationDTO) {
        return null;
    }

    @Override
    public ResponseDTO deleteReserva(Long id) {
        return null;
    }

    //METODOS PARA REUTILIZAR
    @Override
    public Boolean reserveSaved(FinalHotelReservationDTO finalHotelReservationDTO) {
        for (HotelReservedDTO reservaGuardada : listarReservas()) {
            FinalHotelReservationDTO reservaExistente = reservaGuardada.getHotelReserved().getFinalHotelReservation();
            if (reservaExistente.equals(finalHotelReservationDTO)) {
                return true;
            }
        }
        return false;
    }

       //Evalua el metodo de pago ingresado para poder hacer el carlculo de la reserva.
    @Override
    public Double calcInterest(Double amount, Integer dues, String type) {

        if (type.equalsIgnoreCase("Debit") || type.equalsIgnoreCase("Credit")) {
            if (type.equalsIgnoreCase("Debit") && dues > 1) {
                throw new IllegalArgumentException("No puede pagar en cuotas con tarjeta de debito.");
            } else
                switch (dues) {
                    case 1:
                        return 0.0;
                    case 2, 3:
                        return amount * 0.05;
                    case 4, 5, 6:
                        return amount * 0.10;
                    case 7, 8, 9, 10, 11, 12:
                        return amount * 0.20;
                    default:
                        throw new IllegalArgumentException("Número de cuotas no válido.");
                }
        } else {
            throw new IllegalArgumentException("Tipo de pago no válido.");
        }

    }

    //comparar tipo de habitacion con cantidad de personas ingresadas
    public Boolean roomCapacity(HotelReservationDTO reservation) {
        Double people;


        switch (reservation.getRoomType()) {
            case "Single":
                people = 1D;
                break;
            case "Doble":
                people = 2D;
                break;
            case "Triple":
                people = 3D;
                break;
            case "Múltiple":
                people = 4D;
                break;
            default:
                people = 0D;
                break;
        }
        if (!people.equals(reservation.getPeopleAmount())) {
            throw new IllegalArgumentException("La cantidad de personas no coincide con el tipo de habitación.");
        } else if (people != reservation.getPeopleDTO().size()) {
            throw new IllegalArgumentException("El tipo de habitación seleccionada no coincide con la cantidad de personas que se alojarán en ella.");
        }
        return true;
    }


}
