package com.example.sprevio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.sprevio.entities.Manga;

public interface MangaRepository extends JpaRepository<Manga, Integer> {
}