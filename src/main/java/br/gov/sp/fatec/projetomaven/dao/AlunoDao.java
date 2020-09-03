package br.gov.sp.fatec.projetomaven.dao;

import br.gov.sp.fatec.projetomaven.entity.Aluno;

public interface AlunoDao {

    public Aluno cadastrarAluno(String nomeUsuario, String senha, Long ra);

    public Aluno salvarAluno(Aluno aluno);

    public Aluno salvarAlunoSemCommit(Aluno aluno);

    public Aluno buscarAlunoPorRa(Long ra);

    public void removerAluno(Long ra);
    
}