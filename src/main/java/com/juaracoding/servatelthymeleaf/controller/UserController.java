package com.juaracoding.servatelthymeleaf.controller;

import com.juaracoding.servatelthymeleaf.dto.validasi.RespRoleDTO;
import com.juaracoding.servatelthymeleaf.dto.validasi.ValLoginDTO;
import com.juaracoding.servatelthymeleaf.dto.validasi.ValRegisDTO;
import com.juaracoding.servatelthymeleaf.dto.validasi.ValUserDTO;
import com.juaracoding.servatelthymeleaf.httpclient.UserService;
import com.juaracoding.servatelthymeleaf.util.GlobalFunction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("")
    public String showAllUsers(Model model,
                               WebRequest webRequest) {
        ResponseEntity<Object> response = null;
        String jwt = GlobalFunction.tokenCheck(model, webRequest);
        try {
            response = userService.findAll("Bearer " + jwt);
        } catch (Exception e) {
            model.addAttribute("usr", new ValLoginDTO());
            return "auth/login-page";
        }
        // Ambil response body
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        if (responseBody != null && (Boolean) responseBody.get("success")) {
            // Ambil "data" dari response
            Map<String, Object> getData = (Map<String, Object>) responseBody.get("data");
            List<Map<String, Object>> users = (List<Map<String, Object>>) getData.get("content");
            // Kirim data ke UI
//            model.addAttribute("page", page);
            model.addAttribute("users", users);
        } else {
            model.addAttribute("message", "No user found.");
        }
        Object menuNavBar = webRequest.getAttribute("MENU_NAVBAR", 1);
        Object username = webRequest.getAttribute("USR_NAME", 1);
        model.addAttribute("MENU_NAVBAR", menuNavBar);
        model.addAttribute("USR_NAME", username);

        return "admin/user-list";
    }

    @GetMapping("/edit/{id}")
    public String showEditUser(@PathVariable Long id, Model model, WebRequest webRequest) {
        ResponseEntity<Object> response = null;
        String jwt = GlobalFunction.tokenCheck(model, webRequest);
        try {
            response = userService.findById("Bearer " + jwt, id);
            // Ambil response body
            Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
            if (responseBody != null && (Boolean) responseBody.get("success")) {
                // Ambil "data" dari response
                Map<String, Object> userData = (Map<String, Object>) responseBody.get("data");
                // Kirim data ke UI

                ValUserDTO valUserDTO = ValUserDTO.builder()
                        .username(userData.get("username").toString())
                        .firstName(userData.get("firstName").toString())
                        .lastName(userData.get("lastName").toString())
                        .email(userData.get("email").toString())
                        .noHp(userData.get("noHp").toString())
                        .address(userData.get("address").toString())
                        .tanggalLahir(LocalDate.parse(userData.get("tanggalLahir").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .build();
                        model.addAttribute("valUserDTO", valUserDTO);
                        model.addAttribute("id", id);
            } else {
                model.addAttribute("message", "User not found.");
            }
        } catch (Exception e) {
            model.addAttribute("message", "User not found.");
            return "admin/user-list";
        }
        return "admin/user-edit";
    }

    @PostMapping("/edit/{id}")
    public String editUsers(@ModelAttribute("valUserDTO") @Valid ValUserDTO valUserDTO,
                            @PathVariable(value = "id") Long id,
                            BindingResult result,
                            Model model,
                            WebRequest webRequest,
                            RedirectAttributes redirectAttributes) {
        if(result.hasErrors()){
            return "/admin/user-edit";
        }
        ResponseEntity<Object> response = null;
        String jwt = GlobalFunction.tokenCheck(model, webRequest);
        try {
            response = userService.update("Bearer " + jwt, id, valUserDTO);
        } catch (Exception e) {
            model.addAttribute("valUserDTO", valUserDTO);
            result.addError(new ObjectError("globalError", e.getMessage() == null ? e.getCause().getMessage() : e.getMessage()));
            return "/admin/user-edit";
        }
        Object menuNavBar = webRequest.getAttribute("MENU_NAVBAR", 1);
        Object username = webRequest.getAttribute("USR_NAME", 1);
        model.addAttribute("MENU_NAVBAR", menuNavBar);
        model.addAttribute("USR_NAME", username);
        redirectAttributes.addFlashAttribute("updatedUserId", id);
        return "redirect:/user?success";
    }
}
