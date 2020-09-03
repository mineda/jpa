package br.gov.sp.fatec.projetomaven.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceManager {
    
    private static PersistenceManager instance;
    
    private PersistenceManager () {
    }
    
    protected EntityManagerFactory emf;
    
    public static PersistenceManager getInstance() {
        if(instance == null) {
            instance = new PersistenceManager();
        }
        return instance;
    }
    
    public EntityManagerFactory getEntityManagerFactory() {
        if(emf == null) {
            createEntityManagerFactory();
        }
        return emf;
    }
    
    public EntityManager getEntityManager() {
        if(emf == null) {
            createEntityManagerFactory();
        }
        return emf.createEntityManager();
    }
    
    private void createEntityManagerFactory() {
        emf = Persistence.createEntityManagerFactory("avaliacao");
    }

}