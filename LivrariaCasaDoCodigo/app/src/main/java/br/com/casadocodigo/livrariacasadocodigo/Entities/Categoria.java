package br.com.casadocodigo.livrariacasadocodigo.Entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by nrdossantos on 03/11/2017.
 */

public class Categoria extends EntidadeBase {

    private Long id;
    private String nome;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return getNome();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Categoria categoria = (Categoria) o;

        return nome.equalsIgnoreCase(categoria.nome);
    }

    @Override
    public int hashCode() {
        return nome.hashCode();
    }
}
