package com.livros_livres.Server.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livros_livres.Server.Registers.RequestBody.LoginRequest;
import com.livros_livres.Server.Registers.Server.RetornoApi;
import com.livros_livres.Server.Registers.Server.UsuariosAuth;
import com.livros_livres.Server.Registers.Server.UsuariosLogados;
import com.livros_livres.Server.Registers.Usuarios.Cliente;
import com.livros_livres.Server.Registers.Usuarios.Funcionario;
import com.livros_livres.Server.Repository.FuncionarioRepo;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepo funcionarioRepo;
    @Autowired
	private AuthenticationService authService;
    @Autowired
	private MailService mailService;
    @Autowired
    ClienteService clienteService;

    public Funcionario salvarFuncionario( String token, Funcionario funcionarioData ){
        if(!authService.checkAdminPerm(token)){ return null; }
        return funcionarioRepo.save(funcionarioData);
    }

    public Funcionario buscaFuncionarioMatricula(String matricula) {
        Optional<Funcionario> funcionario;

        funcionario = funcionarioRepo.findByMatriculaIgnoreCase(matricula);

        if(funcionario.isEmpty()) {
            return null;
        }

        return funcionario.get();
    }

    public RetornoApi loginFuncionario(LoginRequest loginRequest) {
        Funcionario buscaFuncionario;
        Integer userPerm = 1;
        UsuariosLogados loggedInUser;

        buscaFuncionario = this.buscaFuncionarioMatricula(loginRequest.getUsuario());

        // confere se a senha ta certa
        if( buscaFuncionario == null || !loginRequest.getSenha().equals(buscaFuncionario.getSenha())) {
            return RetornoApi.errorLoginNotFound();
        }

        // Tenta adicionar o funcionário à lista de usuarios logados
        loggedInUser = authService.logarUsuario(loginRequest.getUsuario(), userPerm);
        // Caso não tenha sido adicionado, retorna que nao encontrou combinacao email x senha.
        if (loggedInUser == null) { return RetornoApi.errorLoginNotFound();}

        return RetornoApi.sucess("Usuário autenticado e logado com sucesso!", loggedInUser);
    }

    public RetornoApi criaNovoCliente(String token, Cliente clienteData) {
        // Checks user permissions
        if(!authService.checkAdminPerm(token)){ return RetornoApi.errorForbidden(); }

        String senhaTmp = authService.tokenGenerator(16, true);
        UsuariosAuth userCreatedAuth;

        clienteData.setSenha(senhaTmp);

        clienteService.novoCliente(clienteData);
        userCreatedAuth = authService.criarSolicitacaoAutenticacao(clienteData.getEmail());

        mailService.sendMail("Olá! Sua conta do livros livres foi criada por um de nossos funcionários.\n"+
                             "Aqui segue seu acesso <strong>TEMPORÁRIO</strong>: " + userCreatedAuth.getAuthToken() + "\n" +
                             "Utilize-o para acessar sua conta por <strong>este link: http://example.com/primeiro-acesso</strong>",
                            "Sua conta do Livros Livres foi criada com sucesso!", clienteData.getEmail());


        return RetornoApi.sucess("Cliente cadastrado com sucesso!");
    }
}
