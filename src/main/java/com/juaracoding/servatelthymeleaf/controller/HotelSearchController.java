package com.juaracoding.servatelthymeleaf.controller;

import com.juaracoding.servatelthymeleaf.dto.validasi.ValLoginDTO;
import com.juaracoding.servatelthymeleaf.httpclient.HotelSearchService;
import com.juaracoding.servatelthymeleaf.util.GlobalFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("hotel")
@RequestMapping("/search")
public class HotelSearchController {
    @Autowired
    private HotelSearchService hotelSearchService;

    @GetMapping("")
    public String searchAvailableHotels(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                        @RequestParam(value = "city") String city,
                                        @RequestParam(value = "roomCount") Integer roomCount,
                                        @RequestParam(value = "checkinDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkinDate,
                                        @RequestParam(value = "checkoutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkoutDate,
                                        Model model,
                                        WebRequest webRequest) {
        ResponseEntity<Object> response =null;
        String jwt = GlobalFunction.tokenCheck(model, webRequest);
        try {
            response = hotelSearchService.findAllAvailableHotels("Bearer "+jwt,page,city,checkinDate,checkoutDate, roomCount);
        } catch (Exception e) {
            model.addAttribute("usr", new ValLoginDTO());
            return "auth/login-page";
        }
        // Ambil response body
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();

        if (responseBody != null && (Boolean) responseBody.get("success")) {
            // Ambil "data" dari response
            Map<String, Object> data = (Map<String, Object>) responseBody.get("data");

            // Ambil list "content" dari dalam "data"
            List<Map<String, Object>> hotels = (List<Map<String, Object>>) data.get("content");

            // Kirim data ke UI
//            model.addAttribute("page", page);
            model.addAttribute("hotels", hotels);
            model.addAttribute("message", responseBody.get("message"));
            model.addAttribute("totalPages", data.get("total-page"));
            model.addAttribute("currentPage", page);
            // Menyimpan parameter pencarian untuk dipertahankan dalam pagination
            model.addAttribute("city", city);
            model.addAttribute("checkinDate", checkinDate);
            model.addAttribute("checkoutDate", checkoutDate);
            model.addAttribute("roomCount", roomCount);
        } else {
            model.addAttribute("hotels", List.of());
            model.addAttribute("message", "No hotels found.");
        }

     return "/search/search-results";
    }

    @GetMapping("/{id}")
    public String showHotelAvailableDetails(@PathVariable (value = "id") Long id,
                                            @RequestParam(value = "checkinDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkinDate,
                                            @RequestParam(value = "checkoutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkoutDate,
                                            @RequestParam(value = "roomCount") Integer roomCount,
                                            Model model,
                                            WebRequest webRequest) {

        ResponseEntity<Object> response =null;
        String jwt = GlobalFunction.tokenCheck(model, webRequest);
        try {
            response = hotelSearchService.findAvailableHotelById("Bearer "+jwt,id,checkinDate, checkoutDate, roomCount);
        } catch (Exception e) {
            model.addAttribute("usr", new ValLoginDTO());
            return "auth/login-page";
        }
        // Ambil response body
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();

        if (responseBody != null && (Boolean) responseBody.get("success")) {
            // Ambil "data" dari response
            Map<String, Object> hotel = (Map<String, Object>) responseBody.get("data");
            Map<String, String> address = (Map<String, String>) hotel.get("address");
            String city = address.get("city");

            Long durationDays = ChronoUnit.DAYS.between(checkinDate, checkoutDate);

            // Kirim data ke UI
//            model.addAttribute("page", page);
            model.addAttribute("hotel", hotel);
            model.addAttribute("durationDays", durationDays);
            // Menyimpan parameter pencarian untuk dipertahankan dalam pagination
            model.addAttribute("checkinDate", checkinDate);
            model.addAttribute("checkoutDate", checkoutDate);
            model.addAttribute("roomCount", roomCount);
            model.addAttribute("city", city);
        } else {
            model.addAttribute("message", "No hotels found.");
        }
        return "/search/search-details";
    }
}
