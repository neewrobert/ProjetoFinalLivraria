package br.com.casadocodigo.livrariacasadocodigo.Entities;

import java.io.Serializable;

/**
 * Created by nrdossantos on 03/11/2017.
 */

public class Livro implements Serializable{


    private Long id;
    private Long isbn;
    private String titulo;
    private String subTitulo;
    private String edicao;
    private String autoR;
    private Long qtdPaginas;
    private Long ano;
    private String editora;
    private Categoria categoria;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubTitulo() {
        return subTitulo;
    }

    public void setSubTitulo(String subTitulo) {
        this.subTitulo = subTitulo;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public String getAutoR() {
        return autoR;
    }

    public void setAutoR(String autoR) {
        this.autoR = autoR;
    }

    public Long getQtdPaginas() {
        return qtdPaginas;
    }

    public void setQtdPaginas(Long qtdPaginas) {
        this.qtdPaginas = qtdPaginas;
    }

    public Long getAno() {
        return ano;
    }

    public void setAno(Long ano) {
        this.ano = ano;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Livro livro = (Livro) o;

        return getIsbn().equals(livro.getIsbn());
    }

    @Override
    public int hashCode() {
        return getIsbn().hashCode();
    }
}
