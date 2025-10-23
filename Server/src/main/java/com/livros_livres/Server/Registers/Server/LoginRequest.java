package com.livros_livres.Server.Registers.Server;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    private String usuario;
    private String senha;

}