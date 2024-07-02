package com.projetos.biblioteca.service;


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

        if (aluno.getNome().isEmpty() || aluno.getTurma().isEmpty() || livro.getNomeLivro().isEmpty()) {
            mensagem.setMensagem("Erro ao fazer registro. Existem campos vazios!");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        } 
        else if (registro.getDataEmprestimo().isEmpty() || registro.getDataDevolucao().isEmpty()) {
           mensagem.setMensagem("Erro ao fazer registro. Informe as datas deste empréstimo!");
           return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        } 
        else {
            Livro livroRetornado = livroRepository.findByNomeLivro(livro.getNomeLivro());
            if(livroRetornado == null){
                mensagem.setMensagem("Erro: Livro não encontrado no acervo!");
                return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
            } else {
                Integer numeroExemplaresAtualizado = livroRetornado.getNumeroExemplares() - 1;
                if(numeroExemplaresAtualizado >= 0){
                    livroRetornado.setNumeroExemplares(numeroExemplaresAtualizado);
                    livroRepository.save(livroRetornado);
                    alunoRepository.save(aluno);
                    registro.setLivro(livroRetornado);
                    registroRepository.save(registro);
                    mensagem.setMensagem("Registro efetuado com sucesso!");
                    return new ResponseEntity<>(mensagem, HttpStatus.CREATED);
                } else {
                    mensagem.setMensagem("Erro: Todos os exemplares foram emprestados!");
                    return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
                }
            }
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
}
