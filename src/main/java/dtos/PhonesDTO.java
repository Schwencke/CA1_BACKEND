package dtos;

import entities.Phone;

import java.util.ArrayList;
import java.util.List;

public class PhonesDTO {

    private List<PhoneDTO> all = new ArrayList<>();

    public PhonesDTO(List<Phone> phoneEntities) {
        phoneEntities.forEach((phone -> {
            all.add(new PhoneDTO(phone));
        }));
    }

    public List<PhoneDTO> getAll() {
        return all;
    }
}
