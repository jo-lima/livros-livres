package com.livros_livres.Server.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.livros_livres.Server.registers.livros.Autor;

@Repository
public interface AutorRepo extends JpaRepository<Autor, Integer> {

}
