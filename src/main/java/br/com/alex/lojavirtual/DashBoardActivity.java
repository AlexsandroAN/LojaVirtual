package br.com.alex.lojavirtual;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class DashBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);


    }

    public void callCadastro(View view) {
        Intent intent = new Intent(this, PessoaActivity.class);
        startActivity(intent);

    }

    public void callList(View view) {

        Intent intent = new Intent(this, ListaPessoaActivity.class);
        startActivity(intent);
    }

}
