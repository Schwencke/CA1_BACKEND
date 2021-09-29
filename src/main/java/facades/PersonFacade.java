package facades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class PersonFacade implements IPersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    public PersonFacade() {
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }


    @Override
    public PersonDTO addPerson(String fName, String lName, int age, String gender, String email, String city, String city2, String zip, List<String> hobbies) {
        Person person = new Person(String fName, String lName, int age, String gender, String email, String city, String city2, String zip, List<String> hobbies);

        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }

    @Override
    public PersonDTO deletePerson(int id) {
        return null;
    }

    @Override
    public PersonDTO editPerson(PersonDTO p) {
        return null;
    }

    @Override
    public PersonDTO getPerson(int id) {
        EntityManager em = getEntityManager();
        Person person = em.find(Person.class, id);
        return new PersonDTO(person);
    }

    @Override
    public PersonsDTO getAllPersons() {
        EntityManager em = EntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
        List<Person> persons = query.getResultList();
        return new PersonsDTO;
    }
}
