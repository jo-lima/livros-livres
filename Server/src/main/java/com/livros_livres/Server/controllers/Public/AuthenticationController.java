package com.livros_livres.Server.controllers.Public;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.livros_livres.Server.Registers.Server.Authentication;
import com.livros_livres.Server.Registers.Server.LoginRequest;
import com.livros_livres.Server.Registers.Server.RetornoApi;
import com.livros_livres.Server.services.AuthenticationService;
import com.livros_livres.Server.services.MailService;

@RestController // Define a classe como um controlador rest
@RequestMapping("/auth") // raíz dos endpoints dessa classe
public class AuthenticationController {

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

    @GetMapping("/login/listar") // ENDPOINT APENAS PARA DEBUG
    public RetornoApi loginUsuario() {
        return authService.listarLogins();
    }

    @PostMapping("/trocar-senha")
	public RetornoApi trocaSenhaUsuario(@RequestBody LoginRequest body) {
		return RetornoApi.errorForbidden();
	}

    @PostMapping("/enviar-validacao-email")
	public RetornoApi enviarCodigoValidarEmailUsuario(@RequestBody LoginRequest body) {
		return mailService.sendMail(body.getSenha(), body.getUsuario());
		// return RetornoApi.errorForbidden();
	}

    @PostMapping("/verificar-validacao-email")
	public RetornoApi validarCodigoEmailUsuario(@RequestBody Authentication body) {
		return RetornoApi.errorForbidden();
	}

    @PostMapping("/enviar-troca-senha")
	public RetornoApi enviarEmailTrocaSenha(@RequestBody Authentication body) {
		return RetornoApi.errorForbidden();
	}

    @PostMapping("/verificar-troca-senha")
	public RetornoApi validarEmailTrocaSenha(@RequestBody Authentication body) {
		return RetornoApi.errorForbidden();
	}

}
