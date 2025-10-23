package com.livros_livres.Server.Registers.Server;

import java.util.List;

public interface Authentication {

    public static final List<UsuariosLogados> usuariosLogados = null;

    RetornoApi logarUsuario(LoginRequest loginRequest);
    RetornoApi trocarSenha(String token, String email, String novaSenha);

    RetornoApi enviarCodigoValidarEmailUsuario(String email);
    RetornoApi validarCodigoEnviadoEmailUsuario(String email, String codigo);

    RetornoApi enviarEmailTrocaSenha(String email);
    RetornoApi validarEmailTrocaSenha(String email, String codigo);
}
