package rest;

import dtos.PersonDTO;
import entities.*;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
//Uncomment the line below, to temporarily disable this test
//@Disabled

public class PersonResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Person p1, p2;
    private static Address a1, a2;
    private static CityInfo c1;
    private static Phone t1, t2, t3, t4;
    private static Hobby h1, h2;
    private static List<Phone> phones1 = new ArrayList<>();
    private static List<Phone> phones2 = new ArrayList<>();
    private static List<Hobby> hobbies1 = new ArrayList<>();

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

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
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        p1 = new Person("Mogens", "Glad", "m@g.dk");
        p2 = new Person("Peter", "Belly", "p@b.dk");

        a1 = new Address("Danmarksgade 111", "Home address");
        a2 = new Address("Bornholmergaden 222", "Home address");
        p1.setAddress(a1);
        p2.setAddress(a2);

        c1 = new CityInfo("3700", "Rønne");
        a1.setCityInfo(c1);
        a2.setCityInfo(c1);

        t1 = new Phone("11111111", "Home", p1);
        t2 = new Phone("22222222", "Work", p1);

        phones1.add(t1);
        phones1.add(t2);
        p1.setPhones(phones1);

        t3 = new Phone("33333333", "Home", p2);
        t4 = new Phone("44444444", "Work", p2);
        phones2.add(t3);
        phones2.add(t4);
        p2.setPhones(phones2);

        h1 = new Hobby("3D-udskrivning", "https://en.wikipedia.org/wiki/3D_printing", p1);
        h2 = new Hobby("Akrobatik", "https://en.wikipedia.org/wiki/Acrobatics", p1);

        hobbies1.add(h1);
        hobbies1.add(h2);
        p1.setHobbies(hobbies1);
        p2.setHobbies(hobbies1);

        try {
            em.getTransaction().begin();
            em.createQuery("DELETE from Address");
            em.createQuery("DELETE from CityInfo");
            em.createQuery("DELETE from Hobby");
            em.createQuery("DELETE from Person");
            em.createQuery("DELETE from Phone");
            em.persist(a1);
            em.persist(c1);
            em.persist(p1);
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.persist(a2);
            em.persist(p2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testAll() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .get("/person").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("all", hasSize(2));
    }

    @Test
    public void findById() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .get("/person/" + p1.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("fName", equalTo("Mogens"));
    }

    @Test
    public void editById() {


        PersonDTO test = new PersonDTO(p1);
        test.setfName("Thomas");


        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(test)
                .when()
                .put("/person/")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("fName", equalTo("Thomas"));
    }

}