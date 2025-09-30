package com.livros_livres.Server.registers.usuarios;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tbl_Funcionario")
public class Funcionario extends Usuario{

    // Table Columns
    @Id
    @Column(name="idFuncionario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFuncionario;

    @Column(name="matricula")
    int matricula;

    // Setters
    public void setIdFuncionario(int idFuncionario){
        this.idFuncionario = idFuncionario;
    }
    public void setMatricula(int matricula){
        this.matricula = matricula;
    }

    // Getters
    public int getIdAutor(){
        return this.idFuncionario;
    }
    public int getMatricula(){
        return matricula;
    }
}