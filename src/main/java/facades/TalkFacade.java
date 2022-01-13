package facades;

import dtos.Conference.ConferenceDTOs;
import dtos.Talk.TalkDTO;
import dtos.Talk.TalkDTOs;
import entities.Conference;
import entities.Talk;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.WebApplicationException;
import java.util.List;

public class TalkFacade {
    private static TalkFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private TalkFacade() {
    }

    public static TalkFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TalkFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    //USERSTORY -4 AND CONNECTS TO A CON
    public TalkDTO createTalk(TalkDTO talkDTO) {
        EntityManager em = emf.createEntityManager();
        Talk talk = new Talk(talkDTO.getTopic(), talkDTO.getDuration(), talkDTO.getPropsList());

        //Adds the conference to the talk, when talk is created.
        TypedQuery<Conference> query = em.createQuery("SELECT c FROM Conference c WHERE c.id =:conferenceId", Conference.class);
        query.setParameter("conferenceId", talkDTO.getConference().getId());
        Conference conference = query.getSingleResult();
        talk.setConference(conference);

        try {
            em.getTransaction().begin();
            em.persist(talk);
            em.getTransaction().commit();
            return new TalkDTO(talk);

        } finally {
            em.close();
        }
    }

    public TalkDTOs getAllTalks() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Talk> query = em.createQuery("SELECT t FROM Talk t", Talk.class);
            List<Talk> talkList = query.getResultList();
            return new TalkDTOs(talkList);
        } finally {
            em.close();
        }
    }


    //delete boat Solve us 7
    public TalkDTO deleteTalk(int id) throws WebApplicationException {
        EntityManager em = emf.createEntityManager();
        Talk talk = em.find(Talk.class, id);
        if (talk == null) {
            throw new WebApplicationException("no talks matches the id");
        } else {
            try {
                em.getTransaction().begin();
                em.createNativeQuery("DELETE FROM TALK_SPEAKER WHERE speakers_id = ?").setParameter(1, talk.getId()).executeUpdate();
                em.remove(talk);
                em.getTransaction().commit();

                return new TalkDTO(talk);
            } finally {
                em.close();
            }
        }

    }
}
