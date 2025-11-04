package com.livros_livres.Server.Registers.RequestBody;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    private String usuario;
    private String senha;

}