package com.juaracoding.servatelthymeleaf.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "address-services",url = "http://localhost:8080/address")
public interface AddressService {

    @GetMapping("/city")
    public ResponseEntity<Object> findCity(@RequestParam(value = "city", required = false) String city);
}
