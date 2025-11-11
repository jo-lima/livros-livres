package com.livros_livres.Server.Controllers.Private;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.livros_livres.Server.Registers.Livros.Livro;
import com.livros_livres.Server.Registers.RequestBody.LivroRequest;
import com.livros_livres.Server.Registers.Server.RetornoApi;
import com.livros_livres.Server.Services.LivroService;

import io.micrometer.common.lang.Nullable;

@RestController
@RequestMapping("/livro")
public class LivroController{
    private LivroService livroService;

    public LivroController(LivroService livroService)
    {
        this.livroService = livroService;
    }

    @PostMapping("/novo")
    public RetornoApi novoLivro(@Nullable @RequestHeader("token") String token, @RequestBody LivroRequest body){
        return livroService.novoLivro(token, body);
    }

    @GetMapping("/{id}/busca")
    public RetornoApi buscaLivro(@PathVariable("id") String idParam){
        int idLivro = Integer.parseInt(idParam);
        return livroService.buscaLivro(idLivro);
        }

    @PostMapping("/lista")
    public RetornoApi listaLivros(@Nullable @RequestBody Livro body){
        return livroService.listaLivros(body);
    }

    @PostMapping("/{id}/atualizar")
    public RetornoApi atualizarLivro(@Nullable @RequestHeader("token") String token, @PathVariable("id") String idParam, @RequestBody LivroRequest body){
        int idLivro = Integer.parseInt(idParam);
        return livroService.atualizarLivro(token, idLivro, body);
    }

    @PostMapping("/{id}/inativar")
    public RetornoApi inativarLivro(@Nullable @RequestHeader("token") String token, @PathVariable("id") String idParam){
        int idLivro = Integer.parseInt(idParam);
        return livroService.inativarLivro(token, idLivro);
    }

    @PostMapping("/{id}/ativar")
    public RetornoApi ativarLivro(@Nullable @RequestHeader("token") String token, @PathVariable("id") String idParam){
        int idLivro = Integer.parseInt(idParam);
        return livroService.ativarLivro(token, idLivro);
    }

    @PostMapping("/estoque/{id}/adicionar")
    public RetornoApi adicionarLivroEstoque(@Nullable @RequestHeader("token") String token, @PathVariable("id") String idParam, @Nullable @RequestParam Integer quantidade){
        int idLivro = Integer.parseInt(idParam);
        return livroService.adicionarLivroEstoque(token, idLivro, quantidade);
    }

    @PostMapping("/estoque/{id}/remover")
    public RetornoApi removerLivroEstoque(@Nullable @RequestHeader("token") String token, @PathVariable("id") String idParam, @Nullable @RequestParam Integer quantidade){
        int idLivro = Integer.parseInt(idParam);
        return livroService.removerLivroEstoque(token, idLivro, quantidade);
    }

    @PostMapping("/estoque/{id}/alterar")
    public RetornoApi alterarEstoqueLivro(@Nullable @RequestHeader("token") String token, @PathVariable("id") String idParam, @RequestParam Integer quantidade){
        int idLivro = Integer.parseInt(idParam);
        return livroService.alterarEstoqueLivro(token, idLivro, quantidade);
    }
}