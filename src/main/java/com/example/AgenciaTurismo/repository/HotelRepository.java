package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.exception.InvalidReservationException;
import com.example.AgenciaTurismo.model.Hotel;
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


//@Repository
//public class HotelRepository implements IHotelRepository {
//
//    private List<Hotel> hotelList;
//    public HotelRepository() {
//        System.out.println("Se esta inicializando el repository de Hoteles");
//        this.hotelList = loadData();
//    }
//    @Override
//    public List<Hotel> findAll() {
//        return hotelList;
//    }
//
//    //CREATE
//    public Hotel save(Hotel hotel){
//        hotelList.add(hotel);
//        return hotel;
//    }
//
//    //UPDATE
//    @Override
//    public Hotel update(Hotel hotel) {
////        Optional<Hotel> existingHotel = hotelList.stream()
////                .filter(h -> h.getHotelCode().equals(hotel.getHotelCode()))
////                .findFirst();
////        if (existingHotel.isPresent()) {
////            hotelList.remove(existingHotel.get());
////            hotelList.add(hotel);
////            return hotel;
////        }
////        return null;  // or throw an exception
//
//        for (int i = 0; i < hotelList.size(); i++) {
//            Hotel existingHotel = hotelList.get(i);
//            if (existingHotel.getHotelCode().equals(hotel.getHotelCode())) {
//                hotelList.set(i, hotel);
//                return hotel;
//            }
//        }
//        throw new InvalidReservationException("No se encontró ningún hotel para actualizar.");
//        // Si no se encuentra el hotel, podrías lanzar una excepción
//        // o manejarlo de alguna otra manera según tus necesidades.
//    }
//
//    //DELETE
//    @Override
//    public Hotel deleteHotel(String hotel) {
//
//        for (int i = 0; i < hotelList.size(); i++) {
//            Hotel existingHotel = hotelList.get(i);
//            if (existingHotel.getHotelCode().equals(hotel)) {
//                hotelList.remove(existingHotel);
//                return existingHotel;
//            }
//        }
//        throw new InvalidReservationException("No se encontró ningún hotel para eliminar.");
//    }
//
//
//
//
//        private List<Hotel> loadData () {
//            List<Hotel> loadedData = null;
//            File file;
//
//            ObjectMapper objectMapper = new ObjectMapper()
//                    .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
//                    .registerModule(new JavaTimeModule());
//
//            TypeReference<List<Hotel>> typeRef = new TypeReference<>() {
//            };
//
//            try {
//                file = ResourceUtils.getFile("classpath:hotels.json");
//                loadedData = objectMapper.readValue(file, typeRef);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                System.out.println("Failed while initializing DB, check your resources files");
//            } catch (IOException e) {
//                e.printStackTrace();
//                System.out.println("Failed while initializing DB, check your JSON formatting.");
//            }
//
//            return loadedData;
//        }
//
//
//}
