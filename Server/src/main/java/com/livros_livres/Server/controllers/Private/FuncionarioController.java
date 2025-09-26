package com.livros_livres.Server.controllers.Private;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
public class FuncionarioController {

	@GetMapping("/funcionario")
	public String helloFuncionario(){
		return "Hello Funcionário!";
	}

}
