package com.livros_livres.Server.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.livros_livres.Server.Registers.livros.Livro;

@Repository
public interface LivroRepo extends JpaRepository<Livro, Integer>{

    @Query(
        value = "SELECT L.* FROM tbl_livros L " +
    "WHERE L.nome LIKE CONCAT ('%', :nome, '%') \n" +
    //"AND A.nome LIKE CONCAT ('%', :nome, '%') \n"
    "AND L.genero LIKE CONCAT ('%', :genero, '%') \n" +
    "AND L.paginas LIKE CONCAT ('%', :paginas, '%') \n" +
    "AND L.isbn LIKE CONCAT ('%', :isbn, '%') \n" +
    "AND L.descricao LIKE CONCAT ('%', :descricao, '%') \n" +
    "AND L.editora LIKE CONCAT ('%', :editora, '%') \n" +
    "AND L.datapublicacao LIKE CONCAT ('%', :datapublicacao, '%') \n" +
    "AND (:ativo IS NULL OR L.ativo = :ativo)",
        nativeQuery = true
    )

    List<Livro> findLivrosBySearch(
        @Param("nome") String nome,
        @Param("genero") String genero,
        @Param("paginas") String paginas,
        @Param("isbn") String isbn,
        @Param("descricao") String descricao,
        @Param("editora") String editora,
        @Param("datapublicacao") String datapublicacao,
        @Param("ativo") String ativo
    );
}
