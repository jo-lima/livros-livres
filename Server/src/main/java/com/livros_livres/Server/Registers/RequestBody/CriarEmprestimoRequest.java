package com.livros_livres.Server.Registers.RequestBody;

import java.time.LocalDate;

import io.micrometer.common.lang.NonNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriarEmprestimoRequest {

    @NonNull
    private Integer livroId;
    @NonNull
    private Integer clienteId;

    @NonNull
    private LocalDate dataPrevistaDevolucao;
}
