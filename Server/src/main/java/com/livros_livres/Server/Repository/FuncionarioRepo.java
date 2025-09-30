package com.livros_livres.Server.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.livros_livres.Server.registers.usuarios.Funcionario;

// Repository that manages database connection.
// Here lives the methods such as save, delete, etc...
// TODO: List all methods this class has.
@Repository
public interface FuncionarioRepo extends JpaRepository<Funcionario, Integer> {

}
