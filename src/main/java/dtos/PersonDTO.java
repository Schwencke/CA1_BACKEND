package dtos;

import entities.Address;
import entities.Person;

import java.util.Date;

public class PersonDTO {
    private Integer id;
    private String fName;
    private String lName;
    private int age;
    private String gender;
    private String phone;
    private String email;
    private String srt;
    private String zp;
    private String ct;
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

    public PersonDTO(Person ps) {
        this.fName = ps.getFirstName();
        this.lName = ps.getLastName();
        this.srt = ps.getAddress().getStreet();
        this.zp = ps.getAddress().getZip();
        this.ct = ps.getAddress().getCity();
        this.id = ps.getId();
    }


    public PersonDTO(String fName, String lName, String srt, String zp, String ct) {
        this.fName = fName;
        this.lName = lName;
        this.srt = srt;
        this.zp = zp;
        this.ct = ct;
    }

    public static PersonDTO getDto(Person ps){
        return new PersonDTO(ps.getFirstName(), ps.getLastName());
    }

    public static PersonDTO getDtoWithAddress(Person ps, Address ad){
        return new PersonDTO(ps.getFirstName(), ps.getLastName(), ad.getStreet(), ad.getZip(), ad.getCity());
    }

    public String getSrt() {
        return srt;
    }

    public void setSrt(String srt) {
        this.srt = srt;
    }

    public String getZp() {
        return zp;
    }

    public void setZp(String zp) {
        this.zp = zp;
    }

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
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
                ", srt='" + srt + '\'' +
                ", zp='" + zp + '\'' +
                ", ct='" + ct + '\'' +
                ", created=" + created +
                ", lastEdited=" + lastEdited +
                '}';
    }
}
