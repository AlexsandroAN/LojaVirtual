package br.com.alex.lojavirtual.repository;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.alex.lojavirtual.Util.Constantes;
import br.com.alex.lojavirtual.Util.Util;

/**
 * Created by 39091 on 04/07/2016.
 */
public class LoginRepository extends SQLiteOpenHelper {

    public LoginRepository(Context context) {
        super(context, Constantes.BD_NOME, null, Constantes.BD_VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE TB_LOGIN (");
        query.append(" ID INTEGER PRIMARY KEY AUTOINCREMENT,");
        query.append(" USUARIO TEXT NOT NULL,");
        query.append(" SENHA TEXT NOT NULL)");

        db.execSQL(query.toString());

        popularBD(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void popularBD(SQLiteDatabase db) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO TB_LOGIN(USUARIO, SENHA) VALUES(?, ?)");

        //SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query.toString(), new String[]{"admin", "admin"});
    }

    public void listarLogins(Activity activity) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("TB_LOGIN", null, "id = ? and usuario = ?", new String[]{"1", "admin"}, null, null, "USUARIO");
        // Cursor cursor = db.query("TB_LOGIN", null, null, null, null, null, "USUARIO");
        while (cursor.moveToNext()) {
            String txt = "Id de usuário " + String.valueOf(cursor.getInt(cursor.getColumnIndex("USUARIO")));
            // Log.d("ID de usuário", cursor.getString(cursor.getColumnIndex("ID")));
            //  Log.d("Nome de usuário", cursor.getString(cursor.getColumnIndex("USUARIO")));
            //  Log.d("Senha do usuário", cursor.getString(cursor.getColumnIndex("SENHA")));
            Util.showMsgToast(activity, txt);
        }
    }


    public void addLogin(String usuario, String senha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("USUARIO", usuario);
        contentValues.put("SENHA", senha);

        db.insert("TB_LOGIN", null, contentValues);
    }

    public void updateLogin(String usuario, String senha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("USUARIO", usuario);
        contentValues.put("SENHA", senha);

        db.update("TB_LOGIN", contentValues, "ID > 1", null);
    }

    public void deleteLogin(String usuario, String senha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("USUARIO", usuario);
        contentValues.put("SENHA", senha);

        db.delete("TB_LOGIN", "USUARIO = ? OR SENHA = ?", new String[]{usuario, senha});
    }


}
