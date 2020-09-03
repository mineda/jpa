package br.gov.sp.fatec.projetomaven.dao;

import br.gov.sp.fatec.projetomaven.entity.Professor;

public interface ProfessorDao {
    
    public Professor cadastrarProfessor(String nomeUsuario, String senha, String titulo);

    public Professor salvarProfessor(Professor professor);

    public Professor salvarProfessorSemCommit(Professor professor);

    public Professor buscarProfessor(String nomeUsuario);

    public void removerProfessor(String nomeUsuario);

}