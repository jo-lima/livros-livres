package com.livros_livres.Server.controllers.Private;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.livros_livres.Server.Registers.RequestBody.LoginRequest;
import com.livros_livres.Server.Registers.Server.RetornoApi;
import com.livros_livres.Server.Registers.usuarios.Cliente;
import com.livros_livres.Server.Registers.usuarios.Funcionario;
import com.livros_livres.Server.services.ClienteService;
import com.livros_livres.Server.services.FuncionarioService;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

	// Services

	// @Autowired // Cria a classe construtora automaticamente.
	private FuncionarioService funcionarioService;
	private ClienteService clienteService;

	// Classe construtora
	public FuncionarioController( FuncionarioService funcionarioService)
	{
		this.funcionarioService = funcionarioService;
	}

	@GetMapping("")
	public String helloFuncionario(){
		return "Hello Funcionário!";
	}

    @PostMapping("/login")
    public RetornoApi loginUsuario(@RequestBody LoginRequest loginRequest) {
        return funcionarioService.loginFuncionario(loginRequest);
    }

	@PostMapping("/novo")
	public Funcionario novoFuncionario(@RequestHeader("token") String token, @RequestBody Funcionario body){
		return funcionarioService.salvarFuncionario(body);
	}

	@PostMapping("/novo-cliente")
	public RetornoApi novoCliente(@RequestHeader("token") String token, @RequestBody Cliente body){
		return funcionarioService.criaNovoCliente(token, body);
	}

}
