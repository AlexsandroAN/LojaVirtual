package br.com.alex.lojavirtual;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.alex.lojavirtual.bo.LoginBO;
import br.com.alex.lojavirtual.validation.LoginValidation;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUsuario, edtSenha;
    private Button btnLogar;
    private SharedPreferences preferences;
    private LoginBO loginBO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBO = new LoginBO(this);

       // getSupportActionBar().hide();

        preferences = getSharedPreferences("pref",Context.MODE_PRIVATE);
        String usuario = preferences.getString("usuario", null);
        String senha = preferences.getString("senha", null);

        if (usuario != null && senha != null) {
            // Intenção
            Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
            startActivity(intent);
            finish();
        }

        edtUsuario = (EditText) findViewById(R.id.edtUsuario);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        btnLogar = (Button) findViewById(R.id.btnLogar);

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = edtUsuario.getText().toString();
                String senha = edtSenha.getText().toString();

                LoginValidation validation = new LoginValidation();
                validation.setActivity(LoginActivity.this);
                validation.setEdtUsuario(edtUsuario);
                validation.setEdtSenha(edtSenha);
                validation.setUsuario(usuario);
                validation.setSenha(senha);

                boolean isValido = loginBO.validarCamposLogin(validation);

                if (isValido) {
                    // Intenção
                    Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
