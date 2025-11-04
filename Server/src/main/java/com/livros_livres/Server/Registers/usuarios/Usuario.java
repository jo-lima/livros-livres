package com.livros_livres.Server.Registers.usuarios;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
@MappedSuperclass
public abstract class Usuario {

    @Column(name="Cpf")
    private String cpf;

    @Column(name="Nome", nullable=false)
    private String nome;

    @Column(name="Senha", nullable=false)
    private String senha;

    @Column(name="Ativo", nullable=false)
    private Boolean ativo;

    protected Usuario() {}

    protected Usuario(
            String cpf, String nome,
            String senha, Boolean ativo
        ) {
        this.cpf = cpf;
        this.nome = nome;
        this.senha = senha;
        this.ativo = ativo;
    }
}