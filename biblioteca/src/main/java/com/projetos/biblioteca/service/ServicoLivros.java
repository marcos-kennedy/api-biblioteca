package com.projetos.biblioteca.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.projetos.biblioteca.models.Livro;
import com.projetos.biblioteca.repositorys.LivroRepository;
import com.projetos.biblioteca.repositorys.RegistroRepository;

@Service
public class ServicoLivros {
    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private RegistroRepository registroRepository;
    private Mensagem mensagem = new Mensagem();

    public ResponseEntity<?> cadastrar(Livro livro) {
        if (livro.getNomeLivro().isEmpty() || livro.getNomeAutor().isEmpty()) {
            mensagem.setMensagem("Erro! Existem campos vazios.");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        } else if (livro.getNumeroExemplares() <= 0) {
            mensagem.setMensagem("Erro! Quantidade inválida.");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(livroRepository.save(livro), HttpStatus.OK);
        }
    }
    public ResponseEntity<?> devolver(String nomeLivro){
        Livro livroRetornado = livroRepository.findByNomeLivro(nomeLivro);
        if(livroRetornado == null){
            mensagem.setMensagem("Erro! Livro não encontrado.");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        } else {
            Integer numeroExemplaresAtualizado = livroRetornado.getNumeroExemplares() + 1;
            livroRetornado.setNumeroExemplares(numeroExemplaresAtualizado);
            livroRepository.save(livroRetornado);
            mensagem.setMensagem("Livro devolvido com sucesso!");
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        }
    }
    public ResponseEntity<?> listar(){
        return new ResponseEntity<>(livroRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<?> listar(String nomeLivro){
        if(livroRepository.findByNomeLivro(nomeLivro) == null){
            mensagem.setMensagem("Erro! Livro não encontrado.");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(livroRepository.findByNomeLivro(nomeLivro), HttpStatus.OK);
    }

    public ResponseEntity<?> excluir(String nomeLivro){
        Livro livro = livroRepository.findByNomeLivro(nomeLivro);
        if (livro == null) {
            mensagem.setMensagem("Livro não existe no acervo!");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        } else {
            registroRepository.deleteById(livro.getId());
            livroRepository.deleteById(livro.getId());
            mensagem.setMensagem("Livro excluído com sucesso!");
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        }
    }

}
