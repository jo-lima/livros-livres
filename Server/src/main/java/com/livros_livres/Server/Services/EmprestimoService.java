package com.livros_livres.Server.Services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livros_livres.Server.Registers.RequestBody.PedidoEmprestimoRequest;
import com.livros_livres.Server.Registers.Server.RetornoApi;
import com.livros_livres.Server.Registers.Server.UsuariosLogados;
import com.livros_livres.Server.Registers.Emprestimos.Emprestimo;
import com.livros_livres.Server.Registers.Emprestimos.EmprestimoStatus;
import com.livros_livres.Server.Registers.Livros.Livro;
import com.livros_livres.Server.Registers.Usuarios.Cliente;
import com.livros_livres.Server.Repository.ClienteRepo;
import com.livros_livres.Server.Repository.EmprestimoRepo;

@Service
public class EmprestimoService {

    @Autowired
    EmprestimoRepo emprestimoRepo;
    @Autowired
    ClienteRepo clienteRepo;
    @Autowired
    AuthenticationService authService;
    @Autowired
    ClienteService clienteService;
    @Autowired
    LivroService livroService;

    public RetornoApi test() {
        return RetornoApi.errorForbidden();
    }

    public Emprestimo buscarEmprestimoById(Integer idEmprestimo){
        Optional<Emprestimo> buscaEmprestimo;

        buscaEmprestimo = emprestimoRepo.findById(idEmprestimo);
        if (!buscaEmprestimo.isPresent()) {
            return null;
        }

        return buscaEmprestimo.get();
    };

    public RetornoApi buscaEmprestimo(String token, Integer idEmprestimo) {
        // -- VALIDATIONS --
        UsuariosLogados usuarioLogado = authService.buscaUsuarioLogado(token);
        String clienteEmprestimoMail = clienteService.buscaClienteEmail(usuarioLogado.getUser()).getEmail();

        if (usuarioLogado == null || idEmprestimo == null) {return RetornoApi.errorForbidden();}
        if(!authService.checkRestrictedPerm(token, clienteEmprestimoMail)){return RetornoApi.errorForbidden();}

        // -- METHOD LOGIC --
        Emprestimo emprestimo = this.buscarEmprestimoById(idEmprestimo);

        if(emprestimo == null) {return RetornoApi.errorBadRequest("Empréstimo não encontrado para o ID fornecido.");}

        return RetornoApi.sucess("Empréstimo encontrado com sucesso.", emprestimo);
    }

    public RetornoApi criarPedido(String token, PedidoEmprestimoRequest pedidoEmprestimo) {
        // -- VALIDATIONS --
        UsuariosLogados usuarioLogado = authService.buscaUsuarioLogado(token);
        if(usuarioLogado == null || usuarioLogado.getUserPerm() != 0 ){return RetornoApi.errorForbidden();}

        Livro livroPedido = livroService.buscaLivroById(pedidoEmprestimo.getLivroId());
        Cliente cliente = clienteService.buscaClienteEmail(usuarioLogado.getUser());

        // TODO: Validação confere se usuário tem +3 pedidos feitos
        if(cliente.getClienteId() != pedidoEmprestimo.getClienteId()) {return RetornoApi.errorForbidden();}
        if(livroPedido.getEstoque() <= 0 || livroPedido.getAtivo() != true) {return RetornoApi.errorBadRequest("Este livro não possui disponibilidade no estoque!");}

        // -- METHOD LOGIC --
        Emprestimo newEmprestimo = new Emprestimo();
        Emprestimo emprestimoCriado;

        newEmprestimo.setLivro(livroPedido);
        newEmprestimo.setCliente(cliente);
        newEmprestimo.setStatus(EmprestimoStatus.PEDIDO);
        newEmprestimo.setDataSolicitacaoEmprestimo(LocalDate.now());

        emprestimoCriado = emprestimoRepo.save(newEmprestimo);

        return RetornoApi.sucess("Pedido de empréstimo criado com sucesso!", emprestimoCriado);
    }

    public RetornoApi aceitarPedido(String token, Emprestimo emprestimo) {
        UsuariosLogados usuarioLogado = authService.buscaUsuarioLogado(token);
        if(usuarioLogado == null || usuarioLogado.getUserPerm() != 1 ){return RetornoApi.errorForbidden();}
        Emprestimo emprestimoCriado;

        emprestimo.setDataColeta(LocalDate.now());
        emprestimo.setStatus(EmprestimoStatus.ACEITO);
        emprestimoCriado = emprestimoRepo.save(emprestimo);

        // Confere se qntd livro >= 0
        // TODO: livroService.removeEstoque(emprestimo.getLivro(), 1);

        return RetornoApi.sucess("Pedido aceito e empréstimo criado com sucesso!", emprestimoCriado);
    }

    public RetornoApi criarEmprestimo(String token, PedidoEmprestimoRequest pedidoEmprestimo) {
        UsuariosLogados usuarioLogado = authService.buscaUsuarioLogado(token);
        if(usuarioLogado == null || usuarioLogado.getUserPerm() != 1 ){return RetornoApi.errorForbidden();}

        Livro livroPedido = livroService.buscaLivroById(pedidoEmprestimo.getLivroId());
        Cliente cliente = clienteRepo.findById(pedidoEmprestimo.getClienteId()).get();

        if(livroPedido.getEstoque() <= 0 || livroPedido.getAtivo() != true) {return RetornoApi.errorBadRequest("Este livro não possui disponibilidade no estoque!");}

        Emprestimo newEmprestimo = new Emprestimo();
        Emprestimo emprestimoCriado;

        newEmprestimo.setLivro(livroPedido);
        newEmprestimo.setCliente(cliente);
        newEmprestimo.setStatus(EmprestimoStatus.CRIADO);
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
        emprestimo.setDataDevolucao(LocalDate.now());
        emprestimo.setStatus(EmprestimoStatus.CANCELADO);

        emprestimoCriado = emprestimoRepo.save(emprestimo);

        return RetornoApi.sucess("Pedido de empréstimo cancelad com sucesso.", emprestimoCriado);
    }

