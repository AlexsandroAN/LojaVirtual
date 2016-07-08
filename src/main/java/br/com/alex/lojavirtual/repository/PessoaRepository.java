package br.com.alex.lojavirtual.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.alex.lojavirtual.Util.Constantes;

/**
 * Created by 39091 on 08/07/2016.
 */
public class PessoaRepository extends SQLiteOpenHelper {

    public PessoaRepository(Context context) {
        super(context, Constantes.BD_NOME, null, Constantes.BD_VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE TB_PESSOA (");
        query.append(" ID INTEGER PRIMARY KEY AUTOINCREMENT,");
        query.append(" NOME TEXT(30) NOT NULL,");
        query.append(" ENDERECO TEXT(50),");
        query.append(" CPF TEXT(14),");
        query.append(" SEXO INTERGER(1) NOT NULL,");
        query.append(" PROFISSAO INTERGER(3) NOT NULL,");
        query.append(" DT_NASC INTERGER NOT NULL)");

        db.execSQL(query.toString());
    }
}
