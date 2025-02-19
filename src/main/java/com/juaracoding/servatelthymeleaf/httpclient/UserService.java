package com.juaracoding.servatelthymeleaf.httpclient;

import com.juaracoding.servatelthymeleaf.dto.validasi.ValForgotPasswordDTO;
import com.juaracoding.servatelthymeleaf.dto.validasi.ValUserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service", url = "localhost:8080/user")
public interface UserService {

    @GetMapping("")
    public ResponseEntity<Object> findAll(@RequestHeader("Authorization") String token);

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@RequestHeader("Authorization") String token,
                                           @PathVariable(value = "id") Long id);

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestHeader("Authorization") String token,
                                         @PathVariable(value = "id") Long id,
                                         @RequestBody ValUserDTO userDTO);

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@RequestHeader("Authorization") String token,
                                         @PathVariable(value = "id") Long id);

}
