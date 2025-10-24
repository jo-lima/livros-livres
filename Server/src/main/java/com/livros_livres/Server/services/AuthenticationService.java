package com.livros_livres.Server.services;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.livros_livres.Server.Registers.RequestBody.AuthRequest;
import com.livros_livres.Server.Registers.RequestBody.LoginRequest;
import com.livros_livres.Server.Registers.Server.Authentication;
import com.livros_livres.Server.Registers.Server.RetornoApi;
import com.livros_livres.Server.Registers.Server.UsuariosAuth;
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
    private List<UsuariosAuth> usuariosAuths = new ArrayList<>();

    @Value("${livrosLivres.debug}") // Getting value from application.properties
    private boolean debug;

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private FuncionarioService funcionarioService;
    @Autowired
    private MailService mailService;

    // Methods
    private String tokenGenerator(Integer tokenSize, Boolean compleate) {
        String CHARSOPTIONS = compleate == true ?
                                "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$" :
                                "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        int TOKEN_SIZE = tokenSize;
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
            this.tokenGenerator(32, true),
            java.time.LocalDateTime.now()
        );

        this.usuariosLogados.add(newUser);

        return RetornoApi.sucess("Usuário logado com sucesso!", newUser);
    }

    public RetornoApi trocarSenha(String token, String email, String novaSenha) {
        return RetornoApi.errorInternal();
    }

    public RetornoApi enviarCodigoValidarEmailUsuario(String email) {
        UsuariosAuth newUser;
        RetornoApi retornoEmail = null;
        String tokenGenerated = this.tokenGenerator(8, false);

        newUser = new UsuariosAuth(
            email,
            tokenGenerated,
            java.time.LocalDateTime.now()
        );

        this.usuariosAuths.add(newUser);

        retornoEmail = mailService.sendMail("Olá! Seu código verificador é: " + tokenGenerated,
                                    "Seu código de verificação.", email);

        return retornoEmail;
    }

    public RetornoApi validarCodigoEnviadoEmailUsuario(AuthRequest requestData) {
        for (UsuariosAuth usuarioAuth : usuariosAuths) {
            if( usuarioAuth.getEmail().equals(requestData.getEmail()) &&
                usuarioAuth.getAuthToken().equals(requestData.getCodigo().get()) )
            {
                usuarioAuth.setVerificado(true);
                usuarioAuth.setDataUserVerificado(LocalDateTime.now());
                return RetornoApi.sucess("Email verificado com sucesso!");
            }
        }
        return RetornoApi.errorNotFound("Código inválido.");
    }

    public RetornoApi enviarEmailTrocaSenha(String email) {
        return RetornoApi.errorInternal();
    }

    public RetornoApi validarEmailTrocaSenha(String email, String codigo) {
        return RetornoApi.errorInternal();
    }

    // METHODS FOR DEBUG ONLY
    public RetornoApi listarLogins() {
        if(debug){
            return RetornoApi.sucess("", usuariosLogados);
        }
        return RetornoApi.errorNotFound();
    }

    // METHODS FOR DEBUG ONLY
    public RetornoApi listarAuths() {
        if(debug){
            return RetornoApi.sucess("", usuariosAuths);
        }
        return RetornoApi.errorNotFound();
    }

}
