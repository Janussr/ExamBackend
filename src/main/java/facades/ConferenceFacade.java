package facades;

import dtos.Conference.ConferenceDTO;
import dtos.Conference.ConferenceDTOs;
import dtos.Speaker.SpeakerDTO;
import dtos.Talk.TalkDTO;
import entities.Conference;
import entities.Speaker;
import entities.Talk;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.WebApplicationException;
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

    //CREATE CONFERENCE BY ITSELF
    public ConferenceDTO createConference(ConferenceDTO conferenceDTO) {
        EntityManager em = emf.createEntityManager();
        if (conferenceDTO.getCapacity() > 50000 || conferenceDTO.getCapacity() < 1) {
            throw new WebApplicationException("Maximum 50000" + conferenceDTO.getCapacity() + "Minimum 1");
        } else if (conferenceDTO.getDate().isEmpty()|| conferenceDTO.getDate().length()>=11) {
            throw new WebApplicationException("There has to be a valid date");
        } else if (conferenceDTO.getTime().isEmpty() || conferenceDTO.getTime().length() >=6) {
            throw new WebApplicationException("Type any hours of the day example: 12:00");
        } else {

            Conference conference = new Conference(conferenceDTO.getName(), conferenceDTO.getLocation(), conferenceDTO.getCapacity(), conferenceDTO.getDate(), conferenceDTO.getTime());
            try {
                em.getTransaction().begin();
                em.persist(conference);
                em.getTransaction().commit();

                return new ConferenceDTO(conference);
            } finally {
                em.close();
            }
        }
    }

    // USERSTORY 1
    public ConferenceDTOs getAllConferences() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Conference> query = em.createQuery("SELECT c FROM Conference c", Conference.class);
            List<Conference> conferenceList = query.getResultList();
            return new ConferenceDTOs(conferenceList);
        } finally {
            em.close();
        }
    }

    public ConferenceDTO editConference(ConferenceDTO conferenceDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            Conference conference = em.find(Conference.class, conferenceDTO.getId());

            conference.setName(conferenceDTO.getName());
            conference.setLocation(conferenceDTO.getLocation());
            conference.setCapacity(conferenceDTO.getCapacity());
            conference.setDate(conferenceDTO.getDate());
            conference.setTime(conferenceDTO.getTime());

            em.getTransaction().begin();
            em.merge(conference);
            em.getTransaction().commit();
            return new ConferenceDTO(conference);

        } finally {
            em.close();
        }
    }


}
