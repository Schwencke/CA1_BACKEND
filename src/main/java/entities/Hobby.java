package entities;

import dtos.HobbyDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "hobby")
@Entity
public class Hobby implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String name;
    private String description;

    @ManyToMany
    private List<Person> persons;

    public Hobby() {
    }

    public Hobby(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Hobby(int id, String name, String description) {
        if(id != 0){
            this.id = id;
        }
        this.name = name;
        this.description = description;
        this.persons = new ArrayList<>();
    }

    public Hobby(HobbyDTO hDTO){
        this.id = hDTO.getId();
        this.name = hDTO.getName();
        this.description = hDTO.getDescription();
    }


    public static List<Hobby> getHobbies(List<HobbyDTO> list){
        List<Hobby> hobbies = new ArrayList<>();
        list.forEach(dtos -> hobbies.add(new Hobby(dtos)));
        return hobbies;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void addPerson(Person person) {
        this.persons.add(person);
    }


}