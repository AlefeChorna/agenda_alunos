package br.com.agenda.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.agenda.R;
import br.com.agenda.model.Aluno;

public class MainActivityAdapter extends BaseAdapter {

    private final List<Aluno> alunos = new ArrayList<>();
    private Context mContext;

    public MainActivityAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Aluno getItem(int posicao) {
        return alunos.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return alunos.get(posicao).getId();
    }

    @Override
    public View getView(int posicao, View view, ViewGroup viewGroup) {
        View adapaterView = LayoutInflater
                .from(mContext)
                .inflate(R.layout.item_aluno, viewGroup, false);

        Aluno aluno = alunos.get(posicao);

        TextView nomeAluno = adapaterView.findViewById(R.id.item_aluno_nome);
        nomeAluno.setText(aluno.getNome());

        TextView telefoneAluno = adapaterView.findViewById(R.id.item_aluno_telefone);
        telefoneAluno.setText(aluno.getTelefone());

        return adapaterView;
    }

    public void clear() {
        alunos.clear();
    }

    public void addAll(List<Aluno> alunos) {
        this.alunos.addAll(alunos);
    }

    public void remove(Aluno aluno) {
        alunos.remove(aluno);
    }
}
