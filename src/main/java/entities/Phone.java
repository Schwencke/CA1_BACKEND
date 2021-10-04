package entities;

import dtos.AddressDTO;
import dtos.HobbyDTO;
import dtos.PhoneDTO;
import dtos.PhonesDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "phone")
@Entity
public class Phone implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String number;
    private String description;

    @ManyToOne
    private Person person;

    public Phone() {
    }

    public Phone(String number, String description) {
        this.number = number;
        this.description = description;
    }

    public Phone(String number, String description, Person person) {
        this.number = number;
        this.description = description;
        this.person = person;
    }

    public Phone(int id, String number, String description) {
        if (id != 0){
            this.id = id;
        }
        this.number = number;
        this.description = description;
    }

    public Phone(PhoneDTO dtos) {
        this.id = dtos.getId();
        this.number = dtos.getNumber();
        this.description = dtos.getDescription();

    }

    public static List<Phone> getPhones(List<PhoneDTO> list){
        List<Phone> phones = new ArrayList<>();
        list.forEach(dtos -> phones.add(new Phone(dtos)));
        return phones;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }


}