package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.model.People;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPeopleRepository extends JpaRepository<People, Long> {
}
