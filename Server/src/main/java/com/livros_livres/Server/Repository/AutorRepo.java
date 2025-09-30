package com.livros_livres.Server.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.livros_livres.Server.registers.livros.Autor;

// Repositório que controla a conexão com o banco na tabela de autores.
// Aqui tem os métodos de salvar, deletar etc...
// TODO: Listar todos métodos que essa classe tem.
@Repository
public interface AutorRepo extends JpaRepository<Autor, Integer> {

}
