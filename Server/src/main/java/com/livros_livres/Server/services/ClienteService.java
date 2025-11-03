package com.livros_livres.Server.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livros_livres.Server.Registers.Server.RetornoApi;
import com.livros_livres.Server.Registers.usuarios.Cliente;
import com.livros_livres.Server.Repository.ClienteRepo;

@Service
public class ClienteService {

    //private Cliente cliente = new Cliente();
    
    @Autowired
    private ClienteRepo clienteRepo;

    public RetornoApi criarNovoCliente(Cliente clienteData){
        clienteData.setAtivo(true);
        clienteRepo.save(clienteData);
        /*cliente.setCpf(dataCliente.getCpf());
        cliente.setNome(dataCliente.getNome());
        cliente.setSenha(dataCliente.getSenha());
        cliente.setEndereco(dataCliente.getEndereco());
        cliente.setTelefone(dataCliente.getTelefone());
        */
        // Criar na base tb

        return RetornoApi.sucess("Cliente criado com sucesso", clienteData);
    }

    public RetornoApi buscaCliente(Integer idCliente){
        Optional<Cliente> buscaCliente;

        buscaCliente = clienteRepo.findById(idCliente);
        if(!buscaCliente.isPresent()){
            return RetornoApi.errorNotFound("Nenhum cliente encontrado");
        }
        return RetornoApi.sucess("",buscaCliente);
    }

    public RetornoApi listaClientes(){
        List<Cliente> buscaCliente;

        buscaCliente = clienteRepo.findAll();
        if(buscaCliente.isEmpty()){
            return RetornoApi.errorNotFound("Nenhum autor encontrado.");
        }

        return RetornoApi.sucess("",buscaCliente);
    }

    public RetornoApi listaClientes(Cliente clienteData){
        List<Cliente> listaCliente;
        String clienteAtivo = null;

        if(clienteData==null){
            listaCliente = clienteRepo.findAll();
        }
        else{
            if(clienteData.getAtivo() != null){
                clienteAtivo = clienteData.getAtivo() == true ? "1" : "0";
            }
            listaCliente = clienteRepo.findClientesbySearch(
                //clienteData.getCpf(),
                clienteData.getNome(),
                //clienteData.getSenha(),
                clienteData.getEndereco(),
                clienteData.getTelefone(),
                clienteAtivo
            );
        }
        if(listaCliente.isEmpty()){
            return RetornoApi.errorNotFound("Nenhum cliente encontrado.");
        }
        return RetornoApi.sucess("",listaCliente);
    }

    public RetornoApi atualizarCliente(Integer idCliente, Cliente clienteData){
        Optional<Cliente> buscaCliente;
        Cliente cliente;

        buscaCliente = clienteRepo.findById(idCliente);

        if(!buscaCliente.isPresent()){
            return RetornoApi.errorNotFound("Nenhum aturo encontrado");
        }
        if(clienteData.getAtivo() != null){
            return RetornoApi.errorBadRequest("Campo ativo alterado apenas no endpoint de inativar ou ativar.");
        }

        cliente = buscaCliente.get();

        if(clienteData.getNome() != null){
            cliente.setNome(clienteData.getNome());
        }

        if(clienteData.getTelefone() != null){
            cliente.setTelefone(clienteData.getTelefone());
        }
         
        if(clienteData.getCpf() != null){
            cliente.setCpf(clienteData.getCpf());
        }       
         
        if(clienteData.getEndereco() != null){
            cliente.setEndereco(clienteData.getEndereco());
        }
         
        if(clienteData.getSenha() != null){
            cliente.setSenha(clienteData.getSenha());
        }               

        clienteRepo.save(cliente);

        return RetornoApi.sucess("Cliente atualizado com sucesso", cliente);
    }
        
    public RetornoApi deletarCliente(Integer idCliente){
        Optional<Cliente> buscaCliente;

        buscaCliente = clienteRepo.findById(idCliente);

        if(!buscaCliente.isPresent()){
            return RetornoApi.errorNotFound("Nenhum cliente encontrado");
        }

        clienteRepo.deleteById(idCliente);

        return RetornoApi.sucess("Cliente excluido com sucesso");
    }

    public RetornoApi inativarCliente(Integer idCliente){
        Optional<Cliente> buscaCliente;
        Cliente cliente;

        buscaCliente = clienteRepo.findById(idCliente);

        if(!buscaCliente.isPresent()){
            return RetornoApi.errorNotFound("Nenhum cliente encontrado");
        }
        if(!buscaCliente.get().getAtivo()){
            return RetornoApi.errorBadRequest("Cliente já inativo.");
        }

        cliente = buscaCliente.get();
        cliente.setAtivo(false);

        clienteRepo.save(cliente);

        return RetornoApi.sucess("Cliente inativado!", cliente);
    }

    public RetornoApi ativarCliente(Integer idCliente){
        Optional<Cliente> buscaCliente;
        Cliente cliente;

        buscaCliente = clienteRepo.findById(idCliente);

        if(!buscaCliente.isPresent()){
            return RetornoApi.errorNotFound("Nenhum cliente encontrado");
        }
        if(buscaCliente.get().getAtivo()){
            return RetornoApi.errorBadRequest("Cliente já ativo");
        }

        cliente = buscaCliente.get();
        cliente.setAtivo(true);
        clienteRepo.save(cliente);
        return RetornoApi.sucess("Cliente ativado com sucesso!");
    }








}
