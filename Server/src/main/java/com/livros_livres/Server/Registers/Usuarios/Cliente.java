package com.livros_livres.Server.Registers.Usuarios;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import com.livros_livres.Server.Registers.Emprestimos.Emprestimo;

@Getter
@Setter
@Entity
@Table(name="tbl_Cliente")
public class Cliente extends Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ClienteId")
    private int clienteId;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Emprestimo> emprestimos = new ArrayList<>();

    @Column(name="Endereco")
    private String endereco;
    @Column(name="Telefone")
    private String telefone;
    @Column(name="Email", unique = true, nullable=false)
    private String email;

    public Cliente() {
        super();
    }

    public Cliente(
        String cpf, String nome, String email, String senha,
        Boolean ativo, String endereco, String telefone
    ) {
        super(cpf, nome, senha, ativo);
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
    }
}