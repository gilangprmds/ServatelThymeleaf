package com.juaracoding.servatelthymeleaf.dto.validasi;

import com.juaracoding.servatelthymeleaf.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomSelectionDTO {

    private RoomType roomType;
    private Integer roomCount;
}
