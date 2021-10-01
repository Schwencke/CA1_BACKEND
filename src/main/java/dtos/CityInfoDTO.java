package dtos;

import entities.CityInfo;

public class CityInfoDTO {
    private Integer id;
    private String zipCode;
    private String city;

    public CityInfoDTO(CityInfo cityInfo) {
        this.id = cityInfo.getId();
        this.zipCode = cityInfo.getZipCode();
        this.city = cityInfo.getZipCode();
    }
}
