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

    private final Context mContext;

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
        View adapaterView = criarView(viewGroup);

        Aluno aluno = alunos.get(posicao);

        vincularValoresItem(adapaterView, aluno);

        return adapaterView;
    }

    private void vincularValoresItem(View adapaterView, Aluno aluno) {
        TextView nomeAluno = adapaterView.findViewById(R.id.item_aluno_nome);
        nomeAluno.setText(aluno.getNome());

        TextView telefoneAluno = adapaterView.findViewById(R.id.item_aluno_telefone);
        telefoneAluno.setText(aluno.getTelefone());
    }

    private View criarView(ViewGroup viewGroup) {
        return LayoutInflater
                .from(mContext)
                .inflate(R.layout.item_aluno, viewGroup, false);
    }

    public void atualizar(List<Aluno> alunos) {
        this.alunos.clear();
        this.alunos.addAll(alunos);

        notifyDataSetChanged();
    }

    public void remove(Aluno aluno) {
        alunos.remove(aluno);

        notifyDataSetChanged();
    }
}
