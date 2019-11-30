package br.com.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.com.agenda.R;
import br.com.agenda.dao.AlunoDAO;
import br.com.agenda.model.Aluno;

import static br.com.agenda.ui.activity.ConstantesActivities.ALUNO_EDITAR;

public class MainActivity extends AppCompatActivity {

    private AlunoDAO alunoDAO;

    private ArrayAdapter<Aluno> alunoAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        criarFABNovoAluno();
        configurarLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.activity_main_item_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.activity_main_item_remover) {
            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Aluno alunoSelecionado = alunoAdapter.getItem(menuInfo.position);
            removerAluno(alunoSelecionado);
        }

        return super.onContextItemSelected(item);
    }

    private void criarFABNovoAluno() {
        FloatingActionButton fab = findViewById(R.id.activity_main_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirFormularioNovoAluno(null);
            }
        });
    }

    private void abrirFormularioNovoAluno(Aluno aluno) {
        Intent intent = new Intent(MainActivity.this, FormularioAlunoActivity.class);

        if (aluno != null) {
            intent.putExtra(ALUNO_EDITAR, aluno);
        }

        startActivity(intent);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    protected void onResume() {
        super.onResume();

        atualizarAlunos();
    }

    private void atualizarAlunos() {
        alunoAdapter.clear();
        alunoAdapter.addAll(alunoDAO.todos());
    }

    private void configurarLista() {
        alunoDAO = new AlunoDAO();

        ListView listaAlunos = findViewById(R.id.activity_main_lista_alunos);
        configurarAdapter(listaAlunos);
        configurarItemClickListener(listaAlunos);
        registerForContextMenu(listaAlunos);
    }

    private void removerAluno(Aluno aluno) {
        alunoDAO.deletar(aluno);
        alunoAdapter.remove(aluno);
    }

    private void configurarItemClickListener(ListView listaAlunos) {
        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Aluno alunoSelecionado = (Aluno) parent.getItemAtPosition(position);

                abrirFormularioNovoAluno(alunoSelecionado);
            }
        });
    }

    private void configurarAdapter(ListView listaAlunos) {
        alunoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        listaAlunos.setAdapter(alunoAdapter);
    }
}
