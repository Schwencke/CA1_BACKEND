package dtos;

import entities.Address;

public class AddressDTO {
    private Integer id;
    private String street;
    private String additionalInfo;



    public AddressDTO(String street, String additionalInfo) {
        this.street = street;
        this.additionalInfo = additionalInfo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public static AddressDTO getDTO(Address address) {
        return new AddressDTO(address.getStreet(), address.getAdditionalInfo());
    }
}
