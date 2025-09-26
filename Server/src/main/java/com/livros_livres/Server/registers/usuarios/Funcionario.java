package com.livros_livres.Server.registers.usuarios;

public class Funcionario extends Usuario{
    int matricula;

    public void setMatricula(int matricula){
        this.matricula = matricula;
    }

    public int getMatricula(){
        return matricula;
    }
}