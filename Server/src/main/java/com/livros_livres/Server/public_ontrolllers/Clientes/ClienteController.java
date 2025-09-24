package com.livros_livres.Server.public_ontrolllers.Clientes;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.livros_livres.Server.registers.livros.*;
import com.livros_livres.Server.registers.usuarios.*;

@RestController
@RequestMapping("/index")
public class ClienteController {

	@GetMapping("/cliente")
	public String helloCliente() {
		return "Hello Cliente!";
	}

	public Cliente getClienteById(int id) {
		return null;
	}

}
