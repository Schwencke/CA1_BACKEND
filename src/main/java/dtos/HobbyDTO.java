package dtos;


import entities.Address;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;
@Schema(name = "Hobby")
public class HobbyDTO {
    @Schema(required = true, example ="1", description = "must match prefixed valid ids")
    private int id;
    @Schema(required = true, example ="3D-udskrivning", description = "must match prefixed valid hobby name")
    private String name;
    @Schema(required = true, example ="https://en.wikipedia.org/wiki/3D_printing", description = "must match prefixed valid hobby description")
    private String description;

    public HobbyDTO(Hobby hobby) {
        this.id = hobby.getId();
        this.name = hobby.getName();
        this.description = hobby.getDescription();
    }

    public static List<HobbyDTO> getDtos(List<Hobby> hobbys) {
        List<HobbyDTO> pDTO = new ArrayList();
        hobbys.forEach(hobby -> pDTO.add(new HobbyDTO(hobby)));
        return pDTO;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
