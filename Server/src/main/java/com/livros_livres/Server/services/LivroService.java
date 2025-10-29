package com.livros_livres.Server.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livros_livres.Server.Registers.Server.RetornoApi;
import com.livros_livres.Server.Registers.livros.Livro;
import com.livros_livres.Server.Repository.LivroRepo;

@Service 
public class LivroService{
    @Autowired
    private LivroRepo livroRepo;

    public RetornoApi buscaLivro(Integer idLivro){
        Optional<Livro> buscaLivro;

        buscaLivro = livroRepo.findbyId(idLivro);
        if(!buscaLivro.isPresent()){
            return RetornoApi.errorNotFound("Nenhum livro encontrado")
        }

        return RetornoApi.sucess("", buscaLivro);
    }

    public RetornoApi listaLivros(){
        List<Livro> buscaLivro;

        buscaLivro = livroRepo.findAll();

        if(buscaLivro.isEmpty()){
            return RetornoApi.errorNotFound("Nenhum livro encontrado")
        }
        return RetornoApi.sucess("", buscaLivro);
    }

    public RetornoApi listaLivros(Livro livroData){
        List<Livro> listaLivro;
        
        if(livroData==null){
            listaLivro = livroRepo.findAll();
        }
        else{
            listaLivro =livroRepo.findLivrosbySearch(
                livroData.getautor(),
                livroData.getNome(),
                livroData.getGenero(),
                livroData.getEditora(),
                livroData.getDataPublicacao(),
                livroData.getIsbn(),
                livroData.getDescricao()
            );
        }

        if(listaLivro.isEmpty()){
            return RetornoApi.errorNotFound("Nenhum livro encontrado");
        }
        return RetornoApi.sucess("", listaLivro);
    }

    public RetornoAPi novoLivro(Livro livroData){
        livroData.setAtiv(true);
        livroRepo.save(livroData);
        return RetornoApi.sucess("Livro cadastrado com sucesso",livroData)
    }

    public RetornoApi atualizarLivro(Integer idLivro, Livro livroData){
        Optional<Livro> buscaLivro;
        Livro livro;

        buscaLivro = livrorepo.findById(idLivro);

        if(!buscaLivro.isPresent()){
            return RetornoApi.errorNotFound("Nenhum livro encontrado.")
        }
        if(autorData,getAtivo() != null){
            return RetornoApi.errorBadRequest("Campo ativo alterado apenas no endpoint de inativar ou ativar")
        }

        livro = buscaLivro,get();

        if(livroData.getNome() != null) {
            livro.setNome(livroData.getNome());
        }
        if(livroData.getAutor() != null) {
            livro.setAutor(livroData.getAutor());
        }
        if(livroData.getGenero() != null){
            livro.setGenero(livroData.getGenero());
        }
        if(livroData.getPaginas()!= null){
            livro.setPaginas(livroData.getPaginas());
        }
        if(livroData.getIsbn()!= null){
            livro.setIsbn(livroData.getIsbn());
        }
        if(livroData.getDescricao()!= null){
            livro.setDescricao(livroData.getDescricao());
        }
        if(livroData.getEstoque()!= null){
            livro.setEstoque(livroData.getEstoque());
        }
        if(livroData.getEditora()!= null){
            livro.setEditora(livroData.getEditora());
        }
        if(livroData.getDataPublicacao()!= null){
            livro.setDataPublicacao(livroData.getDataPublicacao());
        }
        if(livroData.getAtivo()!= null){
            livro.setAtivo(livroData.getAtivo());
        }
        livroRepo.save(livro);
        return RetornoApi.sucess("Autor atualizado com sucesso!", livro)
    }

    public RetornoApi inativarLivro(Integer idLivro){
        Optional<Livro> buscaLivro;

        buscaLivro = livroRepo.findById(idLivro);

        if(!buscaLivro,isPresent()){
            return RetornoApi.errorNotFound("Nenhum livro encontrado");
        }
        if(!buscalivroo.get().getAtivo()){
            return RetornoApi.errorBadRequest("Livro já inativo.")
        }

        livro = buscaLivro.get();
        livro.setAtivo(false);
        livroRepo.save(livro);

        return RetornoApi.sucess("Autor inativado com sucesso.!", livro);
    }

    public RetornoAPi ativarLivro(Integer idLivro){
        Optional<Livro> buscaLivro;
        Livro livro;

        buscaLivro = livroRepo.findbyId(idLivro);
        if(!buscaLivro.isPresent()){
            return RetornoApi.errorNotFound("Nenhum livro encontrado")
        }
        if(!buscaLivro.get().getAtivo()){
            return RetornoApi.errorBadRequest("livro já ativo!")
        }

        livro = buscaLivro.get();
        livro.setAtivo(true);
        livroRepo.save(livro);
        return RetornoAPi.sucess("Livro ativado com sucesso")
    }
    
}
