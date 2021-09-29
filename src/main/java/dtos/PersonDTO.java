package dtos;

import entities.Address;
import entities.Person;

import java.util.Date;

public class PersonDTO {
    private String fName;
    private String lName;
    private String phone;
    private Integer id;
    private Date created;
    private Date lastEdited;
    private String srt;
    private String zp;
    private String ct;

    public PersonDTO(String fName, String lName, String phone) {
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
    }

    public PersonDTO(String fName, String lName, String phone, Integer id) {
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
        this.id = id;
    }

    public PersonDTO(Person ps) {
        this.fName = ps.getFirstName();
        this.lName = ps.getLastName();
        this.phone = ps.getPhone();
        this.srt = ps.getAddress().getStreet();
        this.zp = ps.getAddress().getZip();
        this.ct = ps.getAddress().getCity();
        this.id = ps.getId();
    }


    public PersonDTO(String fName, String lName, String phone, String srt, String zp, String ct) {
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
        this.srt = srt;
        this.zp = zp;
        this.ct = ct;
    }

    public static PersonDTO getDto(Person ps){
        return new PersonDTO(ps.getFirstName(), ps.getLastName(), ps.getPhone());
    }

    public static PersonDTO getDtoWithAddress(Person ps, Address ad){
        return new PersonDTO(ps.getFirstName(), ps.getLastName(), ps.getPhone(), ad.getStreet(), ad.getZip(), ad.getCity());
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
                "fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", phone='" + phone + '\'' +
                ", id=" + id +
                ", srt='" + srt + '\'' +
                ", zp='" + zp + '\'' +
                ", ct='" + ct + '\'' +
                '}';
    }
}
