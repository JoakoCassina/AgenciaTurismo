package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.model.Flight;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Repository
public class FlightRepository implements IFlightRepository {





    private List<Flight> loadData() {
        List<Flight> loadedData = null;
        File file;

        ObjectMapper objectMapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        TypeReference<List<Flight>> typeRef = new TypeReference<>() {};

        try {
            file = ResourceUtils.getFile("classpath:flights.json");
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
