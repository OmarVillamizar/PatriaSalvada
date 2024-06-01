package com.example.sprevio.entities;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
	 public Usuario(String username) {
	        this.username = username;
	    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String nombre;
    private String email;
    private String password;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<Favorito> favoritos;
}

