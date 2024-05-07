package com.projetos.biblioteca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projetos.biblioteca.models.Registro;
import com.projetos.biblioteca.service.ServicoEmprestimo;

@RestController
@RequestMapping("/emprestimos")
public class RegistroController {

    @Autowired
    private ServicoEmprestimo servicoEmprestimo;

    @PostMapping
    public ResponseEntity<?> cadastrarEmprestimo(@RequestBody Registro registro){
        return servicoEmprestimo.registrar(registro);
    }

    @GetMapping
    public ResponseEntity<?> listarEmprestimos(){
        return servicoEmprestimo.listar();
    }

    @GetMapping("/{nomeAluno}")
    public ResponseEntity<?> listarEmprestimo(@PathVariable String nomeAluno){
        return servicoEmprestimo.listar(nomeAluno);
    }

    @DeleteMapping("/{qtdRegistros}")
    public ResponseEntity<?> deletarEmprestimos(@PathVariable int qtdRegistros){
        return servicoEmprestimo.deletar(qtdRegistros);
    }

}
