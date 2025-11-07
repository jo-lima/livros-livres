package com.livros_livres.Server.Controllers.Public;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.livros_livres.Server.Registers.RequestBody.PedidoEmprestimoRequest;
import com.livros_livres.Server.Registers.Server.RetornoApi;
import com.livros_livres.Server.Registers.Emprestimos.Emprestimo;
import com.livros_livres.Server.Services.EmprestimoService;

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
	public RetornoApi criarPedido(@RequestHeader String token, @RequestBody PedidoEmprestimoRequest body) {
		return emprestimoService.test();
    }

	@PostMapping("/aceitar-pedido")
	public RetornoApi aceitarPedido(@RequestHeader String token, @RequestBody Emprestimo body) {
		return emprestimoService.test();
    }

	@PostMapping("/criar-empréstimo")
	public RetornoApi criarEmprestimo(@RequestHeader String token, @RequestBody Emprestimo body) {
		return emprestimoService.test();
    }

	@PostMapping("/cancelar-pedido")
	public RetornoApi cancelarPedido(@RequestHeader String token, @RequestBody Emprestimo body) {
		return emprestimoService.test();
    }

	@PostMapping("/finalizar-emprestimo")
	public RetornoApi finalizarEmprestimo(@RequestHeader String token, @RequestBody Emprestimo body) {
		return emprestimoService.test();
    }

	@PostMapping("/editar-emprestimo")
	public RetornoApi editarEmprestimo(@RequestHeader String token, @RequestBody Emprestimo body) {
		return emprestimoService.test();
    }

	@PostMapping("/adiar-emprestimo")
	public RetornoApi adiarEmprestimo(@RequestHeader String token, @RequestBody Emprestimo body) {
		return emprestimoService.test();
    }

	@GetMapping("/listar-emprestimos")
    // token = funcionario ou proprio cliente.
    // Body: Emprestimo completo, status, date range, etc... (esse vai ser chatinho.....)
	public RetornoApi listarEmprestimos(@RequestHeader String token, @RequestBody Emprestimo body){
		return emprestimoService.test();
    }

	@GetMapping("/listar-emprestimos/{id}")
    // token = funcionario ou proprio cliente.
	// id == clienteId
	public RetornoApi listarEmprestimos(@RequestHeader String token, @PathVariable("id") String idParam){
		return emprestimoService.test();
    }

	@GetMapping("/{id}/busca")
    // token = funcionario ou proprio cliente.
	public RetornoApi buscaEmprestimo(@RequestHeader String token, @PathVariable("id") String idParam){
		return emprestimoService.test();
    }

}
