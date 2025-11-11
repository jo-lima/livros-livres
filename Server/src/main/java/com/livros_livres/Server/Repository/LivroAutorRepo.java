package com.livros_livres.Server.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.livros_livres.Server.Registers.Livros.LivroAutor;

// Repositório que controla a conexão com o banco na tabela de autores.
// Aqui tem os métodos de salvar, deletar etc...
@Repository
public interface LivroAutorRepo extends JpaRepository<LivroAutor, Integer> {

    @Query(
        value = "SELECT la FROM tbl_LivroAutor la \n" +
        "WHERE la.Autor.id = :idAutor \n" +
        "AND la.Livro.id = :idLivro",
        nativeQuery = true
    )
    List<LivroAutor> findLivroAutoresBySearch(
        @Param("idLivro") Integer idLivro,
        @Param("idAutor") Integer idAutor
    );
}