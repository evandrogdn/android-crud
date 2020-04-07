package com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class AlunoDAO extends SQLiteOpenHelper {

    private static final int VERSAO = 1;
    private static final String TABELA = "CadastroAlunos";
    private static final String DATABASE = "alunos";
    private static final String[] COLUNAS = {"id", "nome", "endereco", "telefone", "site", "foto", "nota"};

    public AlunoDAO(@Nullable Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABELA + "("
                + "id INTEGER PRIMARY KEY,"
                + "nome TEXT UNIQUE NOT NULL,"
                + "telefone TEXT,"
                + "endereco TEXT,"
                + "site TEXT,"
                + "foto TEXT,"
                + "nota REAL);";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABELA;
        db.execSQL(sql);
        onCreate(db);
    }

    public void onInsertAluno(Aluno aluno) {
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("telefone", aluno.getTelefone());
        values.put("endereco", aluno.getEndereco());
        values.put("site", aluno.getSite());
        values.put("foto", aluno.getFoto());
        values.put("nota", aluno.getNota());

        getWritableDatabase().insert(TABELA, null, values);
    }

    public List<Aluno> onListAlunos() {
        List<Aluno> alunos = new ArrayList<>();

        Cursor c = getWritableDatabase().query(
                TABELA,
                COLUNAS,
                null,
                null,
                null,
                null,
                null,
                null
        );

        while (c.moveToNext()) {
            Aluno aluno = new Aluno();
            aluno.setId(c.getLong(0));
            aluno.setNome(c.getString(1));
            aluno.setTelefone(c.getString(2));
            aluno.setEndereco(c.getString(3));
            aluno.setSite(c.getString(4));
            aluno.setFoto(c.getString(5));
            aluno.setNota(c.getDouble(6));

            alunos.add(aluno);
        }
        c.close();

        return alunos;
    }

    public void onDeleteAluno(Aluno aluno) {
        String[] where = {aluno.getId().toString()};

        getWritableDatabase().delete(TABELA, "id=?", where);
    }

    public void onUpdateAluno(Aluno aluno) {
        String[] where = {aluno.getId().toString()};

        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("telefone", aluno.getTelefone());
        values.put("endereco", aluno.getEndereco());
        values.put("site", aluno.getSite());
        values.put("foto", aluno.getFoto());
        values.put("nota", aluno.getNota());

        getWritableDatabase().update(TABELA, values, "id=?", where);
    }

    public boolean isAluno(String aluno) {
        Cursor c = getWritableDatabase().rawQuery(
            "select aluno from " + TABELA + " where aluno = ?",
                new String[] {aluno}
        );

        int total = c.getCount();
        c.close();

        return total > 0;
    }
}

