package dtos;

import entities.Address;
import entities.CityInfo;

public class CityInfoDTO {
    private String zipCode;
    private String city;

    public CityInfoDTO(String zipCode, String city) {
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

    public static CityInfoDTO getDTO(CityInfo cityInfo) {
        return new CityInfoDTO(cityInfo.getZipCode(), cityInfo.getCity());
    }
}
