package com.projetos.biblioteca.service;

import java.util.List;

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
public class ServicoEmprestimo {
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private RegistroRepository registroRepository;
    private Mensagem mensagem = new Mensagem();

    // serviço para validar empréstimos
    public ResponseEntity<?> registrar(Registro registro){
        Aluno aluno = registro.getAluno();
        Livro livro = registro.getLivro();

        if (aluno.getNome().isEmpty() || aluno.getTurma().isEmpty() || livro.getNomeLivro().isEmpty() || livro.getNomeAutor().isEmpty()) {
            mensagem.setMensagem("Erro ao fazer registro. Existem campos vazios!");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        } 
        else if (livro.getNumeroExemplares() <= 0) {
            mensagem.setMensagem("Erro ao fazer registro. Existem valores inválidos!");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        } 
        else if (registro.getDataEmprestimo().isEmpty() || registro.getDataDevolucao().isEmpty()) {
           mensagem.setMensagem("Erro ao fazer registro. Informe as datas deste empréstimo!");
           return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        } 
        else {
            alunoRepository.save(aluno);
            livroRepository.save(livro);
            return new ResponseEntity<>(registroRepository.save(registro), HttpStatus.CREATED);
        }
    }

    // serviço para listar todos os empréstimos
    public ResponseEntity<?> listar(){
        return new ResponseEntity<>(registroRepository.findAll(), HttpStatus.OK);
    }

    // serviço para listar empréstimos por nome do aluno
    public ResponseEntity<?> listar(String nomeAluno){
        if (nomeAluno.isEmpty()){
            mensagem.setMensagem("Erro! Nome Inválido.");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(registroRepository.findByAlunoNomeContaining(nomeAluno), HttpStatus.OK);
        }
    }

    // serviço para deletar empréstimos por qtd informada
    public ResponseEntity<?> deletar(int qtdRegistros) {
        if (qtdRegistros <= 0) {
            mensagem.setMensagem("Erro! valor inválido.");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        } else {
            List<Integer> idsToDelete = registroRepository.idsToDelete(qtdRegistros);
            for (Integer id : idsToDelete) {
                registroRepository.deleteById(id);
                alunoRepository.deleteById(id);
            }
            mensagem.setMensagem("Registros excluidos com sucesso!");
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        }
    }
}
