package com.example.sprevio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.sprevio.entities.Pais;

public interface PaisRepository extends JpaRepository<Pais, Integer> {
}