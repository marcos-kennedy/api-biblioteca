package com.projetos.biblioteca.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetos.biblioteca.models.Livro;

public interface LivroRepository extends JpaRepository<Livro, Integer> {
    List<Livro> findAll();
    Livro findByNomeLivro(String nomeLivro);
    List<Livro> findByNomeLivroContaining(String termo);
}
