package com.juaracoding.servatelthymeleaf.httpclient;

import com.juaracoding.servatelthymeleaf.dto.validasi.ValLoginDTO;
import com.juaracoding.servatelthymeleaf.dto.validasi.ValRegisDTO;
import com.juaracoding.servatelthymeleaf.dto.validasi.ValVerifyRegisDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@FeignClient(name = "auth-service",  url = "localhost:8080/auth")
public interface AuthService {

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody ValLoginDTO valLoginDTO);

    @PostMapping("/regis")
    public ResponseEntity<Object> register(@RequestBody ValRegisDTO regisDTO);

    @PostMapping("/verify-regis")
    public ResponseEntity<Object> verifyRegister(@RequestBody ValVerifyRegisDTO valVerifyRegisDTO);


}