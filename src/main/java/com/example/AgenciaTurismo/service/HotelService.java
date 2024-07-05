package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.HotelDTO;
import com.example.AgenciaTurismo.dto.HotelReservationDTO;
import com.example.AgenciaTurismo.dto.HotelReservedDTO;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HotelService implements IHotelService {

    @Autowired
    IHotelRepository repository;


    @Override
    public List<HotelDTO> listarHotels() {
        List<HotelDTO> listHotel = repository.findAll().stream()
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
        return listHotel;
    }


    @Override
    public HotelAvailableDTO hotelesDisponibles(HotelConsultDTO hotelConsultDTO) {
        // Verificar si hay hoteles disponibles para las fechas y el destino
        List<HotelDTO> availableHotel = this.validarHotelesDisponibles(hotelConsultDTO);

        // Crear y configurar el objeto HotelAvailableDTO
        HotelAvailableDTO hotelAvailable = new HotelAvailableDTO();
        hotelAvailable.setAvailableHotelDTO(availableHotel);

        return hotelAvailable;
    }


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

        repository.save(hotel);

        return new ResponseDTO("Hotel creado con éxito");
    }

    @Override
    public ResponseDTO updateHotel(Long id, HotelDTO hotelDTO) {

        if(!repository.existsById(id)){
            return new ResponseDTO("No se encontro el hotel a actualizar");
        }
        Hotel hotel = new Hotel(
                id,
                hotelDTO.getHotelCode(),
                hotelDTO.getHotelName(),
                hotelDTO.getDestination(),
                hotelDTO.getRoomType(),
                hotelDTO.getPriceForNight(),
                hotelDTO.getDateFrom(),
                hotelDTO.getDateTo(),
                hotelDTO.getReserved()
        );
        repository.save(hotel);
        return new ResponseDTO("Hotel actualizado con éxito");


    }
    @Override
    public ResponseDTO deleteHotel(Long id) {
        if(!repository.existsById(id)){
            return new ResponseDTO("No se encontro el hotel a eliminar");
        }
        repository.deleteById(id);
        return new ResponseDTO("Hotel eliminado con éxito");
    }

    //METODOS PARA REUTILIZAR
    // Validando Hoteles disponible
    @Override
    public List<HotelDTO> validarHotelesDisponibles(HotelConsultDTO hotelConsultDTO) {
        // llamamo al metodo que verifica la existencia del destino
        this.destinationValid(hotelConsultDTO.getDestination());

        // llamamos al metodo que verifica las fechas
        this.dateValid(hotelConsultDTO.getDateFrom(), hotelConsultDTO.getDateTo());

        List<HotelDTO> listHotelDTO = this.listarHotels();

        List<HotelDTO> availableHotel = new ArrayList<>();
        for (HotelDTO hotel : listHotelDTO) {
            if (hotel.getDateFrom().equals(hotelConsultDTO.getDateFrom())
                    && hotel.getDateTo().equals(hotelConsultDTO.getDateTo())) {
                availableHotel.add(hotel);
            }
        }

        if (availableHotel.isEmpty()) {
            throw new InvalidReservationException("No hay hoteles disponibles para las fechas y la ruta especificadas.");
        }

        return availableHotel;
    }

    //Validando fechas
    @Override
    public Boolean dateValid(LocalDate dateFrom, LocalDate dateTo) {
        if (!dateFrom.isBefore(dateTo)) {
            throw new IllegalArgumentException("La fecha de entrada debe ser menor a la de salida");
        } else
            return true;
    }

    //Validando existencia del destino solicitado.
    public Boolean destinationValid(String destination) {
        List<String> validDestination = listarHotels().stream()
                .map(HotelDTO::getDestination)
                .toList();
        if (validDestination.contains(destination)) {
            return true;
        }
        throw new IllegalArgumentException("El destino elegido no existe");
    }
}
