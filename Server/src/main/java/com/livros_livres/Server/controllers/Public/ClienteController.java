package com.livros_livres.Server.controllers.Public;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.livros_livres.Server.services.DebugService;
import com.livros_livres.Server.Registers.Server.RetornoApi;


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
		DebugService.log("Hello cliente!");
		return "Hello Cliente!";
	}

	// AUTH METHODS
	@PostMapping("/login")
	public RetornoApi loginUsuario(@RequestBody LoginRequest loginRequest) {
		DebugService.log("Chamado endpoint loginUsuario");
		return clienteServices.loginCliente(loginRequest);
	}

	@PostMapping("/enviar-validacao-email")
	public RetornoApi enviarEmailValidarEmail(@RequestBody AuthRequest authRequest) {
		DebugService.log("Chamado endpoint enviarEmailValidarEmail");
		return clienteServices.enviarEmailValidarEmail(authRequest.getEmail());
	}

	@PostMapping("/verificar-validacao-email")
	public RetornoApi validarTokenValidarEmail(@RequestBody AuthRequest authRequest) {
		DebugService.log("Chamado endpoint validarTokenValidarEmail");
		return clienteServices.validarTokenValidarEmail(authRequest);
	}

	@PostMapping("/enviar-email-esqueci-senha")
	public RetornoApi enviarEmailEsqueciSenha(@RequestBody AuthRequest authRequest) {
		DebugService.log("Chamado endpoint enviarEmailEsqueciSenha");
		return clienteServices.enviarEmailEsqueciSenha(authRequest.getEmail());
	}

	@PostMapping("/validar-token-troca-senha")
	public RetornoApi validarTokenTrocaSenha(@RequestBody AuthRequest authRequest) {
		DebugService.log("Chamado endpoint validarTokenTrocaSenha");
		return clienteServices.validarTokenTrocaSenha(authRequest);
	}

	@PostMapping("/alterar-senha")
	public RetornoApi alterarSenha(@RequestHeader("token") String token, @RequestBody Cliente clienteData) {
		DebugService.log("Chamado endpoint alterarSenha");
		return clienteServices.trocarSenhaCliente(token, clienteData.getSenha());
	}

	@PostMapping("/alterar-email")
	public RetornoApi alterarEmail(@RequestHeader("token") String token, @RequestBody Cliente clienteData) {
		DebugService.log("Chamado endpoint alterarEmail");
		return clienteServices.trocarEmailCliente(token, clienteData.getEmail());
	}

	// CRUD METHODS
 	@PostMapping("/novo")
	public RetornoApi novoCliente(@RequestBody Cliente body) {
		DebugService.log("Chamado endpoint novoCliente");
		return clienteServices.novoCliente(body);
	}

	@GetMapping("/{id}/busca")
	public RetornoApi buscaCliente(@PathVariable("id") String idParam){
		int idCliente = Integer.parseInt(idParam);
		return clienteServices.buscaCliente(idCliente);
	}
	@GetMapping("/lista")
	public RetornoApi listaCliente(@RequestBody Cliente body){
		return clienteServices.listaClientes(body);
	}
	@PostMapping("/{id}/atualizar")
	public RetornoApi atualizarCliente(@RequestHeader("token") String token,@PathVariable("id") String idParam, @RequestBody Cliente body){
		int idCliente = Integer.parseInt(idParam);
		return clienteServices.atualizarCliente(token, idCliente, body);
	}
	@PostMapping("/{id}/inativar")
	public RetornoApi inativarCliente(@RequestHeader("token") String token,@PathVariable("id") String idParam){
		int idCliente = Integer.parseInt(idParam);
		return clienteServices.inativarCliente(token, idCliente);
	}
	@PostMapping("/{id}/ativar")
	public RetornoApi ativarCliente(@RequestHeader("token") String token, @PathVariable("id") String idParam){
		int idCliente = Integer.parseInt(idParam);
		return clienteServices.ativarCliente(token, idCliente);
	}

	//analisar
	@PostMapping("/{id}/deletar")
	public RetornoApi deletarCliente(@RequestHeader("token") String token, @PathVariable("id") String idParam){
		int idCliente = Integer.parseInt(idParam);
		return clienteServices.deletarCliente(token, idCliente);
	}
}
