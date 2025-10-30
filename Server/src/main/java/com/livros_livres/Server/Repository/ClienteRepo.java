package com.livros_livres.Server.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.livros_livres.Server.Registers.usuarios.Cliente;

@Repository
public interface ClienteRepo extends JpaRepository<Cliente,Integer>{
    
    @Query(
        value = "SELECT * FROM tbl_Usuario \n" +
        "WHERE nome LIKE CONCAT('%', :nome, '%') \n" +
        /*"AND senha LIKE CONCAT('%', :senha, '%') \n" + */
        "AND endereco LIKE CONCAT('%', :endereco, '%') \n" +
        "AND telefone LIKE CONCAT('%', :telefone, '%') \n" +
        "AND (:ativo IS NULL OR ativo = :ativo)",
        nativeQuery = true
    )
    List<Cliente> findClientesbySearch(
        @Param("nome") String nome,
        @Param("endereco") String endereco,
        @Param("telefone") String telefone,
        @Param("ativo") String ativo
    );
}


