package facades;

import dtos.PersonDTO;
import dtos.PersonsDTO;
import entities.Address;
import entities.Hobby;
import entities.Phone;

import java.util.List;

public interface IPersonFacade {
    public PersonDTO addPerson(String fName,
                               String lName,
                               String email,
                               Address address,
                               List<Phone> phones,
                               List<Hobby> hobbies);

    public void deletePerson(int id);

    public void editPerson(PersonDTO p);

    public PersonDTO getPerson(int id);

    public PersonsDTO getAllPersons();
}
