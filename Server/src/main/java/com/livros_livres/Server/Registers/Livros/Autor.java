package com.livros_livres.Server.Registers.Livros;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    @Column(name="Descricao", length = 2000)
    private String descricao;

    @Column(name="Citacao")
    private String citacao;

    @Column(name="Imagem", length = 512)
    private String imagem;

    // @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(mappedBy="autor")
    @JsonIgnoreProperties("autor")
    private Collection<Livro> livros;

    @Column(name="Ativo")
    private Boolean ativo;

    public Autor() {}

    public Autor(Collection<Livro> livros, String nome, String descricao, String imagem, String citacao, Boolean ativo){
        this.livros = livros;
        this.nome = nome;
        this.descricao = descricao;
        this.imagem = imagem;
        this.citacao = citacao;
        this.ativo = ativo;
    }
}