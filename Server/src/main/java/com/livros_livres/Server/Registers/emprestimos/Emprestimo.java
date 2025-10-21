package com.livros_livres.Server.Registers.emprestimos;

import com.livros_livres.Server.Registers.usuarios.Cliente;
import com.livros_livres.Server.Registers.livros.Livro;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name="tbl_Emprestimo")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idEmprestimo")
    private int idEmprestimo;

    @ManyToOne
    @JoinColumn(name="idCliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name="idLivro")
    private Livro livro;

    @Column(name="dataEmprestimo")
    private LocalDate dataEmprestimo;

    @Column(name="dataDevolucao")
    private LocalDate dataDevolucao;

    public Emprestimo() {
        this.dataEmprestimo = LocalDate.now();
        this.dataDevolucao = LocalDate.now().plusDays(7);
    }

    public Emprestimo(Cliente cliente, Livro livro) {
        this.cliente = cliente;
        this.livro = livro;
        this.dataEmprestimo = LocalDate.now();
        this.dataDevolucao = dataEmprestimo.plusDays(7);
    }
}