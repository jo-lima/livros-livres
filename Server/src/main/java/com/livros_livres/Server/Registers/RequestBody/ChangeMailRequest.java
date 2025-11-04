package com.livros_livres.Server.Registers.RequestBody;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeMailRequest {

    private String email;
    private String novoEmail;

}