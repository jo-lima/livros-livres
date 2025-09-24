package com.livros_livres.Server.registers.usuarios;

public class Usuario{
    String cpf;
    String nome;
    String email;
    String senha;
    String endereco;
    String telefone;

    public Usuario(String cpf, String nome, String email, String senha, String endereco, String telefone){
        this.cpf      = cpf;
        this.nome     = nome;
        this.email    = email;
        this.senha    = senha;
        this.endereco = endereco;
        this.telefone = telefone;
    }
}