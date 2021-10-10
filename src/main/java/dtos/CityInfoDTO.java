package dtos;

import entities.Address;
import entities.CityInfo;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "City info")
public class CityInfoDTO {
    @Schema(required = true, example ="3700", description = "Any valid danish zipcode, as string")
    private String zipCode;
    @Schema(required = true, example ="Rønne", description = "Any valid danish city matching the corresponding zipcode, as string")
    private String city;

    public CityInfoDTO(CityInfo cityInfo) {
        this.zipCode = cityInfo.getZipCode();
        this.city = cityInfo.getCity();
    }

    public CityInfoDTO(String zipCode, String city){
        this.zipCode = zipCode;
        this.city = city;
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
