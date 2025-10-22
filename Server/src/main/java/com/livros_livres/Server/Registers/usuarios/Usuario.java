package com.livros_livres.Server.Registers.usuarios;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
@MappedSuperclass
public abstract class Usuario {

    @Column(name="Cpf")
    private String cpf;

    @Column(name="Nome")
    private String nome;

    @Column(name="Email", unique = true)
    private String email;

    @Column(name="Senha")
    private String senha;

    @Column(name="Ativo")
    private Boolean ativo;

    protected Usuario() {}

    protected Usuario(
            String cpf, String nome, String email,
            String senha, Boolean ativo
        ) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.ativo = ativo;
    }
}