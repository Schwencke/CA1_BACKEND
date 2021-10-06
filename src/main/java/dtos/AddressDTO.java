package dtos;

import entities.Address;
import entities.CityInfo;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Address")
public class AddressDTO {
    @Schema(required = false, example = "1", description = "Person ID, never supplied with POST")
    private Integer id;

    @Schema(required = true, example = "Ternevangen", description = "Street name")
    private String street;

    @Schema(required = true, example = "2", description = "This field is meant for house number")
    private String additionalInfo;

    @Schema(required = true, example = "3700", description = "Zipcode")
    private String zipCode;

    @Schema(required = true, example = "Rønne", description = "City")
    private String city;


    public AddressDTO(Address address) {
        if (address.getId() != null){
            this.id = address.getId();
        }
    this.street = address.getStreet();
    this.additionalInfo = address.getAdditionalInfo();
    this.zipCode = address.getCityInfo().getZipCode();
    this.city = address.getCityInfo().getCity();
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
