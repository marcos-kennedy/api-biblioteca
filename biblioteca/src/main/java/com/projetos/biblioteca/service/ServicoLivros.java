package com.projetos.biblioteca.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.projetos.biblioteca.models.Aluno;
import com.projetos.biblioteca.models.Livro;
import com.projetos.biblioteca.models.Registro;
import com.projetos.biblioteca.repositorys.AlunoRepository;
import com.projetos.biblioteca.repositorys.LivroRepository;
import com.projetos.biblioteca.repositorys.RegistroRepository;

@Service
public class ServicoLivros {
    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private RegistroRepository registroRepository;
    @Autowired
    private AlunoRepository alunoRepository;
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
    public ResponseEntity<?> devolver(Integer idRegistro){
        Optional<Registro> registroOptional = registroRepository.findById(idRegistro);
        Registro registroRetornado = null;
        Livro livroRetornado = null;
        Aluno alunoRetornado = null;

        if (registroOptional.isPresent()) {
            registroRetornado = registroOptional.get();
            livroRetornado = registroRetornado.getLivro();
            alunoRetornado = registroRetornado.getAluno();
        } else {
            mensagem.setMensagem("Erro! Registro de empréstimo não encontrado!");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        }
        if(livroRetornado == null){
            mensagem.setMensagem("Erro! Livro não encontrado.");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        } else {
            Integer numeroExemplaresAtualizado = livroRetornado.getNumeroExemplares() + 1;
            livroRetornado.setNumeroExemplares(numeroExemplaresAtualizado);
            livroRepository.save(livroRetornado);
            registroRepository.delete(registroRetornado);
            alunoRepository.delete(alunoRetornado);
            mensagem.setMensagem("Livro devolvido com sucesso!");
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        }
    }
    public ResponseEntity<?> adicionar(Integer idLivro){
        Optional<Livro> livroOptional = livroRepository.findById(idLivro);
        if (livroOptional.isPresent()) {
            Livro livroRetornado = livroOptional.get();
            Integer numeroExemplaresAtualizado = livroRetornado.getNumeroExemplares() + 1;
            livroRetornado.setNumeroExemplares(numeroExemplaresAtualizado);
            livroRepository.save(livroRetornado);
            mensagem.setMensagem("Quantidade de exemplares atualizada!");
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } else {
            mensagem.setMensagem("Erro! Livro não encontrado.");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
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
