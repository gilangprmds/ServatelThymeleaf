package com.juaracoding.servatelthymeleaf.controller;

import com.juaracoding.servatelthymeleaf.dto.validasi.BookingRequestDTO;
import com.juaracoding.servatelthymeleaf.httpclient.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("hotel")
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    BookingService bookingService;

    @GetMapping("/confirmation")
    public String showBookingConfirmation(
            @ModelAttribute("hotel") Map<String, Object> hotel,
            @RequestParam Integer roomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkinDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkoutDate,
            @RequestParam Integer roomCount,
            Model model) {

        // Ambil data room dari hotel berdasarkan roomId
        List<Map<String, Object>> rooms = (List<Map<String, Object>>) hotel.get("rooms");
        Map<String, Object> selectedRoom = rooms.stream()
                .filter(room -> room.get("id").equals(roomId))
                .findFirst()
                .orElse(null);
        Long durationDays = ChronoUnit.DAYS.between(checkinDate, checkoutDate);
        // Kirim data ke UI
        model.addAttribute("hotel", hotel);
        model.addAttribute("room", selectedRoom);
        model.addAttribute("checkinDate", checkinDate);
        model.addAttribute("checkoutDate", checkoutDate);
        model.addAttribute("roomCount", roomCount);
        model.addAttribute("durationDays", durationDays);

        return "booking/confirmation-booking";
    }

    @PostMapping("/create")
    public String confirmBooking(@ModelAttribute BookingRequestDTO bookingRequestDTO){
        ResponseEntity<Object> response=null;
        try {
            response = bookingService.createBooking(bookingRequestDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "home";
    }
}
