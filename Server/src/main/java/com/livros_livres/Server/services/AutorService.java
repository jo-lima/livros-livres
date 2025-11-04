package com.livros_livres.Server.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livros_livres.Server.Registers.Server.RetornoApi;
import com.livros_livres.Server.Registers.livros.Autor;
import com.livros_livres.Server.Repository.AutorRepo;

@Service // Classe de serviço
public class AutorService {

    @Autowired // Automaticamente monta e importa a classe que faz a conexão da tabela do autor no bd
	private AutorRepo autorRepo;

    // Recupera os dados de um autor específico no banco.
    public RetornoApi buscaAutor( Integer idAutor ){
        Optional<Autor> buscaAutor;

        buscaAutor = autorRepo.findById(idAutor);
        if(!buscaAutor.isPresent()) {
            return RetornoApi.errorNotFound("Nenhum autor encontrado para o id #" + idAutor.toString());
        }

        return RetornoApi.sucess("", buscaAutor);
    }

    // Recupera os dados de todos os autores no banco.
    public RetornoApi listaAutores(){
        List<Autor> buscaAutor;

        buscaAutor = autorRepo.findAll();
        if(buscaAutor.isEmpty()) {
            return RetornoApi.errorNotFound("Nenhum autor encontrado.");
        }

        return RetornoApi.sucess("", buscaAutor);
    }

    // Recupera os dados de todos os autores no banco com os filtros do objeto de Autor
    public RetornoApi listaAutores(Autor autorData){
        List<Autor> listaAutor;
        String autorAtivo = null;

        if (autorData==null) {
            listaAutor = autorRepo.findAll();
        }
        else{
            if(autorData.getAtivo() != null) {
                autorAtivo = autorData.getAtivo() == true ? "1" : "0"; // no banco true é 1 e false é 0
            }
            listaAutor = autorRepo.findAutoresBySearch(
                autorData.getNome(),
                autorData.getCitacao(),
                autorData.getDescricao(),
                autorAtivo
            );
        }

        if(listaAutor.isEmpty()) {
            return RetornoApi.errorNotFound("Nenhum autor encontrado.");
        }

        return RetornoApi.sucess("", listaAutor);
    }

    // Chama o método da classe repository que salva os dados de um Autor para o banco.
    public RetornoApi novoAutor( Autor autorData ){
        autorData.setAtivo(true);
        autorRepo.save(autorData);
        return RetornoApi.sucess("Autor criado com sucesso!", autorData);
    }

    // Edita a coluna "ativo" de um autor para fazer sua inativação. (excluão lógica).
    public RetornoApi atualizarAutor( Integer idAutor, Autor autorData){
        Optional<Autor> buscaAutor;
        Autor autor;

        buscaAutor = autorRepo.findById(idAutor);
        if(!buscaAutor.isPresent()) {
            return RetornoApi.errorNotFound("Nenhum autor encontrado para o id #" + idAutor.toString());
        }
        if (autorData.getAtivo() != null) {
            return RetornoApi.errorBadRequest("Campo ativo alterado apenas no endpoint de inativar ou ativar.");
        }

        autor = buscaAutor.get();

        if (autorData.getNome() != null) {
            autor.setNome(autorData.getNome());
        }
        if (autorData.getDescricao() != null) {
            autor.setDescricao(autorData.getDescricao());
        }
        if (autorData.getCitacao() != null) {
            autor.setCitacao(autorData.getCitacao());
        }

        autorRepo.save(autor);

        return RetornoApi.sucess("Autor atualizado com sucesso!", autor);

    }

    // Chama o método da classe repository que deleta um Autor no banco (excluão física).
    public RetornoApi deletarAutor( Integer idAutor ){
        Optional<Autor> buscaAutor;

        buscaAutor = autorRepo.findById(idAutor);
        if(!buscaAutor.isPresent()) {
            return RetornoApi.errorNotFound("Nenhum autor encontrado para o id #" + idAutor.toString());
        }

        autorRepo.deleteById(idAutor);
        return RetornoApi.sucess("Autor excluído do sistema com sucesso.");
    }

    // Edita a coluna "ativo" de um autor para fazer sua inativação. (excluão lógica).
    public RetornoApi inativarAutor( Integer idAutor ){
        Optional<Autor> buscaAutor;
        Autor autor;

        buscaAutor= autorRepo.findById(idAutor);

        if(!buscaAutor.isPresent()) { // não encontrou autor
            return RetornoApi.errorNotFound("Nenhum autor encontrado para o id #" + idAutor.toString());
        }
        if(!buscaAutor.get().getAtivo()) { // autor inativo
            return RetornoApi.errorBadRequest("Autor já inativo.");
        }

        autor = buscaAutor.get();
        autor.setAtivo(false);
        autorRepo.save(autor);

        return RetornoApi.sucess("Autor inativado com sucesso!", autor);

    }

    // Edita a coluna "ativo" de um autor para fazer sua ativação.
    public RetornoApi ativarAutor( Integer idAutor ){
        Optional<Autor> buscaAutor;
        Autor autor;

        buscaAutor = autorRepo.findById(idAutor);
        if(!buscaAutor.isPresent()) { // não encontrou autor
            return RetornoApi.errorNotFound("Nenhum autor encontrado para o id #" + idAutor.toString());
        }
        if(buscaAutor.get().getAtivo()) { // autor ativo
            return RetornoApi.errorBadRequest("Autor já ativo.");
        }

        autor = buscaAutor.get();
        autor.setAtivo(true);
        autorRepo.save(autor);
        return RetornoApi.sucess("Autor ativado com sucesso!");
    }

}
