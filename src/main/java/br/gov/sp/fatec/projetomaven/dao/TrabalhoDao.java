package br.gov.sp.fatec.projetomaven.dao;

import br.gov.sp.fatec.projetomaven.entity.Trabalho;

public interface TrabalhoDao {

    public Trabalho salvarTrabalho(Trabalho trabalho);

    public Trabalho buscarTrabalho(Long id);

    public void removerTrabalho(Long id);

}