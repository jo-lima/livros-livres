package com.livros_livres.Server.Services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livros_livres.Server.Registers.RequestBody.PedidoEmprestimoRequest;
import com.livros_livres.Server.Registers.Server.RetornoApi;
import com.livros_livres.Server.Registers.Server.UsuariosLogados;
import com.livros_livres.Server.Registers.Emprestimos.Emprestimo;
import com.livros_livres.Server.Registers.Livros.Livro;
import com.livros_livres.Server.Registers.Usuarios.Cliente;
import com.livros_livres.Server.Repository.EmprestimoRepo;

@Service
public class EmprestimoService {

    @Autowired
    EmprestimoRepo emprestimoRepo;
    @Autowired
    AuthenticationService authService;
    @Autowired
    ClienteService clienteService;
    @Autowired
    LivroService livroService;

    public RetornoApi test() {
        return RetornoApi.errorForbidden();
    }

    public RetornoApi criarPedido(String token, PedidoEmprestimoRequest pedidoEmprestimo) {
        UsuariosLogados usuarioLogado = authService.buscaUsuarioLogado(token);
        if(usuarioLogado == null || usuarioLogado.getUserPerm() != 0 ){return RetornoApi.errorForbidden();}

        Livro livroPedido = livroService.buscaLivroById(pedidoEmprestimo.getLivroId());
        Cliente cliente = clienteService.buscaClienteEmail(usuarioLogado.getUser());

        // TODO: Validação confere se usuário tem +3 pedidos feitos
        if(cliente.getClienteId() != pedidoEmprestimo.getClienteId()) {return RetornoApi.errorForbidden();}
        if(livroPedido.getEstoque() <= 0 || livroPedido.getAtivo() != true) {return RetornoApi.errorBadRequest("Este livro não possui disponibilidade no estoque!");}

        Emprestimo newEmprestimo = new Emprestimo();
        Emprestimo emprestimoCriado;

        newEmprestimo.setLivro(livroPedido);
        newEmprestimo.setCliente(cliente);
        newEmprestimo.setDataSolicitacaoEmprestimo(LocalDate.now());

        emprestimoCriado = emprestimoRepo.save(newEmprestimo);

        return RetornoApi.sucess("Pedido de empréstimo criado com sucesso!", emprestimoCriado);
    }

    public RetornoApi aceitarPedido(String token, Emprestimo emprestimo) {
        UsuariosLogados usuarioLogado = authService.buscaUsuarioLogado(token);
        if(usuarioLogado == null || usuarioLogado.getUserPerm() != 1 ){return RetornoApi.errorForbidden();}
        Emprestimo emprestimoCriado;

        emprestimo.setDataColeta(LocalDate.now());

        emprestimoCriado = emprestimoRepo.save(emprestimo);

        // Confere se qntd livro >= 0
        // TODO: livroService.removeEstoque(emprestimo.getLivro(), 1);

        return RetornoApi.sucess("Pedido aceito e empréstimo criado com sucesso!", emprestimoCriado);
    }

    public RetornoApi criarEmprestimo(String token, PedidoEmprestimoRequest pedidoEmprestimo) {
        UsuariosLogados usuarioLogado = authService.buscaUsuarioLogado(token);
        if(usuarioLogado == null || usuarioLogado.getUserPerm() != 1 ){return RetornoApi.errorForbidden();}

        Livro livroPedido = livroService.buscaLivroById(pedidoEmprestimo.getLivroId());
        Cliente cliente = clienteService.buscaClienteById(pedidoEmprestimo.getClienteId());

        if(livroPedido.getEstoque() <= 0 || livroPedido.getAtivo() != true) {return RetornoApi.errorBadRequest("Este livro não possui disponibilidade no estoque!");}

        Emprestimo newEmprestimo = new Emprestimo();
        Emprestimo emprestimoCriado;

        newEmprestimo.setLivro(livroPedido);
        newEmprestimo.setCliente(cliente);
        newEmprestimo.setDataSolicitacaoEmprestimo(LocalDate.now());

        emprestimoCriado = emprestimoRepo.save(newEmprestimo);

        // TODO: livroService.removeEstoque(emprestimo.getLivro(), 1);

        return RetornoApi.sucess("Empréstimo criado com sucesso!", emprestimoCriado);
    }

    public RetornoApi cancelarEmprestimo(String token, Emprestimo emprestimo) {
        UsuariosLogados usuarioLogado = authService.buscaUsuarioLogado(token);
        if(usuarioLogado == null || usuarioLogado.getUserPerm() == null ){return RetornoApi.errorForbidden();}
        if(emprestimo.getDataColeta() != null) {return RetornoApi.errorBadRequest("Pedido de empréstimo já concluído! Para finalizar o empréstimo é necessário contatar um funcionário.");}

        Emprestimo emprestimoCriado;

        emprestimo.setDataColeta(null);
        emprestimo.setDataDevolucao(LocalDate.now()); // ideal seria fazer a troca de status....

        emprestimoCriado = emprestimoRepo.save(emprestimo);

        return RetornoApi.sucess("Pedido de empréstimo cancelad com sucesso.", emprestimoCriado);
    }

}
