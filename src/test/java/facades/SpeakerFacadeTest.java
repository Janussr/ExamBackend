package facades;

import entities.Conference;
import entities.Speaker;
import entities.Talk;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

class SpeakerFacadeTest {
    private static EntityManagerFactory emf;
    private static SpeakerFacade facade;

    private static Conference c1, c2;
    private static Speaker s1, s2;
    private static Talk t1, t2, t3, t4;

    public SpeakerFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = SpeakerFacade.getInstance(emf);
    }

    @AfterAll
    public static void tearDownClass() {
        //Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        c1 = new Conference("Blizzcon","USA",5000,"24-04-2024","18:00" );
        c2 = new Conference("Twitchcon","Denmark",2500,"31-08-2024","11:00");

        t1 = new Talk("Drummer",5,"drums");
        t2 = new Talk("Drummer",5,"drums");
        t3 = new Talk("Gamer",5,"World of warcraft");
        t4 = new Talk("Gamer",5,"World of warcraft");

        s1 = new Speaker("Janus","Drummer","male");
        s2 = new Speaker("Frederik","Gamer","male");

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
            em.createQuery("delete from Talk ").executeUpdate();
            em.createQuery("delete from Speaker ").executeUpdate();
            em.createQuery("delete from Conference ").executeUpdate();
            em.getTransaction().commit();

        }finally {
            em.close();
        }
    }

}