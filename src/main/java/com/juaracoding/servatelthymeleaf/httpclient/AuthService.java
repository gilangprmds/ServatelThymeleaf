package com.juaracoding.servatelthymeleaf.httpclient;

import com.juaracoding.servatelthymeleaf.dto.validasi.*;
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


    @PostMapping("/forgot-password")
    public ResponseEntity<Object> forgotPassword(@RequestBody ValForgotPasswordDTO valForgotPasswordDTO);

    @PostMapping("/checking-password")
    public ResponseEntity<Object> checkingOtp(@RequestBody ValOtpDTO valOtpDTO);

    @PostMapping("/change-password")
    public ResponseEntity<Object> changePassword(@RequestBody ValChangePasswordDTO valChangePasswordDTO);
}