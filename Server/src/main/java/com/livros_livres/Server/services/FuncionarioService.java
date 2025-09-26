package com.livros_livres.Server.services;

import com.livros_livres.Server.registers.usuarios.Funcionario;

public class FuncionarioService {

    private Funcionario funcionario = new Funcionario();

    public Funcionario criarNovoFuncionario(Funcionario dataFuncionario){
        funcionario.setCpf(dataFuncionario.getCpf());
        funcionario.setNome(dataFuncionario.getNome());
        funcionario.setSenha(dataFuncionario.getSenha());
        funcionario.setEndereco(dataFuncionario.getEndereco());
        funcionario.setTelefone(dataFuncionario.getTelefone());
        funcionario.setMatricula(dataFuncionario.getMatricula());

        // Criar na base tb

        return funcionario;
    }

}
