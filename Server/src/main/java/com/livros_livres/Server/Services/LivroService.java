package com.livros_livres.Server.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livros_livres.Server.Registers.Livros.Livro;
import com.livros_livres.Server.Registers.RequestBody.LivroRequest;
import com.livros_livres.Server.Registers.Server.RetornoApi;
import com.livros_livres.Server.Repository.LivroRepo;

@Service
public class LivroService{
    @Autowired
    private LivroRepo livroRepo;
    @Autowired
    private AutorService autorService;

    private boolean checkIsbnUnique(String isbn) {
        // Pesquisa por códigos isbn
        Optional<Livro> buscaLivro = null;
        // livroRepo.findById(isbn);
        if (buscaLivro == null) {return false;}
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

    public RetornoApi novoLivro(LivroRequest livroData){
        Livro novoLivro = new Livro();
        Livro salvoLivro;
        // get autor by id (change into another request type?)
        // paginas cannot be <= 1
        if (
            livroData.getNome() == null ||
            livroData.getGenero() == null ||
            livroData.getPaginas() <= 0 ||
            // livroData.getEstoque() == 0  ||
            livroData.getIsbn() == null
        ) {
            return RetornoApi.errorBadRequest("Insira os valores requeridos para criação do livro.");
        }
        // if( !this.checkIsbnUnique(livroData.getIsbn()) ) {
        if(false) {
            return RetornoApi.errorBadRequest("ISBN deve ser único.");
        }

        // TODO: Check if autor exists
        novoLivro.setAutor(autorService.buscaAutorById(livroData.getAutorId()));
        novoLivro.setAtivo(true);

        novoLivro.setNome(livroData.getNome());
        novoLivro.setGenero(livroData.getGenero());
        novoLivro.setPaginas(livroData.getPaginas());
        novoLivro.setIsbn(livroData.getIsbn());
        novoLivro.setDescricao(livroData.getDescricao());
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
        if(quantidade == null || quantidade == 0){quantidade = 1;}

        Optional<Livro> buscaLivro;
        Livro livro;

        buscaLivro = livroRepo.findById(idLivro);

        if(!buscaLivro.isPresent() && quantidade != 0){
            return RetornoApi.errorNotFound("Nenhum livro encontrado.");
        }

        livro = buscaLivro.get();

        livro.setEstoque(livro.getEstoque()+quantidade);
        livroRepo.save(livro);

        return RetornoApi.sucess("Adicionado " + quantidade.toString() + " a quantidade de livros no estoque.", livro);
    }

    public RetornoApi removerLivroEstoque(Integer idLivro, Integer quantidade){
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

    public RetornoApi alterarEstoqueLivro(Integer idLivro, Integer quantidade){
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

        return RetornoApi.sucess("Autor inativado com sucesso.!", livro);
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
