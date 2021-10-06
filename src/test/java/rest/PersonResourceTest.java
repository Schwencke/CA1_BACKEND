package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import entities.*;
import io.restassured.path.json.JsonPath;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import io.restassured.parsing.Parser;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
//Uncomment the line below, to temporarily disable this test
//@Disabled

public class PersonResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Person p1, p2;
    private static Address a1, a2;
    private static CityInfo c1, c2;
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
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        httpServer = startServer();
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
        em.createNamedQuery("Person.deleteAllRows").executeUpdate();
        em.createNamedQuery("Address.deleteAllRows").executeUpdate();
        em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
        em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
        em.createNativeQuery("insert into city_info (id,city, zipcode)"
                + "values(1,'Rønne', '3700')").executeUpdate();
        em.createNativeQuery("insert into city_info (id,city, zipcode)"
                +"values(2,'Pedersker','3720')").executeUpdate();
        em.createNativeQuery("insert into hobby (id,description, name)"
                +"values(1,'https://en.wikipedia.org/wiki/3D_printing','3D-udskrivning')").executeUpdate();
        em.createNativeQuery("insert into hobby (id,description, name)"
                +"values(2,'https://en.wikipedia.org/wiki/Acrobatics','Akrobatik')").executeUpdate();
        em.getTransaction().commit();

    }

    @AfterAll
    public static void closeTestServer() {
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @AfterEach
    public void clean(){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.find(Person.class, p1.getId());
            em.remove(p1);
            em.getTransaction().commit();
        }finally {
            em.close();
        }

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
        c2 = new CityInfo("3720", "Pedersker");
        c1.setId(1);
        c2.setId(2);
        a1.setCityInfo(c1);
        a2.setCityInfo(c2);

        t1 = new Phone("11111111", "Home");
        t2 = new Phone("22222222", "Work");

        phones1.add(t1);
        phones1.add(t2);
        p1.setPhones(phones1);

        t3 = new Phone("33333333", "Home");
        t4 = new Phone("44444444", "Work");
        phones2.add(t3);
        phones2.add(t4);
        p2.setPhones(phones2);

        h1 = new Hobby(1,"3D-udskrivning", "https://en.wikipedia.org/wiki/3D_printing");
        h2 = new Hobby(2,"Akrobatik", "https://en.wikipedia.org/wiki/Acrobatics");


        hobbies1.add(h1);
        hobbies1.add(h2);
        p1.setHobbies(hobbies1);
        p2.setHobbies(hobbies1);


        em.getTransaction().begin();
        em.persist(p1);
        em.persist(p1.getAddress());
        em.getTransaction().commit();


    }

    @Test
    public void testServerIsUp() {
        System.out.println("Test server is running (okay)");
        given().when().get("/person/").then().statusCode(200);
    }

    @Test
    void create() {
        PersonDTO expected = new PersonDTO(p2);

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(expected)
                .when()
                .post("/person/").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("fName", equalTo("Peter"));
    }

    @Test
    public void testAll() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .get("/person/").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("all", hasSize(1));
    }

    @Test
    public void findById() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .get("/person/" + p1.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("fName", equalTo(p1.getFirstName()));
    }

    @Test
    public void editById() {
        PersonDTO expected = new PersonDTO(p1);
        expected.setfName("Thomas");
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(expected)
                .when()
                .put("/person/")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("fName", equalTo("Thomas"));

    }

    @Test
    public void deleteById() {
        int id = p1.getId();
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .delete("/person/" + id)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode());
    }
}