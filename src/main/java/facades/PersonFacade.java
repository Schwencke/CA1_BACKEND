package facades;

import dtos.PersonDTO;
import dtos.PersonsDTO;
import entities.*;
import javassist.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class PersonFacade {

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


    public void addPerson(PersonDTO pDto) {
        Person p = new Person(pDto);
        Address a = new Address(pDto.getAddress());
        p.setAddress(a);
        EntityManager em = getEntityManager();
        boolean n = true;
            TypedQuery<Address> query = em.createQuery("SELECT a FROM Address a WHERE a.street = :street AND a.cityInfo.zipCode = :zipcode AND a.additionalInfo = :additionalInfo", Address.class);
            query.setParameter("street", a.getStreet());
            query.setParameter("zipcode", a.getCityInfo().getZipCode());
            query.setParameter("additionalInfo", a.getAdditionalInfo());
            a = query.getSingleResult();
            p.setAddress(a);

            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
    }



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


    public PersonDTO getPerson(int id) {
        EntityManager em = getEntityManager();
        Person person = em.find(Person.class, id);
        return new PersonDTO(person);
    }


    public PersonsDTO getAllPersons() {
        EntityManager em = getEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
        List<Person> persons = query.getResultList();
        return new PersonsDTO(persons);
    }
}
