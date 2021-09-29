package entities;

import dtos.PersonDTO;

import javax.persistence.*;
import java.util.Date;

@Table(name = "person")
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String firstName;
    private String lastName;
    private String phone;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastEdited;
    @ManyToOne
    private Address address;

    public Person() {
    }

    public Person(String firstName, String lastName, String phone, Date created, Date lastEdited) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.created = created;
        this.lastEdited = lastEdited;
    }

    public Person(String firstName, String lastName, String phone,Integer id, Date created) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.id = id;
        this.created = created;
    }

    public Person(String firstName, String lastName, String phone) {
        this.created = new Date();
        this.lastEdited = created = new Date();
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }
    public Person(String firstName, String lastName, String phone,Address address) {
        this.created = new Date();
        this.lastEdited = created = new Date();
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
    }

    public Person(String firstName, String lastName, String phone, Integer id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.id = id;
    }

    public Person(Integer id, String firstName, String lastName, String phone, Address address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
    }

    public Person fromDTO(PersonDTO dto, Person ps){
        ps.setFirstName(dto.getfName());
        ps.setLastName(dto.getlName());
        ps.setPhone(dto.getPhone());
        ps.lastEdited = new Date();
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(Date lastEdited) {
        this.lastEdited = lastEdited;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


}