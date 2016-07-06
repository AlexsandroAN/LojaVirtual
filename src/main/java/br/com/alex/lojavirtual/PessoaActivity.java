package br.com.alex.lojavirtual;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;

import br.com.alex.lojavirtual.Util.Mask;
import br.com.alex.lojavirtual.entidade.Profissao;

public class PessoaActivity extends AppCompatActivity {

    private Spinner spnProfissao;
    private EditText edtCpfCnpj;
    private RadioGroup rbgCpfCnpj;
    private RadioButton rbtCpf;
    private TextWatcher cpfMask;
    private TextWatcher cnpjMask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spnProfissao = (Spinner) findViewById(R.id.spnProfissao);
        edtCpfCnpj = (EditText) findViewById(R.id.edtCpfCnpj);
        rbgCpfCnpj = (RadioGroup) findViewById(R.id.rbgCpfCnpj);
        rbtCpf = (RadioButton) findViewById(R.id.rbtCpf);

        cpfMask = Mask.insert("###.###.##-##", edtCpfCnpj);
        edtCpfCnpj.addTextChangedListener(cpfMask);
        cnpjMask = Mask.insert("##.###.###/####-##", edtCpfCnpj);

        rbgCpfCnpj.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int opcao = group.getCheckedRadioButtonId();
                if (opcao == rbtCpf.getId()) {
                    edtCpfCnpj.removeTextChangedListener(cnpjMask);
                    edtCpfCnpj.addTextChangedListener(cpfMask);
                } else {
                    edtCpfCnpj.removeTextChangedListener(cpfMask);
                    edtCpfCnpj.addTextChangedListener(cnpjMask);
                }
            }
        });


        this.initProfissoes();
    }

    private void initProfissoes() {
        ArrayList<String> prodissoes = new ArrayList<>();
        for (Profissao profissao : Profissao.values()) {
            prodissoes.add(profissao.getDescrisao());
        }
        ArrayAdapter adapter = new ArrayAdapter(PessoaActivity.this, android.R.layout.simple_spinner_item, prodissoes);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spnProfissao.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        // if (id == R.id.action_settings) {
        //     return true;
        // }

        switch (item.getItemId()) {
            case R.id.menu_sair:
                SharedPreferences.Editor editor = getSharedPreferences("pref", Context.MODE_PRIVATE).edit();
                editor.remove("usuario");
                editor.remove("senha");
                editor.commit();

                finish();
                Intent intent = new Intent(PessoaActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
