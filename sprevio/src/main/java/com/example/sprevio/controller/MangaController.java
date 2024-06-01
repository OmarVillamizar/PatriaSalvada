package com.example.sprevio.controller;

import com.example.sprevio.entities.Favorito;
import com.example.sprevio.entities.Manga;
import com.example.sprevio.entities.Pais;
import com.example.sprevio.entities.Tipo;
import com.example.sprevio.models.FavoritoRequest;
import com.example.sprevio.models.MangaRequest;
import com.example.sprevio.models.MangaResponse;
import com.example.sprevio.service.FavoritoService;
import com.example.sprevio.service.MangaService;

import jakarta.persistence.EntityNotFoundException;

import com.example.sprevio.repository.FavoritoRepository;
import com.example.sprevio.repository.MangaRepository;
import com.example.sprevio.repository.PaisRepository;
import com.example.sprevio.repository.TipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mangas")
public class MangaController {

    @Autowired
    private MangaService mangaService;

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private TipoRepository tipoRepository;

    @Autowired
    private MangaRepository mangaRepository;

    @Autowired
    private FavoritoService favoritoService;

    @Autowired
    private FavoritoRepository favoritoRepository;

    @GetMapping
    public List<Manga> getAllMangas() {
        return mangaService.getAllMangas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMangaById(@PathVariable int id) {
        Optional<Manga> mangaOpt = mangaService.getMangaById(id);
        if (mangaOpt.isPresent()) {
            return ResponseEntity.ok(mangaOpt.get());
        } else {
            return ResponseEntity.status(404).body("{\"error\":true,\"msg\":\"Objeto no encontrado\"}");
        }
    }

    @PostMapping
    public ResponseEntity<Object> crearManga(@RequestBody MangaRequest nuevoMangaRequest) {

        Optional<Pais> paisOpt = paisRepository.findById(nuevoMangaRequest.getPaisId());
        Optional<Tipo> tipoOpt = tipoRepository.findById(nuevoMangaRequest.getTipoId());

        if (paisOpt.isEmpty()) {
            return ResponseEntity.status(400).body("{\"error\":true,\"msg\":\"Pais no existe\"}");
        }

        if (tipoOpt.isEmpty()) {
            return ResponseEntity.status(400).body("{\"error\":true,\"msg\":\"Tipo no existe\"}");
        }

        Manga nuevoManga = new Manga();
        nuevoManga.setNombre(nuevoMangaRequest.getNombre());
        nuevoManga.setFechaLanzamiento(nuevoMangaRequest.getFechaLanzamiento());
        nuevoManga.setTemporadas(nuevoMangaRequest.getTemporadas());
        nuevoManga.setPais(paisOpt.get());
        nuevoManga.setTipo(tipoOpt.get());
        nuevoManga.setAnime(nuevoMangaRequest.getAnime());
        nuevoManga.setJuego(nuevoMangaRequest.getJuego());
        nuevoManga.setPelicula(nuevoMangaRequest.getPelicula());

        Manga mangaGuardado = mangaRepository.save(nuevoManga);
        MangaResponse mangaResponse = new MangaResponse(mangaGuardado.getId(), mangaGuardado.getNombre(), mangaGuardado.getFechaLanzamiento(), mangaGuardado.getTemporadas(), paisOpt.get().getNombre(), mangaGuardado.getAnime(), mangaGuardado.getJuego(), mangaGuardado.getPelicula(), tipoOpt.get().getNombre());

        return ResponseEntity.ok(mangaResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateManga(@PathVariable int id, @RequestBody Manga manga) {
        try {
            Manga updatedManga = mangaService.updateManga(id, manga);
            return ResponseEntity.ok(updatedManga);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("{\"error\":true,\"msg\":\"" + e.getMessage() + "\"}");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteManga(@PathVariable int id) {
        Optional<Manga> mangaOpt = mangaRepository.findById(id);
        if (mangaOpt.isEmpty()) {
            return ResponseEntity.status(404).body("{\"error\":true,\"msg\":\"Manga no encontrado\"}");
        }

        List<Favorito> favoritos = favoritoRepository.findByMangaId(id);
        favoritos.forEach(favorito -> favoritoRepository.delete(favorito));
        
        mangaRepository.delete(mangaOpt.get());
        return ResponseEntity.ok("{\"msg\":\"Manga eliminado\"}");
    }
    


    @PostMapping("/{id}/favoritos")
    public ResponseEntity<Object> agregarFavorito(@PathVariable int id, @RequestBody FavoritoRequest favoritoRequest) {
        try {
            Favorito favorito = favoritoService.agregarFavorito(favoritoRequest.getUsername(), id);
            if (favorito != null) {
                return ResponseEntity.ok(favorito);
            } else {
                return ResponseEntity.status(400).body("{\"error\":true,\"msg\":\"El favorito ya existe\"}");
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(400).body("{\"error\":true,\"msg\":\"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/{id}/favoritos")
    public ResponseEntity<?> getFavoritosByMangaId(@PathVariable int id) {
        List<Favorito> favoritos = favoritoRepository.findByMangaId(id);
        return ResponseEntity.ok(favoritos);
    }


    @GetMapping("/{id}/usuarios/{username}/favoritos")
    public ResponseEntity<?> getFavoritosByUsername(@PathVariable int id, @PathVariable String username) {
        List<Favorito> favoritos = favoritoRepository.findByUsuarioUsername(username);
        return ResponseEntity.ok(favoritos);
    }

    @DeleteMapping("/{id}/usuarios/{username}/favoritos")
    public ResponseEntity<?> eliminarFavoritoPorUsuarioYId(@PathVariable int id, @PathVariable String username) {
        favoritoService.eliminarFavoritoPorUsuarioYId(username, id);
        return ResponseEntity.ok("{\"msg\":\"Favorito eliminado\"}");
    }

    @DeleteMapping("/{id}/favoritos")
    public ResponseEntity<?> eliminarFavoritosPorMangaId(@PathVariable int id) {
        List<Favorito> favoritos = favoritoRepository.findByMangaId(id);
        favoritos.forEach(favorito -> favoritoRepository.delete(favorito));
        return ResponseEntity.ok("{\"msg\":\"Favoritos del manga eliminados\"}");
    }


}
