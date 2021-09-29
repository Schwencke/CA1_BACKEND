package entities;

import dtos.PersonDTO;
import javax.persistence.*;
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
    private int age;
    private String gender;
    private String phone;
    private String email;
    private List<String> hobbys;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastEdited;
    @ManyToOne
    private Address address;

    public Person() {
    }


    public Person(String firstName, String lastName, int age, String gender, String phone, String email, List<String> hobbys) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.hobbys = hobbys;
    }

    public Person(String firstName, String lastName, int age, String gender, String email, List<String> hobbys, Date created, Date lastEdited, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.hobbys = hobbys;
        this.created = created;
        this.lastEdited = lastEdited;
        this.address = address;
    }

    public Person(String firstName, String lastName, Integer id, Date created) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.created = created;
    }

    public Person(String firstName, String lastName) {
        this.created = new Date();
        this.lastEdited = created = new Date();
        this.firstName = firstName;
        this.lastName = lastName;

    }
    public Person(String firstName, String lastName,Address address) {
        this.created = new Date();
        this.lastEdited = created = new Date();
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public Person(String firstName, String lastName, Integer id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    public Person(Integer id, String firstName, String lastName, Address address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }



    public Person fromDTO(PersonDTO dto, Person ps){
        ps.setFirstName(dto.getfName());
        ps.setLastName(dto.getlName());
        ps.lastEdited = new Date();
        return ps;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getHobbys() {
        return hobbys;
    }

    public void setHobbys(List<String> hobbys) {
        this.hobbys = hobbys;
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