package com.projetos.biblioteca.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nomeLivro;
    private Integer numeroExemplares;
    private String nomeAutor;
    
    public Integer getId() {
        return id;
    }
    public String getNomeLivro() {
        return nomeLivro;
    }
    public void setNomeLivro(String nomeLivro) {
        this.nomeLivro = nomeLivro;
    }
    public Integer getNumeroExemplares() {
        return numeroExemplares;
    }
    public void setNumeroExemplares(Integer numeroExemplares) {
        this.numeroExemplares = numeroExemplares;
    }
    public String getNomeAutor() {
        return nomeAutor;
    }
    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }
}
