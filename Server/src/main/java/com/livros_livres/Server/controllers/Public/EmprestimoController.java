package com.livros_livres.Server.controllers.Public;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.livros_livres.Server.Registers.Server.RetornoApi;
import com.livros_livres.Server.Registers.emprestimos.Emprestimo;
import com.livros_livres.Server.services.EmprestimoService;

@RestController // Define a classe como um controlador rest
@RequestMapping("/emprestimo") // raíz dos endpoints dessa classe
public class EmprestimoController {

	// Services
	private EmprestimoService emprestimoService;

	// Classe construtora
	public EmprestimoController( EmprestimoService emprestimoService )
	{
		this.emprestimoService = emprestimoService;
	}

	// ENDPOINTS
	@PostMapping("/criar-pedido")
    // token = proprio user ou func
    // body = clientId, bookId, Data Prevista Devolucao
	public RetornoApi criarPedido(@RequestHeader String token, @RequestBody Emprestimo body) {
		return emprestimoService.test();
    }

	@PostMapping("/aceitar-pedido")
    // token = func only
    // body = only emprestimo Id
    // quando o cliente ja coletou o livro na livraria
	public RetornoApi aceitarPedido(@RequestHeader String token, @RequestBody Emprestimo body) {
		return emprestimoService.test();
    }

	@PostMapping("/criar-empréstimo")
    // token = funcionario
    // body = id cliente, id livro, data prevista devolucao
    // Cria um pedido e já aceita automaticamente.
	public RetornoApi criarEmprestimo(@RequestHeader String token, @RequestBody Emprestimo body) {
		return emprestimoService.test();
    }

	@PostMapping("/cancelar-pedido")
    // token = funcionario ou do proprio cliente.
    // body = id solicitacao
	public RetornoApi cancelarPedido(@RequestHeader String token, @RequestBody Emprestimo body) {
		return emprestimoService.test();
    }

	@PostMapping("/finalizar-emprestimo")
    // token = funcionario.
    // body = id solicitacao
	public RetornoApi finalizarEmprestimo(@RequestHeader String token, @RequestBody Emprestimo body) {
		return emprestimoService.test();
    }

	@PostMapping("/editar-emprestimo")
    // token = funcionario.
    // body = objeto completo do emprestimo
	public RetornoApi editarEmprestimo(@RequestHeader String token, @RequestBody Emprestimo body) {
		return emprestimoService.test();
    }

	@PostMapping("/adiar-emprestimo")
    // token = funcionario ou proprio cliente.
    // body = nova data devolucao (regras pra definicao da mesma no front-end, talvez limite razoavel aqui)
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
	public RetornoApi listarEmprestimos(@RequestHeader String token, @PathVariable("id") String idParam){
		return emprestimoService.test();
    }

}
