package dtos;

import entities.Address;
import entities.Hobby;
import entities.Person;
import entities.Phone;

import java.util.Date;
import java.util.List;

public class PersonDTO {
    private Integer id;
    private String fName;
    private String lName;
    private String email;
    private Address address;
    private List<Phone> phones;
    private List<Hobby> hobbies;

    public PersonDTO(Person ps) {
        this.id = ps.getId();
        this.fName = ps.getFirstName();
        this.lName = ps.getLastName();
        this.email = ps.getEmail();
        this.phones = ps.getPhones();
        this.hobbies = ps.getHobbies();
        this.address = ps.getAddress();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public List<Hobby> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<Hobby> hobbies) {
        this.hobbies = hobbies;
    }
}
