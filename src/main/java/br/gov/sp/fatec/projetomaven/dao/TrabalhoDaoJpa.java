package br.gov.sp.fatec.projetomaven.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import br.gov.sp.fatec.projetomaven.entity.Aluno;
import br.gov.sp.fatec.projetomaven.entity.PersistenceManager;
import br.gov.sp.fatec.projetomaven.entity.Trabalho;

public class TrabalhoDaoJpa implements TrabalhoDao {
    
    private EntityManager em;

    public TrabalhoDaoJpa() {
        this(PersistenceManager.getInstance().getEntityManager());
    }

    public TrabalhoDaoJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Trabalho salvarTrabalho(Trabalho trabalho) {
        try {
            em.getTransaction().begin();
            if(trabalho.getAvaliador() != null && trabalho.getAvaliador().getId() == null) {
                ProfessorDao professorDao = new ProfessorDaoJpa(em);
                professorDao.salvarProfessorSemCommit(trabalho.getAvaliador());
            }
            if(trabalho.getAlunos() != null && !trabalho.getAlunos().isEmpty()) {
                AlunoDao alunoDao = new AlunoDaoJpa(em);
                for(Aluno aluno: trabalho.getAlunos()) {
                    if(aluno.getId() == null) {
                        alunoDao.salvarAlunoSemCommit(aluno);
                    }
                }
            }
            if(trabalho.getId() == null) {
                em.persist(trabalho);
            }
            else {
                em.merge(trabalho);
            }
            em.getTransaction().commit();
            return trabalho;
        }
        catch(PersistenceException e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao salvar Trabalho!", e);
        }
    }

    @Override
    public Trabalho buscarTrabalho(Long id) {
        return em.find(Trabalho.class, id);
    }

    @Override
    public void removerTrabalho(Long id) {
        Trabalho trabalho = buscarTrabalho(id);
        if(trabalho == null) {
            throw new RuntimeException("Trabalho não cadastrado!");
        }
        em.getTransaction().begin();
        em.remove(trabalho);
        em.getTransaction().commit();
    }
    
}