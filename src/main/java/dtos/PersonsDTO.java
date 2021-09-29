package dtos;

import entities.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonsDTO {

    List<PersonDTO> all = new ArrayList();

    public PersonsDTO(List<Person> personEntities) {
        personEntities.forEach((p) -> {
            all.add(new PersonDTO(p));
        });
    }

    public List<PersonDTO> getAll() {
        return all;
    }
//    private String fName;
//    private String lName;
//    private String phone;
//    private Integer id;
//
//    public PersonsDTO(String fName, String lName, String phone, Integer id) {
//        this.fName = fName;
//        this.lName = lName;
//        this.phone = phone;
//        this.id = id;
//    }
//
//    public PersonsDTO(Person ps) {
//        if (ps.getId() != null)
//            this.id = ps.getId();
//        this.fName = ps.getFirstName();
//        this.lName = ps.getLastName();
//        this.phone = ps.getPhone();
//    }
//
//
//
//
//    public static List<PersonsDTO> getDtos(List<Person> pes){
//        List<PersonsDTO> psdto = new ArrayList();
//        pes.forEach(ps->psdto.add(new PersonsDTO(ps)));
//        return psdto;
//    }
//
//    public String getfName() {
//        return fName;
//    }
//
//    public void setfName(String fName) {
//        this.fName = fName;
//    }
//
//    public String getlName() {
//        return lName;
//    }
//
//    public void setlName(String lName) {
//        this.lName = lName;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    @Override
//    public String toString() {
//        return "PersonsDTO{" +
//                "fName='" + fName + '\'' +
//                ", lName='" + lName + '\'' +
//                ", phone='" + phone + '\'' +
//                ", id=" + id +
//                '}';
//    }
}
