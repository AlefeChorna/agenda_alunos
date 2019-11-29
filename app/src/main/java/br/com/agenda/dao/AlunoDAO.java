package br.com.agenda.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.agenda.model.Aluno;

public class AlunoDAO {

    private final static List<Aluno> alunos = new ArrayList<>();

    public void salvar(Aluno aluno) {
        alunos.add(aluno);
    }

    public List<Aluno> todos() {
        return new ArrayList<>(alunos);
    }
}
