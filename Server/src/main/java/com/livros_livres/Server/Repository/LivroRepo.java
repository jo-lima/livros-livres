package com.livros_livres.Server.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.livros_livres.Server.Registers.livros.Livro;

@Repository
public interface LivroRepo extends JpaRepository<Livro, Integer>{

    @Query(
        value = "SELECT * FROM tbl_Livro " +
    "WHERE Nome LIKE CONCAT ('%', :Nome, '%') \n" +
    //"AND A.nome LIKE CONCAT ('%', :nome, '%') \n"
    "AND Genero LIKE CONCAT ('%', :Genero, '%') \n" +
    "AND Paginas LIKE CONCAT ('%', :Paginas, '%') \n" +
    "AND Isbn LIKE CONCAT ('%', :Isbn, '%') \n" +
    "AND Descricao LIKE CONCAT ('%', :Descricao, '%') \n" +
    "AND Editora LIKE CONCAT ('%', :Editora, '%') \n" +
    "AND Data_publicacao LIKE CONCAT ('%', :Data_publicacao, '%') \n" +
    "AND (:Ativo IS NULL OR Ativo = :Ativo)",
        nativeQuery = true
    )

    List<Livro> findLivrosBySearch(
        @Param("Nome") String nome,
        @Param("Genero") String genero,
        @Param("Paginas") int paginas,
        @Param("Isbn") String isbn,
        @Param("Descricao") String descricao,
        @Param("Editora") String editora,
        @Param("Data_publicacao") LocalDate datapublicacao,
        @Param("Ativo") String ativo
    );
}
