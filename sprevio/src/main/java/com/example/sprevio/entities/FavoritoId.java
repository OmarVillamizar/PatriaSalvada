package com.example.sprevio.entities;

import java.io.Serializable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoritoId implements Serializable {
    private int manga_id;
    private int usuario_id;
}
