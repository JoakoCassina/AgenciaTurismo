package com.example.AgenciaTurismo.integrationTest;

import com.example.AgenciaTurismo.dto.FlightDTO;
import com.example.AgenciaTurismo.dto.FlightReservationDTO;
import com.example.AgenciaTurismo.dto.PaymentMethodDTO;
import com.example.AgenciaTurismo.dto.PeopleDTO;
import com.example.AgenciaTurismo.dto.request.FinalFlightReservationDTO;
import com.example.AgenciaTurismo.dto.response.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest
@AutoConfigureMockMvc

public class FlightControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
            .registerModule(new JavaTimeModule());

    private static final FlightDTO flightDTO1 = new FlightDTO("BAPI-1235", "Buenos Aires", "Puerto Iguazú", "Economy", 6500, LocalDate.of(2025, 2, 10), LocalDate.of(2025, 2, 15));
    private static final FlightDTO flightDTO2 = new FlightDTO("PIBA-1420", "Puerto Iguazú", "Bogotá", "Business", 43200, LocalDate.of(2025, 2, 10), LocalDate.of(2025, 2, 20));
    private static final FlightDTO flightDTO3 = new FlightDTO("PIBA-1420", "Puerto Iguazú", "Bogotá", "Economy", 25735, LocalDate.of(2025, 2, 10), LocalDate.of(2025, 2, 20));

    //Personas para la lista enviada
    private static final PeopleDTO peopleDTO1 = new PeopleDTO(42533885,"Joako","Cassina",LocalDate.of(2000,04,18), "joako@gmail.com");

    private static final PeopleDTO peopleDTO2 = new PeopleDTO(420000,"Juan","Casi",LocalDate.of(1999,05,19), "juan@gmail.com");

    //Metodo de pago enviado
    private static final PaymentMethodDTO metodoDTO = new PaymentMethodDTO("Credit", "0001", 3);

    private static final FlightReservationDTO reserva = new FlightReservationDTO(LocalDate.of(2025, 2, 10),
            LocalDate.of(2025, 2, 15), "Buenos Aires", "Puerto Iguazú", "Andre1235", 2.0, "Economy",
            List.of(peopleDTO1, peopleDTO2), metodoDTO);

    // StatusCode de respuesta
    private static final StatusCodeDTO statusCodeDTO = new StatusCodeDTO(201, "El proceso terminó satisfactoriamente");

    //US 04: Listar vuelos disponibles
    @Test
    public void listarVuelosTestOK() throws Exception {
        ResultMatcher statusEsperado = MockMvcResultMatchers.status().isOk();
        ResultMatcher bodyEsperado = MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(List.of(flightDTO1, flightDTO2, flightDTO3)));
        ResultMatcher contentTypeEsperado = MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(get("/api/v1/listFlights"))
                .andExpectAll(statusEsperado,bodyEsperado,contentTypeEsperado)
                .andDo(print());
    }

    //US 05: buscar hoteles disponibles
    @Test
    public void flightDisponiblesTestOK() throws Exception {
        String dateFromConsul = "10-02-2025";
        String dateToConsul = "20-02-2025";
        String originConsult = "Puerto Iguazú";
        String destinationConsult = "Bogotá";


        FlightAvailableDTO respuestaEsperada = new FlightAvailableDTO(List.of(flightDTO2,flightDTO3));

        ResultMatcher statusEsperado = MockMvcResultMatchers.status().isOk();
        ResultMatcher bodyEsperado= MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(respuestaEsperada));
        ResultMatcher contentTypeEsperado = MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(get("/api/v1/flights")
                        .param("dateFrom",dateFromConsul)
                        .param("dateTo",dateToConsul)
                        .param("origin",originConsult)
                        .param("destination",destinationConsult))
                .andExpectAll(statusEsperado,bodyEsperado,contentTypeEsperado)
                .andDo(print());
    }

    //us 06: devolver reserva hotel
    @Test
    public void reservedTestOK() throws Exception {
        FinalFlightReservationDTO reservaConsulta = new FinalFlightReservationDTO("Agustina",reserva);
        String payloadDto = objectMapper.writeValueAsString(reservaConsulta);

        TotalFlightReservationDTO resultadoEsperado = new TotalFlightReservationDTO(13000.0, 650.0, 13650.0, reservaConsulta, statusCodeDTO);

        ResultMatcher statusEsperado = MockMvcResultMatchers.status().isCreated();
        ResultMatcher bodyEsperado= MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(resultadoEsperado));
        ResultMatcher contentTypeEsperado = MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(post("/api/v1/flight-reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payloadDto))
                .andExpectAll(statusEsperado,bodyEsperado,contentTypeEsperado)
                .andDo(print());
    }

}
