package com.projetos.biblioteca.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetos.biblioteca.models.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {
    
}
