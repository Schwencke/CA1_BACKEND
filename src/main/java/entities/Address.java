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

    @OneToMany(mappedBy = "address", cascade = CascadeType.PERSIST)
    private List<Person> persons = new ArrayList<>();

    @ManyToOne
    private CityInfo cityInfo;

    public Address() {
    }

    public Address(String street, List<Person> persons, CityInfo cityInfo) {
        this.street = street;
        this.persons = persons;
        this.cityInfo = cityInfo;
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
}