    public RetornoApi finalizarEmprestimo(String token, Emprestimo emprestimo) {
        UsuariosLogados usuarioLogado = authService.buscaUsuarioLogado(token);
        if(usuarioLogado == null || usuarioLogado.getUserPerm() != 1 ){return RetornoApi.errorForbidden();}

        Emprestimo emprestimoCriado;
        EmprestimoStatus emprestimoStatus = (
            emprestimo.getDataPrevistaDevolucao().isBefore(LocalDate.now().plusDays(1)) ||
            emprestimo.getDataEstendidaDevolucao().isBefore(LocalDate.now().plusDays(1))
            ) ? EmprestimoStatus.FINALIZADO : EmprestimoStatus.FINALIZADO_ATRASADO;
        // ^ Emprestimo finalizado com atraso caso tenha sido entregue antes do dia estipulado/extendido

        emprestimo.setDataDevolucao(LocalDate.now());
        emprestimo.setStatus(emprestimoStatus);

        emprestimoCriado = emprestimoRepo.save(emprestimo);

        // TODO: livroService.adicionaEstoque(emprestimo.getLivro(), 1);

        return RetornoApi.sucess("Empréstimo finalizado com sucesso!", emprestimoCriado);
    }

    public RetornoApi editarEmprestimo(String token, Integer idEmprestimo, Emprestimo emprestimoData) {
        UsuariosLogados usuarioLogado = authService.buscaUsuarioLogado(token);
        if(usuarioLogado == null || usuarioLogado.getUserPerm() != 1 ){return RetornoApi.errorForbidden();}
        if(emprestimoData == null || idEmprestimo == null){return RetornoApi.errorBadRequest("Insira todos os dados necessŕaios da request.");}

        Emprestimo emprestimoCriado;
        Emprestimo emprestimo = this.buscarEmprestimoById(idEmprestimo);

        if(emprestimo == null) {return RetornoApi.errorBadRequest("Empréstimo não encontrado para o ID fornecido.");}

        // Adding fields if field is not empty
        // must be a better way to do this ....
        if(emprestimoData.getAtivo() != null) { emprestimo.setAtivo(emprestimoData.getAtivo()); }
        if(emprestimoData.getDataSolicitacaoEmprestimo() != null) {emprestimo.setDataSolicitacaoEmprestimo(emprestimoData.getDataSolicitacaoEmprestimo());}
        if(emprestimoData.getDataColeta() != null) {emprestimo.setDataColeta(emprestimoData.getDataColeta());}
        if(emprestimoData.getDataPrevistaDevolucao() != null) {emprestimo.setDataPrevistaDevolucao(emprestimoData.getDataPrevistaDevolucao());}
        if(emprestimoData.getDataEstendidaDevolucao() != null) {emprestimo.setDataEstendidaDevolucao(emprestimoData.getDataEstendidaDevolucao());}
        if(emprestimoData.getDataDevolucao() != null) {emprestimo.setDataDevolucao(emprestimoData.getDataDevolucao());}
        if(emprestimoData.getStatus() != null) {emprestimo.setStatus(emprestimoData.getStatus());}

        emprestimoCriado = emprestimoRepo.save(emprestimo);

        return RetornoApi.sucess("Dados do empréstimo atualizados.", emprestimoCriado);
    }

    public RetornoApi adiarEmprestimo(String token, Integer idEmprestimo, Emprestimo emprestimoData) {
        // -- VALIDATIONS --
        UsuariosLogados usuarioLogado = authService.buscaUsuarioLogado(token);
        String clienteEmprestimoMail = clienteService.buscaClienteEmail(usuarioLogado.getUser()).getEmail();

        if (usuarioLogado == null || idEmprestimo == null) {return RetornoApi.errorForbidden();}
        if(!authService.checkRestrictedPerm(token, clienteEmprestimoMail)){return RetornoApi.errorForbidden();}

        Emprestimo emprestimo = this.buscarEmprestimoById(idEmprestimo);

        if(emprestimo == null) {return RetornoApi.errorBadRequest("Empréstimo não encontrado para o ID fornecido.");}

        boolean canDefer = emprestimo.getStatus() == EmprestimoStatus.ACEITO ||
                           emprestimo.getStatus() == EmprestimoStatus.CRIADO ||
                           emprestimo.getDataEstendidaDevolucao() == null;

        if(!canDefer) { return RetornoApi.errorBadRequest("Empréstimo não pode ser adiado.");}

        // -- METHOD LOGIC --
        Emprestimo emprestimoCriado;

        emprestimo.setStatus(EmprestimoStatus.ADIADO);
        emprestimo.setDataEstendidaDevolucao(emprestimoData.getDataEstendidaDevolucao());
        emprestimoCriado = emprestimoRepo.save(emprestimo);

        return RetornoApi.sucess("Data do empréstimo adiada com sucesso.", emprestimoCriado);
    }

}
