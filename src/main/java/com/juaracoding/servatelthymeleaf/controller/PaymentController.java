package com.juaracoding.servatelthymeleaf.controller;

import com.juaracoding.servatelthymeleaf.dto.validasi.ValLoginDTO;
import com.juaracoding.servatelthymeleaf.httpclient.PaymentService;
import com.juaracoding.servatelthymeleaf.util.GlobalFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @GetMapping("/create")
    public String createPayment(@RequestParam Long bookingId,
                                @RequestParam String email,
                                @RequestParam Double amount,
                                Model model,
                                WebRequest webRequest) {
        ResponseEntity<Object> response=null;
        String token = null;
        String jwt = GlobalFunction.tokenCheck(model, webRequest);
        try {
            response = paymentService.createPayment("Bearer "+jwt,bookingId,email,amount);
            // Ambil response body
            Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
            if (responseBody != null) {
                // Ambil "data" dari response
//                Map<String, Object> bookingDTO = (Map<String, Object>) responseBody.get("data");

                token = responseBody.get("token").toString();
//                model.addAttribute("bookingDTO", bookingDTO);
            } else {
                model.addAttribute("message", "No hotels found.");
            }
        } catch (Exception e) {
            model.addAttribute("usr", new ValLoginDTO());
            return "auth/login-page";
        }
        return "redirect:https://app.sandbox.midtrans.com/snap/v2/vtweb/" + token;
    }



}
