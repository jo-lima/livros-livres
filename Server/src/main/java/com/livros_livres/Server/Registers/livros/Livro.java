package com.livros_livres.Server.Registers.livros;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="tbl_Livro")
public class Livro {
    // idLivro, autor, nome, genero, paginas, isbn, descricao, estoque, editora, dataPublicacao, ativo
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idLivro")
    private int idLivro;

    /*@ManyToOne
    @JoinColumn(name="idAutor")
    private Autor autor;*/

    @OneToMany(mappedBy="livro")
    private List<LivroAutor> livroAutores;

    @Column(name="Nome")
    private String nome;

    @Column(name="Genero")
    private String genero;

    @Column(name="Paginas")
    private int paginas;

    @Column(name="ISBN")
    private String isbn;

    @Column(name="Descricao")
    private String descricao;

    // @Column(name="Avaliacao")
    // private double avaliacao;

    @Column(name="Estoque")
    private int estoque;

    @Column(name="Editora")
    private String editora;

    @Column(name="Data_publicacao")
    private LocalDate dataPublicacao;

    @Column(name="Ativo")
    private Boolean ativo;

    public Livro() {}

    public Livro(String nome, String genero, Integer paginas, String isbn, String descricao,
                  Integer estoque, String editora, LocalDate dataPublicacao, Boolean ativo ) {
        this.nome = nome;
        this.genero = genero;
        this.paginas = paginas;
        this.isbn = isbn;
        this.descricao = descricao;
        this.estoque = estoque;
        this.editora = editora;
        this.dataPublicacao = dataPublicacao;
        this.ativo = ativo;
    }
}