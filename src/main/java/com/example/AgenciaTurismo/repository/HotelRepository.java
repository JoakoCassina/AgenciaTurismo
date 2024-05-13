package com.example.AgenciaTurismo.repository;

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

@Repository
public class HotelRepository implements IHotelRepository {

    private List<Hotel> hotelList;
    public HotelRepository() {
        System.out.println("Se esta inicializando el repository de Hoteles");
        this.hotelList = loadData();
    }
    @Override
    public List<Hotel> findAll() {
        return hotelList;
    }




    private List<Hotel> loadData() {
        List<Hotel> loadedData = null;
        File file;

        ObjectMapper objectMapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                .registerModule(new JavaTimeModule());

        TypeReference<List<Hotel>> typeRef = new TypeReference<>() {};

        try {
            file = ResourceUtils.getFile("classpath:hotels.json");
            loadedData = objectMapper.readValue(file, typeRef);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Failed while initializing DB, check your resources files");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed while initializing DB, check your JSON formatting.");
        }

        return loadedData;
    }


}
