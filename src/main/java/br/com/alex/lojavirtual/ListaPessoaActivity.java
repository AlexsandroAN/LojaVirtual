package br.com.alex.lojavirtual;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.alex.lojavirtual.entidade.Pessoa;
import br.com.alex.lojavirtual.repository.PessoaRepository;

public class ListaPessoaActivity extends AppCompatActivity {

    private ListView listPessoas;

    private PessoaRepository pessoaRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pessoa);

        pessoaRepository = new PessoaRepository(this);
        listPessoas = (ListView) findViewById(R.id.listPessoas);

        List<Pessoa> lista = pessoaRepository.listarPessoas();

        List<String> valores = new ArrayList<String>();
        for (Pessoa p : lista) {
            valores.add(p.getNome());
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, valores);

        listPessoas.setAdapter(arrayAdapter);
    }

}
