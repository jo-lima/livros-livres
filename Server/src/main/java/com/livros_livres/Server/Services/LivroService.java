package com.livros_livres.Server.Services;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livros_livres.Server.Registers.Livros.Autor;
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

    private boolean checkIsbnUnique(String isbn) {
        DebugService.log("Checking if isbn " + isbn + " is unique.");
        // Pesquisa por códigos isbn
        Optional<Livro> buscaLivro = null;
        buscaLivro = livroRepo.findOneByIsbn(isbn);

        if (buscaLivro.isPresent()) {return false;}
        return true;
    }

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
        DebugService.log("Started novo livro.");

        if(!authService.checkAdminPerm(token)){ return RetornoApi.errorForbidden(); }

        Livro novoLivro = new Livro();
        Livro salvoLivro;
        Autor buscaAutor;
        // get autor by id (change into another request type?)
        // paginas cannot be <= 1
        if (
            livroData.getNome() == null ||
            livroData.getGenero() == null ||
            livroData.getPaginas() == null ||
            // livroData.getEstoque() == 0  ||
            livroData.getIsbn() == null
        ) {
            return RetornoApi.errorBadRequest("Insira os valores requeridos para criação do livro.");
        }
        if( !this.checkIsbnUnique(livroData.getIsbn()) ) {
            return RetornoApi.errorBadRequest("ISBN deve ser único.");
        }

        if (livroData.getAutorId()!=null){
            buscaAutor = autorService.buscaAutorById(livroData.getAutorId());

            if(buscaAutor == null){
                return RetornoApi.errorBadRequest("Autor não existe para o ID passado.");
            }

            novoLivro.setAutor(buscaAutor);
        }

        novoLivro.setAtivo(true);

        novoLivro.setNome(livroData.getNome());
        novoLivro.setGenero(livroData.getGenero());
        novoLivro.setPaginas(livroData.getPaginas());
        novoLivro.setIsbn(livroData.getIsbn());
        novoLivro.setDescricao(livroData.getDescricao());
        novoLivro.setImagem(livroData.getImagem());
        novoLivro.setEditora(livroData.getEditora());
        novoLivro.setDataPublicacao(livroData.getDataPublicacao());

        if(livroData.getEstoque() == null || livroData.getEstoque() <= 0) {
            novoLivro.setEstoque(1);
        } else {
            novoLivro.setEstoque(livroData.getEstoque());
        }

        salvoLivro = livroRepo.save(novoLivro);
        return RetornoApi.sucess("Livro cadastrado com sucesso", salvoLivro);
    }

    public RetornoApi atualizarLivro(String token, Integer idLivro, LivroRequest livroData){
        Optional<Livro> buscaLivro;
        Autor buscaAutor;
        Livro livro;

        if(!authService.checkAdminPerm(token)){ return RetornoApi.errorForbidden(); }

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
        if(livroData.getGenero() != null){
            livro.setGenero(livroData.getGenero());
        }
        if(livroData.getPaginas() != null){
            livro.setPaginas(livroData.getPaginas());
        }
        if(livroData.getIsbn() != null){
            livro.setIsbn(livroData.getIsbn());
        }
        if(livroData.getDescricao()!= null){
            livro.setDescricao(livroData.getDescricao());
        }
        if(livroData.getImagem()!= null){
            livro.setImagem(livroData.getImagem());
        }
        if(livroData.getEstoque() != null){
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

        if (livroData.getAutorId()!=null){
            buscaAutor = autorService.buscaAutorById(livroData.getAutorId());

            if(buscaAutor == null){
                return RetornoApi.errorBadRequest("Autor não existe para o ID passado.");
            }

            livro.setAutor(buscaAutor);
        }

        livroRepo.save(livro);
        return RetornoApi.sucess("Autor atualizado com sucesso!", livro);
    }

    public RetornoApi adicionarLivroEstoque(String token, Integer idLivro, Integer quantidade){
        if(!authService.checkAdminPerm(token)){ return RetornoApi.errorForbidden(); }

        if(quantidade == null || quantidade == 0){quantidade = 1;}

        Optional<Livro> buscaLivro;
        Livro livro;

        buscaLivro = livroRepo.findById(idLivro);

        if(!buscaLivro.isPresent() && quantidade != null){
            return RetornoApi.errorNotFound("Nenhum livro encontrado.");
        }

        livro = buscaLivro.get();

        livro.setEstoque(livro.getEstoque()+quantidade);
        livroRepo.save(livro);

        return RetornoApi.sucess("Adicionado " + quantidade.toString() + " a quantidade de livros no estoque.", livro);
    }
    public RetornoApi adicionarLivroEstoque(String token, Integer idLivro) {
        return removerLivroEstoque(token, idLivro, 1);
    }

    public RetornoApi removerLivroEstoque(String token, Integer idLivro, Integer quantidade){
        if(!authService.checkAdminPerm(token)){ return RetornoApi.errorForbidden(); }

        if(quantidade == null || quantidade == 0){quantidade = 1;}
        Optional<Livro> buscaLivro;
        Livro livro;

        buscaLivro = livroRepo.findById(idLivro);

        if(!buscaLivro.isPresent()){
            return RetornoApi.errorNotFound("Nenhum livro encontrado.");
        }

        livro = buscaLivro.get();

        livro.setEstoque(livro.getEstoque()-quantidade);
        livroRepo.save(livro);

        return RetornoApi.sucess("Removido " + quantidade.toString() + " a quantidade de livros no estoque.", livro);
    }
    public RetornoApi removerLivroEstoque(String token, Integer idLivro) {
        DebugService.log("Removendo um a quantidade de livros...");
        return removerLivroEstoque(token, idLivro, 1);
    }


    public RetornoApi alterarEstoqueLivro(String token, Integer idLivro, Integer quantidade){
        if(!authService.checkAdminPerm(token)){ return RetornoApi.errorForbidden(); }

        if(quantidade == null || quantidade == 0){return RetornoApi.errorBadRequest("Insira a quantidade a ser alterada!");}
        Optional<Livro> buscaLivro;
        Livro livro;

        buscaLivro = livroRepo.findById(idLivro);

        if(!buscaLivro.isPresent()){
            return RetornoApi.errorNotFound("Nenhum livro encontrado.");
        }

        livro = buscaLivro.get();

        livro.setEstoque(quantidade);
        livroRepo.save(livro);

        return RetornoApi.sucess("Quantidade de livros no estoque alterada.", livro);
    }

    public RetornoApi inativarLivro(String token, Integer idLivro){
        if(!authService.checkAdminPerm(token)){ return RetornoApi.errorForbidden(); }

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

        return RetornoApi.sucess("Livro inativado com sucesso.", livro);
    }

    public RetornoApi ativarLivro(String token, Integer idLivro){
        Optional<Livro> buscaLivro;
        Livro livro;
        Livro livroRetorno;

        if(!authService.checkAdminPerm(token)){ return RetornoApi.errorForbidden(); }

        buscaLivro = livroRepo.findById(idLivro);
        if(!buscaLivro.isPresent()){
            return RetornoApi.errorNotFound("Nenhum livro encontrado");
        }
        if(buscaLivro.get().getAtivo()){
            return RetornoApi.errorBadRequest("livro já ativo!");
        }

        livro = buscaLivro.get();
        livro.setAtivo(true);
        livroRetorno = livroRepo.save(livro);
        return RetornoApi.sucess("Livro ativado com sucesso", livroRetorno);
    }

}
