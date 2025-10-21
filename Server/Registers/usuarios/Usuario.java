package com.livros_livres.Server.Registers.usuarios;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idUsuario")
    private int idUsuario;

    @Column(name="nome")
    private String nome;

    @Column(name="email", unique = true)
    private String email;

    @Column(name="senha")
    private String senha;

    public Usuario() {
        this.nome = "";
        this.email = "";
        this.senha = "";
    }

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }
}