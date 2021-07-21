package com.example.dtrecords.controller;

import com.example.dtrecords.model.Customer;
import com.example.dtrecords.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class customerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/customerlist")
    public ModelAndView showCustomerList(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        Cookie[] cookies = request.getCookies();
        for (Cookie ck : cookies) {
            if (ck.getName().equals("setAdmin")) {
                modelAndView.setViewName("/customer/customerlist");
                modelAndView.addObject("customers",  customerService.findAll());
                break;
            } else {
                modelAndView.setViewName("/admin/error");
            }
        }
        return modelAndView;
    }

    @GetMapping("/edit-customer/{id}")
    public ModelAndView shotEditCustomerForm(@PathVariable(name = "id") Long userID, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        Cookie[] cookies = request.getCookies();
        for (Cookie ck : cookies) {
            if (ck.getName().equals("setAdmin")) {
                modelAndView.setViewName("/customer/edit");
                modelAndView.addObject("customer",  customerService.findById(userID));
                break;
            } else {
                modelAndView.setViewName("/admin/error");
            }
        }
        return modelAndView;
    }

    @PostMapping("/edit-customer")
    public ModelAndView editCustomer(@ModelAttribute(name = "customer") Customer customer, ModelMap modelMap) {
        try {
            ModelAndView modelAndView = new ModelAndView("/customer/edit");
            customerService.save(customer);
            modelAndView.addObject("customer",customer);
            modelAndView.addObject("message","Thông tin khách hàng đã được chỉnh sửa");
            return modelAndView;
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("/customer/edit");
            modelMap.addAttribute("newCustomer", customer);
            modelMap.addAttribute("messageEmailError", "(Email đã được sử dụng hãy sử dụng email khác)");
            return modelAndView;
        }
    }

    @PostMapping("/delete-customer/{id}")
    public String deleteVinyl(@PathVariable(name = "id") Long id) {
        customerService.remove(id);
        return "redirect:/customerlist";
    }
}
