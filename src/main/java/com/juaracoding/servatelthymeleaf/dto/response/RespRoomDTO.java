package com.juaracoding.servatelthymeleaf.dto.response;

import com.juaracoding.servatelthymeleaf.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespRoomDTO {
    private Long id;
    private RoomType roomType;
    private Integer roomCount;
    private Double pricePerNight;
}
