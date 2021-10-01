package dtos;

import entities.Hobby;

import java.util.ArrayList;
import java.util.List;

public class HobbysDTO {
    private List<HobbyDTO> all = new ArrayList<>();

    public HobbysDTO(List<Hobby> hobbyEntities) {
        hobbyEntities.forEach((hobby) -> {
            all.add(new HobbyDTO(hobby));
        });
    }

    public List<HobbyDTO> getAll() {
        return all;
    }
}
