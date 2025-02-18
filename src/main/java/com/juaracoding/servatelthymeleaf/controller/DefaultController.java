package com.juaracoding.servatelthymeleaf.controller;

import com.juaracoding.servatelthymeleaf.dto.validasi.ValLoginDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

@Controller
public class DefaultController {

    @GetMapping("/")
    public String homePage(@RequestParam(value = "transaction_status", required = false) String transaction_status,
                           Model model,
                           WebRequest webRequest) {
        model.addAttribute("MENU_NAVBAR",webRequest.getAttribute("MENU_NAVBAR",1));
        model.addAttribute("USR_NAME",webRequest.getAttribute("USR_NAME",1));
        model.addAttribute("transactionStatus", transaction_status);
        return "home-new2";
    }



}
