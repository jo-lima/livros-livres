package com.livros_livres.Server.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.livros_livres.Server.registers.usuarios.Funcionario;

@Repository
public interface FuncionarioRepo extends JpaRepository<Funcionario, Integer> {

}
