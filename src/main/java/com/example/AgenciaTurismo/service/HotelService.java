package com.example.AgenciaTurismo.service;


import com.example.AgenciaTurismo.dto.request.FinalHotelReservationDTO;
import com.example.AgenciaTurismo.dto.request.HotelConsultDTO;
import com.example.AgenciaTurismo.dto.response.HotelAvailableDTO;
import com.example.AgenciaTurismo.dto.response.ResponseDTO;
import com.example.AgenciaTurismo.dto.response.StatusCodeDTO;
import com.example.AgenciaTurismo.dto.response.TotalHotelReservationDTO;
import com.example.AgenciaTurismo.exception.InvalidReservationException;
import com.example.AgenciaTurismo.model.Hotel;
import com.example.AgenciaTurismo.repository.IHotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.AgenciaTurismo.dto.HotelDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelService implements IHotelService{
    @Autowired
    private IHotelRepository hotelRepository;

    public List<HotelDTO> listHotelsDTO() {
        return hotelRepository.findAll().stream()
                .map(hotel -> new HotelDTO(
                        hotel.getHotelCode(),
                        hotel.getHotelName(),
                        hotel.getDestination(),
                        hotel.getRoomType(),
                        hotel.getPriceForNight(),
                        hotel.getDateFrom(),
                        hotel.getDateTo(),
                        hotel.getReserved()
                )).toList();
    }

    @Override
    public HotelAvailableDTO hotelesDisponibles(HotelConsultDTO hotelConsultDTO) {
        List<HotelDTO> listHotelDTO = listHotelsDTO();

        List<HotelDTO> availableHotel = new ArrayList<>();
        for (HotelDTO hotel : listHotelDTO) {
            if (hotel.getDestination().equals(hotelConsultDTO.getDestination())
                    && hotel.getDateFrom().equals(hotelConsultDTO.getDateFrom())
                    && hotel.getDateTo().equals(hotelConsultDTO.getDateTo())){
                  availableHotel.add(hotel);
            }
        }
        HotelAvailableDTO hotelAvailable = new HotelAvailableDTO();
        hotelAvailable.setAvailableHotelDTO(availableHotel);

        return hotelAvailable;
    }

    @Override
    public Double calcInterest(Double amount, Integer dues) {
        switch (dues) {
            case 1:
                return 0.0;
            case 3:
                return amount * 0.05;
            case 6:
                return amount * 0.15;
            case 12:
                return amount * 0.30;
            default:
                throw new InvalidReservationException("Número de cuotas no válido.");
        }
    }

    @Override
    public TotalHotelReservationDTO reserved(FinalHotelReservationDTO finalHotelReservationDTO) {

        List<HotelDTO> listHotelDTO = listHotelsDTO();

        HotelDTO hotelToReserved = null;
        for (HotelDTO hotel : listHotelDTO) {
            if (hotel.getDestination().equalsIgnoreCase(finalHotelReservationDTO.getHotelReservationDTO().getDestination())
                    && hotel.getDateFrom().equals(finalHotelReservationDTO.getHotelReservationDTO().getDateFrom())
                    && hotel.getDateTo().equals(finalHotelReservationDTO.getHotelReservationDTO().getDateTo())) {
                hotelToReserved = hotel;
                break;
            }
        }
        if (hotelToReserved == null) {
            throw new InvalidReservationException("No se encontró ningún hotel que coincida con los criterios de reserva.");
        }

        Double amount = (hotelToReserved.getPriceForNight() * finalHotelReservationDTO.getHotelReservationDTO().getPeopleAmount());

        Double interest = calcInterest(amount, finalHotelReservationDTO.getHotelReservationDTO().getPaymentMethodDTO().getDues());

        Double total = amount + interest;

        TotalHotelReservationDTO totalHotelReservationDTO = new TotalHotelReservationDTO();
        totalHotelReservationDTO.setAmount(amount);
        totalHotelReservationDTO.setInterest(interest);
        totalHotelReservationDTO.setTotal(total);
        totalHotelReservationDTO.setFinalHotelReservation(finalHotelReservationDTO);
        totalHotelReservationDTO.setStatusCode(new StatusCodeDTO(201, "El proceso terminó satisfactoriamente"));

        return totalHotelReservationDTO;
    }

    //CREATE
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

        hotelRepository.save(hotel);
        return new ResponseDTO("Hotel creado con éxito");
    }

}
