package com.livros_livres.Server.Registers.Usuarios;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="tbl_Funcionario")
public class Funcionario extends Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="FuncionarioId")
    private int funcionarioId;

    @Column(name="Matricula", nullable=false, unique=true)
    private String matricula;

    public Funcionario() {}

    public Funcionario(
        String cpf, String nome, String senha,
        Boolean ativo, String matricula
    ) {
        super(cpf, nome, senha, ativo);
        this.matricula = matricula;
    }
}