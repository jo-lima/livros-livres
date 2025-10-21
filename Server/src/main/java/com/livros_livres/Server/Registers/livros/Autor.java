package com.livros_livres.Server.Registers.livros;

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
    @Column(name="idAutor")
    private int idAutor;

    @Column(name="nome")
    private String nome;
    
    @Column(name="descricao")
    private String descricao;
    
    @Column(name="citacao")
    private String citacao;
    
    @Column(name="ativo")
    private Boolean ativo;

    public Autor() {
        this.nome = "";
        this.descricao = "";
        this.citacao = "";
        this.ativo = true;
    }

    public Autor(String nome, String descricao, String citacao, Boolean ativo){
        this.nome = nome;
        this.descricao = descricao;
        this.citacao = citacao;
        this.ativo = ativo;
    }
}