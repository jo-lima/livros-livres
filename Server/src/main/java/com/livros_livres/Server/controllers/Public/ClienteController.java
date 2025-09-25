package com.livros_livres.Server.controllers.Public;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
public class ClienteController {

	@GetMapping("/cliente")
	public String helloCliente() {
		return "Hello Cliente!";
	}

}
