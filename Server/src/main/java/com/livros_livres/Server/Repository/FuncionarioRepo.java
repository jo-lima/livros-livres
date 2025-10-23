package com.livros_livres.Server.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.livros_livres.Server.Registers.usuarios.Cliente;
import com.livros_livres.Server.Registers.usuarios.Funcionario;

// Repository that manages database connection.
// Here lives the methods such as save, delete, etc...
// TODO: List all methods this class has.
@Repository
public interface FuncionarioRepo extends JpaRepository<Funcionario, Integer> {
    Optional<Funcionario> findByMatriculaIgnoreCase(String matricula);
}
