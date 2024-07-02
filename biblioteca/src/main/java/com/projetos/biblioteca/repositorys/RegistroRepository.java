package com.projetos.biblioteca.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetos.biblioteca.models.Registro;

public interface RegistroRepository extends JpaRepository<Registro, Integer>{
    List<Registro> findAll(); 
    Registro findByAlunoNome(String nome);
    List<Registro> findByAlunoNomeContaining(String termo);
}
