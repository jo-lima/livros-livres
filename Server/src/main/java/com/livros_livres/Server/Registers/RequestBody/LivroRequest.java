package com.livros_livres.Server.Registers.RequestBody;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivroRequest {
    private Integer idLivro;
    private Integer autorId;

    private String nome;
    private String genero;
    private Integer paginas;
    private String isbn;
    private String descricao;
    private String imagem;
    private Integer estoque;
    private String editora;
    private LocalDate dataPublicacao;

    private Boolean ativo;

    public LivroRequest() {}

    public LivroRequest( Integer idLivro, Integer autorId, String nome, String genero, Integer paginas, String isbn, String descricao,
                  String imagem, Integer estoque, String editora, LocalDate dataPublicacao, Boolean ativo ) {

        this.idLivro = idLivro;
        this.autorId = autorId;
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