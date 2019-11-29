package br.com.agenda.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.agenda.R;
import br.com.agenda.dao.AlunoDAO;
import br.com.agenda.model.Aluno;

public class FormularioAlunoActivity extends AppCompatActivity {

    public static final String TITULO_APP_BAR = "Novo aluno";

    private EditText textNome;

    private EditText textTelefone;

    private EditText textEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_formulario_aluno);
        setTitle(TITULO_APP_BAR);
        inicializarCampos();
        botaoSalvar();
    }

    private void botaoSalvar() {
        Button botaoSalvar = findViewById(R.id.activity_formulario_aluno_botao_salvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Aluno novoAluno = criarAluno();
                salvar(novoAluno);
            }
        });
    }

    private void inicializarCampos() {
        textNome = findViewById(R.id.activity_formulario_aluno_nome);
        textTelefone = findViewById(R.id.activity_formulario_aluno_telefone);
        textEmail = findViewById(R.id.activity_formulario_aluno_email);
    }

    private void salvar(Aluno novoAluno) {
        AlunoDAO alunoDAO = new AlunoDAO();
        alunoDAO.salvar(novoAluno);

        finish();
    }

    private Aluno criarAluno() {
        String nome = textNome.getText().toString();
        String telefone = textTelefone.getText().toString();
        String email = textEmail.getText().toString();

        return new Aluno(nome, telefone, email);
    }
}
