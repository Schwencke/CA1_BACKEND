package dtos;

import entities.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonsDTO {

    private List<PersonDTO> all = new ArrayList<>();

    public PersonsDTO(List<Person> personEntities) {

        personEntities.forEach((person) -> {
            all.add(new PersonDTO(person));
        });
    }

    public List<PersonDTO> getAll() {
        return all;
    }
}
