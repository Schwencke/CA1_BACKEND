package dtos;

import entities.Address;
import entities.Person;

import java.util.Date;
import java.util.List;

public class PersonDTO {
    private Integer id;
    private String fName;
    private String lName;
    private int age;
    private String gender;
    private String phone;
    private String email;
    private String st;
    private String zp;
    private String ct;
    private List<String> hobs;
    private Date created;
    private Date lastEdited;

    public PersonDTO(String fName, String lName) {
        this.fName = fName;
        this.lName = lName;
    }

    public PersonDTO(String fName, String lName, Integer id) {
        this.fName = fName;
        this.lName = lName;
        this.id = id;
    }


//    public PersonDTO(Person ps) {
//        this.fName = ps.getFirstName();
//        this.lName = ps.getLastName();
//        this.st = ps.getAddress().getStreet();
//        this.zp = ps.getAddress().getZip();
//        this.ct = ps.getAddress().getCity();
//        this.id = ps.getId();
//    }

        public PersonDTO(Person ps) {
        this.id = ps.getId();
        this.fName = ps.getFirstName();
        this.lName = ps.getLastName();
        this.age = ps.getAge();
        this.gender = ps.getGender();
        this.phone = ps.getPhone();
        this.email = ps.getEmail();
        this.hobs = ps.getHobbies();
        this.st = ps.getAddress().getStreet();
        this.zp = ps.getAddress().getZip();
        this.ct = ps.getAddress().getCity();
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getHobbies() {
        return hobs;
    }

    public void setHobbies(List<String> hobs) {
        this.hobs = hobs;
    }

    public String getStreet() {
        return st;
    }

    public void setStreet(String srt) {
        this.st = srt;
    }

    public String getZip() {
        return zp;
    }

    public void setZip(String zp) {
        this.zp = zp;
    }

    public String getCity() {
        return ct;
    }

    public void setCity(String ct) {
        this.ct = ct;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(Date lastEdited) {
        this.lastEdited = lastEdited;
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "id=" + id +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", srt='" + st + '\'' +
                ", zp='" + zp + '\'' +
                ", ct='" + ct + '\'' +
                ", created=" + created +
                ", lastEdited=" + lastEdited +
                '}';
    }
}
