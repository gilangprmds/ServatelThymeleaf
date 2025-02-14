package com.juaracoding.servatelthymeleaf.dto.validasi;

import com.juaracoding.servatelthymeleaf.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    private HotelRegistrationDTO hotel;
    private RoomType roomType;
    private Integer roomCount;
    private Double pricePerNight;
    private List<MultipartFile> roomImages;
}
