package com.livros_livres.Server.Registers.Emprestimos;

public enum EmprestimoStatus {
    PEDIDO("PEDIDO"),
    ACEITO("ACEITO"),
    CRIADO("CRIADO"),
    ADIADO("ADIADO"),
    FINALIZADO("FINALIZADO"),
    FINALIZADO_ATRASADO("FINALIZADO_ATRASADO"),
    CANCELADO("CANCELADO");

    String emprestimoStatus;

    EmprestimoStatus(String emprestimoStatus) {
        this.emprestimoStatus = emprestimoStatus;
    }

}