package br.com.casadocodigo.livrariacasadocodigo.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.casadocodigo.livrariacasadocodigo.Entities.Livro;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



/**
 * Created by nrdossantos on 04/11/2017.
 */

public class LivroDao extends BaseDao<Livro> {

    public LivroDao (Context context){
        super(context);
    }


    public List<Livro> getTodosLivros() {

        String sql = "select * from livro";
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Livro> livros = new ArrayList<>();

        while (c.moveToNext()) {
            Livro livro = new Livro();

            livro.setId(c.getLong(c.getColumnIndex("id")));
            livro.setIsbn(c.getLong(c.getColumnIndex("isbn")));
            livro.setTitulo(c.getString(c.getColumnIndex("titulo")));
            livro.setSubTitulo(c.getString(c.getColumnIndex("subTitulo")));
            livro.setEdicao(c.getString(c.getColumnIndex("edicao")));
            livro.setAutor(c.getString(c.getColumnIndex("autor")));
            livro.setQtdPaginas(c.getLong(c.getColumnIndex("qtdPaginas")));
            livro.setEditora(c.getString(c.getColumnIndex("editora")));
            livro.setAno(c.getLong(c.getColumnIndex("ano")));
            livro.setIdCategoria(c.getLong(c.getColumnIndex("idCategoria")));
            livro.setFoto(c.getString(c.getColumnIndex("foto")));

            livros.add(livro);
        }
        c.close();
        db.close();
        return livros;
    }

    public List<Livro> getLivrosPorCategoria(Long id) {
        SQLiteDatabase db = getWritableDatabase();

        String params[] = {String.valueOf(id)};
        String coluns[] = {"id", "isbn", "titulo", "subTitulo", "edicao", "autor", "qtdPaginas", "editora", "ano", "idCategoria", "foto"};
        String clause = "idCategoria = ?";
        Cursor c = db.query("livro", coluns, clause, params, null, null, null, null);

        List<Livro> livros = new ArrayList<>();

        if (c != null) {
            while (c.moveToNext()) {
                Livro livro = new Livro();

                livro.setId(c.getLong(c.getColumnIndex("id")));
                livro.setIsbn(c.getLong(c.getColumnIndex("isbn")));
                livro.setTitulo(c.getString(c.getColumnIndex("titulo")));
                livro.setSubTitulo(c.getString(c.getColumnIndex("subTitulo")));
                livro.setEdicao(c.getString(c.getColumnIndex("edicao")));
                livro.setAutor(c.getString(c.getColumnIndex("autor")));
                livro.setQtdPaginas(c.getLong(c.getColumnIndex("qtdPaginas")));
                livro.setEditora(c.getString(c.getColumnIndex("editora")));
                livro.setAno(c.getLong(c.getColumnIndex("ano")));
                livro.setIdCategoria(c.getLong(c.getColumnIndex("idCategoria")));
                livro.setFoto(c.getString(c.getColumnIndex("foto")));

                livros.add(livro);
            }
        }
        c.close();
        db.close();
        return livros;
    }
}
