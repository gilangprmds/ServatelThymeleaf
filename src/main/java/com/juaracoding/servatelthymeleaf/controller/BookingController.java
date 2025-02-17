package com.juaracoding.servatelthymeleaf.controller;

import com.juaracoding.servatelthymeleaf.dto.validasi.BookingRequestDTO;
import com.juaracoding.servatelthymeleaf.dto.validasi.ValLoginDTO;
import com.juaracoding.servatelthymeleaf.httpclient.BookingService;
import com.juaracoding.servatelthymeleaf.util.GlobalFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

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
    public String confirmBooking(@ModelAttribute BookingRequestDTO bookingRequestDTO,
                                 Model model,
                                 WebRequest webRequest){
        ResponseEntity<Object> response=null;
        String jwt = GlobalFunction.tokenCheck(model, webRequest);
        try {
            response = bookingService.createBooking("Bearer "+jwt,bookingRequestDTO);
            // Ambil response body
            Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
            if (responseBody != null && (Boolean) responseBody.get("success")) {
                // Ambil "data" dari response
                Map<String, Object> bookingDTO = (Map<String, Object>) responseBody.get("data");


                model.addAttribute("bookingDTO", bookingDTO);
            } else {
                model.addAttribute("message", "No hotels found.");
            }
        } catch (Exception e) {
            model.addAttribute("usr", new ValLoginDTO());
            return "auth/login-page";
        }
        return "booking/payment";
    }

    @GetMapping("/my-bookings")
    public String showMyBookings(Model model, WebRequest webRequest) {
        ResponseEntity<Object> response = null;
        String jwt = GlobalFunction.tokenCheck(model, webRequest);
        try{
            response = bookingService.findAllBookingsByCustomerId("Bearer "+jwt);
        } catch (Exception e) {
            model.addAttribute("usr", new ValLoginDTO());
            return "auth/login-page";
        }
        // Ambil response body
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        if (responseBody != null && (Boolean) responseBody.get("success")) {
            // Ambil "data" dari response
//            Map<String, Object> bookings = (Map<String, Object>) responseBody.get("data");
            List<Map<String, Object>> bookings = (List<Map<String, Object>>) responseBody.get("data");


            // Kirim data ke UI
//            model.addAttribute("page", page);
            model.addAttribute("bookings", bookings);
        } else {
            model.addAttribute("message", "No hotels found.");
        }

        return "customer/my-bookings";
    }
    @GetMapping("/my-booking/{id}")
    public String showBookingDetails(@PathVariable(value = "id") Long id, Model model,
                                     WebRequest webRequest) {
        ResponseEntity<Object> response = null;
        String jwt = GlobalFunction.tokenCheck(model, webRequest);
        try{
            response = bookingService.findBookingById("Bearer "+jwt,id);
        } catch (Exception e) {
            model.addAttribute("usr", new ValLoginDTO());
            return "auth/login-page";
        }
        // Ambil response body
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        if (responseBody != null && (Boolean) responseBody.get("success")) {
            // Ambil "data" dari response
            Map<String, Object> bookingDTO = (Map<String, Object>) responseBody.get("data");

            // Kirim data ke UI
//            model.addAttribute("page", page);
            model.addAttribute("bookingDTO", bookingDTO);
        } else {
            model.addAttribute("message", "No hotels found.");
        }
        return "customer/booking-details";
    }

}
