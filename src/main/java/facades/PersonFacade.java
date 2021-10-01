package facades;

import dtos.PersonDTO;
import dtos.PersonsDTO;
import entities.*;

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
    public PersonDTO addPerson(String fName,
                               String lName,
                               String email,
                               String street,
                               String additionalInfo,
                               String zipCode,
                               String city) {

        CityInfo cityInfo = new CityInfo(zipCode, city);
        Address address = new Address(street, additionalInfo, cityInfo);
        Person person = new Person(fName, lName, email, address);

        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.persist(person.getAddress());
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }


    @Override
    public void deletePerson(int id) {
        EntityManager em = getEntityManager();
        Person person = em.find(Person.class, id);

        try {
            em.getTransaction().begin();
            em.remove(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

//    @Override
//    public void editPerson(PersonDTO pDTO) {
//        EntityManager em = getEntityManager();
//
//        Person person = new Person(pDTO.getfName(),
//                pDTO.getlName(),
//                pDTO.getEmail(),
//                pDTO.getAddress(),
//                pDTO.getPhones(),
//                pDTO.getHobbies());
//        person.setId(pDTO.getId());
//
//        try {
//            em.getTransaction().begin();
//            em.merge(person);
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//    }

    @Override
    public PersonDTO getPerson(int id) {
        EntityManager em = getEntityManager();
        Person person = em.find(Person.class, id);
        return new PersonDTO(person);
    }

    @Override
    public PersonsDTO getAllPersons() {
        EntityManager em = getEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
        List<Person> persons = query.getResultList();
        return new PersonsDTO(persons);
    }
}
