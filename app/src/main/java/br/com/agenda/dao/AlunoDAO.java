package br.com.agenda.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.agenda.model.Aluno;

public class AlunoDAO {

    private final static List<Aluno> alunos = new ArrayList<>();

    private static int contadorId = 1;

    public void salvar(Aluno aluno) {
        aluno.setId(contadorId);
        alunos.add(aluno);

        incrementarId();
    }

    private void incrementarId() {
        contadorId++;
    }

    public void editar(Aluno alunoEditar) {
        for (Aluno aluno : alunos) {
            if (aluno.getId() == alunoEditar.getId()) {
                editarAlunoPelaPosicao(alunoEditar, aluno);

                return;
            }
        }
    }

    private void editarAlunoPelaPosicao(Aluno alunoEditar, Aluno aluno) {
        int posicaoAluno = alunos.indexOf(aluno);
        alunos.set(posicaoAluno, alunoEditar);
    }

    public List<Aluno> todos() {
        return new ArrayList<>(alunos);
    }

    public void deletar(Aluno alunoExcluir) {
        for (Aluno aluno : alunos) {
            if (aluno.getId() == alunoExcluir.getId()) {
                alunos.remove(aluno);

                return;
            }
        }
    }
}
