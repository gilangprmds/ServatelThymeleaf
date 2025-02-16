package com.juaracoding.servatelthymeleaf.httpclient;

import com.juaracoding.servatelthymeleaf.dto.validasi.BookingRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "booking-services",url = "http://localhost:8080/booking")
public interface BookingService {

    @PostMapping("/create")
    public ResponseEntity<Object> createBooking(@RequestHeader("Authorization") String token,
                                                @RequestBody BookingRequestDTO bookingRequestDTO);

    @GetMapping("/my-bookings")
    public ResponseEntity<Object> findAllBookingsByCustomerId(@RequestHeader("Authorization") String token);

    @GetMapping("/my-booking/{id}")
    public ResponseEntity<Object> findBookingById(@RequestHeader("Authorization") String token,
                                                  @PathVariable("id") Long id);
}
