package com.livros_livres.Server.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livros_livres.Server.Registers.Server.RetornoApi;
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

    public RetornoApi criaNovoCliente(String token, Cliente clienteData) {
        // Checks user permissions
        if(authService.buscaUsuarioLogado(token).getUserPerm() != 1){return RetornoApi.errorForbidden();}
        String acessoTmp = authService.tokenGenerator(16, true);

        clienteData.setSenha(acessoTmp);
        mailService.sendMail("Olá! Sua conta do livros livres foi criada por um de nossos funcionários.\n"+
                             "Aqui segue seu acesso <strong>TEMPORÁRIO</strong>: " + acessoTmp + "\n" +
                             "Utilize-o para acessar sua conta por <strong>este link: http://example.com/primeiro-acesso</strong>",
                            "Sua conta do Livros Livres foi criada com sucesso!", clienteData.getEmail());

        clienteService.novoCliente(clienteData);

        return RetornoApi.sucess("Cliente cadastrado com sucesso!");
    }
}
