package com.livros_livres.Server.Registers.Server;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuariosLogados {
    private String email;
    private String token;
    private LocalDateTime dataTokenGerado;
}
