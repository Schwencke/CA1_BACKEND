package dtos;

import entities.Address;
import entities.Hobby;
import entities.Phone;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;
@Schema(name = "Phone")
public class PhoneDTO {
    @Schema(required = false, example ="1", description = "Phone ID, Never sent with POST")
    private int id;
    @Schema(required = true, example ="60837082", description = "Phone number")
    private String number;
    @Schema(required = true, example ="Mobil", description = "Type or area related to phone number")
    private String description;

    public PhoneDTO(String number, String description) {
        this.number = number;
        this.description = description;
    }

    public PhoneDTO(Phone phone) {
        if (phone.getId() != null){
            this.id = phone.getId();
        }
        this.number = phone.getNumber();
        this.description = phone.getDescription();
    }

    public static List<PhoneDTO> getDtos(List<Phone> phones) {
        List<PhoneDTO> pDTO = new ArrayList();
        phones.forEach(phone -> pDTO.add(new PhoneDTO(phone)));
        return pDTO;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
