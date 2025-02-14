package com.juaracoding.servatelthymeleaf.dto.validasi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
public class HotelSearchDTO {
    private String city;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
