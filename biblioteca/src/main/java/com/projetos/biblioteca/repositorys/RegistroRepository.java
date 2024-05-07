package com.projetos.biblioteca.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.projetos.biblioteca.models.Registro;

public interface RegistroRepository extends JpaRepository<Registro, Integer>{
    List<Registro> findAll();
    List<Registro> findByAlunoNome(String nome);
    List<Registro> findByAlunoNomeContaining(String termo);

    @Query(value = "SELECT id FROM emprestimos ORDER BY id ASC LIMIT ?1", nativeQuery = true)
    List<Integer> idsToDelete(int qtdRegistros);

    

}
