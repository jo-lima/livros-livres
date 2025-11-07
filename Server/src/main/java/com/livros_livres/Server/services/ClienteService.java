package com.livros_livres.Server.services;

import java.lang.foreign.Linker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livros_livres.Server.services.VariaveisHtml;
import com.livros_livres.Server.Registers.RequestBody.AuthRequest;
import com.livros_livres.Server.Registers.RequestBody.LoginRequest;
import com.livros_livres.Server.Registers.Server.RetornoApi;
import com.livros_livres.Server.Registers.Server.UsuariosAuth;
import com.livros_livres.Server.Registers.Server.UsuariosLogados;
import com.livros_livres.Server.Registers.livros.Autor;
import com.livros_livres.Server.Registers.usuarios.Cliente;
import com.livros_livres.Server.Registers.usuarios.Funcionario;
import com.livros_livres.Server.Repository.AutorRepo;
import com.livros_livres.Server.Repository.ClienteRepo;

@Service
public class ClienteService {

    @Autowired // Automaticamente monta e importa a classe que faz a conexão da tabela do cliente no bd
	private ClienteRepo clienteRepo;
    @Autowired
	private AuthenticationService authService;
    @Autowired
	private MailService mailService;

    // Pesquisa por clientes com a combinacao inserida de email.
    private Cliente buscaClienteEmail(String email) {
        Optional<Cliente> cliente;

        cliente = clienteRepo.findByEmailIgnoreCase(email);

        if(cliente.isEmpty()) {
            return null;
        }

        return cliente.get();
    }

    // Troca a senha de um cliente.
    private Cliente alterarSenhaCliente(String token, Cliente cliente, String novaSenha) {
        if(cliente == null || novaSenha == null) { return null; }

        cliente.setSenha(novaSenha);
        clienteRepo.save(cliente);

        return cliente;
    }

    public RetornoApi buscaCliente(Integer idCliente){
        Optional<Cliente> buscaCliente;

        buscaCliente = clienteRepo.findById(idCliente);
        if(!buscaCliente.isPresent()){
            return RetornoApi.errorNotFound("Nenhum cliente encontrado");
        }
        return RetornoApi.sucess("",buscaCliente);
    }

    public RetornoApi listaClientes(){
        List<Cliente> buscaCliente;

        buscaCliente = clienteRepo.findAll();
        if(buscaCliente.isEmpty()){
            return RetornoApi.errorNotFound("Nenhum autor encontrado.");
        }

        return RetornoApi.sucess("",buscaCliente);
    }

    public RetornoApi listaClientes(Cliente clienteData){
        List<Cliente> listaCliente;
        String clienteAtivo = null;

        if(clienteData==null){
            listaCliente = clienteRepo.findAll();
        }
        else{
            if(clienteData.getAtivo() != null){
                clienteAtivo = clienteData.getAtivo() == true ? "1" : "0";
            }
            listaCliente = clienteRepo.findClientesBySearch(
                clienteData.getCpf(),
                clienteData.getNome(),
                clienteData.getSenha(),
                clienteData.getEndereco(),
                clienteData.getTelefone(),
                clienteAtivo
            );
        }
        if(listaCliente.isEmpty()){
            return RetornoApi.errorNotFound("Nenhum cliente encontrado.");
        }
        return RetornoApi.sucess("",listaCliente);
    }

    public RetornoApi atualizarCliente(String token, Integer idCliente, Cliente clienteData){
        Optional<Cliente> buscaCliente;
        Cliente cliente;

        UsuariosLogados usuarioLogado = authService.buscaUsuarioLogado(token);
        if(usuarioLogado == null || (usuarioLogado.getUserPerm() == 0 &&
        clienteData.getEmail() != usuarioLogado.getUser()))
        {
            return RetornoApi.errorForbidden();
        }

        buscaCliente = clienteRepo.findById(idCliente);

        if(!buscaCliente.isPresent()){
            return RetornoApi.errorNotFound("Nenhum aturo encontrado");
        }
        if(clienteData.getAtivo() != null){
            return RetornoApi.errorBadRequest("Campo ativo alterado apenas no endpoint de inativar ou ativar.");
        }

        cliente = buscaCliente.get();

        if(clienteData.getNome() != null){
            cliente.setNome(clienteData.getNome());
        }

        if(clienteData.getTelefone() != null){
            cliente.setTelefone(clienteData.getTelefone());
        }

        if(clienteData.getCpf() != null){
            cliente.setCpf(clienteData.getCpf());
        }

        if(clienteData.getEndereco() != null){
            cliente.setEndereco(clienteData.getEndereco());
        }

        if(clienteData.getSenha() != null){
            cliente.setSenha(clienteData.getSenha());
        }

        clienteRepo.save(cliente);

        return RetornoApi.sucess("Cliente atualizado com sucesso", cliente);
    }

