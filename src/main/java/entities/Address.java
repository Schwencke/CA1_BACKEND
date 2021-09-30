package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "address")
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "street", nullable = false)
    private String street;
    @Column(name = "zip", nullable = false)
    private String zip;
    @Column(name = "city", nullable = false)
    private String city;
    @OneToMany(mappedBy = "address", cascade = CascadeType.PERSIST)
    private List<Person> persons = new ArrayList<>();

    public Address() {
    }

    public Address(String street, String zip, String city) {
        this.street = street;
        this.zip = zip;
        this.city = city;
    }

    public void addPerson(Person p) {
        this.persons.add(p);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}