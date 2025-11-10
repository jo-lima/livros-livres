package com.livros_livres.Server.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livros_livres.Server.Registers.Livros.Livro;
import com.livros_livres.Server.Registers.RequestBody.LivroRequest;
import com.livros_livres.Server.Registers.Server.RetornoApi;
import com.livros_livres.Server.Registers.Server.UsuariosLogados;
import com.livros_livres.Server.Repository.LivroRepo;

@Service
public class LivroService{
    @Autowired
    private LivroRepo livroRepo;
    @Autowired
    private AutorService autorService;
    @Autowired
    private AuthenticationService authService;

    public Livro buscaLivroById(Integer idLivro){
        Optional<Livro> buscaLivro;

        buscaLivro = livroRepo.findById(idLivro);
        if(!buscaLivro.isPresent()){
            return null;
        }

        return buscaLivro.get();
    }

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

    public RetornoApi novoLivro(String token, LivroRequest livroData){
        if(!authService.checkAdminPerm(token)){ return RetornoApi.errorForbidden(); }

        Livro novoLivro = new Livro();

        if (
            livroData.getNome() == null ||
            livroData.getGenero() == null ||
            livroData.getPaginas() <= 0 || // TODO: Change this
            livroData.getIsbn() == null
        ) {
            return RetornoApi.errorBadRequest("Insira os valores requeridos para criação do livro.");
        }

        novoLivro.setAutor(autorService.buscaAutorById(livroData.getAutorId()));
        novoLivro.setAtivo(true);

        novoLivro.setNome(livroData.getNome());
        novoLivro.setGenero(livroData.getGenero());
        novoLivro.setPaginas(livroData.getPaginas());
        novoLivro.setIsbn(livroData.getIsbn());
        novoLivro.setDescricao(livroData.getDescricao());
        novoLivro.setEstoque(livroData.getEstoque());
        novoLivro.setEditora(livroData.getEditora());
        novoLivro.setDataPublicacao(livroData.getDataPublicacao());


        livroRepo.save(novoLivro);
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
        if(livroData.getPaginas() != 0){
            livro.setPaginas(livroData.getPaginas());
        }
        if(livroData.getIsbn() != null){
            livro.setIsbn(livroData.getIsbn());
        }
        if(livroData.getDescricao()!= null){
            livro.setDescricao(livroData.getDescricao());
        }
        if(livroData.getEstoque() != 0){
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
        // TODO: Atualizar autor
        livroRepo.save(livro);
        return RetornoApi.sucess("Autor atualizado com sucesso!", livro);
    }

    public RetornoApi adicionarLivroEstoque(Integer idLivro, Integer quantidade){
        Optional<Livro> buscaLivro;
        Livro livro;

        buscaLivro = livroRepo.findById(idLivro);

        if(!buscaLivro.isPresent() && quantidade != 0){
            return RetornoApi.errorNotFound("Nenhum livro encontrado.");
        }

        if(quantidade == 0){quantidade = 1;}

        livro = buscaLivro.get();

        livro.setEstoque(livro.getEstoque()+quantidade);
        livroRepo.save(livro);

        return RetornoApi.sucess("Adicionado um a quantidade de livros no estoque.", livro);
    }

    public RetornoApi removerLivroEstoque(Integer idLivro, Integer quantidade){
        Optional<Livro> buscaLivro;
        Livro livro;

        buscaLivro = livroRepo.findById(idLivro);

        if(!buscaLivro.isPresent()){
            return RetornoApi.errorNotFound("Nenhum livro encontrado.");
        }

        if(quantidade == 0){quantidade = 1;}

        livro = buscaLivro.get();

        livro.setEstoque(livro.getEstoque()-quantidade);
        livroRepo.save(livro);

        return RetornoApi.sucess("Removido um a quantidade de livros no estoque.", livro);
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
