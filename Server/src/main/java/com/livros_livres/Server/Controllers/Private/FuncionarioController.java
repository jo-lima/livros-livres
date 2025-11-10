package com.livros_livres.Server.Controllers.Private;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.livros_livres.Server.Registers.RequestBody.LoginRequest;
import com.livros_livres.Server.Registers.Server.RetornoApi;
import com.livros_livres.Server.Registers.Usuarios.Cliente;
import com.livros_livres.Server.Registers.Usuarios.Funcionario;
import com.livros_livres.Server.Services.DebugService;
import com.livros_livres.Server.Services.FuncionarioService;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

	// Services

	// @Autowired // Cria a classe construtora automaticamente.
	private FuncionarioService funcionarioService;

	// Classe construtora
	public FuncionarioController( FuncionarioService funcionarioService)
	{
		this.funcionarioService = funcionarioService;
	}

	@GetMapping("")
	public String helloFuncionario(){
		DebugService.log("Hello funcionário!");
		return "Hello Funcionário!";
	}

    @PostMapping("/login")
    public RetornoApi loginUsuario(@RequestBody LoginRequest loginRequest) {
		DebugService.log("Chamado endpoint loginUsuario (Funcionario)");
        return funcionarioService.loginFuncionario(loginRequest);
    }

	@PostMapping("/novo")
	public Funcionario novoFuncionario(@RequestHeader("token") String token, @RequestBody Funcionario body){
		DebugService.log("Chamado endpoint novoFuncionario!");
		return funcionarioService.salvarFuncionario(token, body);
	}

	@PostMapping("/novo-cliente")
	public RetornoApi novoCliente(@RequestHeader("token") String token, @RequestBody Cliente body){
		DebugService.log("Chamado endpoint novoCliente (Funcionario)");
		return funcionarioService.criaNovoCliente(token, body);
	}

}
