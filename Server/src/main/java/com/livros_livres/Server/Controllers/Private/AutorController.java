package com.livros_livres.Server.Controllers.Private;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.livros_livres.Server.Registers.Livros.Autor;
import com.livros_livres.Server.Registers.Server.RetornoApi;
import com.livros_livres.Server.Services.AutorService;
import com.livros_livres.Server.Services.DebugService;

import io.micrometer.common.lang.Nullable;

@RestController
@RequestMapping("/autor")
public class AutorController {

	// Services
	// @Autowired // Cria a classe construtora automaticamente.
	private AutorService autorService;

	// Classe construtora
	public AutorController( AutorService autorService )
	{
		this.autorService = autorService;
	}

	@GetMapping("")
	public String helloAutor(){
		DebugService.log("Hello Autor!");
		return "Hello Autor!";
	}

	@PostMapping("/novo")
	public RetornoApi novoAutor(@Nullable @RequestHeader("token") String token, @RequestBody Autor body){
		DebugService.log("Chamado endpoint novoAutor");
		return autorService.novoAutor(token, body);
	}

	@GetMapping("/{id}/busca")
	public RetornoApi buscaAutor(@PathVariable("id") String idParam){
		DebugService.log("Chamado endpoint buscaAutor");
		int idAutor = Integer.parseInt(idParam);
		return autorService.buscaAutor(idAutor);
	}

	@PostMapping("/lista")
	public RetornoApi listaAutores(@Nullable @RequestBody Autor body){
		DebugService.log("Chamado endpoint listaAutores");
		return autorService.listaAutores(body);
	}

	@PostMapping("/{id}/atualizar")
	public RetornoApi atualizarAutor(@Nullable @RequestHeader("token") String token, @PathVariable("id") String idParam, @RequestBody Autor body){
		DebugService.log("Chamado endpoint atualizarAutor");
		int idAutor = Integer.parseInt(idParam);
		return autorService.atualizarAutor(token, idAutor, body);
	}

	@PostMapping("/{id}/inativar")
	public RetornoApi inativarAutor(@Nullable @RequestHeader("token") String token, @PathVariable("id") String idParam){
		DebugService.log("Chamado endpoint inativarAutor");
		int idAutor = Integer.parseInt(idParam);
		return autorService.inativarAutor(token, idAutor);
	}

	@PostMapping("/{id}/ativar")
	public RetornoApi ativarAutor(@Nullable @RequestHeader("token") String token, @PathVariable("id") String idParam){
		DebugService.log("Chamado endpoint ativarAutor");
		int idAutor = Integer.parseInt(idParam);
		return autorService.ativarAutor(token, idAutor);
	}

	@PostMapping("/{id}/deletar")
	public RetornoApi deletarAutor(@Nullable @RequestHeader("token") String token, @PathVariable("id") String idParam){
		DebugService.log("Chamado endpoint deletarAutor");
		int idAutor = Integer.parseInt(idParam);
		return autorService.deletarAutor(token, idAutor);
	}

}
