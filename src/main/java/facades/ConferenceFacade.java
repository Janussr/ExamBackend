package facades;

import dtos.Conference.ConferenceDTOs;
import entities.Conference;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class ConferenceFacade {
    private static ConferenceFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private ConferenceFacade() {
    }

    public static ConferenceFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ConferenceFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public ConferenceDTOs getAllConferences(){
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Conference> query = em.createQuery("SELECT c FROM Conference c",Conference.class);
            List<Conference> conferenceList = query.getResultList();
            return new ConferenceDTOs(conferenceList);
        }finally {
            em.close();
        }
    }



}
