package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.FlightDTO;
import com.example.AgenciaTurismo.dto.FlightReservationDTO;
import com.example.AgenciaTurismo.dto.PaymentMethodDTO;
import com.example.AgenciaTurismo.dto.PeopleDTO;
import com.example.AgenciaTurismo.dto.request.FinalFlightReservationDTO;
import com.example.AgenciaTurismo.dto.request.FlightConsultDTO;
import com.example.AgenciaTurismo.dto.response.ResponseDTO;
import com.example.AgenciaTurismo.dto.response.StatusCodeDTO;
import com.example.AgenciaTurismo.dto.response.TotalFlightReservationDTO;
import com.example.AgenciaTurismo.model.*;
import com.example.AgenciaTurismo.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FlightReservaService implements IFlightReservaService {
    @Autowired
    IFlightReservaRepository flightReservaRepository;

    @Autowired
    IPeopleRepository peopleRepository;
    @Autowired
    IPaymentMethodRepository paymentMethodRepository;
    @Autowired
    IFlightRepository flightRepository;
    @Autowired
    IFlightService serviceFlight;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public List<FinalFlightReservationDTO> listarReservas() {
        List<ReservarFlight> reservasList = flightReservaRepository.findAll();
        List<FinalFlightReservationDTO> listAMotrar = new ArrayList<>();

        for (ReservarFlight reservas : reservasList) {
            FinalFlightReservationDTO reserva = modelMapper.map(reservas, FinalFlightReservationDTO.class);
            listAMotrar.add(reserva);
        } //guardo la lista de personas

        return listAMotrar;
    }

    @Override
    public ResponseDTO createReserva(FinalFlightReservationDTO finalFlightReservationDTO) {

        if (this.reserveSaved(finalFlightReservationDTO)) {
            throw new IllegalArgumentException("La reserva ya está realizada.");
        }

        FlightConsultDTO vueloBuscado = new FlightConsultDTO(
                finalFlightReservationDTO.getFlightReservationDTO().getDateFrom(),
                finalFlightReservationDTO.getFlightReservationDTO().getDateTo(),
                finalFlightReservationDTO.getFlightReservationDTO().getOrigin(),
                finalFlightReservationDTO.getFlightReservationDTO().getDestination());

        List<FlightDTO> availableFlights = serviceFlight.validarVuelosDisponibles(vueloBuscado);

        FlightDTO flightToReserved = null;
        for (FlightDTO flight : availableFlights) {
            if (flight.getOrigin().equalsIgnoreCase(finalFlightReservationDTO.getFlightReservationDTO().getOrigin())
                    && flight.getDestination().equalsIgnoreCase(finalFlightReservationDTO.getFlightReservationDTO().getDestination())
                    && flight.getDateFrom().equals(finalFlightReservationDTO.getFlightReservationDTO().getDateFrom())
                    && flight.getDateTo().equals(finalFlightReservationDTO.getFlightReservationDTO().getDateTo())) {
                flightToReserved = flight;
                break;
                }
            }
            if (flightToReserved == null) {
                throw new IllegalArgumentException("No se encontró ningún vuelo que coincida con los criterios de reserva.");
            }

        Double amount = (flightToReserved.getPrice() * finalFlightReservationDTO.getFlightReservationDTO().getSeats());

        Double interest = this.calcInterest(amount, finalFlightReservationDTO.getFlightReservationDTO().getPaymentMethodDTO().getDues(),
                finalFlightReservationDTO.getFlightReservationDTO().getPaymentMethodDTO().getType());

        Double total = amount + interest;

        TotalFlightReservationDTO totalFlightReservationDTO = new TotalFlightReservationDTO();
        totalFlightReservationDTO.setAmount(amount);
        totalFlightReservationDTO.setInterest(interest);
        totalFlightReservationDTO.setTotal(total);
        totalFlightReservationDTO.setFinalFlightReservationDTO(finalFlightReservationDTO);
        totalFlightReservationDTO.setStatusCode(new StatusCodeDTO(201, "El proceso terminó satisfactoriamente"));

        List<PeopleDTO> persDeReserva = finalFlightReservationDTO.getFlightReservationDTO().getPeopleDTO();
        List<People> persAGuardar = new ArrayList<>();
        for (PeopleDTO peoples : persDeReserva) {
            People person = modelMapper.map(peoples, People.class);
            persAGuardar.add(person);
            peopleRepository.save(person);
        }//guardo la lista de personas

        PaymentMethodDTO metodoPago = finalFlightReservationDTO.getFlightReservationDTO().getPaymentMethodDTO();
        PaymentMethod metodoPagoAGuardar = modelMapper.map(metodoPago, PaymentMethod.class);
        paymentMethodRepository.save(metodoPagoAGuardar); //guardo el metodo de pago

        Flight flightExistente = flightRepository.findByFlightCode(flightToReserved.getFlightCode());
        Flight flightAGuardar = modelMapper.map(flightExistente, Flight.class);
        flightAGuardar.setReserved(true);

        ReservarFlight flight = new ReservarFlight();
        flight.setSeats(finalFlightReservationDTO.getFlightReservationDTO().getSeats());
        flight.setPeople(persAGuardar);
        flight.setPaymentMethod(metodoPagoAGuardar);
        flight.setFlights(flightAGuardar);

        flightReservaRepository.save(flight);

        return new ResponseDTO("Reserva de vuelo dada de alta correctamente");
    }

    @Override
    public ResponseDTO updateReserva(Long id, FinalFlightReservationDTO finalFlightReservationDTO){
        Optional<ReservarFlight> optionalReservaFlight = flightReservaRepository.findById(id);

        if (optionalReservaFlight.isEmpty()) {
            throw new IllegalArgumentException("No se encontró la reserva a actualizar");
        }

        ReservarFlight reservaExistente = optionalReservaFlight.get();

        // Actualizar campos simples directamente
        reservaExistente.setSeats(finalFlightReservationDTO.getFlightReservationDTO().getSeats());

        /// Actualizar personas (people)
        List<PeopleDTO> personasDTO = finalFlightReservationDTO.getFlightReservationDTO().getPeopleDTO();
        if (personasDTO != null && !personasDTO.isEmpty()) {
            List<People> personas = new ArrayList<>();
            for (PeopleDTO peopleDTO : personasDTO) {
                // Buscar una persona existente por algún atributo único (en este caso, el nombre)
                Optional<People> optionalPersonaExistente = peopleRepository.findByName(peopleDTO.getName());
                if (optionalPersonaExistente.isPresent()) {
                    // Si la persona existe, actualizar los campos relevantes
                    People personaExistente = optionalPersonaExistente.get();
                    modelMapper.map(peopleDTO, personaExistente);
                    personas.add(personaExistente);
                } else {
                    // Si la persona no existe, mapear una nueva persona desde el DTO
                    People personaNueva = modelMapper.map(peopleDTO, People.class);
                    personas.add(personaNueva);
                }
            }
            reservaExistente.setPeople(personas);
        } else {
            // Si no se proporcionan personas en el DTO, mantener las existentes (no hacer nada)
            reservaExistente.setPeople(reservaExistente.getPeople());
        }

        // Actualizar método de pago (paymentMethod)
        PaymentMethodDTO metodoPagoDTO = finalFlightReservationDTO.getFlightReservationDTO().getPaymentMethodDTO();
        if (metodoPagoDTO != null) {
            // Obtener el método de pago existente de la reserva
            PaymentMethod metodoPagoExistente = reservaExistente.getPaymentMethod();

            // Verificar si hay un método de pago existente
            if (metodoPagoExistente != null) {
                // Actualizar los campos del método de pago existente con los del DTO
                modelMapper.map(metodoPagoDTO, metodoPagoExistente);
            } else {
                // Si no hay método de pago existente, mapear uno nuevo
                PaymentMethod metodoPago = modelMapper.map(metodoPagoDTO, PaymentMethod.class);
                reservaExistente.setPaymentMethod(metodoPago);
            }
        } else {
            // Si no se proporciona método de pago en el DTO, mantener el existente (no hacer nada)
        }

        // Guardar la reserva actualizada
        flightReservaRepository.save(reservaExistente);

        return new ResponseDTO("Reserva actualizada correctamente");
    }

    @Override
    public ResponseDTO deleteReserva(Long id) {
        if (!flightReservaRepository.existsById(id)) {
            return new ResponseDTO("No se encontró la reserva a eliminar");
        }
        flightReservaRepository.deleteById(id);
        return new ResponseDTO("Reserva eliminada con éxito");
    }

    @Override
    public Boolean reserveSaved(FinalFlightReservationDTO finalFlightReservationDTO) {
        String flightCode = finalFlightReservationDTO.getFlightReservationDTO().getFlightCode();
        Flight flightEncontrado = flightRepository.findByFlightCode(flightCode);

        if (flightEncontrado.getReserved() == true) {
            throw new IllegalStateException("Este vuelo se encuentra reservado");
        }

        return false;
    }

    //METODOS PARA REUTILIZAR
    @Override
    public Double calcInterest(Double amount, Integer dues, String type) {
        if (type.equalsIgnoreCase("Debit") || type.equalsIgnoreCase("Credit")) {
            if (type.equalsIgnoreCase("Debit") && dues > 1) {
                throw new IllegalArgumentException("No puede pagar en cuotas con tarjeta de débito.");
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

}
