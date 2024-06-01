package com.example.sprevio.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(FavoritoId.class)
public class Favorito {
    @Id
    private int manga_id;

    @Id
    private int usuario_id;

    @ManyToOne
    @MapsId("manga_id")
    @JoinColumn(name = "manga_id")
    private Manga manga;

    @ManyToOne
    @MapsId("usuario_id")
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}

