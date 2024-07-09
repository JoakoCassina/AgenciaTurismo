package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IClientRepository extends JpaRepository<Client,Long> {

    Optional<Client> findByUsername(String username);
}
