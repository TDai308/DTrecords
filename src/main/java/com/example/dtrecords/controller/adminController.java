package com.example.dtrecords.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class adminController {

    @GetMapping("/admin")
    public String adminPage(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String adminPage = null;
        for (Cookie ck : cookies) {
            if (ck.getName().equals("setAdmin")) {
                adminPage = "/admin/adminPage";
                break;
            } else {
                adminPage = "/admin/adminLogin";
            }
        }
        return adminPage;
    }

    @PostMapping("/admin/login")
    public ModelAndView adminLogin(@RequestParam(name = "name") String name, @RequestParam(name = "password") String password, @CookieValue(value = "setAdmin", defaultValue = "") String setAdmin, HttpServletResponse response, Model model) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin");
        if (name.equals("admin") && password.equals("admin")) {
            setAdmin = name;
            Cookie cookie = new Cookie("setAdmin", setAdmin);
            cookie.setPath("/");
            cookie.setMaxAge(24 * 60 * 60);
            response.addCookie(cookie);
        } else {
            model.addAttribute("message", "Login failed. Try again.");
            modelAndView.setViewName("/admin/adminLogin");
        }
        return modelAndView;
    }

    @GetMapping("/admin-logout")
    public ModelAndView LogOut(@CookieValue(value = "setAdmin", defaultValue = "") String setAdmin, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        Cookie cookie = new Cookie("setAdmin", setAdmin);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return modelAndView;
    }
}
