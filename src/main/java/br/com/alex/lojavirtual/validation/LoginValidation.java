package br.com.alex.lojavirtual.validation;

import android.app.Activity;
import android.widget.EditText;

/**
 * Created by 39091 on 04/07/2016.
 */
public class LoginValidation {

    private String usuario, senha;


    private EditText edtUsuario, edtSenha;

    private Activity activity;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public EditText getEdtUsuario() {
        return edtUsuario;
    }

    public void setEdtUsuario(EditText edtUsuario) {
        this.edtUsuario = edtUsuario;
    }

    public EditText getEdtSenha() {
        return edtSenha;
    }

    public void setEdtSenha(EditText edtSenha) {
        this.edtSenha = edtSenha;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
