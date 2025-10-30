package com.livros_livres.Server.controllers.Public;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.livros_livres.Server.Registers.RequestBody.AuthRequest;
import com.livros_livres.Server.Registers.RequestBody.LoginRequest;
import com.livros_livres.Server.Registers.Server.RetornoApi;
import com.livros_livres.Server.Registers.usuarios.Cliente;
import com.livros_livres.Server.services.ClienteService;

@RestController // Define a classe como um controlador rest
@RequestMapping("/cliente") // raíz dos endpoints dessa classe
public class ClienteController {

	// Services

	// @Autowired // Cria a classe construtora automaticamente.
	private ClienteService clienteServices;

	// Classe construtora
	public ClienteController( ClienteService clienteServices )
	{
		this.clienteServices = clienteServices;
	}

	// Endpoints
	@GetMapping("")
	public String helloCliente() {
		return "Hello Cliente!";
	}

	@PostMapping("/novo")
	public RetornoApi novoCliente(@RequestBody Cliente body) {
		return clienteServices.novoCliente(body);
	}

	@PostMapping("/login")
	public RetornoApi loginUsuario(@RequestBody LoginRequest loginRequest) {
		return clienteServices.loginCliente(loginRequest);
	}

	@PostMapping("/enviar-validacao-email")
	public RetornoApi enviarEmailValidarEmail(@RequestBody AuthRequest authRequest) {
		return clienteServices.enviarEmailValidarEmail(authRequest.getEmail());
	}

	@PostMapping("/verificar-validacao-email")
	public RetornoApi validarTokenValidarEmail(@RequestBody AuthRequest authRequest) {
		return clienteServices.validarTokenValidarEmail(authRequest);
	}

	@PostMapping("/enviar-email-esqueci-senha")
	public RetornoApi enviarEmailEsqueciSenha(@RequestBody AuthRequest authRequest) {
		return clienteServices.enviarEmailEsqueciSenha(authRequest.getEmail());
	}

	@PostMapping("/validar-token-troca-senha")
	public RetornoApi validarTokenTrocaSenha(@RequestBody AuthRequest authRequest) {
		return clienteServices.validarTokenTrocaSenha(authRequest);
	}

	@PostMapping("/alterar-senha")
	public RetornoApi alterarSenha(@RequestHeader("token") String token, @RequestBody Cliente clienteData) {
		return clienteServices.trocarSenhaCliente(token, clienteData.getSenha());
	}

	@PostMapping("/alterar-email")
	public RetornoApi alterarEmail(@RequestHeader("token") String token, @RequestBody Cliente clienteData) {
		return clienteServices.trocarEmailCliente(token, clienteData.getEmail());
	}

}
