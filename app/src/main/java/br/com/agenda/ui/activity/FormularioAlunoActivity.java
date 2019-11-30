package br.com.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import br.com.agenda.R;
import br.com.agenda.dao.AlunoDAO;
import br.com.agenda.model.Aluno;

import static br.com.agenda.ui.activity.ConstantesActivities.ALUNO_EDITAR;

public class FormularioAlunoActivity extends AppCompatActivity {

    private static final String TITULO_APP_BAR_NOVO_ALUNO = "Novo aluno";

    private static final String TITULO_APP_BAR_EDITAR_ALUNO = "Editar aluno";

    private Aluno aluno;

    private EditText textNome;

    private EditText textTelefone;

    private EditText textEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_formulario_aluno);
        inicializarCampos();
        botaoSalvar();
        carregarAluno();
    }

    private void carregarAluno() {
        Intent alunoParam = getIntent();
        aluno = (Aluno) alunoParam.getSerializableExtra(ALUNO_EDITAR);

        if (aluno != null) {
            setTitle(TITULO_APP_BAR_EDITAR_ALUNO);

            textNome.setText(aluno.getNome());
            textTelefone.setText(aluno.getTelefone());
            textEmail.setText(aluno.getEmail());
        } else {
            setTitle(TITULO_APP_BAR_NOVO_ALUNO);
            aluno = new Aluno();
        }
    }

    private void botaoSalvar() {
        Button botaoSalvar = findViewById(R.id.activity_formulario_aluno_botao_salvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preencherAluno();
                salvar();
            }
        });
    }

    private void inicializarCampos() {
        textNome = findViewById(R.id.activity_formulario_aluno_nome);
        textTelefone = findViewById(R.id.activity_formulario_aluno_telefone);
        textEmail = findViewById(R.id.activity_formulario_aluno_email);
    }

    private void salvar() {
        AlunoDAO alunoDAO = new AlunoDAO();

        if (aluno.getId() == 0) {
            alunoDAO.salvar(aluno);
        } else {
            alunoDAO.editar(aluno);
        }

        finish();
    }

    private Aluno preencherAluno() {
        String nome = textNome.getText().toString();
        String telefone = textTelefone.getText().toString();
        String email = textEmail.getText().toString();

        aluno.setNome(nome);
        aluno.setTelefone(telefone);
        aluno.setEmail(email);

        return aluno;
    }
}
