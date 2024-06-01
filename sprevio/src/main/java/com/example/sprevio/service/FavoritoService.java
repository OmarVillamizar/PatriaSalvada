package com.example.sprevio.service;

import com.example.sprevio.entities.Favorito;
import com.example.sprevio.entities.Manga;
import com.example.sprevio.entities.Usuario;
import com.example.sprevio.repository.FavoritoRepository;
import com.example.sprevio.repository.MangaRepository;
import com.example.sprevio.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoritoService {

    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private MangaRepository mangaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public void eliminarFavorito(int mangaId) {
        List<Favorito> favoritos = favoritoRepository.findByMangaId(mangaId);
        favoritos.forEach(favoritoRepository::delete);
    }

    public List<Favorito> getFavoritosByUsername(String username) {
        return favoritoRepository.findByUsuarioUsername(username);
    }

    public void eliminarFavoritoPorUsuarioYId(String username, int mangaId) {
        Optional<Favorito> favoritoOpt = favoritoRepository.findByUsuarioUsernameAndMangaId(username, mangaId);
        favoritoOpt.ifPresent(favorito -> favoritoRepository.delete(favorito));
    }

    public Favorito agregarFavorito(String username, int mangaId) {
        Optional<Favorito> favoritoOpt = favoritoRepository.findByUsuarioUsernameAndMangaId(username, mangaId);
        if (favoritoOpt.isEmpty()) {
            Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);
            Optional<Manga> mangaOpt = mangaRepository.findById(mangaId);

            if (usuarioOpt.isPresent() && mangaOpt.isPresent()) {
                Favorito favorito = new Favorito();
                favorito.setUsuario(usuarioOpt.get());
                favorito.setManga(mangaOpt.get());
                return favoritoRepository.save(favorito);
            } else {
                throw new EntityNotFoundException("Usuario o manga no encontrado");
            }
        }
        return null; // o lanzar una excepción indicando que ya existe el favorito
    }
}
