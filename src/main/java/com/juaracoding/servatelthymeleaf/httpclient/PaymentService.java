package com.juaracoding.servatelthymeleaf.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient(name = "payment-service", url = "localhost:8080/payment")
public interface PaymentService {

    @PostMapping("/create")
    public ResponseEntity<Object> createPayment(@RequestHeader("Authorization") String token,
                                                @RequestParam Long bookingId,
                                                @RequestParam String email,
                                                @RequestParam Double amount);
}
