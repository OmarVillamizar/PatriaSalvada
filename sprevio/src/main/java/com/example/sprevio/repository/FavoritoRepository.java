package com.example.sprevio.repository;

import com.example.sprevio.entities.Favorito;
import com.example.sprevio.entities.FavoritoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, FavoritoId> {

    List<Favorito> findByMangaId(int mangaId);

    List<Favorito> findByUsuarioUsername(String username);

    Optional<Favorito> findByUsuarioUsernameAndMangaId(String username, int mangaId);

}
