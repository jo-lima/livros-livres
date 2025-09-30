package com.livros_livres.Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livros_livres.Server.Repository.AutorRepo;
import com.livros_livres.Server.registers.livros.Autor;

@Service
public class AutorService {

	@Autowired
	private AutorRepo autorRepo;

    public Autor salvarAutor( Autor autorData ){
        return autorRepo.save(autorData);
    }

}
