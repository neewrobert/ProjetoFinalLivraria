package br.com.casadocodigo.livrariacasadocodigo.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.casadocodigo.livrariacasadocodigo.Entities.Usuario;

/**
 * Created by nrdossantos on 02/11/2017.
 */

public class UsuarioDao extends BaseDao<Usuario>{

    public UsuarioDao(Context context) {

        super(context);
    }

    public Usuario getUsuarioByEmail (String email){
        SQLiteDatabase db = getWritableDatabase();

        String params[] = {email};
        String coluns[] = {"id", "nome", "email", "senha"};
        String clause = "email = ?";

        Usuario usuario = new Usuario();

        Cursor cursor = db.query("usuario", coluns, clause, params, null, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                usuario.setId(cursor.getLong(cursor.getColumnIndex("id")));
                usuario.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                usuario.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                usuario.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
            } else {
                return null;
            }
        } else {
            return null;
        }
    db.close();
    return usuario;
    }


}
