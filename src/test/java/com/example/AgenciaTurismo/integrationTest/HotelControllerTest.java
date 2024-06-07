package com.example.AgenciaTurismo.integrationTest;


import com.example.AgenciaTurismo.controller.HotelController;
import com.example.AgenciaTurismo.dto.HotelDTO;
import com.example.AgenciaTurismo.dto.request.HotelConsultDTO;
import com.example.AgenciaTurismo.dto.response.HotelAvailableDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class HotelControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
            .registerModule(new JavaTimeModule());

    private static final HotelDTO hotelDTO1 = new HotelDTO("CH-0002", "Cataratas Hotel", "Puerto Iguazú",
            "Doble", 6300, LocalDate.of(2025, 2, 10), LocalDate.of(2025, 3, 20), false);

    private static final HotelDTO hotelDTO2 = new HotelDTO("CH-0003", "Cataratas Hotel 2", "Puerto Iguazú",
            "Triple", 8200, LocalDate.of(2025, 2, 10), LocalDate.of(2025, 3, 23), false);

    private static final HotelDTO hotelDTO3 = new HotelDTO("HB-0001", "Hotel Bristol", "Buenos Aires",
            "Single", 5435, LocalDate.of(2025, 2, 10), LocalDate.of(2025, 3, 19), false);



    //US 01: test de integracion de listar hoteles
    @Test
    public void listarHotelesTestOK() throws Exception {
        ResultMatcher statusEsperado = MockMvcResultMatchers.status().isOk();
        ResultMatcher bodyEsperado = MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(List.of(hotelDTO1,hotelDTO2,hotelDTO3)));
        ResultMatcher contentTypeEsperado = MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(get("/api/v1/listHotels"))
                .andExpectAll(statusEsperado,bodyEsperado,contentTypeEsperado)
                .andDo(print());
    }

    //US 02: buscar hoteles disponibles

    @Test
    public void hotelesDisponiblesTestOK() throws Exception {
        String dateFromConsul = "10-02-2025";
        String dateToConsul = "20-03-2025";
        String destinationConsult = "Puerto Iguazú";

        HotelAvailableDTO respuestaEsperada = new HotelAvailableDTO(List.of(hotelDTO1));

        ResultMatcher statusEsperado = MockMvcResultMatchers.status().isOk();
        ResultMatcher bodyEsperado= MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(respuestaEsperada));
        ResultMatcher contentTypeEsperado = MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(get("/api/v1/hotels").param("dateFrom",dateFromConsul).param("dateTo",dateToConsul).param("destination",destinationConsult))
                .andExpectAll(statusEsperado,bodyEsperado,contentTypeEsperado)
                .andDo(print());
    }




}
