package dtos;


import entities.Person;
import dtos.PhonesDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
@Schema(name = "Person")
public class PersonDTO {
    @Schema(required = false,name ="id", example ="1", description = "never with POST")
    private Integer id;
    @Schema(required = true,name ="fName",example ="Thomas", description = "Firstname")
    private String fName;
    @Schema(required = true,name ="lName", example ="Overgaard", description = "Lastname")
    private String lName;
    @Schema(required = true, example ="T@T.dk", description = "Email")
    private String email;
    @Schema(required = true, implementation = AddressDTO.class)
    private AddressDTO address;
    @Schema(required = true, implementation = PhoneDTO.class)
    private List<PhoneDTO> phones;
    @Schema(required = true, implementation = HobbyDTO.class)
    private List<HobbyDTO> hobbies;

    public PersonDTO(Person ps) {
        if (ps.getId() != null){
            this.id = ps.getId();
        }
        this.fName = ps.getFirstName();
        this.lName = ps.getLastName();
        this.email = ps.getEmail();
        this.phones = PhoneDTO.getDtos(ps.getPhones());
        this.hobbies = HobbyDTO.getDtos(ps.getHobbies());
        this.address = new AddressDTO(ps.getAddress());
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
