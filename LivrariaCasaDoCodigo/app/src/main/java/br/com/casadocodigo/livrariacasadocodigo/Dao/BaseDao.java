package br.com.casadocodigo.livrariacasadocodigo.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import br.com.casadocodigo.livrariacasadocodigo.Entities.EntidadeBase;

/**
 * Created by nrdossantos on 03/11/2017.
 */

public class BaseDao<T extends EntidadeBase> extends SQLiteOpenHelper {

    private final String TABLE_USUARIO = "Create table usuario (" +
            "id INTERGER PRIMARY KEY," +
            "nome TEXT NOT NULL," +
            "email TEXT NOT NULL," +
            "senha TEXT NOT NULL);";

    private static final String TABLE_CATEGORIA = "CREATE TABLE categoria (" +
            "id INTERGER PRIMARY KEY," +
            "nome TEXT NOT NULL); ";

    private static  final String TABELA_lIVRO = "CREATE TABLE livro (" +
            "id INTERGER PRIMARY KEY," +
            "isbn LONG NOT NULL," +
            "titulo TEXT NOT NULL," +
            "subTitulo TEXT," +
            "edicao TEXT," +
            "autor TEXT," +
            "qtdPaginas INTERGER," +
            "ano INTERGER," +
            "idCategoria INTERGER," +
            "foto TEXT);";

    private static final String ALTER_TAB_LIVRO = "ALTER TABLE livro ADD COLUMN editora TEXT";

    public BaseDao(Context context) {
        super(context, "CasaDoCodigo", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_USUARIO);
        db.execSQL(TABLE_CATEGORIA);
        db.execSQL(TABELA_lIVRO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        switch (oldVersion){
            case 1:
                db.execSQL(TABLE_CATEGORIA);
            case 2:
                db.execSQL(TABELA_lIVRO);
            case 3:
                db.execSQL(ALTER_TAB_LIVRO);
        }
    }

    public void insere(T t){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = getContentValues(t);

        if (dados.get("id") == null){
            dados.remove("id");
        }

        if (dados.size() > 0) {
            db.insert(t.getClass().getSimpleName(), null, dados);
        }
    }

    public void delete (T t){
        SQLiteDatabase db = getWritableDatabase();

        Class<?> clazz = t.getClass();
        Field fields[] = clazz.getDeclaredFields();
        String[] params = {};

        try {
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getName().equalsIgnoreCase("id")){
                    params[0] = field.get(t).toString();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        db.delete(t.getClass().getSimpleName(), "id = ?", params);
    }

    public void update (T t){
        SQLiteDatabase db = getWritableDatabase();
        Class<?> clazz = t.getClass();
        Field fields[] = clazz.getDeclaredFields();

        ContentValues dados = getContentValues(t);
        String[] params = {dados.get("id").toString()};
        db.update(clazz.getSimpleName(), dados, "id = ?", params);
    }



    protected ContentValues getContentValues(T t) {

        ContentValues dados = new ContentValues();

        Class<?> clazz = t.getClass();
        Field fields[] = clazz.getDeclaredFields();

        try {
            for (Field field : fields) {
                field.setAccessible(true);

                if(field.getType().equals(Long.class)){
                    dados.put(field.getName(), (Long) field.get(t) );
                } else if (field.getType().equals(String.class)){
                    dados.put(field.getName(), (String) field.get(t) );
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return dados;
    }

}
