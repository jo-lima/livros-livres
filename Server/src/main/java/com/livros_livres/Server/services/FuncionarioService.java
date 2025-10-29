package com.livros_livres.Server.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livros_livres.Server.Registers.RequestBody.LoginRequest;
import com.livros_livres.Server.Registers.Server.RetornoApi;
import com.livros_livres.Server.Registers.Server.UsuariosAuth;
import com.livros_livres.Server.Registers.Server.UsuariosLogados;
import com.livros_livres.Server.Registers.usuarios.Cliente;
import com.livros_livres.Server.Registers.usuarios.Funcionario;
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

    public Funcionario salvarFuncionario( Funcionario funcionarioData ){
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

        buscaFuncionario = this.buscaFuncionarioMatricula(loginRequest.getUsuario());

        // confere se a senha ta certa
        if( buscaFuncionario == null || !loginRequest.getSenha().equals(buscaFuncionario.getSenha())) {
            return RetornoApi.errorLoginNotFound();
        }

        return authService.logarUsuario(loginRequest, userPerm);
    }

    public RetornoApi criaNovoCliente(String token, Cliente clienteData) {
        // Checks user permissions
        UsuariosLogados usuarioLogado = authService.buscaUsuarioLogado(token);
        if(usuarioLogado == null || usuarioLogado.getUserPerm() != 1){return RetornoApi.errorForbidden();}

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
