package com.livros_livres.Server.Registers.Server;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailAutenticacao {
    private String email;
    private String codigo;
    private Date dataEmailEnviado;
}
