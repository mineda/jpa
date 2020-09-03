package br.gov.sp.fatec.projetomaven.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import br.gov.sp.fatec.projetomaven.entity.PersistenceManager;
import br.gov.sp.fatec.projetomaven.entity.Professor;

public class ProfessorDaoJpa implements ProfessorDao {

    private EntityManager em;

    public ProfessorDaoJpa() {
        this(PersistenceManager.getInstance().getEntityManager());
    }

    public ProfessorDaoJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Professor cadastrarProfessor(String nomeUsuario, String senha, String titulo) {
        Professor professor = new Professor();
        professor.setNomeUsuario(nomeUsuario);
        professor.setSenha(senha);
        professor.setTitulo(titulo);
        return salvarProfessor(professor);
    }

    @Override
    public Professor salvarProfessor(Professor professor) {
        try {
            em.getTransaction().begin();
            salvarProfessorSemCommit(professor);
            em.getTransaction().commit();
            return professor;
        }
        catch(PersistenceException e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao salvar Professor!", e);
        }
    }

    @Override
    public Professor salvarProfessorSemCommit(Professor professor) {
        if(professor.getId() == null) {
            em.persist(professor);
        }
        else {
            em.merge(professor);
        }
        return professor;
    }

    @Override
    public Professor buscarProfessor(String nomeUsuario) {
        String jpql = "select p from Professor p where p.nomeUsuario = :nome";
        TypedQuery<Professor> query = em.createQuery(jpql, Professor.class);
        query.setParameter("nome", nomeUsuario);
        return query.getSingleResult();
    }

    @Override
    public void removerProfessor(String nomeUsuario) {
        Professor professor = buscarProfessor(nomeUsuario);
        if(professor == null) {
            throw new RuntimeException("Professor não cadastrado!");
        }
        em.getTransaction().begin();
        em.remove(professor);
        em.getTransaction().commit();
    }

}