package dtos;


import entities.Person;
import dtos.PhonesDTO;
import java.util.List;

public class PersonDTO {
    private Integer id;
    private String fName;
    private String lName;
    private String email;
    private AddressDTO address;
    private List<PhoneDTO> phones;
    private List<HobbyDTO> hobbies;

    public PersonDTO(Person ps) {
        PhonesDTO p = new PhonesDTO(ps.getPhones());
        HobbysDTO h = new HobbysDTO(ps.getHobbies());
        this.id = ps.getId();
        this.fName = ps.getFirstName();
        this.lName = ps.getLastName();
        this.email = ps.getEmail();
        this.phones = p.getAll();
        this.hobbies = h.getAll();
        this.address = AddressDTO.getDTO(ps.getAddress());
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


    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public List<PhoneDTO> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDTO> phones) {
        this.phones = phones;
    }

    public List<HobbyDTO> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<HobbyDTO> hobbies) {
        this.hobbies = hobbies;
    }
}
