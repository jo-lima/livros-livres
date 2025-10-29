package com.livros_livres.Server.services;

import java.lang.foreign.Linker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livros_livres.Server.Registers.Server.RetornoApi;
import com.livros_livres.Server.Registers.livros.Autor;
import com.livros_livres.Server.Registers.usuarios.Cliente;
import com.livros_livres.Server.Repository.AutorRepo;
import com.livros_livres.Server.Repository.ClienteRepo;

@Service
public class ClienteService {

    @Autowired // Automaticamente monta e importa a classe que faz a conexão da tabela do cliente no bd
	private ClienteRepo clienteRepo;

    public RetornoApi novoCliente( Cliente clienteData ){
        clienteData.setAtivo(true);
        clienteRepo.save(clienteData);
        return RetornoApi.sucess("Cliente criado com sucesso!", clienteData);
    }

    // Pesquisa por clientes com a combinacao inserida de email.
    public Cliente buscaClienteEmail(String email) {
        Optional<Cliente> cliente;

        cliente = clienteRepo.findByEmailIgnoreCase(email);

        if(cliente.isEmpty()) {
            return null;
        }

        return cliente.get();
    }

    // Troca a senha de um cliente.
    public Cliente alterarSenhaCliente(Cliente cliente, String novaSenha) {
        if(cliente == null || novaSenha == null) { return null; }

        cliente.setSenha(novaSenha);
        clienteRepo.save(cliente);

        return cliente;
    }
}
