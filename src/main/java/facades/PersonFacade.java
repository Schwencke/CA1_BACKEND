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




    public PersonDTO addPerson(PersonDTO pDto) throws Exception {
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
        person.getHobbies().forEach(hob -> {
            if (hob.getId() == 0){
               newHobbyList.add(makeHobby(em, hob));
            }
        });

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
        return pDto;
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

    private Hobby makeHobby(EntityManager em,Hobby hb){

     em.getTransaction().begin();
     em.persist(hb);
     em.getTransaction().commit();
     return hb;
    }

    public Phone checkPhone(Phone phone){
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Phone> query = em.createQuery("SELECT p FROM Phone p WHERE p.number = :number", Phone.class);
            query.setParameter("number", phone.getNumber());
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
            //add specific error?
        } finally {
            em.close();
        }
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


    //TODO: fejlhåndtering
    public void editPerson(PersonDTO pDTO) {
        EntityManager em = getEntityManager();
       Person dtoPs = new Person();
       dtoPs = dtoPs.fromDTO(pDTO, dtoPs);

       Person ps;
       Address ad = dtoPs.getAddress();

        try {
            ps = em.find(Person.class, pDTO.getId());

            //****//PHONE HANDLING//****//
            List<Phone> phl = new ArrayList<>(ps.getPhones());
            Phone alreadyInUse = new Phone();
            phl.forEach(ps::removePhone);

            dtoPs.getPhones().forEach(newPhones -> {
                if (newPhones.getId() != null){
                   Phone phone = em.find(Phone.class, newPhones.getId());
                    phone.setDescription(newPhones.getDescription());
                    phone.setNumber(newPhones.getNumber());
                    ps.addPhone(phone);
                } else {
            Phone phone = checkPhone(newPhones);
            if(phone == null){
                ps.addPhone(newPhones);
            } else if(phone.getPerson().getId() != null && phone.getPerson().getId().equals(ps.getId())) {
                ps.addPhone(phone);
            }else {

                alreadyInUse.setNumber(phone.getNumber());
            }
            } });
            if (alreadyInUse.getNumber() != null){
                System.out.println("Nummeret eksistere allerede");
                //TODO: implement errorhandler
            }
            //****//PHONE HANDLING//****//

            //****//HOBBY HANDLING//****//
            List<Hobby> hby = new ArrayList<>(ps.getHobbies());
            Hobby notFoundHobby = new Hobby();
            hby.forEach(ps::removeHobby);
            dtoPs.getHobbies().forEach(newHobbies -> {
                Hobby hobby = em.find(Hobby.class, newHobbies.getId());
                if (hobby == null){
                    notFoundHobby.setName(newHobbies.getName());
                } else {
                    ps.addHobbies(hobby);
                }
            });
            if (notFoundHobby.getName() != null){
                System.out.println("hobby not found");
                //TODO: implement errorhandler
            }
            //****//HOBBY HANDLING//****//


            ps.setAddress(ad);
            ps.setFirstName(pDTO.getfName());
            ps.setLastName(pDTO.getlName());
            ps.setEmail(pDTO.getEmail());

            em.getTransaction().begin();
            em.merge(ps);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }


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
