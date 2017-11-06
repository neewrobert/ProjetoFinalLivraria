package br.com.casadocodigo.livrariacasadocodigo.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.casadocodigo.livrariacasadocodigo.Entities.Categoria;
import br.com.casadocodigo.livrariacasadocodigo.Entities.Usuario;

/**
 * Created by nrdossantos on 03/11/2017.
 */

public class CategoriaDao extends BaseDao<Categoria> {

    public CategoriaDao (Context context){
        super(context);
    }


    public List<Categoria> getTodasCategorias(){
        String sql = "Select * from categoria";
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Categoria> categorias = new ArrayList<Categoria>();

        while (c.moveToNext()){
            Categoria categoria = new Categoria();
            categoria.setId(c.getLong(c.getColumnIndex("id")));
            categoria.setNome(c.getString(c.getColumnIndex("nome")));

            categorias.add(categoria);
        }
        c.close();
        return categorias;
    }

    public Categoria getCategoriaById(int id){
        SQLiteDatabase db = getWritableDatabase();

        String params[] = {String.valueOf(id)};
        String coluns[] = {"id", "nome"};
        String clause = "id = ?";

        Categoria categoria = new Categoria();

        Cursor cursor = db.query("categoria", coluns, clause, params, null, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                categoria.setId(cursor.getLong(cursor.getColumnIndex("id")));
                categoria.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            } else {
                return null;
            }
        } else {
            return null;
        }
        db.close();
        return categoria;
    }

}
