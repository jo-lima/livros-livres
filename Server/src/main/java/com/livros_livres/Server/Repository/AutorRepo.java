package com.livros_livres.Server.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.livros_livres.Server.Registers.livros.Autor;

// Repositório que controla a conexão com o banco na tabela de autores.
// Aqui tem os métodos de salvar, deletar etc...
@Repository
public interface AutorRepo extends JpaRepository<Autor, Integer> {

    @Query(
        value = "SELECT * FROM tbl_Autor \n" +
        "WHERE nome LIKE CONCAT('%', :nome, '%') \n" +
        "AND citacao LIKE CONCAT('%', :citacao, '%') \n" +
        "AND descricao LIKE CONCAT('%', :descricao, '%') \n" +
        "AND (:ativo IS NULL OR ativo = :ativo)",
        nativeQuery = true
    )
    List<Autor> findAutoresBySearch(
        @Param("nome") String nome,
        @Param("citacao") String citacao,
        @Param("descricao") String descricao,
        @Param("ativo") String ativo
    );
}