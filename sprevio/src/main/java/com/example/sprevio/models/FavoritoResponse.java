package com.example.sprevio.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FavoritoResponse {
    private int id;
    private String username;
    private int mangaId;
}
