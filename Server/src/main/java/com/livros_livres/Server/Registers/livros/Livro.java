package com.livros_livres.Server.Registers.livros;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="tbl_Livro")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idLivro")
    private int idLivro;

    @Column(name="titulo")
    private String titulo;

    @Column(name="genero")
    private String genero;

    @Column(name="paginas")
    private int paginas;

    @ManyToOne
    @JoinColumn(name="idAutor")
    private Autor autor;

    @Column(name="disponivel")
    private Boolean disponivel;

    public Livro() {
        this.titulo = "";
        this.genero = "";
        this.paginas = 0;
        this.disponivel = true;
    }

    public Livro(String titulo, String genero, int paginas, Autor autor, Boolean disponivel){
        this.titulo = titulo;
        this.genero = genero;
        this.paginas = paginas;
        this.autor = autor;
        this.disponivel = disponivel;
    }
}