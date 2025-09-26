package com.livros_livres.Server.registers.usuarios;

public class Cliente extends Usuario {

	String livroFavorito;

    public void setLivroFavorito(String livroFavorito){
        this.livroFavorito = livroFavorito;
    }

    public String getLivroFavorito(){
        return livroFavorito;
    }

}