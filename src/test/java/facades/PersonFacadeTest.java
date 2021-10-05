package facades;

import dtos.PersonDTO;
import entities.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private static Person p1, p2;
    private static Address a1, a2;
    private static CityInfo c1,c2;
    private static Phone t1, t2, t3, t4;
    private static Hobby h1, h2;
    private static List<Phone> phones1 = new ArrayList<>();
    private static List<Phone> phones2 = new ArrayList<>();
    private static List<Hobby> hobbies1 = new ArrayList<>();



    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PersonFacade.getPersonFacade(emf);
    }

    @AfterEach
    public void clean(){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
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

        //// IMPORTANT - TEST-DATABASE MUST CONTAIN THEESE TWO CITIES
        c1 = new CityInfo("3700", "Rønne"); /// MUST HAVE ID. 1
        c2 = new CityInfo("3720", "Pedersker"); /// MUST HAVE ID. 2
        //// IMPORTANT - TEST-DATABASE MUST CONTAIN THEESE TWO CITIES
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

        //// IMPORTANT - TEST-DATABASE MUST CONTAIN THEESE TWO HOBBIES
        h1 = new Hobby(1,"3D-udskrivning", "https://en.wikipedia.org/wiki/3D_printing"); /// MUST HAVE ID. 1
        h2 = new Hobby(2,"Akrobatik", "https://en.wikipedia.org/wiki/Acrobatics"); /// MUST HAVE ID. 2
        //// IMPORTANT - TEST-DATABASE MUST CONTAIN THEESE TWO HOBBIES

        hobbies1.add(h1);
        hobbies1.add(h2);
        p1.setHobbies(hobbies1);
        p2.setHobbies(hobbies1);


        em.getTransaction().begin();
        em.persist(p1);
        em.persist(p1.getAddress());
        em.getTransaction().commit();

    }


    @AfterEach
    void tearDown() {

    }

    @Test
    void addPerson() throws Exception {
        PersonDTO expected = new PersonDTO(p2);
        PersonDTO actual = facade.addPerson(new PersonDTO(p2));
        assertTrue(Objects.equals(expected.getfName(), actual.getfName()) &&
                Objects.equals(expected.getlName(), actual.getlName()));
    }

    @Test
    void checkPhone() {
        String expected = t1.getDescription();
        String actual = facade.checkPhone(t1).getDescription();
        assertEquals(expected, actual);
    }

    @Test
    void checkCity() {
        String expected = c2.getCity();
        String actual = facade.checkCity(c2).getCity();
        assertEquals(expected, actual);
    }

    @Test
    void editPerson() {
        p1.setFirstName("Thomas");
        PersonDTO expected = new PersonDTO(p1);
        PersonDTO actual = facade.editPerson(new PersonDTO(p1));
        assertTrue(Objects.equals(expected.getfName(), actual.getfName()) &&
                Objects.equals(expected.getlName(), actual.getlName()));
    }

    @Test
    void getPerson() {
        String expected = p1.getFirstName();
        String actual = facade.getPerson(p1.getId()).getfName();
        assertEquals(expected, actual);
    }

    @Test
    void getAllPersons() throws Exception {
        facade.addPerson(new PersonDTO(p2));
        int expected = 2;
        int actual = facade.getAllPersons().getAll().size();
        assertEquals(expected, actual);
    }
}