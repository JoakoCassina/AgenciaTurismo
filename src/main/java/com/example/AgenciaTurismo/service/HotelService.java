package com.example.AgenciaTurismo.service;

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
import com.example.AgenciaTurismo.dto.HotelDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class HotelService implements IHotelService {
    @Autowired
    private IHotelRepository hotelRepository;

    private final List<HotelReservedDTO> hotelReserve = new ArrayList<>();

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
        // Verificar si hay hoteles disponibles para las fechas y el destino
        List<HotelDTO> availableHotel = validarHotelesDisponibles(hotelConsultDTO);

        // Crear y configurar el objeto HotelAvailableDTO
        HotelAvailableDTO hotelAvailable = new HotelAvailableDTO();
        hotelAvailable.setAvailableHotelDTO(availableHotel);

        return hotelAvailable;
    }

    @Override
    public TotalHotelReservationDTO reserved(FinalHotelReservationDTO finalHotelReservationDTO) {

        if (reserveSaved(finalHotelReservationDTO)) {
            throw new InvalidReservationException("La reserva ya está realizada.");
        }

        roomCapacity(finalHotelReservationDTO.getHotelReservationDTO());


        HotelConsultDTO hotelBuscado = new HotelConsultDTO(finalHotelReservationDTO.getHotelReservationDTO().getDateFrom(),
                finalHotelReservationDTO.getHotelReservationDTO().getDateTo(),
                finalHotelReservationDTO.getHotelReservationDTO().getDestination());

        List<HotelDTO> availableHotel = validarHotelesDisponibles(hotelBuscado);


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
            throw new InvalidReservationException("No se encontró ningún hotel que coincida con los criterios de reserva.");
        }

        Double amount = (hotelToReserved.getPriceForNight() * finalHotelReservationDTO.getHotelReservationDTO().getPeopleAmount());

        Double interest = calcInterest(amount, finalHotelReservationDTO.getHotelReservationDTO().getPaymentMethodDTO().getDues(),
                finalHotelReservationDTO.getHotelReservationDTO().getPaymentMethodDTO().getType());

        Double total = amount + interest;

        TotalHotelReservationDTO totalHotelReservationDTO = new TotalHotelReservationDTO();
        totalHotelReservationDTO.setAmount(amount);
        totalHotelReservationDTO.setInterest(interest);
        totalHotelReservationDTO.setTotal(total);
        totalHotelReservationDTO.setFinalHotelReservation(finalHotelReservationDTO);
        totalHotelReservationDTO.setStatusCode(new StatusCodeDTO(201, "El proceso terminó satisfactoriamente"));

        hotelReserve.add(new HotelReservedDTO(totalHotelReservationDTO));

        return totalHotelReservationDTO;
    }


                                        //CRUD

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

    //UPDATE
    @Override
    public ResponseDTO updateHotel(String hotelCode, HotelDTO hotelDTO) {
        Hotel hotel = new Hotel(
                hotelCode,
                hotelDTO.getHotelName(),
                hotelDTO.getDestination(),
                hotelDTO.getRoomType(),
                hotelDTO.getPriceForNight(),
                hotelDTO.getDateFrom(),
                hotelDTO.getDateTo(),
                hotelDTO.getReserved()
        );
        Hotel updatedHotel = hotelRepository.update(hotel);
        if (updatedHotel != null) {
            return new ResponseDTO("Hotel actualizado con éxito");
        } else {
            return new ResponseDTO("Hotel no encontrado");
        }
    }

    //DELETE
    @Override
    public ResponseDTO deleteHotel(String hotelCode) {
        Hotel hotel = hotelRepository.deleteHotel(hotelCode);
        if (hotel != null) {
            return new ResponseDTO("Hotel eliminado con éxito");
        } else {
            return new ResponseDTO("No se encontro el hotel a eliminar");
        }

    }



                                    //METODOS PARA REUTILIZAR
    @Override
    public Boolean reserveSaved(FinalHotelReservationDTO finalHotelReservationDTO) {
        for (HotelReservedDTO reservaGuardada : hotelReserve) {
            FinalHotelReservationDTO reservaExistente = reservaGuardada.getHotelReserved().getFinalHotelReservation();
            if (reservaExistente.equals(finalHotelReservationDTO)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<HotelReservedDTO> hotelSaved() {
        return hotelReserve;
    }

    //Evalua el metodo de pago ingresado para poder hacer el carlculo de la reserva.
    @Override
    public Double calcInterest(Double amount, Integer dues, String type) {

        if (type.equalsIgnoreCase("Debit") || type.equalsIgnoreCase("Credit")) {
            if (type.equalsIgnoreCase("Debit") && dues > 1) {
                throw new InvalidReservationException("No puede pagar en cuotas con tarjeta de debito.");
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
                        throw new InvalidReservationException("Número de cuotas no válido.");
                }
        } else {
            throw new InvalidReservationException("Tipo de pago no válido.");
        }

    }

    //comparar tIpo de habitacion con cantidad de personas ingresadas
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

    // Validando Hoteles disponible
    @Override
    public List<HotelDTO> validarHotelesDisponibles(HotelConsultDTO hotelConsultDTO) {
        // llamamo al metodo que verifica la existencia del destino
        destinationValid(hotelConsultDTO.getDestination());

        // llamamos al metodo que verifica las fechas
        dateValid(hotelConsultDTO.getDateFrom(), hotelConsultDTO.getDateTo());

        List<HotelDTO> listHotelDTO = listHotelsDTO();

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
        List<String> validDestination = listHotelsDTO().stream()
                .map(HotelDTO::getDestination)
                .toList();
        if (validDestination.contains(destination)) {
            return true;
        }
        throw new IllegalArgumentException("El destino elegido  no existe");
    }
}








