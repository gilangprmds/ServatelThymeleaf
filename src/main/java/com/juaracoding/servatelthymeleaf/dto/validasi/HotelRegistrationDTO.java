package com.juaracoding.servatelthymeleaf.dto.validasi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelRegistrationDTO {
    private String name;

    private AddressDTO address;

    private List<RoomDTO> rooms = new ArrayList<>();

    private String description;
}
