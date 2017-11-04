package br.com.casadocodigo.livrariacasadocodigo.Entities;

import java.io.Serializable;

/**
 * Created by nrdossantos on 03/11/2017.
 */

public class Livro extends EntidadeBase{


    private Long id;
    private Long isbn;
    private String titulo;
    private String subTitulo;
    private String edicao;
    private String autor;
    private Long qtdPaginas;
    private Long ano;
    private String editora;
    private Long idCategoria;
    private String foto;



    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

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

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
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

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
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
