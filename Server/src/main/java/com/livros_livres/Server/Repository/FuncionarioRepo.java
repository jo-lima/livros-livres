package com.livros_livres.Server.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.livros_livres.Server.Registers.Usuarios.Cliente;
import com.livros_livres.Server.Registers.Usuarios.Funcionario;

// Repository that manages database connection.
// Here lives the methods such as save, delete, etc...
@Repository
public interface FuncionarioRepo extends JpaRepository<Funcionario, Integer> {
    Optional<Funcionario> findByMatriculaIgnoreCase(String matricula);
}
