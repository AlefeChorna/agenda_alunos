package br.com.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.com.agenda.R;
import br.com.agenda.dao.AlunoDAO;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        criarFABNovoAluno();
    }

    private void criarFABNovoAluno() {
        FloatingActionButton fab = findViewById(R.id.activity_main_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirFormularioNovoAluno();
            }
        });
    }

    private void abrirFormularioNovoAluno() {
        Intent intent = new Intent(MainActivity.this, FormularioAlunoActivity.class);

        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        AlunoDAO alunoDAO = new AlunoDAO();

        ListView listaAlunos = findViewById(R.id.activity_main_lista_alunos);
        listaAlunos.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alunoDAO.todos()));
    }
}
