package com.livros_livres.Server.Registers.RequestBody;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoEmprestimoRequest {

    private Integer livroId;
    private Integer clienteId;

}
