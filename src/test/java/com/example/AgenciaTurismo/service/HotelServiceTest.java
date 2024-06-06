package com.example.AgenciaTurismo.service;


import com.example.AgenciaTurismo.dto.HotelDTO;
import com.example.AgenciaTurismo.dto.request.HotelConsultDTO;
import com.example.AgenciaTurismo.dto.response.HotelAvailableDTO;
import com.example.AgenciaTurismo.model.Hotel;
import com.example.AgenciaTurismo.repository.IHotelRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class HotelServiceTest {

    @Mock
    private IHotelRepository hotelRepository;

    @InjectMocks
    private HotelService hotelService;

    private static final HotelDTO hotelDTO1 = new HotelDTO("CH-0002", "Cataratas Hotel", "Puerto Iguazú",
            "Doble", 6300, LocalDate.of(2025, 2, 10), LocalDate.of(2025, 3, 20), false);

    private static final HotelDTO hotelDTO2 = new HotelDTO("CH-0003", "Cataratas Hotel 2", "Puerto Iguazú",
            "Triple", 8200, LocalDate.of(2025, 2, 10), LocalDate.of(2025, 3, 23), false);

    private static final HotelDTO hotelDTO3 = new HotelDTO("HB-0001", "Hotel Bristol", "Buenos Aires",
            "Single", 5435, LocalDate.of(2025, 2, 10), LocalDate.of(2025, 3, 19), false);

    private static final Hotel hotel1 = new Hotel("CH-0002", "Cataratas Hotel", "Puerto Iguazú",
            "Doble", 6300, LocalDate.of(2025, 2, 10), LocalDate.of(2025, 3, 20), false);

    private static final Hotel hotel2 = new Hotel("CH-0003", "Cataratas Hotel 2", "Puerto Iguazú",
            "Triple", 8200, LocalDate.of(2025, 2, 10), LocalDate.of(2025, 3, 23), false);

    private static final Hotel hotel3 = new Hotel("HB-0001", "Hotel Bristol", "Buenos Aires",
            "Single", 5435, LocalDate.of(2025, 2, 10), LocalDate.of(2025, 3, 19), false);




    // US-0001: Listar todos los hoteles registrados
    @Test
    @DisplayName("Test ListarHotelesDTO OK")   //public List<HotelDTO> listHotelsDTO()
    public void listHotelesDTOTestOK(){
        List<Hotel> listaEsperada = new ArrayList<>();
        listaEsperada.add(hotel1);
        listaEsperada.add(hotel2);
        listaEsperada.add(hotel3);

        //ACT
        Mockito.when(hotelRepository.findAll()).thenReturn(listaEsperada);
        List<HotelDTO> listaDTO = hotelService.listHotelsDTO();

        //ASSERT
        Assertions.assertEquals(listaEsperada.size(), listaDTO.size());
    }



    @Test
    @DisplayName("Test HotelDisponibleDTO OK") // public HotelAvailableDTO hotelesDisponibles(HotelConsultDTO hotelConsultDTO)
    public void hotelesDisponiblesDTOTestOK() {

        LocalDate dateFrom = LocalDate.of(2025, 2, 10);
        LocalDate dateTo = LocalDate.of(2025, 3, 19);


        HotelConsultDTO hotelConsultado = new HotelConsultDTO(dateFrom,
                dateTo, "Buenos Aires");

        List<HotelDTO> listaEsperada = new ArrayList<>();
        listaEsperada.add(hotelDTO3);


        //ACT
        Mockito.when(hotelService.validarHotelesDisponibles(hotelConsultado)).thenReturn(listaEsperada);
        HotelAvailableDTO hotelDisponible = hotelService.hotelesDisponibles(hotelConsultado);

        //ASSERT
        Assertions.assertEquals(listaEsperada, hotelDisponible);

    }




}
