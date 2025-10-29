package com.livros_livres.Server.controllers.Private;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.livros_livres.Server.Registers.Server.RetornoApi;
import com.livros_livres.Server.Registers.livros.Livro;
import com.livros_livres.Server.services.LivroService;

@RestController
@RequestMapping("/livro")
public class LivroController{
    private LivroService livroService;

    public LivroController(LivroService livroService)
    {
        this.livroService = livroService;
    }

    @PostMapping("/novo")
    public RetornoApi novoLivro(@RequestBody Livro body){
            return livroService.novoLivro(body);
        }

    @GetMapping("/{id}/busca")
    public RetornoApi buscaLivro(@PathVariable("id") String idParam){
        int idLivro = Integer.parseInt(idParam);
        return livroService.buscaLivro(idLivro);
        }

    @GetMapping("/lista")
    public RetornoApi listaLivro(@RequestBody Livro body){
            return livroService.listaLivro(body);
        }

    @PostMapping("{id}/atualizar")
    public RetornoApi atualizarLivro(@PathVariable("id") String idParam, @RequestBody Livro body){
            int idLivro = Integer.parseInt(idParam);
            return livroService.atualizarLivro(idLivro, body)
        }

    @PostMapping("{id}/inativar")
    public RetornoApi inativarLivro(@PathVariable("id") String idParam){
        int idAutor = Integer.parseInt(idParam);
        return livroService.inativarLivro(idLivro);
        }

    @PostMapping("{id}/deletar")
    public RetornoApi deletarLivro(@PathVariable("id") String idParam){
        int idLivro = Integer.parseInt(idParam);
        return livroService.deletarLivro(idLivro);
    }
}