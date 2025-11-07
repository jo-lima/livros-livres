package com.livros_livres.Server.Registers.Emprestimos;

import java.time.LocalDate;

import com.livros_livres.Server.Registers.Livros.Livro;
import com.livros_livres.Server.Registers.Usuarios.Cliente;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="tbl_UsuarioEmprestimo")
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idEmprestimo")
    private int idEmprestimo;

    @ManyToOne
    @JoinColumn(name="idLivro")
    private Livro livro;

    @ManyToOne
    @JoinColumn(name="idUsuario")
    private Cliente cliente;

    @Column(name="Ativo", nullable = false)
    private Boolean ativo;

    @Column(name="Data_SolicitacaoEmprestimo")
    private LocalDate dataSolicitacaoEmprestimo;

    @Column(name="Data_Coleta")
    private LocalDate dataColeta;

    @Column(name="Data_PrevistaDevolucao")
    private LocalDate dataPrevistaDevolucao;

    @Column(name="Data_EstendidaDevolucao")
    private LocalDate dataEstendidaDevolucao;

    @Column(name="Data_Devolucao")
    private LocalDate dataDevolucao;


    public Emprestimo() {}

    public Emprestimo(Livro livro, Cliente cliente, LocalDate dataPrevistaDevolucao, LocalDate dataDevolucao,
                      Boolean ativo, LocalDate dataColeta, LocalDate dataSolicitacaoEmprestimo, LocalDate dataEstendidaDevolucao) {
        this.livro = livro;
        this.cliente = cliente;
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
        this.dataDevolucao = dataDevolucao;
        this.ativo = ativo;
        this.dataColeta = dataColeta;
        this.dataSolicitacaoEmprestimo = dataSolicitacaoEmprestimo;
        this.dataEstendidaDevolucao = dataEstendidaDevolucao;
    }
}