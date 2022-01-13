package rest;

import dtos.Conference.ConferenceDTO;
import dtos.Talk.TalkDTO;
import entities.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

//Disabled
public class ConferenceResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    private static Conference c1, c2;
    private static Speaker s1, s2;
    private static Talk t1, t2, t3, t4;


    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();

        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();


        c1 = new Conference("Blizzcon","USA",5000,"24-04-2024","18:00" );
        c2 = new Conference("Charity","Denmark",2500,"31-08-2024","11:00");

        t1 = new Talk("Drummer",5,"drums");
        t2 = new Talk("Drummer",5,"drums");
        t3 = new Talk("Gamer",5,"World of warcraft");
        t4 = new Talk("Gamer",5,"World of warcraft");

        s1 = new Speaker("Janus","Drummer","male");
        s2 = new Speaker("Frederik","Gamer","male");

        c1.addTalk(t1);
        c1.addTalk(t2);
        c2.addTalk(t3);
        c2.addTalk(t4);

        t1.addSpeaker(s1);
        t1.addSpeaker(s2);

        try {
            em.getTransaction().begin();
            //Delete existing users and roles to get a "fresh" database
            em.createQuery("delete from Speaker ").executeUpdate();
            em.createQuery("delete from Talk ").executeUpdate();
            em.createQuery("delete from Conference ").executeUpdate();
            em.persist(t1);
            em.persist(t2);
            em.persist(t3);
            em.persist(t4);
            em.persist(c1);
            em.persist(c2);
            em.persist(s1);
            em.persist(s2);
            //System.out.println("Saved test data to database");
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    //This is how we hold on to the token after login, similar to that a client must store the token somewhere
    private static String securityToken;

    //Utility method to login and set the returned securityToken
    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
        //System.out.println("TOKEN ---> " + securityToken);
    }

    private void logOut() {
        securityToken = null;
    }

    @Test
    public void serverIsRunning() {
        given().when().get("/conference").then().statusCode(200);
    }



    @Test
    public void testGetAllConferences() {
        given()
                .contentType("application/json")
                .get("/conference/all")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode());
    }

    @Test
    public void testDelete() {


        given()
                .contentType("application/json")
                .pathParam("id", t2.getId())
                .delete("/talk/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode());


        List<TalkDTO> allTalks;

        allTalks = given()
                .contentType("application/json")
                .when()
                .get("/talk/all")
                .then()
                .extract()
                .body()
                .jsonPath()
                .getList("talks", TalkDTO.class);

        TalkDTO t2DTO = new TalkDTO(t2);

        assertThat(allTalks, not(hasItem(t2DTO)));
    }


    //TODO: Get THIS TEST TO WORK.
    @Test
    public void testGetAll() {
        List<ConferenceDTO> conferences;



        ConferenceDTO c1DTO = new ConferenceDTO(c1);
        ConferenceDTO c2DTO = new ConferenceDTO(c2);

        conferences = given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .get("/conference/all").then()
                .extract()
                .body()
                .jsonPath()
                .getList("conferenceDTOs", ConferenceDTO.class);


        assertEquals(2, conferences.size());

        assertThat(conferences, containsInAnyOrder(c1DTO, c2DTO));
    }




}
