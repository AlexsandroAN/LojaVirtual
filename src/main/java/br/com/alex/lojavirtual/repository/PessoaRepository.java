package br.com.alex.lojavirtual.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.alex.lojavirtual.Util.Constantes;
import br.com.alex.lojavirtual.entidade.Pessoa;
import br.com.alex.lojavirtual.entidade.Profissao;
import br.com.alex.lojavirtual.entidade.Sexo;
import br.com.alex.lojavirtual.entidade.TipoPessoa;

/**
 * Created by 39091 on 08/07/2016.
 */
public class PessoaRepository extends SQLiteOpenHelper {

    public PessoaRepository(Context context) {
        super(context, Constantes.BD_NOME, null, Constantes.BD_VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE IF NOT EXISTS TB_PESSOA (");
        query.append(" ID INTEGER PRIMARY KEY AUTOINCREMENT,");
        query.append(" NOME TEXT(30) NOT NULL,");
        query.append(" ENDERECO TEXT(50),");
        query.append(" CPF TEXT(14),");
        query.append(" CNPJ TEXT(14),");
        query.append(" SEXO INTERGER(1) NOT NULL,");
        query.append(" PROFISSAO INTERGER(3) NOT NULL,");
        query.append(" DT_NASC INTERGER NOT NULL)");

        db.execSQL(query.toString());
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void salvarPessoa(Pessoa pessoa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = getContentValuesPessoas(pessoa);

        db.insert("TB_PESSOA", null, contentValues);
    }

    public void atualizarPessoa(Pessoa pessoa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = getContentValuesPessoas(pessoa);

        db.update("TB_PESSOA", contentValues, "ID = ?", new String[]{String.valueOf(pessoa.getId())});
    }

    @NonNull
    private ContentValues getContentValuesPessoas(Pessoa pessoa) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("NOME", pessoa.getNome());
        contentValues.put("ENDERECO", pessoa.getEndereco());
        switch (pessoa.getTipoPessoa()) {
            case FISICA:
                contentValues.put("CPF", pessoa.getCpfCnpj());
                contentValues.put("CNPJ", "");
                break;
            case JURIDICA:
                contentValues.put("CNPJ", pessoa.getCpfCnpj());
                contentValues.put("CPF", "");
                break;
        }
        contentValues.put("SEXO", pessoa.getSexo().ordinal());
        contentValues.put("PROFISSAO", pessoa.getProfissao().ordinal());
        contentValues.put("DT_NASC", pessoa.getDtNasc().getTime());
        return contentValues;
    }


    public List<Pessoa> listarPessoas() {
        List<Pessoa> lista = new ArrayList<Pessoa>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("TB_PESSOA", null, null, null, null, null, "NOME");

        while (cursor.moveToNext()) {
            Pessoa pessoa = new Pessoa();
            setPessoaFromCursor(cursor, pessoa);

            lista.add(pessoa);
        }

        return lista;
    }


    public Pessoa consultarPessoaPorId(int idPessoa) {
        Pessoa pessoa = new Pessoa();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("TB_PESSOA", null, "ID = ?", new String[]{String.valueOf(idPessoa)}, null, null, "NOME");

        if (cursor.moveToNext()) {
            setPessoaFromCursor(cursor, pessoa);
        }

        return pessoa;
    }

    public void removerPessoaPorId(int idPessoa) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete("TB_PESSOA", "ID = ?", new String[]{String.valueOf(idPessoa)});
    }


    private void setPessoaFromCursor(Cursor cursor, Pessoa pessoa) {
        pessoa.setId(cursor.getInt(cursor.getColumnIndex("ID")));
        pessoa.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
        pessoa.setEndereco(cursor.getString(cursor.getColumnIndex("ENDERECO")));
        String cpf = cursor.getString(cursor.getColumnIndex("CPF"));
        String cnpj = cursor.getString(cursor.getColumnIndex("CNPJ"));
        if (cpf != null && !"".equals(cpf)) {
            pessoa.setTipoPessoa(TipoPessoa.FISICA);
            pessoa.setCpfCnpj(cpf);
        } else {
            pessoa.setTipoPessoa(TipoPessoa.JURIDICA);
            pessoa.setCpfCnpj(cnpj);
        }
        int sexo = cursor.getInt(cursor.getColumnIndex("SEXO"));
        pessoa.setSexo(Sexo.getSexo(sexo));

        int profissao = cursor.getInt(cursor.getColumnIndex("PROFISSAO"));
        pessoa.setProfissao(Profissao.getProfissao(profissao));

        long time = cursor.getLong(cursor.getColumnIndex("DT_NASC"));
        Date dtNasc = new Date();
        dtNasc.setTime(time);
        pessoa.setDtNasc(dtNasc);
    }
}

