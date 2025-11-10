package com.livros_livres.Server.Controllers.Public;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.livros_livres.Server.Registers.RequestBody.CriarEmprestimoRequest;
import com.livros_livres.Server.Registers.RequestBody.EmprestimoSearchRequest;
import com.livros_livres.Server.Registers.Server.RetornoApi;
import com.livros_livres.Server.Registers.Emprestimos.Emprestimo;
import com.livros_livres.Server.Services.EmprestimoService;

import io.micrometer.common.lang.Nullable;

@RestController // Define a classe como um controlador rest
@RequestMapping("/emprestimo") // raíz dos endpoints dessa classe
public class EmprestimoController {

	// Services
	private EmprestimoService emprestimoService;

	// Classe construtora
	EmprestimoController( EmprestimoService emprestimoService )
	{
		this.emprestimoService = emprestimoService;
	}

	// ENDPOINTS
	@PostMapping("/criar-pedido")
	public RetornoApi criarPedido(@Nullable @RequestHeader String token, @RequestBody CriarEmprestimoRequest body) {
		return emprestimoService.criarPedido(token, body);
    }

	@PostMapping("{id}/aceitar-pedido")
	public RetornoApi aceitarPedido(@Nullable @RequestHeader String token, @PathVariable("id") String idParam) {
		int idEmprestimo = Integer.parseInt(idParam);
		return emprestimoService.aceitarPedido(token, idEmprestimo);
    }

	@PostMapping("/criar-emprestimo")
	public RetornoApi criarEmprestimo(@Nullable @RequestHeader String token, @RequestBody CriarEmprestimoRequest body) {
		return emprestimoService.criarEmprestimo(token, body);
    }

	@PostMapping("{id}/cancelar")
	public RetornoApi cancelarPedido(@Nullable @RequestHeader String token, @PathVariable("id") String idParam) {
		int idEmprestimo = Integer.parseInt(idParam);
		return emprestimoService.cancelarEmprestimo(token, idEmprestimo);
    }

	@PostMapping("{id}/finalizar")
	public RetornoApi finalizarEmprestimo(@Nullable @RequestHeader String token, @PathVariable("id") String idParam) {
		int idEmprestimo = Integer.parseInt(idParam);
		return emprestimoService.finalizarEmprestimo(token, idEmprestimo);
    }

	@PostMapping("{id}/editar")
	public RetornoApi editarEmprestimo(@Nullable @RequestHeader String token, @PathVariable("id") String idParam, @RequestBody Emprestimo body) {
		int idEmprestimo = Integer.parseInt(idParam);
		return emprestimoService.editarEmprestimo(token, idEmprestimo, body);
    }

	@PostMapping("{id}/adiar")
	// unico campo necessario é o dataeestendidadevolucao
	public RetornoApi adiarEmprestimo(@Nullable @RequestHeader String token, @PathVariable("id") String idParam, @RequestBody Emprestimo body) {
		int idEmprestimo = Integer.parseInt(idParam);
		return emprestimoService.adiarEmprestimo(token, idEmprestimo, body);
    }

	@GetMapping("/lista")
	// unicos campos aceitos sao idCliente, idLivro e status
	public RetornoApi listarEmprestimos(@Nullable @RequestHeader String token, @RequestBody EmprestimoSearchRequest body){
		return emprestimoService.listaEmprestimo(token, body);
    }

	@GetMapping("/lista/cliente/{id}")
	public RetornoApi listarEmprestimos(@Nullable @RequestHeader String token, @PathVariable("id") String idParam, @Nullable @RequestBody EmprestimoSearchRequest body){
		int idCliente = Integer.parseInt(idParam);
		if(body==null){body = new EmprestimoSearchRequest();} // TODO: dirty fix...
		return emprestimoService.listaEmprestimoCliente(token, idCliente, body);
    }

	@GetMapping("/{id}/busca")
	public RetornoApi buscaEmprestimo(@Nullable @RequestHeader String token, @PathVariable("id") String idParam){
		int idEmprestimo = Integer.parseInt(idParam);
		return emprestimoService.buscaEmprestimo(token, idEmprestimo);
    }

}
