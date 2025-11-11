package com.livros_livres.Server.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.livros_livres.Server.Registers.Usuarios.Cliente;

// Repositório que controla a conexão com o banco na tabela de clientes.
// Aqui tem os métodos de salvar, deletar etc...
@Repository
public interface ClienteRepo extends JpaRepository<Cliente,Integer>{

    @Query(
        value = "SELECT * FROM tbl_Cliente \n" +
        "WHERE (:nome IS NULL OR nome LIKE CONCAT('%', :nome, '%')) \n" +
        "AND (:endereco IS NULL OR endereco LIKE CONCAT('%', :endereco, '%')) \n" +
        "AND (:telefone IS NULL OR telefone LIKE CONCAT('%', :telefone, '%')) \n" +
        "AND (:ativo IS NULL OR ativo = :ativo)",
        nativeQuery = true
    )
    List<Cliente> findClientesBySearch(
        @Param("cpf") String cpf,
        @Param("nome") String nome,
        @Param("senha") String senha,
        @Param("endereco") String endereco,
        @Param("telefone") String telefone,
        @Param("ativo") String ativo
    );
    Optional<Cliente> findByEmailIgnoreCase(String email);

}


