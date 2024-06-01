package com.example.sprevio.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class MangaResponse {
    private int id;
    private String nombre;
    private Date fechaLanzamiento;
    private int temporadas;
    private String pais;
    private int anime;
    private int juego;
    private int pelicula;
    private String tipo;
}
