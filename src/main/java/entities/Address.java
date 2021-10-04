package entities;

import com.google.gson.annotations.Expose;
import dtos.AddressDTO;
import dtos.PersonDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "address")
@Entity
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "additional_info", nullable = false)
    private String additionalInfo;

    @OneToMany(mappedBy = "address", cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
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
    // added during testing - refactor?
    public Address(String street, CityInfo cityInfo) {
        this.street = street;
        this.cityInfo = cityInfo;
    }

    public Address(AddressDTO a) {
        if (a.getId() !=null){
            this.id = a.getId();
        }
        this.street = a.getStreet();
        this.additionalInfo = a.getAdditionalInfo();
        this.cityInfo = new CityInfo(a.getZipCode(), a.getCity());
        this.persons = new ArrayList<>();
    }

    public Address(String street, String additionalInfo, CityInfo cityInfo) {
        this.street = street;
        this.additionalInfo = additionalInfo;
        this.cityInfo = cityInfo;
    }

    public Address(String street, String additionalInfo) {
        this.street = street;
        this.additionalInfo = additionalInfo;
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

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public CityInfo getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfo cityInfo) {
        this.cityInfo = cityInfo;
    }


}