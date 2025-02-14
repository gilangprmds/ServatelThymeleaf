package com.juaracoding.servatelthymeleaf.httpclient;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient(name = "hotel-search-services",url = "http://localhost:8080/search")
public interface HotelSearchService {

//    @GetMapping("")
//    public ResponseEntity<Object> findAllAvailableHotels(@RequestParam(value = "city") String city,
//                                                         @RequestParam(value = "checkinDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkinDate,
//                                                         @RequestParam(value = "checkoutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkoutDate);
    @GetMapping("")
    public ResponseEntity<Object> findAllAvailableHotels(@RequestParam(value = "page") Integer page,
                                                         @RequestParam(value = "city") String city,
                                                         @RequestParam(value = "checkinDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkinDate,
                                                         @RequestParam(value = "checkoutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkoutDate,
                                                         @RequestParam(value = "roomCount") Integer roomCount);

    @GetMapping("/{id}")
    public ResponseEntity<Object> findAvailableHotelById(@PathVariable (value = "id") Long id,
                                                         @RequestParam(value = "checkinDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkinDate,
                                                         @RequestParam(value = "checkoutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkoutDate,
                                                         @RequestParam(value = "roomCount") Integer roomCount);
}
