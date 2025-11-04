package com.livros_livres.Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livros_livres.Server.Registers.usuarios.Funcionario;
import com.livros_livres.Server.Repository.FuncionarioRepo;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepo funcionarioRepo;

    public Funcionario salvarFuncionario( Funcionario funcionarioData ){
        return funcionarioRepo.save(funcionarioData);
    }

}
