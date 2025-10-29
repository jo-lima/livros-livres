package com.livros_livres.Server.Registers.Server;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuariosLogados {
    private String user;
    private String token;
    private Integer userPerm; // 0 = user normal // 1 = funcionario
    private LocalDateTime dataTokenGerado;

    public UsuariosLogados(){}

    public UsuariosLogados(String user, String token, LocalDateTime dataTokenGerado) {
        this.user = user;
        this.token = token;
        this.userPerm = null;
        this.dataTokenGerado = dataTokenGerado;
    }

    public UsuariosLogados(String user, String token, Integer userPerm, LocalDateTime dataTokenGerado) {
        this.user = user;
        this.token = token;
        this.userPerm = userPerm;
        this.dataTokenGerado = dataTokenGerado;
    }

}
