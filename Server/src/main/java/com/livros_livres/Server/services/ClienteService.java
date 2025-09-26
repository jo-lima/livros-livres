package com.livros_livres.Server.services;

import com.livros_livres.Server.registers.usuarios.Cliente;

public class ClienteService {

    private Cliente cliente = new Cliente();

    public Cliente criarNovoCliente(Cliente dataCliente){
        cliente.setCpf(dataCliente.getCpf());
        cliente.setNome(dataCliente.getNome());
        cliente.setSenha(dataCliente.getSenha());
        cliente.setEndereco(dataCliente.getEndereco());
        cliente.setTelefone(dataCliente.getTelefone());
        cliente.setLivroFavorito(dataCliente.getLivroFavorito());

        // Criar na base tb

        return cliente;
    }

}