    public RetornoApi deletarCliente(String token, Integer idCliente){
        Optional<Cliente> buscaCliente;

        UsuariosLogados buscaUsuario = authService.buscaUsuarioLogado(token);
        if (token == null) {return RetornoApi.errorBadRequest("Request invalida. Insira valores para token, email e senha.");}
        if (buscaUsuario == null) { return RetornoApi.errorForbidden();}

        buscaCliente = clienteRepo.findById(idCliente);

        if(!buscaCliente.isPresent()){
            return RetornoApi.errorNotFound("Nenhum cliente encontrado");
        }

        clienteRepo.deleteById(idCliente);

        return RetornoApi.sucess("Cliente excluido com sucesso");
    }

    public RetornoApi inativarCliente(String token, Integer idCliente){
        Optional<Cliente> buscaCliente;
        Cliente cliente;

        UsuariosLogados usuarioLogado = authService.buscaUsuarioLogado(token);
        if(usuarioLogado.getUserPerm() != 1){
            return RetornoApi.errorForbidden();
        }

        buscaCliente = clienteRepo.findById(idCliente);

        if(!buscaCliente.isPresent()){
            return RetornoApi.errorNotFound("Nenhum cliente encontrado");
        }
        if(!buscaCliente.get().getAtivo()){
            return RetornoApi.errorBadRequest("Cliente já inativo.");
        }

        cliente = buscaCliente.get();
        cliente.setAtivo(false);

        clienteRepo.save(cliente);

        return RetornoApi.sucess("Cliente inativado!", cliente);
    }

    public RetornoApi ativarCliente(String token, Integer idCliente){
        Optional<Cliente> buscaCliente;
        Cliente cliente;

        UsuariosLogados usuarioLogado = authService.buscaUsuarioLogado(token);
        if(usuarioLogado.getUserPerm() != 1){
            return RetornoApi.errorForbidden();
        }

        buscaCliente = clienteRepo.findById(idCliente);

        if(!buscaCliente.isPresent()){
            return RetornoApi.errorNotFound("Nenhum cliente encontrado");
        }
        if(buscaCliente.get().getAtivo()){
            return RetornoApi.errorBadRequest("Cliente já ativo");
        }

        cliente = buscaCliente.get();
        cliente.setAtivo(true);
        clienteRepo.save(cliente);
        return RetornoApi.sucess("Cliente ativado com sucesso!");
    }

    // Troca o email de um cliente.
    private Cliente alterarEmailCliente(Cliente cliente, String novoEmail) {
        if(cliente == null || novoEmail == null) { return null; }

        
        cliente.setEmail(novoEmail);
        clienteRepo.save(cliente);

        return cliente;
    }

    public RetornoApi novoCliente(Cliente clienteData ){
        clienteData.setAtivo(true);

        if(authService.buscaEmailAutenticado(clienteData.getEmail()) == null) {return RetornoApi.errorBadRequest("Email não verificado!");}
        if(buscaClienteEmail(clienteData.getEmail()) != null) {return RetornoApi.errorBadRequest("Email já cadastrado no sistema!");}

        clienteRepo.save(clienteData);
        return RetornoApi.sucess("Cliente criado com sucesso!", clienteData);
    }

    // Confere email e senha do cliente, adiciona-o à lista de usuarios logados.
    public RetornoApi loginCliente(LoginRequest loginRequest) {
        Cliente buscaCliente;
        Integer userPerm = 0;
        UsuariosLogados loggedInUser;

        if (!authService.isValidEmail(loginRequest.getUsuario())){return RetornoApi.errorBadRequest("Email inválido.");}

        buscaCliente = this.buscaClienteEmail(loginRequest.getUsuario());

        // confere se a senha ta certa
        if( buscaCliente == null || !loginRequest.getSenha().equals(buscaCliente.getSenha())) {
            return RetornoApi.errorLoginNotFound();
        }

        // Loga o usuario, adicionando-o a uma lista de logins
        loggedInUser = authService.logarUsuario(loginRequest.getUsuario(), userPerm);

        // Caso tenha falhado em logar o usuário
        if (loggedInUser == null) { return RetornoApi.errorLoginNotFound();}

        return RetornoApi.sucess("Usuário autenticado e logado com sucesso!", loggedInUser);
    }

    // Envia um token para o email solicitado, com objetivo de, depois, conferir se o mesmo tem acesso a este email.
    public RetornoApi enviarEmailValidarEmail(String email) {
        if (email == null || !authService.isValidEmail(email)) {
            return RetornoApi.errorBadRequest("Email inválido.");
        }

        UsuariosAuth newUser;
        RetornoApi retornoEmail = null;

        // Adiciona email na lista de emails a serem autenticados.
        newUser = authService.criarSolicitacaoAutenticacao(email);
        String htmlCodigo = VariaveisHtml.html_validarToken(newUser.getAuthToken());
    retornoEmail =mailService.sendMail(htmlCodigo, "Verificação de email", email);
        return retornoEmail;
}

    // Procura pela combinacao de token x email e marca o email como "autenticado" caso tenha encontrado.
    public RetornoApi validarTokenValidarEmail(AuthRequest requestData) {
        // busca um usuario com essa requisicao de autenticacao e seta o email como "verificado"
        UsuariosAuth buscaAuth = authService.verificaSolicitacaoAutenticacao(requestData);

        // Confere se email está mesmo autenticado
        if (buscaAuth == null || buscaAuth.getVerificado() != true) { return RetornoApi.errorInvalidCode(); }

        return RetornoApi.sucess("Email autenticado!");
    }

