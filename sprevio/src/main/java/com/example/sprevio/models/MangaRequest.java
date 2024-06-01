package com.example.sprevio.models;

import lombok.Data;

import java.util.Date;

@Data
public class MangaRequest {
    private String nombre;
    private Date fechaLanzamiento;
    private int temporadas;
    private int paisId;
    private int anime;
    private int juego;
    private int pelicula;
    private int tipoId;
}
