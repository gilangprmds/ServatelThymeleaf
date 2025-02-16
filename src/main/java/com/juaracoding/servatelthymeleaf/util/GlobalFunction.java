package com.juaracoding.servatelthymeleaf.util;

import com.juaracoding.servatelthymeleaf.dto.validasi.ValLoginDTO;
import org.springframework.ui.Model;
import org.springframework.web.context.request.WebRequest;

public class GlobalFunction {


    public static String tokenCheck(Model model, WebRequest request){
        Object tokenJwt = request.getAttribute("JWT",1);
        if(tokenJwt == null){
            model.addAttribute("authProblem","Belum melakukan Login");
            model.addAttribute("usr", new ValLoginDTO());
            return "auth/login-page";
        }
        return tokenJwt.toString();
    }
}
