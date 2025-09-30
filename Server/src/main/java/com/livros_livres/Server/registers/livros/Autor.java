package com.livros_livres.Server.registers.livros;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // Class is an entity
@Table(name="tbl_Autor") // Entity table name
public class Autor {

    // Table Columns
    @Id // Variable that stores the PK for the column.
    @Column(name="idAutor") // Name of column
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Annotation that generates the id's for us.
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

    // Getters
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
