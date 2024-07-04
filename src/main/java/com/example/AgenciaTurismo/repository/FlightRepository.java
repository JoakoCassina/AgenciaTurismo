package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.exception.InvalidReservationException;
import com.example.AgenciaTurismo.model.Flight;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

//@Repository
//public class FlightRepository implements IFlightRepository {
//
//    private List<Flight> flightList;
//    public FlightRepository() {
//        System.out.println("Se esta inicializando el repository de Vuelos");
//        this.flightList = loadData();
//    }
//    @Override
//    public List<Flight> findAll() {
//        return flightList;
//    }
//
//    //CREATE
//    public Flight save(Flight flight){
//        flightList.add(flight);
//        return flight;
//    }
//
//    //UPDATE
//    @Override
//    public Flight update(Flight flight) {
//        Optional<Flight> existingFlight = flightList.stream()
//                .filter(h -> h.getFlightCode().equals(flight.getFlightCode()))
//                .findFirst();
//        if (existingFlight.isPresent()) {
//            flightList.remove(existingFlight.get());
//            flightList.add(flight);
//            return flight;
//        }
//        throw new InvalidReservationException("No se encontró ningún vuelo para actualizar.");
//
////        for (int i = 0; i < flightList.size(); i++) {
////            Flight existingFlight = flightList.get(i);
////            if (existingFlight.getFlightCode().equals(flight.getFlightCode())) {
////                flightList.set(i, flight);
////                return flight;
////            }
////        }
////        throw new InvalidReservationException("No se encontró ningún vuelo para actualizar.");
//        // Si no se encuentra el hotel, podrías lanzar una excepción
//        // o manejarlo de alguna otra manera según tus necesidades.
//    }
//
//    //DELETE
//    @Override
//    public Flight deleteFlight(String flight) {
//
//        for (int i = 0; i < flightList.size(); i++) {
//            Flight existingFlight = flightList.get(i);
//            if (existingFlight.getFlightCode().equals(flight)) {
//                flightList.remove(existingFlight);
//                return existingFlight;
//            }
//        }
//        throw new InvalidReservationException("No se encontró ningún vuelo para eliminar.");
//    }
//
//
//
//
//    private List<Flight> loadData() {
//        List<Flight> loadedData = null;
//        File file;
//
//        ObjectMapper objectMapper = new ObjectMapper()
//                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
//                .registerModule(new JavaTimeModule());
//
//        TypeReference<List<Flight>> typeRef = new TypeReference<>() {};
//
//        try {
//            file = ResourceUtils.getFile("classpath:flights.json");
//            loadedData = objectMapper.readValue(file, typeRef);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            System.out.println("Failed while initializing DB, check your resources files");
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Failed while initializing DB, check your JSON formatting.");
//        }
//
//        return loadedData;
//    }
//}
