package com.livros_livres.Server.controllers.Public;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.livros_livres.Server.Registers.usuarios.Cliente;
import com.livros_livres.Server.services.ClienteService;
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
		return "Hello Cliente!";
	}

	@PostMapping("/novo")
	public Cliente novoCliente(@RequestBody Cliente body) {
		return clienteServices.criarNovoCliente(body);
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
	public RetornoApi atualizarCliente(@PathVariable("id") String idParam, @RequestBody Cliente body){
		int idCliente = Integer.parseInt(idParam);
		return clienteServices.atualizarCliente(idCliente, body);
	}
	@PostMapping("/{id}/inativar")
	public RetornoApi inativarCliente(@PathVariable("id") String idParam){
		int idCliente = Integer.parseInt(idParam);
		return clienteServices.inativarCliente(idCliente);
	}
	@PostMapping("/{id}/ativar")
	public RetornoApi ativarCliente(@PathVariable("id") String idParam){
		int idCliente = Integer.parseInt(idParam);
		return clienteServices.ativarCliente(idCliente);
	}
	@PostMapping("/{id}/deletar")
	public RetornoApi deletarCliente(@PathVariable("id") String idParam){
		int idCliente = Integer.parseInt(idParam);
		return clienteServices.deletarCliente(idCliente);
	}
}
