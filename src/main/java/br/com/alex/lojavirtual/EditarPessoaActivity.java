package br.com.alex.lojavirtual;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import br.com.alex.lojavirtual.Util.Mask;
import br.com.alex.lojavirtual.Util.Util;
import br.com.alex.lojavirtual.entidade.Pessoa;
import br.com.alex.lojavirtual.entidade.Profissao;
import br.com.alex.lojavirtual.entidade.Sexo;
import br.com.alex.lojavirtual.entidade.TipoPessoa;
import br.com.alex.lojavirtual.fragment.DatePickerFragment;
import br.com.alex.lojavirtual.repository.PessoaRepository;

public class EditarPessoaActivity extends AppCompatActivity {

    private Pessoa pessoa;

    private Spinner spnProfissao;
    private TextView txtCpfCnpj;
    private EditText edtNome, edtEndereco, edtCpfCnpj, edtNasc;
    private RadioGroup rbgCpfCnpj, rbgSexo;
    private RadioButton rbtCpf, rbtCnpj, rbtMasc, rbtFem;
    private TextWatcher cpfMask, cnpjMask;
    private int cpfCnpjSelecionado;

    private PessoaRepository pessoaRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_pessoa);

        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Editar Pessoa");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        this.pessoaRepository = new PessoaRepository(this);

        pessoa = (Pessoa) getIntent().getExtras().getSerializable("pessoa");

        spnProfissao = (Spinner) findViewById(R.id.spnProfissao);
        edtCpfCnpj = (EditText) findViewById(R.id.edtCpfCnpj);
        rbgCpfCnpj = (RadioGroup) findViewById(R.id.rbgCpfCnpj);
        rbgSexo = (RadioGroup) findViewById(R.id.rbgSexo);
        rbtCpf = (RadioButton) findViewById(R.id.rbtCpf);
        rbtCnpj = (RadioButton) findViewById(R.id.rbtCnpj);
        rbtMasc = (RadioButton) findViewById(R.id.rbtMasc);
        rbtFem = (RadioButton) findViewById(R.id.rbtFem);
        txtCpfCnpj = (TextView) findViewById(R.id.txtCpfCnpj);
        edtNasc = (EditText) findViewById(R.id.edtNasc);
        edtNome = (EditText) findViewById(R.id.edtNome);
        edtEndereco = (EditText) findViewById(R.id.edtEndereco);

        cpfMask = Mask.insert("###.###.###-##", edtCpfCnpj);
        cnpjMask = Mask.insert("##.###.###/####-##", edtCpfCnpj);

        rbgCpfCnpj.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                edtCpfCnpj.setText("");
                edtCpfCnpj.requestFocus();
                cpfCnpjSelecionado = group.getCheckedRadioButtonId();
                if (cpfCnpjSelecionado == rbtCpf.getId()) {
                    edtCpfCnpj.removeTextChangedListener(cnpjMask);
                    edtCpfCnpj.addTextChangedListener(cpfMask);
                    txtCpfCnpj.setText("CPF:");
                } else {
                    edtCpfCnpj.removeTextChangedListener(cpfMask);
                    edtCpfCnpj.addTextChangedListener(cnpjMask);
                    txtCpfCnpj.setText("CNPJ:");
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
        this.initCampos();
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

    private void initCampos() {
        edtNome.setText(pessoa.getNome());
        edtEndereco.setText(pessoa.getEndereco());
        edtCpfCnpj.setText(pessoa.getCpfCnpj());
        switch (pessoa.getTipoPessoa()) {
            case FISICA:
                txtCpfCnpj.setText("CPF:");
                rbtCpf.setChecked(true);
                edtCpfCnpj.setText(pessoa.getCpfCnpj());
                break;
            case JURIDICA:
                txtCpfCnpj.setText("CNPJ:");
                rbtCnpj.setChecked(true);
                edtCpfCnpj.setText(pessoa.getCpfCnpj());
                break;
        }
        switch (pessoa.getSexo()) {
            case MASCULINO:
                rbtMasc.setChecked(true);
                break;
            case FEMININO:
                rbtFem.setChecked(true);
                break;
        }
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        spnProfissao.setSelection(pessoa.getProfissao().ordinal());
        edtNasc.setText(dateFormat.format(pessoa.getDtNasc()));
    }

    private void initProfissoes() {
        ArrayList<String> prodissoes = new ArrayList<>();
        for (Profissao profissao : Profissao.values()) {
            prodissoes.add(profissao.getDescrisao());
        }
        ArrayAdapter adapter = new ArrayAdapter(EditarPessoaActivity.this, android.R.layout.simple_spinner_item, prodissoes);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spnProfissao.setAdapter(adapter);

    }

    private DatePickerDialog.OnDateSetListener datelistener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            edtNasc.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
        }
    };


    private boolean validarPessoa(Pessoa pessoa) {
        boolean erro = false;
        if (pessoa.getNome() == null || "".equals(pessoa.getNome())) {
            erro = true;
            edtNome.setError("Campo Nome obrigatório!");
        }
        if (pessoa.getEndereco() == null || "".equals(pessoa.getEndereco())) {
            erro = true;
            edtEndereco.setError("Campo Endereço obrigatório!");
        }

        if (pessoa.getCpfCnpj() == null || "".equals(pessoa.getCpfCnpj())) {
            erro = true;
            switch (rbgCpfCnpj.getCheckedRadioButtonId()) {
                case R.id.rbtCpf:
                    edtCpfCnpj.setError("Campo CPF obrigatório!");
                    break;
                case R.id.rbtCnpj:
                    edtCpfCnpj.setError("Campo CNPJ obrigatório!");
                    break;
            }
        } else {
            switch (rbgCpfCnpj.getCheckedRadioButtonId()) {
                case R.id.rbtCpf:
                    if (edtCpfCnpj.getText().length() < 14) {
                        erro = true;
                        edtCpfCnpj.setError("Campo CPF deve ter 11 caracteres!");
                    }
                    break;
                case R.id.rbtCnpj:
                    if (edtCpfCnpj.getText().length() < 18) {
                        erro = true;
                        edtCpfCnpj.setError("Campo CNPJ deve ter 14 caracteres!");
                    }
                    break;
            }

        }
        if (pessoa.getDtNasc() == null) {
            erro = true;
            edtEndereco.setError("Campo data de Nasc obrigatório!");
        }
        return erro;
    }


    private Pessoa montarPessoa() {
        Pessoa pessoa = new Pessoa();

        pessoa.setId(this.pessoa.getId());

        pessoa.setNome(edtNome.getText().toString());
        pessoa.setEndereco(edtEndereco.getText().toString());
        pessoa.setCpfCnpj(edtCpfCnpj.getText().toString());

        switch (rbgCpfCnpj.getCheckedRadioButtonId()) {
            case R.id.rbtCpf:
                pessoa.setTipoPessoa(TipoPessoa.FISICA);
                break;
            case R.id.rbtCnpj:
                pessoa.setTipoPessoa(TipoPessoa.JURIDICA);
                break;
        }
        switch (rbgSexo.getCheckedRadioButtonId()) {
            case R.id.rbtMasc:
                pessoa.setSexo(Sexo.MASCULINO);
                break;
            case R.id.rbtFem:
                pessoa.setSexo(Sexo.FEMININO);
                break;
        }

        Profissao profissao = Profissao.getProfissao(spnProfissao.getSelectedItemPosition());
        pessoa.setProfissao(profissao);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date nasc = dateFormat.parse(edtNasc.getText().toString());
            pessoa.setDtNasc(nasc);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return pessoa;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }


    public void atualizarPessoa(View view) {
        Pessoa pessoa = montarPessoa();
        if (!validarPessoa(pessoa)) {
            pessoaRepository.atualizarPessoa(pessoa);
            Intent i = new Intent(this, ListaPessoaActivity.class);
            startActivity(i);
            finish();
            Util.showMsgToast(this, "Atualização da Pessoa efetuada com sucesso!");
        }
    }

}
