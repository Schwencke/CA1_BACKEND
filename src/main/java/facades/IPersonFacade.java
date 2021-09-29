package facades;

import dtos.PersonDTO;
import dtos.PersonsDTO;

import java.util.List;

public interface IPersonFacade {
    public PersonDTO addPerson(String fName,
                               String lName,
                               int age,
                               String gender,
                               String email,
                               String city,
                               String city2,
                               String zip,
                               List<String> hobbies);

    public PersonDTO deletePerson(int id);

    public PersonDTO editPerson(PersonDTO p);

    public PersonDTO getPerson(int id);

    public PersonsDTO getAllPersons();
}
