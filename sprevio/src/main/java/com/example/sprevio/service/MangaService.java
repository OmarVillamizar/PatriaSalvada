package com.example.sprevio.service;

import com.example.sprevio.entities.Manga;
import com.example.sprevio.entities.Pais;
import com.example.sprevio.entities.Tipo;
import com.example.sprevio.repository.MangaRepository;
import com.example.sprevio.repository.PaisRepository;
import com.example.sprevio.repository.TipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MangaService {
    
    @Autowired
    private MangaRepository mangaRepository;

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private TipoRepository tipoRepository;

    public List<Manga> getAllMangas() {
        return mangaRepository.findAll();
    }

    public Optional<Manga> getMangaById(int id) {
        return mangaRepository.findById(id);
    }

    public Manga createManga(Manga manga) throws Exception {
        if (!paisRepository.existsById(manga.getPais().getId())) {
            throw new Exception("Pais no existe");
        }
        if (!tipoRepository.existsById(manga.getTipo().getId())) {
            throw new Exception("Tipo no existe");
        }
        return mangaRepository.save(manga);
    }

    public Manga updateManga(int id, Manga updatedManga) throws Exception {
        Optional<Manga> existingMangaOpt = mangaRepository.findById(id);
        if (existingMangaOpt.isPresent()) {
            Manga existingManga = existingMangaOpt.get();
            existingManga.setNombre(updatedManga.getNombre());
            existingManga.setFechaLanzamiento(updatedManga.getFechaLanzamiento());
            existingManga.setTemporadas(updatedManga.getTemporadas());
            existingManga.setAnime(updatedManga.getAnime());
            existingManga.setJuego(updatedManga.getJuego());
            existingManga.setPelicula(updatedManga.getPelicula());

            if (!paisRepository.existsById(updatedManga.getPais().getId())) {
                throw new Exception("Pais no existe");
            }
            if (!tipoRepository.existsById(updatedManga.getTipo().getId())) {
                throw new Exception("Tipo no existe");
            }
            existingManga.setPais(updatedManga.getPais());
            existingManga.setTipo(updatedManga.getTipo());

            return mangaRepository.save(existingManga);
        } else {
            throw new Exception("Objeto no encontrado");
        }
    }
}