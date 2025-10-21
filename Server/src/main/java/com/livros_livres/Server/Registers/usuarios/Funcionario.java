package com.livros_livres.Server.Registers.usuarios;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="tbl_Funcionario")
public class Funcionario extends Usuario {

    @Column(name="admin")
    private Boolean admin;

    public Funcionario() {
        super();
        this.admin = true;
    }

    public Funcionario(String nome, String email, String senha, Boolean admin) {
        super(nome, email, senha);
        this.admin = admin;
    }
}