package facades;

import dtos.PersonDTO;
import dtos.PersonsDTO;
import dtos.PhoneDTO;

import java.util.List;

public interface IPersonFacade {
    public PersonDTO addPerson(String fName,
                               String lName,
                               String email,
                               String street,
                               String additionalInfo,
                               String zipCode,
                               String city,
                               List<PhoneDTO> phones);

    public void deletePerson(int id);

  //  public void editPerson(PersonDTO p);

    public PersonDTO getPerson(int id);

    public PersonsDTO getAllPersons();
}
