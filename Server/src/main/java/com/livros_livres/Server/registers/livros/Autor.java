package com.livros_livres.Server.registers.livros;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
    @Column(name="ativo")
    private Boolean ativo;

    public Autor() {
        this.nome="";
        this.descricao="";
        this.citacao="";
        this.ativo=true;
    };

    public Autor(String nome, String descricao, String citacao, Boolean ativo){
        this.nome=nome;
        this.descricao=descricao;
        this.citacao=citacao;
        this.ativo=ativo;
    }
}
