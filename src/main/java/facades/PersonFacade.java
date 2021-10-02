package facades;

import dtos.PersonDTO;
import dtos.PersonsDTO;
import entities.*;
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




    public void addPerson(PersonDTO pDto) throws Exception {
        EntityManager em = getEntityManager();

        Person person;
        person = new Person(pDto);
        if (checkAddress(em,person) != null){
            person.setAddress(checkAddress(em,person));
        }
        if (checkCity(em,person.getAddress().getCityInfo()) != null){
            person.getAddress().setCityInfo(checkCity(em,person.getAddress().getCityInfo()));
        }

        List<Hobby> newHobbyList = new ArrayList<>();
        person.getHobbies().forEach(hobby -> {
            hobby = em.find(Hobby.class, hobby.getId());
            if (!hobby.getPersons().contains(person)){
            hobby.addPerson(person);}
            newHobbyList.add(hobby);

        });
        person.setHobbies(newHobbyList);

        person.getPhones().forEach(phone -> {
            phone.setPerson(person);
        });
        em.getTransaction().begin();
        em.persist(person);
        em.merge(person.getAddress().getCityInfo());
        em.persist(person.getAddress());
        em.getTransaction().commit();
    }


    private CityInfo checkCity(EntityManager em,CityInfo cityInfo) {
        CityInfo cinf;
        try{
            TypedQuery<CityInfo> query = em.createQuery("SELECT c FROM CityInfo c WHERE c.zipCode = :zipCode AND c.city = :city", CityInfo.class);
            query.setParameter("zipCode", cityInfo.getZipCode());
            query.setParameter("city", cityInfo.getCity());
            cinf = query.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
        return cinf;
    }


    public Address checkAddress(EntityManager em,Person p){
        Address address;
        try{
        TypedQuery<Address> query = em.createQuery("SELECT a FROM Address a WHERE a.street = :street AND a.cityInfo.zipCode = :zipcode AND a.additionalInfo = :additionalInfo", Address.class);
        query.setParameter("street", p.getAddress().getStreet());
        query.setParameter("zipcode", p.getAddress().getCityInfo().getZipCode());
        query.setParameter("additionalInfo", p.getAddress().getAdditionalInfo());
        address = query.getSingleResult();
        }catch (NoResultException e){
          return null;
        }
        return address;
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
