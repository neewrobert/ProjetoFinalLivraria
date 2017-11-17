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
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nome TEXT NOT NULL," +
            "email TEXT NOT NULL," +
            "senha TEXT NOT NULL);";

    private static final String TABLE_CATEGORIA = "CREATE TABLE categoria (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nome TEXT NOT NULL); ";

    private static  final String TABELA_lIVRO = "CREATE TABLE livro (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "isbn LONG NOT NULL," +
            "titulo TEXT NOT NULL," +
            "subTitulo TEXT," +
            "edicao TEXT," +
            "autor TEXT," +
            "qtdPaginas INTEGER," +
            "ano INTEGER," +
            "idCategoria INTEGER," +
            "editora TEXT," +
            "foto TEXT);";

    private static final String CAT_FICCAO = "INSERT INTO categoria (id, nome) values (1, 'Ficcao')";
    private static final String CAT_LITERATURA = "INSERT INTO categoria (id, nome) values (2, 'Literatura')";
    private static final String CAT_ESTRANGEIRA = "INSERT INTO categoria (id, nome) values (3, 'Literatura Estrangeira')";
    private static final String CAT_AUTOAJUDA = "INSERT INTO categoria (id, nome) values (4, 'Auto-Ajuda')";
    private static final String CAT_ESPORTE = "INSERT INTO categoria (id, nome) values (5, 'Esporte')";
    private static final String CAT_FANTASIA = "INSERT INTO categoria (id, nome) values (6, 'Fantasia')";
    private static final String CAT_HISTORIA = "INSERT INTO categoria (id, nome) values (7, 'Historia')";
    private static final String CAT_MEDICINA = "INSERT INTO categoria (id, nome) values (8, 'Medicina')";
    private static final String CAT_BIOGRAF = "INSERT INTO categoria (id, nome) values (9, 'Biografico')";
    private static final String CAT_POESIA = "INSERT INTO categoria (id, nome) values (10, 'Poesia')";
    private static final String CAT_ROMANCE = "INSERT INTO categoria (id, nome) values (11, 'Romance')";
    private static final String CAT_POLITICA = "INSERT INTO categoria (id, nome) values (12, 'Politica')";
    private static final String CAT_TERROR = "INSERT INTO categoria (id, nome) values (13, 'Terror')";

    private static final String ALTER_TAB_LIVRO = "ALTER TABLE livro ADD COLUMN editora TEXT";
    private static final String DROP_CATEGORIA = "DROP TABLE IF EXISTS categoria;";
    private static final String DROP_LIVRO = "DROP TABLE IF EXISTS livro;";
    private static final String DROP_USARIO = "DROP TABLE IF EXISTS usuario;";

    private static final String LIVRO_1 = "INSERT INTO livro " +
            "(id, isbn, titulo, subtitulo, edicao, autor, qtdPaginas, ano, idCategoria, editora) " +
            "values (1, 9788532530783, 'Harry Potter Pedra Filosofal', 'Livro 1', 1, 'JK Rowling', 208, 2009, 3, 'Rocco')";

    private static final String LIVRO_2 = "INSERT INTO livro " +
            "(id, isbn, titulo, subtitulo, edicao, autor, qtdPaginas, ano, idCategoria, editora) " +
            "values (2, 9788528622508, 'Harry Potter Camara secreta', 'Livro 2', 1, 'JK Rowling', 196, 2014, 11, 'Rocco')";

    private static final String LIVRO_3 = "INSERT INTO livro " +
            "(id, isbn, titulo, subtitulo, edicao, autor, qtdPaginas, ano, idCategoria, editora) " +
            "values (3, 9788561578725, 'Harry Potter Principe de Azkaban', 'Livro 3', 2, 'JK Rowling', 210, 2016, 1, 'Rocco')";

    private static final String USUARIO_DEFAULT = "INSERT INTO usuario (id, nome, email, senha) " +
            "values (1, 'administrador', 'admin', 'admin')";

    public BaseDao(Context context) {
        super(context, "CasaDoCodigo", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_USUARIO);
        db.execSQL(USUARIO_DEFAULT);
        db.execSQL(TABLE_CATEGORIA);
        db.execSQL(TABELA_lIVRO);
        db.execSQL(CAT_FICCAO);
        db.execSQL(CAT_LITERATURA);
        db.execSQL(CAT_ESTRANGEIRA);
        db.execSQL(CAT_AUTOAJUDA);
        db.execSQL(CAT_ESPORTE);
        db.execSQL(CAT_FANTASIA);
        db.execSQL(CAT_HISTORIA);
        db.execSQL(CAT_MEDICINA);
        db.execSQL(CAT_BIOGRAF);
        db.execSQL(CAT_POESIA);
        db.execSQL(CAT_ROMANCE);
        db.execSQL(CAT_POLITICA);
        db.execSQL(CAT_TERROR);
        db.execSQL(LIVRO_1);
        db.execSQL(LIVRO_2);
        db.execSQL(LIVRO_3);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        switch (oldVersion){

            case 1:
                db.execSQL(DROP_LIVRO);
                db.execSQL(DROP_CATEGORIA);
                db.execSQL(DROP_USARIO);
                db.execSQL(TABLE_USUARIO);
                db.execSQL(USUARIO_DEFAULT);
                db.execSQL(TABLE_CATEGORIA);
                db.execSQL(TABELA_lIVRO);
                db.execSQL(CAT_FICCAO);
                db.execSQL(CAT_LITERATURA);
                db.execSQL(CAT_ESTRANGEIRA);
                db.execSQL(CAT_AUTOAJUDA);
                db.execSQL(CAT_ESPORTE);
                db.execSQL(CAT_FANTASIA);
                db.execSQL(CAT_HISTORIA);
                db.execSQL(CAT_MEDICINA);
                db.execSQL(CAT_BIOGRAF);
                db.execSQL(CAT_POESIA);
                db.execSQL(CAT_ROMANCE);
                db.execSQL(CAT_POLITICA);
                db.execSQL(CAT_TERROR);
                db.execSQL(LIVRO_1);
                db.execSQL(LIVRO_2);
                db.execSQL(LIVRO_3);
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
        String[] params = new String[1];

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
