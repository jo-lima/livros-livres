package com.livros_livres.Server.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livros_livres.Server.Repository.AutorRepo;
import com.livros_livres.Server.registers.livros.Autor;

@Service // Classe de serviço
public class AutorService {

    @Autowired // Automaticamente monta e importa a classe que faz a conexão da tabela do autor no bd
	private AutorRepo autorRepo;

    // Chama o método da classe repository que salva os dados de um Autor para o banco.
    public Autor salvarAutor( Autor autorData ){
        autorData.setAtivo(true);
        return autorRepo.save(autorData);
    }

    // Edita a coluna "ativo" de um autor para fazer sua inativação. (excluão lógica).
    public Autor atualizarAutor( Integer idAutor, Autor autorData){
        Optional<Autor> buscaAutor = autorRepo.findById(idAutor);
        if(buscaAutor.isPresent()){
            Autor autor = buscaAutor.get();

            // adicionar if not empty
            autor.setNome(autorData.getNome());
            autor.setDescricao(autorData.getDescricao());
            autor.setCitacao(autorData.getCitacao());
            autor.setAtivo(autorData.getAtivo());

            return autorRepo.save(autor);
        }
        else {
            return null;
        }
    }

    // Chama o método da classe repository que deleta um Autor no banco (excluão física).
    // TODO: mudar o retorno para alguma classe do tipo "retorno/sucesso"
    public Boolean deletarAutor( Integer idAutor ){
        autorRepo.deleteById(idAutor);
        return true; // adicionar validacao de erro
    }

    // Edita a coluna "ativo" de um autor para fazer sua inativação. (excluão lógica).
    public Autor inativarAutor( Integer idAutor ){
        Optional<Autor> buscaAutor = autorRepo.findById(idAutor);
        if(buscaAutor.isPresent()){
            Autor autor = buscaAutor.get();
            autor.setAtivo(false);
            return autorRepo.save(autor);
        }
        else {
            return null;
        }
    }

    // Edita a coluna "ativo" de um autor para fazer sua ativação.
    public Autor ativarAutor( Integer idAutor ){
        Optional<Autor> buscaAutor = autorRepo.findById(idAutor);
        if(buscaAutor.isPresent()){
            Autor autor = buscaAutor.get();
            autor.setAtivo(true);
            return autorRepo.save(autor);
        }
        else {
            return null;
        }
    }

}