    public RetornoApi enviarEmailEsqueciSenha(String email) {
        if (email == null || !authService.isValidEmail(email)) {
            return RetornoApi.errorBadRequest("Email inválido.");
        }

        // Caso nao exista nenhum usuário com este email no sistema.
        if (this.buscaClienteEmail(email) == null) {
            // Retorno fake, pra nao vazar dados de um email que existe no sistema.
            return RetornoApi.sucess("Tentativa de envio de email efetuada com sucesso!");
        }

        UsuariosAuth newUser;
        RetornoApi retornoEmail = null;

        // Adiciona email na lista de emails a serem autenticados.
        newUser = authService.criarSolicitacaoAutenticacao(email);

        String htmlesqueciSenha = VariaveisHtml.html_esqueciSenha(newUser.getAuthToken());

        retornoEmail = mailService.sendMail(htmlesqueciSenha,"Esqueceu sua senha do LivrosLivres?", email);

        return retornoEmail;
    }

    public RetornoApi validarTokenTrocaSenha(AuthRequest requestData) {
        Cliente buscaCliente;
        Integer userPerm = 0;
        UsuariosLogados loggedInUser;
        UsuariosAuth buscaAuth = authService.verificaSolicitacaoAutenticacao(requestData);

        buscaCliente = this.buscaClienteEmail(requestData.getEmail()); // procura por um cliente com esse email

        // Confere se email está autenticado e email pertence a um cliente.
        if (buscaAuth == null || buscaAuth.getVerificado() != true) { return RetornoApi.errorInvalidCode(); }
        if(buscaCliente == null) { return RetornoApi.errorInvalidCode(); }

        // Loga o usuario, adicionando-o a uma lista de logins
        loggedInUser = authService.logarUsuario(requestData.getEmail(), userPerm);
        authService.deletaSolicitacaoAutenticacao(buscaAuth);

        // Caso tenha falhado em logar o usuário
        if (loggedInUser == null) { return RetornoApi.errorLoginNotFound();}

        return RetornoApi.sucess("Usuário autenticado e logado com sucesso!", loggedInUser);
    }

    // Troca a senha do usuario. Usuario precisa estar logado.
    public RetornoApi trocarSenhaCliente(String token, String novaSenha) {
        Cliente clienteAtual;
        Cliente clienteAlterado;
        UsuariosLogados buscaUsuario = authService.buscaUsuarioLogado(token);

        // Confere se enviou algum dado vazio e se o usuário é o mesmo que está tentando trocar a senha.
        if (token == null || novaSenha == null) {return RetornoApi.errorBadRequest("Request invalida. Insira valores para token, email e senha.");}
        if (buscaUsuario == null) { return RetornoApi.errorForbidden();}

        clienteAtual = this.buscaClienteEmail(buscaUsuario.getUser());
        clienteAlterado = this.alterarSenhaCliente(token, clienteAtual, novaSenha);

        if (clienteAlterado == null) {return RetornoApi.errorInternal();}

        return RetornoApi.sucess("Senha alterada com sucesso!");
    }

    // Troca o email do usuario. Usuario precisa estar logado
    public RetornoApi trocarEmailCliente(String token, String novoEmail) {
        Cliente clienteAtual;
        Cliente clienteAlterado;
        UsuariosAuth buscaAuth = authService.buscaEmailAutenticado(novoEmail);
        UsuariosLogados buscaLog = authService.buscaUsuarioLogado(token);

        if (token == null || novoEmail == null) {return RetornoApi.errorBadRequest("Request invalida. Insira valores para token, email e novoEmail.");}
        if (buscaLog == null) { return RetornoApi.errorBadRequest("Usuário não logado.");}
        // Confere igualdade entre o novo email e o email cadastrado no sistema, pelo token sendo utilizado.
        if (novoEmail.equals(buscaLog.getUser())) {return RetornoApi.errorBadRequest("Novo email não pode ser igual ao anterior!");}
        // Busca por usuarios ja cadastrados com o novo email inserido
        if (this.buscaClienteEmail(novoEmail)!=null) {return RetornoApi.errorBadRequest("Email já possui cadastro no sistema.");}
        // Confere se o novo email está verificado
        if (buscaAuth == null || !buscaAuth.getVerificado()) {return RetornoApi.errorBadRequest("Novo email não verificado!");}

        clienteAtual = this.buscaClienteEmail(buscaLog.getUser());
        if(clienteAtual == null) {return RetornoApi.errorBadRequest("Usuário não encontrado.");}

        clienteAlterado = this.alterarEmailCliente(clienteAtual, novoEmail);
        if(clienteAlterado == null) {return RetornoApi.errorBadRequest("Não foi possível fazer a alteração do email.");}

        // reseta os logins do cliente.
        authService.deletarLoginsEmailUsuario(buscaLog.getUser());

        return RetornoApi.sucess("Cliente alterado com sucesso!", clienteAlterado);
    }

}
