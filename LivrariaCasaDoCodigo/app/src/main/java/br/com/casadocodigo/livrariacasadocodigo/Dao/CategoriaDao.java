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

}
