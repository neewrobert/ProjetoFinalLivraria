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

public class UsuarioDao extends SQLiteOpenHelper{

    private final String CREATETABLE = "Create table usuario (" +
            "id INTERGER PRIMARY KEY," +
            "nome TEXT NOT NULL," +
            "email TEXT NOT NULL," +
            "senha TEXT NOT NULL);";

    public UsuarioDao(Context context) {

        super(context, "CasaDoCodigo", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATETABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "Drop table if EXISTS usuario";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insere(Usuario usuario){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = getContentValues(usuario);
        db.insert("usuario", null, dados);

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

    private ContentValues getContentValues(Usuario usuario) {

        ContentValues values = new ContentValues();
        values.put("nome", usuario.getNome());
        values.put("email", usuario.getEmail());
        values.put("senha", usuario.getSenha());

        return values;
    }
}
