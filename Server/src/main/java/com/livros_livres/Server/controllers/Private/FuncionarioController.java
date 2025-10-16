package com.livros_livres.Server.controllers.Private;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.livros_livres.Server.Registers.usuarios.Funcionario;
import com.livros_livres.Server.services.FuncionarioService;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

	// Services

	// @Autowired // Cria a classe construtora automaticamente.
	private FuncionarioService funcionarioService;

	// Classe construtora
	public FuncionarioController( FuncionarioService funcionarioService )
	{
		this.funcionarioService = funcionarioService;
	}

	@GetMapping("")
	public String helloFuncionario(){
		return "Hello Funcionário!";
	}

	@PostMapping("/novo")
	public Funcionario novoFuncionario(@RequestBody Funcionario body){
		return funcionarioService.salvarFuncionario(body);
	}

}
