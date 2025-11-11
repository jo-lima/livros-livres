package com.livros_livres.Server.Registers.RequestBody;

import com.livros_livres.Server.Registers.Emprestimos.EmprestimoStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmprestimoSearchRequest {

    private Integer emprestimoId;
    private Integer livroId;
    private Integer clienteId;

    private EmprestimoStatus emprestimoStatus;

    private String livroNome;
    private String clienteNome;
    private String clienteCpf;
    private String clienteEmail;

}
