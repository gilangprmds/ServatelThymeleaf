package com.juaracoding.servatelthymeleaf.httpclient;

import com.juaracoding.servatelthymeleaf.dto.validasi.BookingRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "booking-services",url = "http://localhost:8080/booking")
public interface BookingService {

    @PostMapping("/create")
    public ResponseEntity<Object> createBooking(@RequestBody BookingRequestDTO bookingRequestDTO);
}
