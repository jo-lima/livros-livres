package com.livros_livres.Server.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.livros_livres.Server.Registers.Emprestimos.Emprestimo;

// Repository that manages database connection.
// Here lives the methods such as save, delete, etc...

@Repository
public interface EmprestimoRepo extends JpaRepository<Emprestimo, Integer> {
}
