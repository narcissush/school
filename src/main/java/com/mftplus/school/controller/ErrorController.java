package com.mftplus.school.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
    
    @GetMapping("/error")
    public String errorPage(Model model) {
        return "error";
    }
    
    @GetMapping("/access-denied")
    public String accessDenied(Model model) {
        model.addAttribute("message", "شما اجازه دسترسی به این صفحه را ندارید");
        return "access-denied";
    }
}

