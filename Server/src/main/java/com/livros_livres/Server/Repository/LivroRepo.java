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
        value =
        "SELECT * FROM tbl_Livro l " +
        "WHERE (:nome IS NULL OR :nome = '' OR LOWER(l.Nome) LIKE CONCAT('%', LOWER(:nome), '%')) " +
        "AND (:genero IS NULL OR :genero = '' OR LOWER(l.Genero) LIKE CONCAT('%', LOWER(:genero), '%')) " +
        // "AND (:paginas IS NULL OR l.Paginas = :paginas) " +
        "AND (:isbn IS NULL OR :isbn = '' OR LOWER(l.Isbn) LIKE CONCAT('%', LOWER(:isbn), '%')) " +
        "AND (:descricao IS NULL OR :descricao = '' OR LOWER(l.Descricao) LIKE CONCAT('%', LOWER(:descricao), '%')) " +
        "AND (:editora IS NULL OR :editora = '' OR LOWER(l.Editora) LIKE CONCAT('%', LOWER(:editora), '%')) " +
        "AND (:dataPublicacao IS NULL OR l.DataPublicacao = :dataPublicacao) " +
        "AND (:ativo IS NULL OR l.Ativo = :ativo)",
        nativeQuery = true
    )
    List<Livro> findLivrosBySearch(
        @Param("nome") String nome,
        @Param("genero") String genero,
        @Param("paginas") Integer paginas,
        @Param("isbn") String isbn,
        @Param("descricao") String descricao,
        @Param("editora") String editora,
        @Param("dataPublicacao") LocalDate dataPublicacao,
        @Param("ativo") String ativo
    );
}
