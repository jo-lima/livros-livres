package com.livros_livres.Server.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.livros_livres.Server.Registers.Server.RetornoApi;
//import com.livros_livres.Server.Registers.livros.Autor;
import com.livros_livres.Server.Registers.livros.Livro;
import com.livros_livres.Server.Registers.livros.LivroAutor;
import com.livros_livres.Server.Registers.livros.Autor;
import com.livros_livres.Server.Repository.LivroRepo;
import com.livros_livres.Server.Repository.AutorRepo;
import com.livros_livres.Server.Repository.LivroAutorRepo;
import com.livros_livres.Server.Repository.AutorRepo;


@Service 
public class LivroService{
    @Autowired
    private LivroRepo livroRepo;

    @Autowired
    private AutorRepo autorRepo;
    
     @Autowired
    private LivroAutorRepo livroautorRepo;
    
    public RetornoApi buscaLivro(Integer idLivro){
        Optional<Livro> buscaLivro;

        buscaLivro = livroRepo.findById(idLivro);
        if(!buscaLivro.isPresent()){
            return RetornoApi.errorNotFound("Nenhum livro encontrado");
        }
        
        return RetornoApi.sucess("", buscaLivro);
    }

    public RetornoApi listaLivros(){
        List<Livro> buscaLivro;

        buscaLivro = livroRepo.findAll();

        if(buscaLivro.isEmpty()){
            return RetornoApi.errorNotFound("Nenhum livro encontrado");
        }
        return RetornoApi.sucess("", buscaLivro);
    }

    public RetornoApi listaLivros(Livro livroData){
        List<Livro> listaLivro;
        String livroAtivo = null;

        if(livroData==null){
            listaLivro = livroRepo.findAll();
        }
        else{
            if(livroData.getAtivo() != null){
                livroAtivo = livroData.getAtivo() == true ? "1" :  "0";
            }
            listaLivro = livroRepo.findLivrosBySearch(
                //livroData.getAutor(),
                livroData.getNome(),
                livroData.getGenero(),
                livroData.getPaginas(),
                livroData.getIsbn(),
                livroData.getEditora(),
                livroData.getDescricao(),
                livroData.getDataPublicacao(),
                livroAtivo
            );
        }

        if(listaLivro.isEmpty()){
            return RetornoApi.errorNotFound("Nenhum livro encontrado");
        }
        return RetornoApi.sucess("", listaLivro);
    }

    public RetornoApi novoLivro(Livro livroData, int idAutor){
        livroData.setAtivo(true);
        Optional<Autor> autorOptional = autorRepo.findById(idAutor);
        if(autorOptional.isEmpty()){
            return RetornoApi.errorNotFound("Autor com ID " + idAutor + "não encontrado");
        }

        Autor autor = autorOptional.get();
        Livro livroSalvo = livroRepo.save(livroData);
        
        LivroAutor relacionamento = new LivroAutor();
        relacionamento.setLivro(livroSalvo);
        relacionamento.setAutor(autor);
        livroautorRepo.save(relacionamento);

        return RetornoApi.sucess("Livro cadastrado com sucesso",livroData);
    }

    public RetornoApi atualizarLivro(Integer idLivro, Livro livroData){
        Optional<Livro> buscaLivro;
        Livro livro;

        buscaLivro = livroRepo.findById(idLivro);

        if(!buscaLivro.isPresent()){
            return RetornoApi.errorNotFound("Nenhum livro encontrado.");
        }
        if(livroData.getAtivo() != null){
            return RetornoApi.errorBadRequest("Campo ativo alterado apenas no endpoint de inativar ou ativar");
        }

        livro = buscaLivro.get();

        if(livroData.getNome() != null) {
            livro.setNome(livroData.getNome());
        }
        // if(livroData.getAutor() != null) {
        //     livro.setAutor(livroData.getAutor());
        // }
        if(livroData.getGenero() != null){
            livro.setGenero(livroData.getGenero());
        }
        /*if(livroData.getPaginas() != null){
            livro.setPaginas(livroData.getPaginas());
        }*/
        livroData.setPaginas(livroData.getPaginas());
        if(livroData.getIsbn() != null){
            livro.setIsbn(livroData.getIsbn());
        }
        if(livroData.getDescricao()!= null){
            livro.setDescricao(livroData.getDescricao());
        }
        /*if(livroData.getEstoque() != null){
            livro.setEstoque(livroData.getEstoque());
        }*/
        if(livroData.getEditora()!= null){
            livro.setEditora(livroData.getEditora());
        }
        if(livroData.getDataPublicacao()!= null){
            livro.setDataPublicacao(livroData.getDataPublicacao());
        }
        if(livroData.getAtivo()!= null){
            livro.setAtivo(livroData.getAtivo());
        }
        //Verificar como colocar o autor, colocar pelo ID ou nome? 
        livroRepo.save(livro);
        return RetornoApi.sucess("Autor atualizado com sucesso!", livro);
    }

    public RetornoApi inativarLivro(Integer idLivro){
        Optional<Livro> buscaLivro;
        Livro livro;

        buscaLivro = livroRepo.findById(idLivro);

        if(!buscaLivro.isPresent()){
            return RetornoApi.errorNotFound("Nenhum livro encontrado");
        }
        if(!buscaLivro.get().getAtivo()){
            return RetornoApi.errorBadRequest("Livro já inativo.");
        }

        livro = buscaLivro.get();
        livro.setAtivo(false);
        livroRepo.save(livro);

        return RetornoApi.sucess("Livro inativado com sucesso.!", livro);
    }

    public RetornoApi ativarLivro(Integer idLivro){
        Optional<Livro> buscaLivro;
        Livro livro;

        buscaLivro = livroRepo.findById(idLivro);
        if(!buscaLivro.isPresent()){
            return RetornoApi.errorNotFound("Nenhum livro encontrado");
        }
        if(buscaLivro.get().getAtivo()){
            return RetornoApi.errorBadRequest("livro já ativo!");
        }

        livro = buscaLivro.get();
        livro.setAtivo(true);
        livroRepo.save(livro);
        return RetornoApi.sucess("Livro ativado com sucesso");
    }
    
}
