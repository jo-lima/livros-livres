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
import com.livros_livres.Server.Registers.usuarios.Usuario;

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
    private MailService mailService;

    // ----= Methods =----
    // ----= Helper Methods =----
    // Universal secure token generator. (Should it be private insted? or in another class?)
    public String tokenGenerator(Integer tokenSize, Boolean compleate) {
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

    public Boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" +
                        "[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return email.matches(emailRegex);
    }

    // Busca pelos usuários logados no sistema.
    public UsuariosLogados buscaUsuarioLogado(String token) {
        if (token == null) return null;
        for (UsuariosLogados u : usuariosLogados) {
            if (u != null && token.equals(u.getToken())) {
                return u;
            }
        }
        return null;
    }

    public RetornoApi logarUsuario(LoginRequest loginRequest, Integer userPerm) {
        if (loginRequest == null || loginRequest.getSenha() == null || loginRequest.getUsuario() == null || userPerm == null) {
            return RetornoApi.errorLoginNotFound();
        }

        UsuariosLogados newUser; // Objeto que vai ser adicionado a array de usuarios logados

        newUser = new UsuariosLogados(
            loginRequest.getUsuario(),
            this.tokenGenerator(32, true),
            userPerm,
            java.time.LocalDateTime.now()
        );

        this.usuariosLogados.add(newUser);

        return RetornoApi.sucess("Usuário logado com sucesso!", newUser);
    }

    // Cria uma solicitacao de autenticacao no sistema para verificar a validade de um email.
    // TODO: Remover esse metodo e substituir pelo abaixo.
    public RetornoApi legacyCriarSolicitacaoAutenticacao(String email) {
        if (email == null || this.isValidEmail(email)) {
            return RetornoApi.error(400, "Email inválido.");
        }

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

    public UsuariosAuth criarSolicitacaoAutenticacao(String email) {
        UsuariosAuth newUser;
        String tokenGenerated = this.tokenGenerator(8, false);

        newUser = new UsuariosAuth(
            email,
            tokenGenerated,
            java.time.LocalDateTime.now()
        );

        this.usuariosAuths.add(newUser);

        return newUser;
    }

    // Procura por uma solicitação de autenticacao no sistema com os dados recebidos.
    // (Para verificar a validade de um email)
    // Caso encontre, marca o "verificado" dela como "true".
    public RetornoApi validaSolicitacaoAutenticacao(AuthRequest requestData) {
        for (UsuariosAuth usuarioAuth : usuariosAuths) {
            if( usuarioAuth.getEmail().equals(requestData.getEmail()) &&
                usuarioAuth.getAuthToken().equals(requestData.getCodigo().get()) )
            {
                usuarioAuth.setVerificado(true);
                usuarioAuth.setDataUserVerificado(LocalDateTime.now());
                System.out.println("Solicitação de autenticação de email encontrada com sucesso!");

                System.out.println("Solicitação de autenticação deletada do sistema.");
                return RetornoApi.sucess("Email verificado com sucesso!");
            }
        }
        return RetornoApi.errorNotFound("Código inválido.");
    }

    public RetornoApi enviarEmailTrocaSenha(String email) {
        if (email == null || !this.isValidEmail(email)) {
            return RetornoApi.error(400, "Email inválido.");
        }

        if (clienteService.buscaClienteEmail(email) == null) {
            return RetornoApi.sucess("Tentativa de envio de email efetuada com sucesso!");
        }

        UsuariosAuth newUser;
        RetornoApi retornoEmail = null;
        String tokenGenerated = this.tokenGenerator(10, false);

        newUser = new UsuariosAuth(
            email,
            tokenGenerated,
            java.time.LocalDateTime.now()
        );

        this.usuariosAuths.add(newUser);

        retornoEmail = mailService.sendMail("Olá! Você enviou uma solicitação para efetuar uma <strong>troca de senha.</strong>\n" +
                                            "Seu código verificador é: " + tokenGenerated,
                                    "Esqueceu sua senha do LivrosLivres?", email);

        return retornoEmail;
    }

    public RetornoApi validarEmailTrocaSenha(AuthRequest requestData) {
        // chamando a outra função que faz a exata mesma coisa
        Cliente buscaCliente;
        RetornoApi verificaAuth = validaSolicitacaoAutenticacao(requestData); // valida o código apresentado
        buscaCliente = clienteService.buscaClienteEmail(requestData.getEmail()); // procura por um cliente com esse email

        if (verificaAuth.getStatusCode() != 200) { return RetornoApi.errorInvalidCode(); }
        if(buscaCliente == null) { return RetornoApi.errorInvalidCode(); }

        UsuariosLogados newUser = new UsuariosLogados(
            requestData.getEmail(),
            this.tokenGenerator(32, true),
            java.time.LocalDateTime.now()
        );

        this.usuariosLogados.add(newUser);

        return RetornoApi.sucess("Usuário autenticado e logado com sucesso!", newUser);
    }

    // Troca a senha do usuario. Usuario precisa estar logado
    public RetornoApi trocarSenhaCliente(String token, String email, String novaSenha) {
        Cliente clienteAtual;
        Cliente clienteAlterado;
        UsuariosLogados buscaUsuario = this.buscaUsuarioLogado(token);

        if (token == null || email == null || novaSenha == null) {return RetornoApi.errorBadRequest("Request invalida. Insira valores para token, email e senha.");}
        if (buscaUsuario == null) { return RetornoApi.errorBadRequest("Usuário não logado.");}

        clienteAtual = clienteService.buscaClienteEmail(email);
        clienteAlterado = clienteService.alterarSenhaCliente(clienteAtual, novaSenha);

        return RetornoApi.sucess("Cliente alterado com sucesso!");
    }

    // Troca o email do usuario. Usuario precisa estar logado
    public RetornoApi trocarEmailCliente(String token, String email, String novoEmail) {
        Cliente clienteAtual;
        Cliente clienteAlterado;
        UsuariosLogados buscaUsuario = this.buscaUsuarioLogado(token);

        if (token == null || email == null || novoEmail == null) {return RetornoApi.errorBadRequest("Request invalida. Insira valores para token, email e novoEmail.");}
        if (buscaUsuario == null) { return RetornoApi.errorBadRequest("Usuário não logado.");}

        clienteAtual = clienteService.buscaClienteEmail(email);
        clienteAlterado = clienteService.alterarEmailCliente(clienteAtual, novoEmail);

        return RetornoApi.sucess("Cliente alterado com sucesso!", clienteAlterado);
    }

    // METHODS FOR DEBUG ONLY:
    public RetornoApi listarLogins() {
        if(debug){
            return RetornoApi.sucess("", usuariosLogados);
        }
        return RetornoApi.errorNotFound();
    }

    public RetornoApi listarAuths() {
        if(debug){
            return RetornoApi.sucess("", usuariosAuths);
        }
        return RetornoApi.errorNotFound();
    }

}
