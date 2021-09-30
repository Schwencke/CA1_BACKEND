package entities;

import dtos.PersonDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "person")
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;

    @JoinColumn(name = "address_fk_id", referencedColumnName = "id")
    @ManyToOne
    private Address address;

    @ManyToMany(mappedBy = "persons", cascade = CascadeType.PERSIST)
    private List<Hobby> hobbies = new ArrayList<>();

    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST)
    private List<Phone> phones;

    public Person() {
    }

    public Person(String firstName, String lastName, String email, Address address, List<Phone> phones, List<Hobby> hobbies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phones = phones;
        this.hobbies.addAll(hobbies);
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Hobby> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<Hobby> hobbies) {
        this.hobbies = hobbies;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
        if (address != null) {
            address.addPerson(this);
        }
    }
}