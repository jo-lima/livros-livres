package com.livros_livres.Server.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.livros_livres.Server.Registers.Emprestimos.Emprestimo;
import com.livros_livres.Server.Registers.Emprestimos.EmprestimoStatus;

// Repository that manages database connection.
// Here lives the methods such as save, delete, etc...
@Repository
public interface EmprestimoRepo extends JpaRepository<Emprestimo, Integer> {
    @Query(
        value = "SELECT E.* FROM tbl_UsuarioEmprestimo E "+
        "WHERE (:status IS NULL OR E.status = :status) "+
        "AND (:idUsuario IS NULL OR E.idUsuario  = :idUsuario) "+
        "AND (:idLivro IS NULL OR E.idLivro  = :idLivro)",
        nativeQuery = true
    )
    List<Emprestimo> findEmprestimoBySearch(
        @Param("status") EmprestimoStatus status,
        @Param("idUsuario") Integer idUsuario,
        @Param("idLivro") Integer idLivro
    );
}
