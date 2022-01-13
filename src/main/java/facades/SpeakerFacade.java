package facades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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

}
