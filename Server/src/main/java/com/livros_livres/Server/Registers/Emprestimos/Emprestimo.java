package com.livros_livres.Server.Registers.Emprestimos;

import com.livros_livres.Server.Registers.Livros.Livro;
import com.livros_livres.Server.Registers.Usuarios.Cliente;

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
    @JoinColumn(name="IdCliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name="IdLivro")
    private Livro livro;

    @Column(name="Data_SolicitacaoEmprestimo")
    private LocalDate dataSolicitacaoEmprestimo;

    @Column(name="Data_Coleta")
    private LocalDate dataColeta;

    @Column(name="Data_Devolucao")
    private LocalDate dataDevolucao;

    @Column(name="Data_EstendidaDevolucao")
    private LocalDate dataEstendidaDevolucao;

    @Column(name="Data_PrevistaDevolucao")
    private LocalDate dataPrevistaDevolucao;

    @Column(name="Ativo")
    private Boolean ativo;

    public Emprestimo() {}

    public Emprestimo(
        Cliente cliente, Livro livro, LocalDate dataSolicitacaoEmprestimo,
        LocalDate dataColeta, LocalDate dataDevolucao, LocalDate dataEstendidaDevolucao,
        LocalDate dataPrevistaDevolucao, Boolean ativo
        ) {
        this.cliente = cliente;
        this.livro = livro;
        this.dataSolicitacaoEmprestimo = dataSolicitacaoEmprestimo;
        this.dataColeta = dataColeta;
        this.dataDevolucao = dataDevolucao;
        this.dataEstendidaDevolucao = dataEstendidaDevolucao;
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
        this.ativo = ativo;
    }
}