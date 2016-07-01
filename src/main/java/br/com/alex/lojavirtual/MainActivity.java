package br.com.alex.lojavirtual;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import br.com.alex.lojavirtual.Util.TipoMsg;
import br.com.alex.lojavirtual.Util.Util;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setHomeButtonEnabled(true);
        // getSupportActionBar().setTitle("Loja Virtual");

        // Util.showMsgToast(this, "Loja Virtual App v1.0");

        Button btnEnviar = (Button) findViewById(R.id.btEnviar);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.showMsgAlertOK(MainActivity.this, "Titulo", "Mensagem...", TipoMsg.INFO);
            }
        });
    }

    public void clickBtnEnviar(View view) {
        Util.showMsgToast(this, "Loja Virtual App v1.0");
    }


    // FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    // fab.setOnClickListener(new View.OnClickListener() {
    //     @Override
    //     public void onClick(View view) {
    //          Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
    //                 .setAction("Action", null).show();
    //      }
    //  });


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
