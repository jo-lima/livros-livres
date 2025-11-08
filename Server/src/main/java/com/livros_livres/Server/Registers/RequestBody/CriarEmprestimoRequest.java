package com.livros_livres.Server.Registers.RequestBody;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriarEmprestimoRequest {

    private Integer livroId;
    private Integer clienteId;

}
