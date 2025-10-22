package com.livros_livres.Server.Registers.livros;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="tbl_Autor")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="IdAutor")
    private int idAutor;

    @Column(name="Nome")
    private String nome;

    @Column(name="Descricao")
    private String descricao;

    @Column(name="Citacao")
    private String citacao;

    // @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(mappedBy="autor")
    private Collection<Livro> livros;

    @Column(name="Ativo")
    private Boolean ativo;

    public Autor() {}

    public Autor(Collection<Livro> livros, String nome, String descricao, String citacao, Boolean ativo){
        this.livros = livros;
        this.nome = nome;
        this.descricao = descricao;
        this.citacao = citacao;
        this.ativo = ativo;
    }
}