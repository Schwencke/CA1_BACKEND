package facades;

import dtos.*;
import entities.*;
import errorhandling.CustomException;

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

    public void deletePersonById(int id) throws CustomException {
        EntityManager em = getEntityManager();

        Person ps = em.find(Person.class, id);
        if (ps == null) {
            throw new CustomException(404, "No person with provided id (" + id + ") found.");
        }
        Address ad = em.find(Address.class, ps.getAddress().getId());
        if (ad.getPersons().size() <=1){
            em.remove(ad);
        }
        try {
            em.getTransaction().begin();
            em.remove(ps);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public boolean checkPersonExist(PersonDTO pDTO) throws CustomException {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.firstName = :fname and p.lastName = :lname and p.address.street = :street and p.address.additionalInfo = :add", Person.class);
            query.setParameter("fname", pDTO.getfName());
            query.setParameter("lname", pDTO.getlName());
            query.setParameter("street", pDTO.getAddress().getStreet());
            query.setParameter("add", pDTO.getAddress().getAdditionalInfo());
            query.getSingleResult();
        } catch (NoResultException e) {
            return false;
        }
        throw new CustomException(403, "En bruger med samme adresse og navn eksistere allerede");
    }


    public PersonDTO addPerson(PersonDTO pDTO) throws CustomException {
        EntityManager em = getEntityManager();
        checkPersonExist(pDTO);


        //TODO: Refactor
        if (pDTO.getfName().isEmpty() ||
                pDTO.getlName().isEmpty() ||
                pDTO.getEmail().isEmpty() ||
                pDTO.getAddress() == null ||
                pDTO.getPhones() == null ||
                pDTO.getHobbies() == null) {
            throw new CustomException(404, "One or more fields are missing.");
        }

        Person person;
        person = new Person(pDTO);

        if (checkAddress(person) != null) {
            person.setAddress(checkAddress(person));
        }
        if (checkCity(person.getAddress().getCityInfo()) != null) {
            person.getAddress().setCityInfo(checkCity(person.getAddress().getCityInfo()));
        }

        List<Hobby> newHobbyList = new ArrayList<>();
        person.getHobbies().forEach(hob -> {
            if (hob.getId() == 0) {
                newHobbyList.add(makeHobby(em, hob));
            }
        });

        person.getHobbies().forEach(hobby -> {
            hobby = em.find(Hobby.class, hobby.getId());
            if (!hobby.getPersons().contains(person)) {
                hobby.addPerson(person);
            }
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
        return new PersonDTO(person);
    }

    public CityInfo checkCity(CityInfo cityInfo) {
        EntityManager em = emf.createEntityManager();
        CityInfo cinf;
        try {
            TypedQuery<CityInfo> query = em.createQuery("SELECT c FROM CityInfo c WHERE c.zipCode = :zipCode AND c.city = :city", CityInfo.class);
            query.setParameter("zipCode", cityInfo.getZipCode());
            query.setParameter("city", cityInfo.getCity());
            cinf = query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return cinf;
    }

    private Hobby makeHobby(EntityManager em, Hobby hb) {

        em.getTransaction().begin();
        em.persist(hb);
        em.getTransaction().commit();
        return hb;
    }

    public Phone checkPhone(Phone phone) {
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

    public Address checkAddress(Person p) {
        EntityManager em = emf.createEntityManager();
        Address address;
        try {
            TypedQuery<Address> query = em.createQuery("SELECT a FROM Address a WHERE a.street = :street AND a.cityInfo.zipCode = :zipcode AND a.additionalInfo = :additionalInfo", Address.class);
            query.setParameter("street", p.getAddress().getStreet());
            query.setParameter("zipcode", p.getAddress().getCityInfo().getZipCode());
            query.setParameter("additionalInfo", p.getAddress().getAdditionalInfo());
            address = query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return address;
    }

    public List<HobbyDTO> returnAllHobbys(){
        EntityManager em = emf.createEntityManager();
        List<HobbyDTO> list;
      TypedQuery<HobbyDTO> query = em.createQuery("select h.id, h.name, h.description from Hobby h", HobbyDTO.class);
        list = query.getResultList();
        return list;
    }

    public List<CityInfoDTO> returnAllCities(){
        EntityManager em = emf.createEntityManager();
        List<CityInfoDTO> list;
        TypedQuery<CityInfoDTO> query = em.createQuery("select c.id, c.city, c.zipCode from CityInfo c", CityInfoDTO.class);
        list = query.getResultList();
        return list;
    }

    public Address createNewAddress(Address ad){
        EntityManager em = emf.createEntityManager();
        CityInfo cityInfo = checkCity(ad.getCityInfo());
        cityInfo = em.find(CityInfo.class, cityInfo.getId());
        Address newAddress = new Address(ad.getStreet(), ad.getAdditionalInfo(), cityInfo);
        em.getTransaction().begin();
        em.persist(newAddress);
        em.getTransaction().commit();
        return newAddress;
    }

    public PersonDTO editPerson(PersonDTO pDTO) throws CustomException {
        EntityManager em = getEntityManager();

        if (pDTO.getId() == null || pDTO.getId() <= 0){
            throw new CustomException(404, "No person ID was provided");
        }


        //TODO: Refactor
        if (pDTO.getfName().isEmpty() ||
                pDTO.getlName().isEmpty() ||
                pDTO.getEmail().isEmpty() ||
                pDTO.getAddress() == null ||
                pDTO.getPhones() == null ||
                pDTO.getHobbies() == null) {
            throw new CustomException(404, "One or more fields are missing.");
        }

        Person dtoPs = new Person();
        dtoPs = dtoPs.fromDTO(pDTO, dtoPs);

        Person ps;
        Address ad;

        try {
            ps = em.find(Person.class, pDTO.getId());
            Address adrToRemove = em.find(Address.class, ps.getAddress().getId());
            if (dtoPs.getAddress().getId() == null && adrToRemove.getPersons().size() <=0){
                em.remove(adrToRemove);
            }
            // if no id was sent by PUT, it's a new Address, current must be set to null
            if (dtoPs.getAddress().getId() == null){
                ps.setAddress(null);
            }
            //****//PHONE HANDLING//****//
            List<Phone> phl = new ArrayList<>(ps.getPhones());
            Phone alreadyInUse = new Phone();
            phl.forEach(ps::removePhone);
            dtoPs.getPhones().forEach(newPhones -> {
                if (newPhones.getId() != 0) {
                    Phone phone = em.find(Phone.class, newPhones.getId());
                    phone.setDescription(newPhones.getDescription());
                    phone.setNumber(newPhones.getNumber());
                    ps.addPhone(phone);
                } else {
                    Phone phone = checkPhone(newPhones);
                    if (phone == null) {
                        ps.addPhone(newPhones);
                    } else if (phone.getPerson().getId() != null && phone.getPerson().getId().equals(ps.getId())) {
                        ps.addPhone(phone);
                    } else {

                        alreadyInUse.setNumber(phone.getNumber());
                    }
                }
            });
            if (alreadyInUse.getNumber() != null) {
                System.out.println("Nummeret eksisterer allerede");
                throw new CustomException(403, "The number already exists.");
            }
            //****//PHONE HANDLING//****//

            //****//HOBBY HANDLING//****//
            List<Hobby> hby = new ArrayList<>(ps.getHobbies());
            Hobby notFoundHobby = new Hobby();
            hby.forEach(ps::removeHobby);
            dtoPs.getHobbies().forEach(newHobbies -> {
                Hobby hobby = em.find(Hobby.class, newHobbies.getId());
                if (hobby == null) {
                    notFoundHobby.setName(newHobbies.getName());
                } else {
                    ps.addHobbies(hobby);
                }
            });
            if (notFoundHobby.getName() != null) {
                System.out.println("hobby not found");
                throw new CustomException(404, "Hobby not found.");
            }
            //****//HOBBY HANDLING//****//

            if(dtoPs.getAddress().getId() != null){
              ad = em.find(Address.class, ps.getAddress().getId());
              ad.setStreet(dtoPs.getAddress().getStreet());
              ad.setAdditionalInfo(dtoPs.getAddress().getAdditionalInfo());
              if (!ad.getPersons().contains(ps)) {
                  ad.addPerson(ps);
              }
              ps.setAddress(ad);
            } else{
                ps.setAddress( createNewAddress(dtoPs.getAddress()));
                ps.getAddress().addPerson(ps);
            }

            ps.setFirstName(pDTO.getfName());
            ps.setLastName(pDTO.getlName());
            ps.setEmail(pDTO.getEmail());

            em.getTransaction().begin();
            em.merge(ps);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(ps);
    }

    public PersonDTO getPerson(int id) throws CustomException {
        EntityManager em = getEntityManager();
        Person person = em.find(Person.class, id);
        if (person == null) {
            throw new CustomException(404, "No person with provided id (" + id + ") found.");
        }
        return new PersonDTO(person);
    }

    public PersonsDTO getAllPersons() throws CustomException {
        EntityManager em = getEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
        List<Person> persons = query.getResultList();
        if (persons.isEmpty()) {
            throw new CustomException(404, "No persons was found.");
        }
        return new PersonsDTO(persons);
    }
}
