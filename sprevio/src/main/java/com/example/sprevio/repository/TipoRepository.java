package com.example.sprevio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.sprevio.entities.Tipo;

public interface TipoRepository extends JpaRepository<Tipo, Integer> {
}