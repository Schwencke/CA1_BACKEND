package entities;

import dtos.PhoneDTO;

import javax.persistence.*;
import java.util.List;

@Table(name = "phone")
@Entity
public class Phone {
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

    public Phone(String number, String description, Person person) {
        this.number = number;
        this.description = description;
        this.person = person;
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