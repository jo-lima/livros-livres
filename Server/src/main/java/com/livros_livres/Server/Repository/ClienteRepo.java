package com.livros_livres.Server.Repository;

import java.util.List;
import java.util.Optional;

import org.antlr.v4.runtime.atn.SemanticContext.AND;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.livros_livres.Server.Registers.usuarios.Cliente;

// Repositório que controla a conexão com o banco na tabela de clientes.
// Aqui tem os métodos de salvar, deletar etc...
@Repository
public interface ClienteRepo extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByEmailIgnoreCase(String email);
}
