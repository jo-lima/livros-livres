package com.livros_livres.Server.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livros_livres.Server.Registers.Server.RetornoApi;
import com.livros_livres.Server.Registers.Server.UsuariosLogados;
import com.livros_livres.Server.Registers.livros.Livro;
import com.livros_livres.Server.Repository.LivroRepo;

@Service
public class LivroService{
    @Autowired
    private LivroRepo livroRepo;
    @Autowired
    private AuthenticationService authService;

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

        if(livroData == null){
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
                livroData.getDescricao(),
                livroData.getEditora(),
                livroData.getDataPublicacao(),
                livroAtivo
            );
        }

        if(listaLivro.isEmpty()){
            return RetornoApi.errorNotFound("Nenhum livro encontrado");
        }
        return RetornoApi.sucess("", listaLivro);
    }

    public RetornoApi novoLivro(String token, Livro livroData){
        livroData.setAtivo(true);

        UsuariosLogados usuarioLogado = authService.buscaUsuarioLogado(token);
        if(usuarioLogado.getUserPerm() != 1){
            return RetornoApi.errorForbidden();
        }

        // get autor by id (change into another request type?)
        // paginas cannot be <= 1
        if (
            livroData.getNome() == null ||
            livroData.getGenero() == null ||
            livroData.getPaginas() <= 0 ||
            livroData.getIsbn() == null
        ) {
            return RetornoApi.errorBadRequest("Insira os valores requeridos para criação do livro.");
        }

        livroRepo.save(livroData);
        return RetornoApi.sucess("Livro cadastrado com sucesso",livroData);
    }

    public RetornoApi atualizarLivro(String token, Integer idLivro, Livro livroData){
        Optional<Livro> buscaLivro;
        Livro livro;
        
        UsuariosLogados usuarioLogado = authService.buscaUsuarioLogado(token);
        if(usuarioLogado.getUserPerm() != 1){
            return RetornoApi.errorForbidden();
        }

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
        if(livroData.getAutor() != null) {
            livro.setAutor(livroData.getAutor());
        }
        if(livroData.getGenero() != null){
            livro.setGenero(livroData.getGenero());
        }
        /*if(livroData.getPaginas() != null){
            livro.setPaginas(livroData.getPaginas());
        }*/
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

    public RetornoApi inativarLivro(String token, Integer idLivro){
        Optional<Livro> buscaLivro;
        Livro livro;        

        UsuariosLogados usuarioLogado = authService.buscaUsuarioLogado(token);
        if(usuarioLogado.getUserPerm() != 1){
            return RetornoApi.errorForbidden();
        }

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

        return RetornoApi.sucess("Autor inativado com sucesso.!", livro);
    }

    public RetornoApi ativarLivro(String token, Integer idLivro){
        Optional<Livro> buscaLivro;
        Livro livro;

        UsuariosLogados usuarioLogado = authService.buscaUsuarioLogado(token);
        if(usuarioLogado.getUserPerm() != 1){
            return RetornoApi.errorForbidden();
        }

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
