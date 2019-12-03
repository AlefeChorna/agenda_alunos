package br.com.agenda.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.com.agenda.R;
import br.com.agenda.dao.AlunoDAO;
import br.com.agenda.model.Aluno;
import br.com.agenda.ui.adapter.MainActivityAdapter;

import static br.com.agenda.ui.activity.ConstantesActivities.ALUNO_EDITAR;

public class MainActivity extends AppCompatActivity {

    private AlunoDAO alunoDAO;

    private MainActivityAdapter alunoAdapter;

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
            confirmarExclusao(item);
        }

        return super.onContextItemSelected(item);
    }

    private void confirmarExclusao(@NonNull final MenuItem item) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Remover aluno");
        alertDialog.setMessage("Tem certeza que deseja remover este aluno?");
        alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                removerAlunoSelecionado(item);
            }
        });
        alertDialog.setNegativeButton("Não", null);
        alertDialog.show();
    }

    private void removerAlunoSelecionado(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Aluno alunoSelecionado = alunoAdapter.getItem(menuInfo.position);
        removerAluno(alunoSelecionado);
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
        alunoAdapter.atualizar(alunoDAO.todos());
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
        alunoAdapter = new MainActivityAdapter(this);

        listaAlunos.setAdapter(alunoAdapter);
    }
}
