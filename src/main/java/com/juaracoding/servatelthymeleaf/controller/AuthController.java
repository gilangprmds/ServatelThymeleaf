package com.juaracoding.servatelthymeleaf.controller;

import com.juaracoding.servatelthymeleaf.dto.validasi.ValLoginDTO;
import com.juaracoding.servatelthymeleaf.dto.validasi.ValRegisDTO;
import com.juaracoding.servatelthymeleaf.dto.validasi.ValVerifyRegisDTO;
import com.juaracoding.servatelthymeleaf.httpclient.AuthService;
import com.juaracoding.servatelthymeleaf.util.GenerateStringMenu;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @GetMapping("")
    public String login(Model model) {
        model.addAttribute("usr", new ValLoginDTO());
        return "/auth/login-page";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("usr")
                        @Valid ValLoginDTO valLoginDTO,
                        BindingResult result,
                        Model model,
                        WebRequest webRequest){
        if(result.hasErrors()){
            return "auth/login-page";
        }

//        valLoginDTO.setPassword(new String(Base64.decode(valLoginDTO.getPassword())));
        ResponseEntity<Object> response = null;
        String menuNavBar = "";
        String tokenJwt = "";
        String urlImg ="";
        try{
            response = authService.login(valLoginDTO);
            Map<String,Object> map = (Map<String, Object>) response.getBody();

            List<Map<String,Object>> lt = (List<Map<String, Object>>) map.get("menu");
            menuNavBar = new GenerateStringMenu().stringMenu(lt);
            tokenJwt = map.get("token").toString();
        }catch (Exception e){

            model.addAttribute("usr",new ValLoginDTO());
            result.addError(new ObjectError("globalError",e.getMessage()==null?e.getCause().getMessage():e.getMessage()));
            return "/auth/login-page";
        }
        /** untuk di set di table session */
        webRequest.setAttribute("MENU_NAVBAR",menuNavBar,1);
        webRequest.setAttribute("JWT",tokenJwt,1);
        webRequest.setAttribute("USR_NAME",valLoginDTO.getUsername(),1);
        webRequest.setAttribute("PASSWORD",valLoginDTO.getPassword(),1);

        /** untuk di set di page home setelah login*/
        model.addAttribute("MENU_NAVBAR",menuNavBar);
        model.addAttribute("USR_NAME",valLoginDTO.getUsername());
//        model.addAttribute("URL_IMG",urlImg);
        return "home-new2";
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("usrRegis", new ValRegisDTO());
        return "/auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("usrRegis")
                        @Valid ValRegisDTO valRegisDTO,
                        BindingResult result,
                        Model model,
                        WebRequest webRequest){
        if(result.hasErrors()){
            return "auth/register";
        }

//        valLoginDTO.setPassword(new String(Base64.decode(valLoginDTO.getPassword())));
        ResponseEntity<Object> response = null;
        String otp = "";
        try{
            response = authService.register(valRegisDTO);
            Map<String,Object> map = (Map<String, Object>) response.getBody();

            otp = map.get("token").toString();
        }catch (Exception e){

            model.addAttribute("usrRegis",new ValRegisDTO());
            result.addError(new ObjectError("globalError",e.getMessage()==null?e.getCause().getMessage():e.getMessage()));
            return "/auth/register";
        }
        /** untuk di set di table session */

//        webRequest.setAttribute("EMAIL",valRegisDTO.getEmail(),1);

        /** untuk di set di page home setelah login*/
        model.addAttribute("email",valRegisDTO.getEmail());
//        model.addAttribute("URL_IMG",urlImg);
        return "/auth/verify-register";
    }


    @PostMapping("/verify-regis")
    public String verifyRegister(@ModelAttribute("usrVerifRegis")
                           @Valid ValVerifyRegisDTO valVerifyRegisDTO,
                           BindingResult result,
                           Model model,
                           WebRequest webRequest){
        if(result.hasErrors()){
            return "auth/register";
        }

//        valLoginDTO.setPassword(new String(Base64.decode(valLoginDTO.getPassword())));
        ResponseEntity<Object> response = null;
        String message = "";
        try{
            response = authService.verifyRegister(valVerifyRegisDTO);
            Map<String,Object> map = (Map<String, Object>) response.getBody();

            message = map.get("message").toString();
//            message = (String) map.get("message");
        }catch (Exception e){

            model.addAttribute("message","Register Error");
            return "/auth/login-page";
        }
        /** untuk di set di page home setelah login*/
        model.addAttribute("usr", new ValLoginDTO());
        model.addAttribute("message",message);
        return "/auth/login-page";
    }

    @GetMapping("/logout")
    public String destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }
}
