package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.TouristicPackageDTO;
import com.example.AgenciaTurismo.dto.response.ResponseDTO;
import com.example.AgenciaTurismo.model.Client;
import com.example.AgenciaTurismo.model.TouristPackage;
import com.example.AgenciaTurismo.repository.IClientRepository;
import com.example.AgenciaTurismo.repository.ITouristicPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristicPackageService implements ITouristicPackageService{
    @Autowired
    ITouristicPackageRepository touristicPackageRepository;

    @Autowired
    IClientRepository clientRepository;

    public List<Client> listarClientes(){
        return clientRepository.findAll();
    }



//    @Override
//    public ResponseDTO createPackage(TouristicPackageDTO paquete) {
//
////        TouristPackage paqueteTuristico = new TouristPackage();
////        paqueteTuristico.setName(paquete.getName());
////        paqueteTuristico.setCreationDate(paquete.getCreationDate());
////        paqueteTuristico.setClientId();
//        return null;
//    }


}
