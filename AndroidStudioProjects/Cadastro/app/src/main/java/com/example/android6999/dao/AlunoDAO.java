package com.example.android6999.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android6999.cadastro.Aluno;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android6999 on 18/07/17.
 */

public class AlunoDAO extends SQLiteOpenHelper {

    private static final int VERSAO = 1;
    private static final String TABELA = "Alunos";
    private static final String DATABASE = "AlunosDB";

    public AlunoDAO (Context context){
        super(context, DATABASE, null, VERSAO);

    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String ddl = "CREATE TABLE " + TABELA
                + " (id INTEGER PRIMARY KEY, "
                + " nome TEXT NOT NULL, "
                + " telefone TEXT, "
                + " endereco TEXT, "
                + " site TEXT, "
                + " nota REAL);";

        db.execSQL(ddl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versaoAntiga, int versaoNova) {

        String sql = "DROP TABLE IF EXISTS " + TABELA;
        db.execSQL(sql);
        onCreate(db);

    }

    public void insere(Aluno aluno){

        ContentValues values = new ContentValues();

        getDadosAlunos(aluno, values);

        getWritableDatabase().insert(TABELA, null, values);


    }

    public List<Aluno> getLista(){

        List<Aluno> alunos = new ArrayList<Aluno>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABELA + ";", null);

        while (c.moveToNext()){

            Aluno aluno = new Aluno();

            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));

            alunos.add(aluno);

        }

        c.close();
        return alunos;

    }

    public void deletar (Aluno aluno){

        String[] args = {aluno.getId().toString()};
        getWritableDatabase().delete(TABELA, "id=?", args);

    }

    public void altera (Aluno aluno){
        ContentValues values = new ContentValues();

        getDadosAlunos(aluno, values);

        getWritableDatabase().update(TABELA, values, "id=?", new String[]{aluno.getId().toString()});
    }

    private void getDadosAlunos(Aluno aluno, ContentValues values) {
        values.put("nome", aluno.getNome());
        values.put("telefone", aluno.getTelefone());
        values.put("endereco", aluno.getEndereco());
        values.put("site", aluno.getSite());
        values.put("nota", aluno.getNota());
    }
}
