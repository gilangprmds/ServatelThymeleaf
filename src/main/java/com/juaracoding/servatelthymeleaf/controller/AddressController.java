package com.juaracoding.servatelthymeleaf.controller;

import com.juaracoding.servatelthymeleaf.dto.validasi.HotelRegistrationDTO;
import com.juaracoding.servatelthymeleaf.dto.validasi.ValLoginDTO;
import com.juaracoding.servatelthymeleaf.httpclient.AddressService;
import com.juaracoding.servatelthymeleaf.util.GlobalFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping("/city")
    public ResponseEntity<Object> listCity(@RequestParam(value = "city", required = false) String city,
                                           Model model) {
        ResponseEntity<Object> response = null;
        try {
            response = addressService.findCity(city);
            // Ambil response body
            Map<String, Object> responseBody = (Map<String, Object>) response.getBody();

            if (responseBody != null && responseBody.get("data") != "") {
                // Ambil "data" dari response
                List<Map<String, Object>> cityDTO = (List<Map<String, Object>>) responseBody.get("data");
                Map<String, Object> result = new HashMap<>();
                result.put("data", cityDTO);
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.ok(Collections.emptyList());
            }
        } catch (Exception e) {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }
}
