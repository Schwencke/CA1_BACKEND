package dtos;

import entities.Hobby;

public class HobbyDTO {
    private Integer id;
    private String name;
    private String description;

    public HobbyDTO(Hobby hobby) {
        this.id = hobby.getId();
        this.name = hobby.getName();
        this.description = hobby.getDescription();
    }
}
