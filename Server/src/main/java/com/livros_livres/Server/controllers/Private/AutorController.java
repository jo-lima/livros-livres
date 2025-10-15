package com.livros_livres.Server.controllers.Private;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.livros_livres.Server.registers.livros.Autor;
import com.livros_livres.Server.services.AutorService;

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
		return "Hello Autor!";
	}

	@PostMapping("/novo")
	public Autor novoAutor(@RequestBody Autor body){
		return autorService.salvarAutor(body);
	}

	@PostMapping("/{id}/deletar")
	public Boolean novoAutor(@PathVariable("id") String idParam){
		int idAutor = Integer.parseInt(idParam);
		return autorService.deletarAutor(idAutor);
	}

}
