package com.juaracoding.servatelthymeleaf.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juaracoding.servatelthymeleaf.dto.response.RespHotelDTO;
import com.juaracoding.servatelthymeleaf.dto.validasi.HotelRegistrationDTO;
import com.juaracoding.servatelthymeleaf.dto.validasi.ValLoginDTO;
import com.juaracoding.servatelthymeleaf.httpclient.HotelService;
import com.juaracoding.servatelthymeleaf.util.GlobalFunction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.hibernate.cache.RegionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @GetMapping("/list-hotels")
    public String listManagerHotels(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             Model model,
                             WebRequest webRequest) {
        ResponseEntity<Object> response = null;
        String jwt = GlobalFunction.tokenCheck(model, webRequest);
        try {
            response = hotelService.findAllByManagerId("Bearer " + jwt, page);
        } catch (Exception e) {
            model.addAttribute("usr", new ValLoginDTO());
            return "auth/login-page";
        }
        // Ambil response body
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();

        if (responseBody != null && responseBody.get("data") != "") {
            // Ambil "data" dari response
            Map<String, Object> data = (Map<String, Object>) responseBody.get("data");

            // Ambil list "content" dari dalam "data"
            List<Map<String, Object>> hotels = (List<Map<String, Object>>) data.get("content");
            Object menuNavBar =webRequest.getAttribute("MENU_NAVBAR", 1);
            Object username = webRequest.getAttribute("USR_NAME", 1);
            // Kirim data ke UI
//            model.addAttribute("page", page);
            model.addAttribute("hotel", new HotelRegistrationDTO());
            model.addAttribute("hotels", hotels);
            model.addAttribute("message", responseBody.get("message"));
            model.addAttribute("totalPages", data.get("total-page"));
            model.addAttribute("currentPage", page);
            /** untuk di set di page home setelah login*/
            model.addAttribute("MENU_NAVBAR",menuNavBar);
            model.addAttribute("USR_NAME",username);
        } else {
            model.addAttribute("hotel", new HotelRegistrationDTO());
            model.addAttribute("hotels", List.of());
            model.addAttribute("message", "No hotels found.");
        }
        return "manager/list-hotel";
    }
    @GetMapping("/{id}")
    public String showHotelDetails(@PathVariable (value = "id") Long id,
                                            Model model,
                                            WebRequest webRequest) {

        ResponseEntity<Object> response =null;
        String jwt = GlobalFunction.tokenCheck(model, webRequest);
        try {
            response = hotelService.findById("Bearer " + jwt, id);
//            response = hotelSearchService.findAvailableHotelById("Bearer "+jwt,id,checkinDate, checkoutDate, roomCount);
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

//            Long durationDays = ChronoUnit.DAYS.between(checkinDate, checkoutDate);

            // Kirim data ke UI
//            model.addAttribute("page", page);
            model.addAttribute("hotel", hotel);
//            model.addAttribute("durationDays", durationDays);
            // Menyimpan parameter pencarian untuk dipertahankan dalam pagination
//            model.addAttribute("checkinDate", checkinDate);
//            model.addAttribute("checkoutDate", checkoutDate);
//            model.addAttribute("roomCount", roomCount);
            model.addAttribute("city", city);
        } else {
            model.addAttribute("message", "No hotels found.");
        }
        Object menuNavBar =webRequest.getAttribute("MENU_NAVBAR", 1);
        Object username = webRequest.getAttribute("USR_NAME", 1);
        model.addAttribute("MENU_NAVBAR",menuNavBar);
        model.addAttribute("USR_NAME",username);
        return "/manager/hotel-details";
    }

    @GetMapping("/hotels")
    public String listAllHotels(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                    Model model,
                                    WebRequest webRequest) {
        ResponseEntity<Object> response = null;
        String jwt = GlobalFunction.tokenCheck(model, webRequest);
        try {
            response = hotelService.findAll("Bearer " + jwt, page);
        } catch (Exception e) {
            model.addAttribute("usr", new ValLoginDTO());
            return "auth/login-page";
        }
        // Ambil response body
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();

        if (responseBody != null && responseBody.get("data") != "") {
            // Ambil "data" dari response
            Map<String, Object> data = (Map<String, Object>) responseBody.get("data");

            // Ambil list "content" dari dalam "data"
            List<Map<String, Object>> hotels = (List<Map<String, Object>>) data.get("content");

            // Kirim data ke UI
//            model.addAttribute("page", page);
            model.addAttribute("hotel", new HotelRegistrationDTO());
            model.addAttribute("hotels", hotels);
            model.addAttribute("message", responseBody.get("message"));
            model.addAttribute("totalPages", data.get("total-page"));
            model.addAttribute("currentPage", page);
        } else {
            model.addAttribute("hotel", new HotelRegistrationDTO());
            model.addAttribute("hotels", List.of());
            model.addAttribute("message", "No hotels found.");
        }
        Object menuNavBar =webRequest.getAttribute("MENU_NAVBAR", 1);
        Object username = webRequest.getAttribute("USR_NAME", 1);
        model.addAttribute("MENU_NAVBAR",menuNavBar);
        model.addAttribute("USR_NAME",username);
        return "admin/list-all-hotels";
    }


    @GetMapping("/save")
    public String showAddHotelForm(Model model,
                                   WebRequest webRequest) {
        model.addAttribute("hotel", new HotelRegistrationDTO());
        Object menuNavBar =webRequest.getAttribute("MENU_NAVBAR", 1);
        Object username = webRequest.getAttribute("USR_NAME", 1);
        model.addAttribute("MENU_NAVBAR",menuNavBar);
        model.addAttribute("USR_NAME",username);
        return "manager/add-hotel-withimage";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute(name = "hotel") HotelRegistrationDTO hotelRegistrationDTO,
                       @RequestPart("hotelImages") List<MultipartFile> hotelImages,
                       @RequestPart("roomsImages") List<MultipartFile> roomsImages,
                       BindingResult result,
                       Model model,
                       WebRequest webRequest) {
        if (result.hasErrors()) {
            model.addAttribute("hotel", hotelRegistrationDTO);
            return "manager/list-hotel";
        }
        ResponseEntity<Object> response = null;
        String jwt = GlobalFunction.tokenCheck(model,webRequest);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String hotelJson = objectMapper.writeValueAsString(hotelRegistrationDTO);
            response = hotelService.save("Bearer "+jwt,hotelJson, hotelImages, roomsImages);
        } catch (Exception e) {
            model.addAttribute("usr", new ValLoginDTO());
            return "auth/login-page";
        }
        model.addAttribute("message", "Hotel (" + hotelRegistrationDTO.getName() + ") added successfully");
        return "redirect:/hotel/list-hotels";
    }


}
