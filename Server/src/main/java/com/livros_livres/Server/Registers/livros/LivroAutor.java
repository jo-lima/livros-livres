package com.livros_livres.Server.Registers.livros;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="tbl_LivroAutor")
public class LivroAutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    @ManyToOne
    @JoinColumn(name="idAutor")
    private Autor autor;

    @ManyToOne
    @JoinColumn(name="idLivro")
    private Livro livro;

    public LivroAutor() {}

    public LivroAutor(Autor autor, Livro livro ) {
        this.autor = autor;
        this.livro = livro;
    }
}

