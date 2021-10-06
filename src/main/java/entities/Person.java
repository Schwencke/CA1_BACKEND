package entities;

import com.google.gson.annotations.Expose;
import dtos.AddressDTO;
import dtos.PersonDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Table(name = "person")
@Entity
@NamedQuery(name = "Person.deleteAllRows", query = "DELETE from Person")
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;

    @JoinColumn(name = "address_fk_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Address address;

    @ManyToMany(mappedBy = "persons", cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    private List<Hobby> hobbies;

    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST,fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Phone> phones;

    public Person() {
    }

    public Person(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Person(String firstName, String lastName, String email, Address address, List<Phone> phones, List<Hobby> hobbies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phones = phones;
        this.hobbies = hobbies;
        this.address = address;
    }

    public Person(PersonDTO pDto) {
        this.firstName = pDto.getfName();
        this.lastName = pDto.getlName();
        this.email = pDto.getEmail();
        this.address = new Address(pDto.getAddress());
        this.phones = Phone.getPhones(pDto.getPhones());
        this.hobbies = Hobby.getHobbies(pDto.getHobbies());
    }

    public Person fromDTO(PersonDTO dto, Person ps){
        ps.setFirstName(dto.getfName());
        ps.setLastName(dto.getlName());
        ps.setEmail(dto.getEmail());
        ps.setPhones(Phone.getPhones(dto.getPhones()));
        ps.setHobbies(Hobby.getHobbies(dto.getHobbies()));
        ps.setAddress(new Address(dto.getAddress()));
        return ps;
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


    public void addPhone(Phone phone) {
        if (phone != null){
            this.phones.add(phone);
            phone.setPerson(this);
        }
    }

    public void removePhone(Phone phone) {
        if (phone != null){
            this.phones.remove(phone);
            phone.setPerson(null);
        }
    }

    public void addHobbies(Hobby hobby) {
        if (hobby != null){
            this.hobbies.add(hobby);
            hobby.getPersons().add(this);
        }
    }

    public void removeHobby(Hobby hobby) {
        if (hobby != null){
            this.hobbies.remove(hobby);
            hobby.getPersons().remove(this);
        }
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