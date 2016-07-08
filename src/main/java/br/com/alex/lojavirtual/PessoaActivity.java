package br.com.alex.lojavirtual;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import br.com.alex.lojavirtual.Util.Mask;
import br.com.alex.lojavirtual.entidade.Profissao;
import br.com.alex.lojavirtual.fragment.DatePickerFragment;

public class PessoaActivity extends AppCompatActivity {

    private Spinner spnProfissao;
    private TextView txtCpfCnpj;
    private EditText edtCpfCnpj, edtNasc;
    private RadioGroup rbgCpfCnpj;
    private RadioButton rbtCpf;
    private TextWatcher cpfMask;
    private TextWatcher cnpjMask;
    private int cpfCnpjSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        spnProfissao = (Spinner) findViewById(R.id.spnProfissao);
        edtCpfCnpj = (EditText) findViewById(R.id.edtCpfCnpj);
        rbgCpfCnpj = (RadioGroup) findViewById(R.id.rbgCpfCnpj);
        rbtCpf = (RadioButton) findViewById(R.id.rbtCpf);
        txtCpfCnpj = (TextView) findViewById(R.id.txtCpfCnpj);
        edtNasc = (EditText) findViewById(R.id.edtNasc);

        cpfMask = Mask.insert("###.###.###-##", edtCpfCnpj);
        edtCpfCnpj.addTextChangedListener(cpfMask);

        cnpjMask = Mask.insert("##.###.###/####-##", edtCpfCnpj);

        rbgCpfCnpj.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                edtCpfCnpj.setText("");
                edtCpfCnpj.requestFocus();
                cpfCnpjSelecionado = group.getCheckedRadioButtonId();
                // int opcao = group.getCheckedRadioButtonId();
                if (cpfCnpjSelecionado == rbtCpf.getId()) {
                    edtCpfCnpj.removeTextChangedListener(cnpjMask);
                    edtCpfCnpj.addTextChangedListener(cpfMask);
                    txtCpfCnpj.setText("CPF");
                } else {
                    edtCpfCnpj.removeTextChangedListener(cpfMask);
                    edtCpfCnpj.addTextChangedListener(cnpjMask);
                    txtCpfCnpj.setText("CNPJ");
                }
            }
        });

        edtCpfCnpj.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (rbgCpfCnpj.getCheckedRadioButtonId() == rbtCpf.getId()) {
                    if (edtCpfCnpj.getText().length() < 14) {
                        edtCpfCnpj.setText("");
                    }
                } else {
                    if (edtCpfCnpj.getText().length() < 18) {
                        edtCpfCnpj.setText("");
                    }
                }
            }
        });

        this.initProfissoes();
    }

    public void setData(View view) {
        DatePickerFragment datePickerFragment = new DatePickerFragment();

        Calendar calendar = Calendar.getInstance();

        Bundle bundle = new Bundle();
        bundle.putInt("dia", calendar.get(Calendar.DAY_OF_MONTH));
        bundle.putInt("mes", calendar.get(Calendar.MONTH));
        bundle.putInt("ano", calendar.get(Calendar.YEAR));

        datePickerFragment.setArguments(bundle);

        datePickerFragment.setDateListener(datelistener);
        datePickerFragment.show(getFragmentManager(), "Data NAsc.");
    }

    private DatePickerDialog.OnDateSetListener datelistener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            edtNasc.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            //Util.showMsgToast(PessoaActivity.this, "Ano" + year + "Mês" + (monthOfYear + 1) + ", Dia " + dayOfMonth);
        }
    };

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
