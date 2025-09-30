package com.livros_livres.Server.services;

import org.springframework.stereotype.Service;

import com.livros_livres.Server.registers.usuarios.Cliente;

@Service
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
