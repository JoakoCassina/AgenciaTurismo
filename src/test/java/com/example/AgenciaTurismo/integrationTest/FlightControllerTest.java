package com.example.AgenciaTurismo.integrationTest;

import com.example.AgenciaTurismo.dto.FlightDTO;
import com.example.AgenciaTurismo.dto.request.FlightConsultDTO;
import com.example.AgenciaTurismo.dto.response.FlightAvailableDTO;
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
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    /*@GetMapping("/listFlights") //
    public ResponseEntity<?> listFlightsDTO(){
        return new ResponseEntity<>(flightService.listFlightsDTO(), HttpStatus.OK);
    }*/
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
    /*@GetMapping("/flights")
    public ResponseEntity<?> vuelosDisponibles(@RequestParam @Future(message = "La fecha de entrada debe ser en el futuro") @DateTimeFormat(pattern = "dd-MM-yyyy")  LocalDate dateFrom,
                                               @RequestParam @Future(message = "La fecha de salida debe ser en el futuro") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateTo,
                                               @RequestParam @NotBlank String origin,
                                               @RequestParam @NotBlank String destination){
        FlightConsultDTO datos = new FlightConsultDTO(dateFrom, dateTo, origin, destination);
        return new ResponseEntity<>(flightService.vuelosDisponibles(datos), HttpStatus.OK);
    }*/

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


}
