package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.ClienteDTO;
import com.example.AgenciaTurismo.model.Client;
import com.example.AgenciaTurismo.repository.IClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService implements IClientService{

    @Autowired
    IClientRepository clientRepository;

    @Autowired
    ModelMapper modelMapper;

    public List<Client> listarClientes(){
        return clientRepository.findAll();
    }

    @Override
    public ClienteDTO findByUsername(String username) {
        Optional<Client> clienteEncontrado = clientRepository.findByUsername(username);
        ClienteDTO clienteDTO = modelMapper.map(clienteEncontrado, ClienteDTO.class);
        return clienteDTO;
    }

//    @Override
//    public List<ClienteDTO> topClientes() {
//        return List.of();
//    }


}
