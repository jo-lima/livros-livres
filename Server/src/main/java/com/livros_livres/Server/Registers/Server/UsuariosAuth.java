package com.livros_livres.Server.Registers.Server;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuariosAuth {
    String authToken;
    String email;
    Boolean verificado;
    LocalDateTime dataTokenGerado;
    LocalDateTime dataUserVerificado;

    public UsuariosAuth(String email, String authToken, LocalDateTime dataTokenGerado){
        this.authToken = authToken;
        this.email = email;
        this.dataTokenGerado = dataTokenGerado;
        this.verificado = false;
    }
}
