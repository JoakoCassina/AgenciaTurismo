package com.example.AgenciaTurismo.service;


import com.example.AgenciaTurismo.repository.IFlightRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FlightServiceTest {

    @Mock
    private IFlightRepository flightRepository;

    @InjectMocks
    private  FlightService flightService;




}
