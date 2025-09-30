package com.livros_livres.Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livros_livres.Server.Repository.AutorRepo;
import com.livros_livres.Server.registers.livros.Autor;

@Service // Classe de serviço
public class AutorService {

    @Autowired // Automaticamente monta e importa a classe que faz a conexão da tabela do autor no bd
	private AutorRepo autorRepo;

    // Chama o método da classe repository que salva os dados de um Autor para o banco.
    public Autor salvarAutor( Autor autorData ){
        return autorRepo.save(autorData);
    }

}
