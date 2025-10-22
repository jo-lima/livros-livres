package com.livros_livres.Server.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.livros_livres.Server.Registers.Server.Authentication;
import com.livros_livres.Server.Registers.Server.RetornoApi;
import com.livros_livres.Server.Registers.Server.UsuariosLogados;
import com.livros_livres.Server.Registers.livros.Autor;

import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
public class AuthenticationService implements Authentication{

    private List<UsuariosLogados> usuariosLogados = new ArrayList<>();

    @Value("${livrosLivres.debug}") // Getting value from application.properties
    private boolean debug;

    @Autowired
    private AutorService autorService; // TODO: change to clienteService later

    // Methods
    public RetornoApi logarUsuario(String email, String senha) {
        UsuariosLogados newUser = new UsuariosLogados();
        Autor userData = new Autor(); // change to Cliente
        Object buscaAutor; // change to Cliente type and name buscaCliente

        userData.setNome(email);
        userData.setDescricao(senha); // change to email and senha

        // Method will change drastically. Using just for testing.
        // In real implementation, Cliente will have a method that returns a Cliente, and its search will be
        // exclusive. In this example it is a search. (you can type only half of password and will find someone)
        buscaAutor = autorService.listaAutores(userData).getBody();
        if(buscaAutor == null) {
            return RetornoApi.errorNotFound("Nenhum usuário encontrado para combinação de email e senha apresentados.");
        }

        newUser.setEmail(email);
        newUser.setToken("1234"); // generate token randomly
        newUser.setDataTokenGerado(java.time.LocalDateTime.now());

        this.usuariosLogados.add(newUser);

        return RetornoApi.sucess("Usuário logado com sucesso!", newUser);
    }

    // DEBUG ONLY
    public RetornoApi listarLogins() {
        if(debug){
            return RetornoApi.sucess("", usuariosLogados);
        }
        return RetornoApi.errorNotFound();
    }

    public RetornoApi trocarSenha(String token, String email, String novaSenha) {
        return RetornoApi.errorInternal();
    }

    public RetornoApi enviarCodigoValidarEmailUsuario(String email) {
        return RetornoApi.errorInternal();
    }

    public RetornoApi validarCodigoEnviadoEmailUsuario(String email, String codigo) {
        return RetornoApi.errorInternal();
    }

    public RetornoApi enviarEmailTrocaSenha(String email) {
        return RetornoApi.errorInternal();
    }

    public RetornoApi validarEmailTrocaSenha(String email, String codigo) {
        return RetornoApi.errorInternal();
    }

}
