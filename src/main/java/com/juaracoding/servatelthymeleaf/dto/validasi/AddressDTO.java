package com.juaracoding.servatelthymeleaf.dto.validasi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private String streetName;
    private String city;
    private String country;
}
