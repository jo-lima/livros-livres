package com.livros_livres.Server.private_controllers.colaboradores;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.livros_livres.Server.registers.usuarios.*;

@RestController
@RequestMapping("/index")
public class FuncionarioController {

	@GetMapping("/funcionario")
	public String helloFuncionario(){
		return "Hello Funcionário!";
	}

	public Funcionario getFuncionarioById(int id) {
		return null;
	}

}
