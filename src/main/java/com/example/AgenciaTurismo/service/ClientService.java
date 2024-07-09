package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.ClienteDTO;
import com.example.AgenciaTurismo.repository.IClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService implements IClientService{

    @Autowired
    IClientRepository clientRepository;


    @Override
    public List<ClienteDTO> topClientes() {
        return List.of();
    }
}
