package com.livros_livres.Server.registers.usuarios;

import jakarta.persistence.Column;

abstract class Usuario{
    @Column(name="cpf")
    String cpf;
    @Column(name="nome")
    String nome;
    @Column(name="email")
    String email;
    @Column(name="senha")
    String senha;
    @Column(name="endereco")
    String endereco;
    @Column(name="telefone")
    String telefone;

    public void setCpf(String cpf){
        this.cpf = cpf;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setSenha(String senha){
        this.senha = senha;
    }
    public void setEndereco(String endereco){
        this.endereco = endereco;
    }
    public void setTelefone(String telefone){
        this.telefone = telefone;
    }

    public String getCpf(){
        return cpf;
    }
    public String getNome(){
        return nome;
    }
    public String getEmail(){
        return email;
    }
    public String getSenha(){
        return senha;
    }
    public String getEndereco(){
        return endereco;
    }
    public String getTelefone(){
        return telefone;
    }

}