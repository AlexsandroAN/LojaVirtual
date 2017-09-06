package br.com.alex.lojavirtual;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.alex.lojavirtual.Util.TipoMsg;
import br.com.alex.lojavirtual.Util.Util;
import br.com.alex.lojavirtual.entidade.Pessoa;
import br.com.alex.lojavirtual.repository.PessoaRepository;

public class ListaPessoaActivity extends AppCompatActivity {

    private ListView listPessoas;
    private List<Pessoa> listaPessoas;
    private int posicaoSelecionada;
    private PessoaRepository pessoaRepository;

    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pessoa);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Lista de Pessoas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        pessoaRepository = new PessoaRepository(this);

        listPessoas = (ListView) findViewById(R.id.listPessoas);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        setArrayAdapterPessoas();

        listPessoas.setOnItemClickListener(clickListenerPessoas);
        listPessoas.setOnCreateContextMenuListener(contextMenuListener);
        listPessoas.setOnItemLongClickListener(longClickListener);

    }

    private void setArrayAdapterPessoas() {
        listaPessoas = pessoaRepository.listarPessoas();

        List<String> valores = new ArrayList<String>();
        for (Pessoa p : listaPessoas) {
            valores.add(p.getNome());
        }
        adapter.clear();
        adapter.addAll(valores);
        listPessoas.setAdapter(adapter);
    }

    private AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            posicaoSelecionada = position;
            return false;
        }
    };

    private View.OnCreateContextMenuListener contextMenuListener = new View.OnCreateContextMenuListener() {
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Opções").setHeaderIcon(R.drawable.edit).add(1, 10, 1, "Editar");
            menu.add(1, 20, 2, "Deletar");
        }
    };

    private AdapterView.OnItemClickListener clickListenerPessoas = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Pessoa pessoa = pessoaRepository.consultarPessoaPorId(listaPessoas.get(position).getId());

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            StringBuilder info = new StringBuilder();
            info.append("Nome: " + pessoa.getNome());
            info.append("\nEndereço: " + pessoa.getEndereco());
            info.append("\nCPF/CNPJ: " + pessoa.getCpfCnpj());
            info.append("\nSexo: " + pessoa.getSexo().getDescrisao());
            info.append("\nProfissão: " + pessoa.getProfissao().getDescrisao());
            info.append("\nDt Nasc.: " + dateFormat.format(pessoa.getDtNasc()));
            Util.showMsgAlertOK(ListaPessoaActivity.this, "Info", info.toString(), TipoMsg.INFO);
        }
    };

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 10:
                Pessoa pessoa = pessoaRepository.consultarPessoaPorId(listaPessoas.get(posicaoSelecionada).getId());
                Intent i = new Intent(this, EditarPessoaActivity.class);
                i.putExtra("pessoa", pessoa);
                startActivity(i);
                finish();
                break;
            case 20:
                Util.showMsgConfirm(ListaPessoaActivity.this, "Remover Pessoa", "Deseja remover realmente o(a) " + listaPessoas.get(posicaoSelecionada).getNome() + "?",
                        TipoMsg.ALERTA, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int id = listaPessoas.get(posicaoSelecionada).getId();
                                pessoaRepository.removerPessoaPorId(id);
                                setArrayAdapterPessoas();
                                adapter.notifyDataSetChanged();
                                Util.showMsgToast(ListaPessoaActivity.this, "Pessoa deletada com sucesso!");
                            }
                        });
                break;
        }
        return true;
    }

    public void addNewPessoa(View view) {
        Intent i = new Intent(this, PessoaActivity.class);
        startActivity(i);
        finish();
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

}
