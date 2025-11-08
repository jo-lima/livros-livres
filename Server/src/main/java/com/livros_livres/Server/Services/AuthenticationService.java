package com.livros_livres.Server.Services;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.livros_livres.Server.Registers.RequestBody.AuthRequest;
import com.livros_livres.Server.Registers.Server.Authentication;
import com.livros_livres.Server.Registers.Server.RetornoApi;
import com.livros_livres.Server.Registers.Server.UsuariosAuth;
import com.livros_livres.Server.Registers.Server.UsuariosLogados;

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
    private MailService mailService;

    // ----= Methods =----
    // ----= Helper Methods =----
    // Universal secure token generator.
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

    // Check if user is admin or same client as sent mail
    public Boolean checkUserPerm(String token, String mail){
        UsuariosLogados usuarioLogado = this.buscaUsuarioLogado(token);

        if (usuarioLogado == null) {return false;}

        boolean isAdmin = usuarioLogado.getUserPerm() == 1;
        boolean isOwner = usuarioLogado.getUserPerm() == 0 &&
                          usuarioLogado.getUser().equals(mail);

        if (!isAdmin && !isOwner) {return false;}

        return true;
    }

    // Check if user is adm
    public Boolean checkAdminPerm(String token){
        UsuariosLogados usuarioLogado = this.buscaUsuarioLogado(token);

        if (usuarioLogado == null) {return false;}

        boolean isAdmin = usuarioLogado.getUserPerm() == 1;

        if (!isAdmin) {return false;}

        return true;
    }

    // Check if user is logged in
    public Boolean checkClientPerm(String token){
        UsuariosLogados usuarioLogado = this.buscaUsuarioLogado(token);

        if (usuarioLogado == null) {return false;}

        return true;
    }

    public UsuariosAuth buscaEmailAutenticado(String email) {
        if (email == null) return null;
        for (UsuariosAuth u : usuariosAuths) {
            if (u != null && email.equals(u.getEmail()) && u.getVerificado().equals(true)) {
                return u;
            }
        }
        return null;
    }

    // Busca e retorna por um usuario logado no sistema.
    public UsuariosLogados buscaUsuarioLogado(String token) {
        if (token == null) return null;
        for (UsuariosLogados u : usuariosLogados) {
            if (u != null && token.equals(u.getToken())) {
                return u;
            }
        }
        return null;
    }

    public UsuariosLogados logarUsuario(String user, Integer userPerm) {
        if (user == null || userPerm == null) {
            return null;
        }

        UsuariosLogados newUser; // Objeto que vai ser adicionado a array de usuarios logados

        newUser = new UsuariosLogados(
            user,
            this.tokenGenerator(32, true),
            userPerm,
            java.time.LocalDateTime.now()
        );

        this.usuariosLogados.add(newUser);

        return newUser;
    }

    // Deleta todos os logins do usuario de um email/matricula X. Usado no caso de uma alteração do mesmo.
    public Boolean deletarLoginsEmailUsuario(String user) {
        if (user == null) {
            return false;
        }

        // Evita ConcurrentModificationException ao remover enquanto itera
        boolean removed = usuariosLogados.removeIf(u -> u != null && user.equals(u.getUser()));
        return removed;
    }

    // Cria uma solicitacao de autenticacao no sistema para verificar a validade de um email.
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

    // Deleta uma solicitacao de autenticacao, para quando o usuario ja esta logado, por exemplo.
    public boolean deletaSolicitacaoAutenticacao(UsuariosAuth userAuth) {
        try {
            this.usuariosAuths.remove(userAuth);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Procura por uma solicitação de autenticacao no sistema com email e token.
    // Caso encontre, marca o "verificado" dela como "true".
    public UsuariosAuth verificaSolicitacaoAutenticacao(AuthRequest requestData) {
        // pra cada solicitacao de autenticacao...
        for (UsuariosAuth usuarioAuth : usuariosAuths) {
            // caso email e token estejam iguais os passados
            if( usuarioAuth.getEmail().equals(requestData.getEmail()) &&
                usuarioAuth.getAuthToken().equals(requestData.getCodigo().get()) )
            {
                // atualiza a solicitacao como "autenticada".
                usuarioAuth.setVerificado(true);
                usuarioAuth.setDataUserVerificado(LocalDateTime.now());
                DebugService.log("Solicitação de autenticação de email encontrada com sucesso!");

                DebugService.log("Solicitação de autenticação deletada do sistema.");
                return usuarioAuth;
            }
        }
        return null;
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
