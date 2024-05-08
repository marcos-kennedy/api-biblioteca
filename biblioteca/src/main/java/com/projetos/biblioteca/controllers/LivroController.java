package com.projetos.biblioteca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projetos.biblioteca.models.Livro;
import com.projetos.biblioteca.service.ServicoLivros;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/livros")
public class LivroController {
    @Autowired
    private ServicoLivros servicoLivros;

    @PostMapping
    public ResponseEntity<?> cadastrarLivros(@RequestBody Livro livro) { 
        return servicoLivros.cadastrar(livro);
    }
    
    @GetMapping
    public ResponseEntity<?> listarLivros() {
        return servicoLivros.listar();
    }

    @GetMapping("/{termo}")
    public ResponseEntity<?> listarLivros(@PathVariable String termo) {
        return servicoLivros.listar(termo);
    }

    @DeleteMapping("/{nomeLivro}")
    public ResponseEntity<?> excluirLivro(@PathVariable String nomeLivro){
        return servicoLivros.excluir(nomeLivro);
    }

}
