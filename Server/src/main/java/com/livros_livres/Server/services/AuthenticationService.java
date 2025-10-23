package com.livros_livres.Server.services;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.livros_livres.Server.Registers.Server.Authentication;
import com.livros_livres.Server.Registers.Server.LoginRequest;
import com.livros_livres.Server.Registers.Server.RetornoApi;
import com.livros_livres.Server.Registers.Server.UsuariosLogados;
import com.livros_livres.Server.Registers.usuarios.Cliente;
import com.livros_livres.Server.Registers.usuarios.Funcionario;

import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
public class AuthenticationService implements Authentication{

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private List<UsuariosLogados> usuariosLogados = new ArrayList<>();

    @Value("${livrosLivres.debug}") // Getting value from application.properties
    private boolean debug;

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private FuncionarioService funcionarioService;

    // Methods

    private String tokenGenerator() {
        String CHARSOPTIONS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$";
        int TOKEN_SIZE = 64;
        StringBuilder tokenBuilder = new StringBuilder(TOKEN_SIZE);

        for (int i = 0; i < TOKEN_SIZE; i++) {
            int index = SECURE_RANDOM.nextInt(CHARSOPTIONS.length());
            tokenBuilder.append(CHARSOPTIONS.charAt(index));
        }
        return tokenBuilder.toString();
    }

    private Boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" +
                        "[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return email.matches(emailRegex);
    }

    public RetornoApi logarUsuario(LoginRequest loginRequest) {
        if (loginRequest == null || loginRequest.getSenha() == null || loginRequest.getUsuario() == null) {
            return RetornoApi.errorLoginNotFound();
        }

        UsuariosLogados newUser; // Objeto que vai ser adicionado a array de usuarios logados
        Boolean userFound = false;
        Cliente buscaCliente = new Cliente();
        Funcionario buscaFuncionario = new Funcionario();

        // se é um cliente logando com email
        if (isValidEmail(loginRequest.getUsuario()))
        {
            // procura por um cliente com esse email
            buscaCliente = clienteService.buscaClienteEmail(loginRequest.getUsuario());
            // confere se a senha ta certa
            if(buscaCliente == null || !loginRequest.getSenha().equals(buscaCliente.getSenha())) {
                return RetornoApi.errorLoginNotFound();
            }
            userFound = true;
        }
        // Se nao é com email, é funcionario com a matricula.
        else {
            buscaFuncionario = funcionarioService.buscaFuncionarioMatricula(loginRequest.getUsuario());
            // confere se a senha ta certa
            if( buscaFuncionario == null || !loginRequest.getSenha().equals(buscaFuncionario.getSenha())) {
                return RetornoApi.errorLoginNotFound();
            }
            userFound = true;
        }

        if(userFound == false) {
            return RetornoApi.errorLoginNotFound();
        }

        newUser = new UsuariosLogados(
            loginRequest.getUsuario(),
            this.tokenGenerator(),
            java.time.LocalDateTime.now()
        );

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
