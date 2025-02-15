package com.juaracoding.servatelthymeleaf.dto.validasi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestDTO {
    private Long hotelId;
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private Long durationDays;
    private RoomSelectionDTO roomSelections;
    private BigDecimal totalPrice;
}
