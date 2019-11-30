package br.com.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

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
        configurarItemLongClickListener(listaAlunos);
    }

    private void configurarItemLongClickListener(ListView listaAlunos) {
        listaAlunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Aluno alunoSelecionado = (Aluno) adapterView.getItemAtPosition(position);
                removerAluno(alunoSelecionado);
                // Return true evita que os próximos listeners sejam executados
                return true;
            }
        });
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
