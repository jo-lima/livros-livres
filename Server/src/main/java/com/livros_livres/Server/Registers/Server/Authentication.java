package com.livros_livres.Server.Registers.Server;

import java.util.List;

import com.livros_livres.Server.Registers.RequestBody.AuthRequest;
import com.livros_livres.Server.Registers.RequestBody.LoginRequest;

public interface Authentication {

    public static final List<UsuariosLogados> usuariosLogados = null;

    UsuariosLogados logarUsuario(String user, Integer userPerm);

    UsuariosAuth criarSolicitacaoAutenticacao(String email);
    UsuariosAuth verificaSolicitacaoAutenticacao(AuthRequest requestData);
}
