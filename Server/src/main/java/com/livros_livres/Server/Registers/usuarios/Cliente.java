package com.livros_livres.Server.Registers.usuarios;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.livros_livres.Server.Registers.emprestimos.Emprestimo;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="tbl_Cliente")
public class Cliente extends Usuario {

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Emprestimo> emprestimos = new ArrayList<>();

    public Cliente() {
        super();
    }

    public Cliente(String nome, String email, String senha) {
        super(nome, email, senha);
    }
}