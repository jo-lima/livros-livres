package com.livros_livres.Server.Registers.Livros;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @ManyToOne
    @JoinColumn(name="idAutor")
    @JsonIgnoreProperties({"livros", "autor"})
    private Autor autor;

    @Column(name="Nome", nullable=false)
    private String nome;

    @Column(name="Genero")
    private String genero;

    @Column(name="Paginas", nullable=false)
    private int paginas;

    @Column(name="ISBN", nullable=false, unique=true)
    private String isbn;

    @Column(name="Descricao")
    private String descricao;

    @Column(name="Imagem")
    private String imagem;

    @Column(name="Estoque")
    private int estoque;

    @Column(name="Editora")
    private String editora;

    @Column(name="DataPublicacao")
    private LocalDate dataPublicacao;

    @Column(name="Ativo")
    private Boolean ativo;

    public Livro() {}

    public Livro( Autor autor, String nome, String genero, Integer paginas, String isbn, String descricao,
                  String imagem, Integer estoque, String editora, LocalDate dataPublicacao, Boolean ativo ) {
        this.autor = autor;
        this.nome = nome;
        this.genero = genero;
        this.paginas = paginas;
        this.isbn = isbn;
        this.descricao = descricao;
        this.imagem = imagem;
        this.estoque = estoque;
        this.editora = editora;
        this.dataPublicacao = dataPublicacao;
        this.ativo = ativo;
    }
}