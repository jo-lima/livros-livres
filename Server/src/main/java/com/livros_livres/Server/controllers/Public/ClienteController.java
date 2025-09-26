package com.livros_livres.Server.controllers.Public;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.livros_livres.Server.registers.usuarios.Cliente;
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
	public Cliente novoCliente(@RequestBody Cliente body) {
		return clienteServices.criarNovoCliente(body);
	}

}
