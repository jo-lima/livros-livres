package com.livros_livres.Server.controllers.Public;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.livros_livres.Server.Registers.RequestBody.AuthRequest;
import com.livros_livres.Server.Registers.RequestBody.LoginRequest;
import com.livros_livres.Server.Registers.Server.Authentication;
import com.livros_livres.Server.Registers.Server.RetornoApi;
import com.livros_livres.Server.Registers.usuarios.Cliente;
import com.livros_livres.Server.services.AuthenticationService;
import com.livros_livres.Server.services.MailService;

@RestController // Define a classe como um controlador rest
@RequestMapping("/auth") // raíz dos endpoints dessa classe
public class AuthenticationController {

	@Value("${livrosLivres.debug}") // Getting value from application.properties
	private boolean debug;

	// Services
	// @Autowired // Cria a classe construtora automaticamente.
	private AuthenticationService authService;
	private MailService mailService;

	// Classe construtora
	public AuthenticationController( AuthenticationService authService, MailService mailService )
	{
		this.authService = authService;
		this.mailService = mailService;
	}

	// Endpoints

    @PostMapping("/login")
    public RetornoApi loginUsuario(@RequestBody LoginRequest loginRequest) {
        return authService.logarUsuario(loginRequest);
    }

    @PostMapping("/enviar-validacao-email")
	public RetornoApi enviarCodigoValidarEmailUsuario(@RequestBody AuthRequest body) {
		return authService.criarSolicitacaoAutenticacao(body.getEmail());
	}

    @PostMapping("/verificar-validacao-email")
	public RetornoApi validarCodigoEmailUsuario(@RequestBody AuthRequest body) {
		return authService.validaSolicitacaoAutenticacao(body);
	}

    @PostMapping("/enviar-troca-senha")
	public RetornoApi enviarEmailTrocaSenha(@RequestBody AuthRequest body) {
		return authService.enviarEmailTrocaSenha(body.getEmail());
	}

    @PostMapping("/verificar-troca-senha")
	public RetornoApi validarEmailTrocaSenha(@RequestBody AuthRequest body) {
		return authService.validarEmailTrocaSenha(body);
	}

    @PostMapping("/trocar-senha-cliente")
	public RetornoApi trocarSenhaCliente(@RequestHeader("token") String token, @RequestBody Cliente body) {
		return authService.trocarSenhaCliente(token, body.getEmail(), body.getSenha());
	}

	// DEBUG ENDPOINTS
	// lista todos os emails verificados
	@PostMapping("/test-mail")
	public RetornoApi testMail(@RequestBody AuthRequest authRequest) {
		if(!debug) {return RetornoApi.errorNotFound();}
		return mailService.sendMail("Email teste!", "Email teste.", authRequest.getEmail());
	}

	// Lista todos usuarios logados
	@GetMapping("/login/listar")
	public RetornoApi userLogins() {
		if(!debug) {return RetornoApi.errorNotFound();}
		return authService.listarLogins();
	}

	// Lista todos usuarios autenticados/em autenticacao
	@GetMapping("/auths/listar")
	public RetornoApi userAuths() {
		if(!debug) {return RetornoApi.errorNotFound();}
		return authService.listarAuths();
	}

}
