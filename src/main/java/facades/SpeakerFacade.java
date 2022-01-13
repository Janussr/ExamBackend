package facades;

import dtos.Speaker.SpeakerDTOs;
import dtos.Talk.TalkDTOs;
import entities.Speaker;
import entities.Talk;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class SpeakerFacade {
    private static SpeakerFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private SpeakerFacade() {
    }

    public static SpeakerFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new SpeakerFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }



    public SpeakerDTOs getAllSpeakers() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Speaker> query = em.createQuery("SELECT s FROM Speaker s", Speaker.class);
            List<Speaker> speakerList = query.getResultList();
            return new SpeakerDTOs(speakerList);
        } finally {
            em.close();
        }
    }
}
