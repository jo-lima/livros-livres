package com.livros_livres.Server.Registers.Server;

import java.util.List;

import com.livros_livres.Server.Registers.RequestBody.AuthRequest;
import com.livros_livres.Server.Registers.RequestBody.LoginRequest;

public interface Authentication {

    public static final List<UsuariosLogados> usuariosLogados = null;

    RetornoApi logarUsuario(LoginRequest loginRequest);
    RetornoApi trocarSenhaCliente(String token, String email, String novaSenha);

    RetornoApi enviarCodigoValidarEmailUsuario(String email);
    RetornoApi validarCodigoEnviadoEmailUsuario(AuthRequest requestData);

    RetornoApi enviarEmailTrocaSenha(String email);
    RetornoApi validarEmailTrocaSenha(AuthRequest requestData);
}
