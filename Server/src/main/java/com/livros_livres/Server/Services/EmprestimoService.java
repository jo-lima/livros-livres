package com.livros_livres.Server.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import com.livros_livres.Server.Registers.RequestBody.CriarEmprestimoRequest;
import com.livros_livres.Server.Registers.RequestBody.EmprestimoSearchRequest;
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

    // TODO: should delete this and change to repofindbyid... but too lazy...
    private Emprestimo buscarEmprestimoById(Integer idEmprestimo){
        Optional<Emprestimo> buscaEmprestimo;

        buscaEmprestimo = emprestimoRepo.findById(idEmprestimo);
        if (!buscaEmprestimo.isPresent()) {
            return null;
        }

        return buscaEmprestimo.get();
    };

    public RetornoApi buscaEmprestimo(String token, Integer idEmprestimo) {
        // -- VALIDATIONS --
        Emprestimo emprestimo = this.buscarEmprestimoById(idEmprestimo);
        if(emprestimo == null) {return RetornoApi.errorBadRequest("Empréstimo não encontrado para o ID fornecido.");}

        String clienteEmprestimoMail = buscarEmprestimoById(idEmprestimo).getCliente().getEmail();

        if (idEmprestimo == null) {return RetornoApi.errorForbidden();}
        if(!authService.checkRestrictedPerm(token, clienteEmprestimoMail)){return RetornoApi.errorForbidden();}

        return RetornoApi.sucess("Empréstimo encontrado com sucesso.", emprestimo);
    }

    public RetornoApi listaEmprestimo(String token, EmprestimoSearchRequest emprestimoData) {
        // -- VALIDATIONS --
        if(!authService.checkAdminPerm(token)){return RetornoApi.errorForbidden();}

        // -- METHOD LOGIC --
        List<Emprestimo> buscaEmprestimo;

        // TODO: Increase options?
        buscaEmprestimo = emprestimoRepo.findEmprestimoBySearch(
            emprestimoData.getEmprestimoStatus(),
            emprestimoData.getClienteId(),
            emprestimoData.getLivroId()
            );
        if(buscaEmprestimo.isEmpty()) {
            return RetornoApi.errorNotFound("Nenhum empréstimo encontrado.");
        }

        return RetornoApi.sucess("Empréstimos encontrados.", buscaEmprestimo);
    }

    public RetornoApi listaEmprestimoCliente(String token, Integer idCliente, EmprestimoSearchRequest emprestimoData) {
        // -- VALIDATIONS --
        if(idCliente == null){return RetornoApi.errorBadRequest("Insira o id do cliente.");}

        Optional<Cliente> buscaCliente = clienteRepo.findById(idCliente);
        if(!buscaCliente.isPresent()) {return RetornoApi.errorBadRequest("Cliente não encontrado no sistema.");}

        String clienteMail = buscaCliente.get().getEmail();
        if(!authService.checkRestrictedPerm(token, clienteMail)){return RetornoApi.errorForbidden();}

        // -- METHOD LOGIC --
        List<Emprestimo> buscaEmprestimo;

        buscaEmprestimo = emprestimoRepo.findEmprestimoBySearch(
            emprestimoData.getEmprestimoStatus(),
            idCliente,
            emprestimoData.getLivroId()
            );
        if(buscaEmprestimo.isEmpty()) {
            return RetornoApi.errorNotFound("Nenhum empréstimo encontrado.");
        }

        return RetornoApi.sucess("Empréstimos encontrados.", buscaEmprestimo);
    }

    public RetornoApi criarPedido(String token, CriarEmprestimoRequest pedidoEmprestimo) {
        // -- VALIDATIONS --
        UsuariosLogados usuarioLogado = authService.buscaUsuarioLogado(token);
        if(usuarioLogado == null || usuarioLogado.getUserPerm() != 0 ){return RetornoApi.errorForbidden();}
        if(
            pedidoEmprestimo.getDataPrevistaDevolucao()==null ||
            pedidoEmprestimo.getClienteId()==null ||
            pedidoEmprestimo.getLivroId()==null
        ) { return RetornoApi.errorBadRequest("Por favor insira os valores do cliente, livro, e data de devolução.");}

        Livro livroPedido = livroService.buscaLivroById(pedidoEmprestimo.getLivroId());
        Cliente cliente = clienteService.buscaClienteEmail(usuarioLogado.getUser());

        if(cliente == null) {return RetornoApi.errorBadRequest("Não encontrado cliente para o id passado.");}
        if(livroPedido == null) {return RetornoApi.errorBadRequest("Não encontrado livro para o id passado.");}

        if(cliente.getClienteId() != pedidoEmprestimo.getClienteId()) {return RetornoApi.errorForbidden();}
        // if(cliente.getEmprestimos().size() > 3) {return RetornoApi.errorBadRequest("Usuário ja possui 3 empréstimos ativos!");} // TODO: retorna todos os emprestimos, deveria ser todos ativos.
        if(livroPedido.getEstoque() <= 0 || livroPedido.getAtivo() != true) {return RetornoApi.errorBadRequest("Este livro não possui disponibilidade no estoque!");}

        if(
            pedidoEmprestimo.getDataPrevistaDevolucao().isBefore(LocalDate.now())
        ) { return RetornoApi.errorBadRequest("Data de devolução deve ser futura.");}
        if(
            pedidoEmprestimo.getDataPrevistaDevolucao().isAfter(LocalDate.now().plusMonths(3))
        ) { return RetornoApi.errorBadRequest("Data de devolução muito absurda");}

        // -- METHOD LOGIC --
        Emprestimo newEmprestimo = new Emprestimo();
        Emprestimo emprestimoCriado;

        newEmprestimo.setLivro(livroPedido);
        newEmprestimo.setCliente(cliente);
        newEmprestimo.setDataPrevistaDevolucao(pedidoEmprestimo.getDataPrevistaDevolucao());
        newEmprestimo.setStatus(EmprestimoStatus.PEDIDO);
        newEmprestimo.setDataSolicitacaoEmprestimo(LocalDate.now());
        newEmprestimo.setAtivo(true);

        emprestimoCriado = emprestimoRepo.save(newEmprestimo);

        return RetornoApi.sucess("Pedido de empréstimo criado com sucesso!", emprestimoCriado);
    }

    public RetornoApi aceitarPedido(String token, Integer emprestimoId) {
        if(!authService.checkAdminPerm(token)){return RetornoApi.errorForbidden();};

        Optional<Emprestimo> buscaEmprestimo;
        RetornoApi retornoLivro;
        Emprestimo emprestimo;
        Emprestimo emprestimoCriado;

        buscaEmprestimo = emprestimoRepo.findById(emprestimoId);
        if(!buscaEmprestimo.isPresent()){return RetornoApi.errorBadRequest("Empréstimo não encontrado!");}
        if(buscaEmprestimo.get().getDataColeta() != null){return RetornoApi.errorBadRequest("Empréstimo já aceito!");}
        if(buscaEmprestimo.get().getStatus() == EmprestimoStatus.CANCELADO){return RetornoApi.errorBadRequest("Pedido de empréstimo foi cancelado!");}
        if(buscaEmprestimo.get().getDataDevolucao() != null){return RetornoApi.errorBadRequest("Empréstimo já concluído!");}

        emprestimo = buscaEmprestimo.get();

        if(emprestimo.getLivro().getEstoque() <= 0 || emprestimo.getLivro().getAtivo() != true) {
            return RetornoApi.errorBadRequest("Este livro não possui disponibilidade no estoque!");
        }

        emprestimo.setDataColeta(LocalDate.now());
        emprestimo.setStatus(EmprestimoStatus.ACEITO);
        emprestimoCriado = emprestimoRepo.save(emprestimo);

        retornoLivro = livroService.removerLivroEstoque(token, emprestimo.getLivro().getIdLivro());
        DebugService.log("Livro removido:", retornoLivro);

        return RetornoApi.sucess("Pedido aceito e empréstimo criado com sucesso!", emprestimoCriado);
    }

    public RetornoApi criarEmprestimo(String token, CriarEmprestimoRequest pedidoEmprestimo) {
        if(!authService.checkAdminPerm(token)){return RetornoApi.errorForbidden();};

        if(
            pedidoEmprestimo.getDataPrevistaDevolucao()==null ||
            pedidoEmprestimo.getClienteId()==null ||
            pedidoEmprestimo.getLivroId()==null
        ) { return RetornoApi.errorBadRequest("Por favor insira os valores do cliente, livro, e data de devolução.");}

        Livro livroPedido = livroService.buscaLivroById(pedidoEmprestimo.getLivroId());
        Cliente cliente = clienteRepo.findById(pedidoEmprestimo.getClienteId()).get();

        if(cliente == null) {return RetornoApi.errorBadRequest("Não encontrado cliente para o id passado.");}
        if(livroPedido == null) {return RetornoApi.errorBadRequest("Não encontrado livro para o id passado.");}

        if(livroPedido.getEstoque() <= 0 || livroPedido.getAtivo() != true) {return RetornoApi.errorBadRequest("Este livro não possui disponibilidade no estoque!");}

        Emprestimo newEmprestimo = new Emprestimo();
        Emprestimo emprestimoCriado;

        newEmprestimo.setLivro(livroPedido);
        newEmprestimo.setCliente(cliente);
        newEmprestimo.setAtivo(true);
        newEmprestimo.setDataPrevistaDevolucao(pedidoEmprestimo.getDataPrevistaDevolucao());
        newEmprestimo.setStatus(EmprestimoStatus.CRIADO);
        newEmprestimo.setDataColeta(LocalDate.now());
        newEmprestimo.setDataSolicitacaoEmprestimo(LocalDate.now());

        emprestimoCriado = emprestimoRepo.save(newEmprestimo);

        livroService.removerLivroEstoque(token, newEmprestimo.getLivro().getIdLivro());

        return RetornoApi.sucess("Empréstimo criado com sucesso!", emprestimoCriado);
    }

    public RetornoApi cancelarEmprestimo(String token, Integer idEmprestimo) {
        Emprestimo buscaEmprestimo = buscarEmprestimoById(idEmprestimo);
        if(!authService.checkLoginPerm(token)){return RetornoApi.errorBadRequest("Usuário não logado!");}
        if(buscaEmprestimo==null){return RetornoApi.errorBadRequest("Empréstimo não encontrado!");}
        if(buscaEmprestimo.getDataColeta() != null) {return RetornoApi.errorBadRequest("Pedido de empréstimo já concluído! Para finalizar o empréstimo é necessário contatar um funcionário.");}
        if(buscaEmprestimo.getStatus() == EmprestimoStatus.CANCELADO) {return RetornoApi.errorBadRequest("Empréstimo cancelado!");}
        if(buscaEmprestimo.getDataDevolucao() != null) {return RetornoApi.errorBadRequest("Empréstimo já finalizado!");}

        Emprestimo emprestimoCriado;

        buscaEmprestimo.setDataColeta(null);
        buscaEmprestimo.setDataDevolucao(null);
        buscaEmprestimo.setStatus(EmprestimoStatus.CANCELADO);

        emprestimoCriado = emprestimoRepo.save(buscaEmprestimo);

        return RetornoApi.sucess("Pedido de empréstimo cancelado com sucesso.", emprestimoCriado);
    }

    public RetornoApi finalizarEmprestimo(String token, Integer idEmprestimo) {
        Emprestimo buscaEmprestimo = buscarEmprestimoById(idEmprestimo);

        if(!authService.checkAdminPerm(token)){return RetornoApi.errorBadRequest("Usuário não logado!");}
        if(buscaEmprestimo==null){return RetornoApi.errorBadRequest("Empréstimo não encontrado!");}
        if(buscaEmprestimo.getStatus() == EmprestimoStatus.CANCELADO) {return RetornoApi.errorBadRequest("Empréstimo cancelado!");}
        if(buscaEmprestimo.getDataDevolucao() != null) {return RetornoApi.errorBadRequest("Empréstimo já finalizado!");}

        Emprestimo emprestimoCriado;
        EmprestimoStatus emprestimoStatus;
        LocalDate dataEstipuladaDevolucao;

        dataEstipuladaDevolucao = buscaEmprestimo.getDataEstendidaDevolucao() == null ?
                                  buscaEmprestimo.getDataPrevistaDevolucao() :
                                  buscaEmprestimo.getDataEstendidaDevolucao();

        // Emprestimo finalizado com atraso caso tenha sido entregue antes do dia Previsto/Estendido
        emprestimoStatus = dataEstipuladaDevolucao.isAfter(LocalDate.now()) ?
            EmprestimoStatus.FINALIZADO : EmprestimoStatus.FINALIZADO_ATRASADO;

        buscaEmprestimo.setDataDevolucao(LocalDate.now());
        buscaEmprestimo.setStatus(emprestimoStatus);

        emprestimoCriado = emprestimoRepo.save(buscaEmprestimo);

        livroService.adicionarLivroEstoque(token, buscaEmprestimo.getLivro().getIdLivro());

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
        Emprestimo emprestimo = this.buscarEmprestimoById(idEmprestimo);

        if(emprestimo == null) {return RetornoApi.errorBadRequest("Empréstimo não encontrado para o ID fornecido.");}
        if(!authService.checkRestrictedPerm(token, emprestimo.getCliente().getEmail())){return RetornoApi.errorForbidden();}

        if(emprestimo.getDataColeta() == null) {return RetornoApi.errorBadRequest("Empréstimo precisa ser coletado antes de poder ser adiado.");}
        if(emprestimo.getStatus() == EmprestimoStatus.CANCELADO) {return RetornoApi.errorBadRequest("Empréstimo cancelado!");}
        if(emprestimo.getDataDevolucao() != null) {return RetornoApi.errorBadRequest("Empréstimo já finalizado!");}

        if(
            emprestimoData.getDataEstendidaDevolucao()==null
        ) {return RetornoApi.errorBadRequest("Por favor insira a data estendida da devolução.");}
        if(
            emprestimoData.getDataEstendidaDevolucao().isBefore(LocalDate.now())
        ) { return RetornoApi.errorBadRequest("Data de devolução deve ser futura.");}
        if(
            emprestimoData.getDataEstendidaDevolucao().isAfter(emprestimo.getDataPrevistaDevolucao().plusMonths(3))
        ) { return RetornoApi.errorBadRequest("Data de devolução muito absurda");}

        // -- METHOD LOGIC --
        Emprestimo emprestimoCriado;

        emprestimo.setStatus(EmprestimoStatus.ADIADO);
        emprestimo.setDataEstendidaDevolucao(emprestimoData.getDataEstendidaDevolucao());
        emprestimoCriado = emprestimoRepo.save(emprestimo);

        return RetornoApi.sucess("Data do empréstimo adiada com sucesso.", emprestimoCriado);
    }

}
