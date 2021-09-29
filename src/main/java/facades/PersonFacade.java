package facades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
        return null;
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
        return null;
    }

    @Override
    public PersonsDTO getAllPersons() {
        return null;
    }
}
