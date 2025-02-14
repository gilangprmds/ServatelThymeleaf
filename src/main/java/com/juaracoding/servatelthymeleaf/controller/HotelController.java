package com.juaracoding.servatelthymeleaf.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juaracoding.servatelthymeleaf.dto.response.RespHotelDTO;
import com.juaracoding.servatelthymeleaf.dto.validasi.HotelRegistrationDTO;
import com.juaracoding.servatelthymeleaf.httpclient.HotelService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.hibernate.cache.RegionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @GetMapping("/list-hotels")
    public String listHotels(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             Model model) {
        ResponseEntity<Object> response =null;
        try {
            response = hotelService.findAllByManagerId(page,2L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // Ambil response body
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();

        if (responseBody != null && responseBody.get("data")!="") {
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
        return "manager/list-hotel";
    }

    @GetMapping("/save")
    public String showAddHotelForm(Model model) {
        model.addAttribute("hotel", new HotelRegistrationDTO());
        return "manager/add-hotel-withimage";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute(name = "hotel") HotelRegistrationDTO hotelRegistrationDTO,
                       @RequestPart("hotelImages") List<MultipartFile> hotelImages,
                       BindingResult result,
                       Model model) {
        if (result.hasErrors()) {
            model.addAttribute("hotel", hotelRegistrationDTO);
            return "manager/list-hotel";
        }
        ResponseEntity<Object> response=null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String hotelJson = objectMapper.writeValueAsString(hotelRegistrationDTO);
            response = hotelService.save(hotelJson, hotelImages);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("message", "Hotel (" + hotelRegistrationDTO.getName() + ") added successfully");
        return "redirect:/hotel/list-hotels";
    }


//    @PostMapping("/save")
//    public String saveHotel(@ModelAttribute HotelRegistrationDTO hotelDTO, RedirectAttributes redirectAttributes) {
//        ResponseEntity<Object> response = hotelService.saveHotel(
//                hotelDTO.getName(),
////                hotelDTO.getAddress(),
//                hotelDTO.getDescription(),
//                hotelDTO.getHotelImages()
////                hotelDTO.getRooms()
//        );
//
//        redirectAttributes.addFlashAttribute("message", "Hotel successfully added!");
//        return "redirect:/hotel/list-hotels";
//    }
}
