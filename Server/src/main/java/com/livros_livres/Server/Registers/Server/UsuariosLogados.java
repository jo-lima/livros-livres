package com.livros_livres.Server.Registers.Server;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuariosLogados {
    private String user;
    private String token;
    private LocalDateTime dataTokenGerado;

    public UsuariosLogados(){}

    public UsuariosLogados(String user, String token, LocalDateTime dataTokenGerado) {
        this.user = user;
        this.token = token;
        this.dataTokenGerado = dataTokenGerado;
    }

}
