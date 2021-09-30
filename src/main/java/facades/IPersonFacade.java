package facades;

import dtos.PersonDTO;
import dtos.PersonsDTO;
import entities.Hobby;

import java.util.List;

public interface IPersonFacade {
    public PersonDTO addPerson(String fName,
                               String lName,
                               int age,
                               String gender,
                               String email,
                               String phone,
                               String city,
                               String street,
                               String zip,
                               List<Hobby> hobbies);

    public void deletePerson(int id);

    public void editPerson(PersonDTO p);

    public PersonDTO getPerson(int id);

    public PersonsDTO getAllPersons();
}
