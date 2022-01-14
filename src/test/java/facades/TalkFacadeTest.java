package facades;

import dtos.Talk.TalkDTO;
import dtos.Talk.TalkDTOs;
import entities.Conference;
import entities.Speaker;
import entities.Talk;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

class TalkFacadeTest {

    private static EntityManagerFactory emf;
    private static TalkFacade facade;

    private static Conference c1, c2;
    private static Speaker s1, s2, s3, s4;
    private static Talk t1, t2, t3, t4;

    public TalkFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = TalkFacade.getInstance(emf);
    }

    @AfterAll
    public static void tearDownClass() {
        //Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        c1 = new Conference("Blizzcon", "USA", 5000, "24-04-2024", "18:00");
        c2 = new Conference("Charity", "Denmark", 2500, "31-08-2024", "11:00");

        t1 = new Talk("Drummer", 5, "drums");
        t2 = new Talk("Drummer", 5, "drums");
        t3 = new Talk("Gamer", 5, "World of warcraft");
        t4 = new Talk("Gamer", 5, "World of warcraft");

        s1 = new Speaker("Janus", "Drummer", "male");
        s2 = new Speaker("Frederik", "Gamer", "male");
        s3 = new Speaker("Frederik", "Gamer", "male");
        s4 = new Speaker("Frederik", "Gamer", "male");


        c1.addTalk(t1);
        c1.addTalk(t2);
        c2.addTalk(t3);
        c2.addTalk(t4);

        t1.addSpeaker(s1);
        t1.addSpeaker(s2);
        t1.addSpeaker(s3);
        t2.addSpeaker(s1);
        t2.addSpeaker(s2);

        try {
            em.getTransaction().begin();
            em.createQuery("delete from Talk ").executeUpdate();
            em.createQuery("delete from Speaker ").executeUpdate();
            em.createQuery("delete from Conference ").executeUpdate();
            em.persist(t1);
            em.persist(t2);
            em.persist(t3);
            em.persist(t4);
            em.persist(c1);
            em.persist(c2);
            em.persist(s1);
            em.persist(s2);
            em.persist(s3);
            em.persist(s4);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createQuery("delete from Speaker ").executeUpdate();
            em.createQuery("delete from Talk ").executeUpdate();
            em.createQuery("delete from Conference ").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }


    @Test
    public void createTalksTest(){
        Talk t = new Talk("Bager",5,"dej",c1);
        TalkDTO t5 = new TalkDTO(t);
        facade.createTalk(t5);

        assertEquals(5,facade.getAllTalks().getTalks().size());
    }

    @Test
    public void getAllTalksTest(){
       List<TalkDTO> allTalks = facade.getAllTalks().getTalks();

       assertEquals(4, allTalks.size());

        TalkDTO t1DTO = new TalkDTO(t1);
        TalkDTO t2DTO = new TalkDTO(t2);
        TalkDTO t3DTO = new TalkDTO(t3);
        TalkDTO t4DTO = new TalkDTO(t4);

        assertThat(allTalks, containsInAnyOrder(t1DTO, t2DTO, t3DTO,t4DTO));
    }

    @Test
    public void updateTalkTest(){
        TalkDTO talk1 = facade.getSpecificTalk(t1.getId());
        facade.updateTalkSpeaker(t1.getId(), s4.getId(), s2.getId());
        TalkDTO talk2 = facade.getSpecificTalk(t1.getId());

        assertEquals(false, talk1.getSpeakers().equals(talk2.getSpeakers()));

    }


    @Test
    public void deleteTallkTest() {
        facade.deleteTalk(t4.getId());

        List<TalkDTO> allTalks = facade.getAllTalks().getTalks();

        assertEquals(3, allTalks.size());

        TalkDTO t1DTO = new TalkDTO(t1);
        TalkDTO t2DTO = new TalkDTO(t2);
        TalkDTO t3DTO = new TalkDTO(t3);
        TalkDTO t4DTO = new TalkDTO(t4);

        assertThat(allTalks, not(hasItem(t4DTO)));
//        assertThat(allTalks, containsInAnyOrder(t1DTO, t2DTO, t3DTO));
    }

}