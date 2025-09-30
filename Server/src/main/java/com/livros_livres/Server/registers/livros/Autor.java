package com.livros_livres.Server.registers.livros;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tbl_Autor")
public class Autor {

    @Id
    @Column(name="idAutor")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAutor;

    @Column(name="nome")
    private String nome;
    @Column(name="descricao")
    private String descricao;
    @Column(name="citacao")
    private String citacao;

    public Autor() {};

    public Autor(String nome, String descricao, String citacao){
        this.nome=nome;
        this.descricao=descricao;
        this.citacao=citacao;
    }

    public void setIdAutor(int idAutor){
        this.idAutor = idAutor;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }
    public void setCitacao(String citacao){
        this.citacao = citacao;
    }

    public int getIdAutor(){
        return this.idAutor;
    }
    public String getNome(){
        return this.nome;
    }
    public String getDescricao(){
        return this.descricao;
    }
    public String getCitacao(){
        return this.citacao;
    }
}
