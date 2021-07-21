package com.example.dtrecords.controller;

import com.example.dtrecords.model.Order;
import com.example.dtrecords.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class orderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orderlist")
    public ModelAndView showOrderList(HttpServletRequest request) {
        Iterable<Order> orders = orderService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        Cookie[] cookies = request.getCookies();
        for (Cookie ck : cookies) {
            if (ck.getName().equals("setAdmin")) {
                modelAndView.setViewName("/order/orderlist");
                modelAndView.addObject("orders",orders);
                break;
            } else {
                modelAndView.setViewName("/admin/error");
            }
        }
        return modelAndView;
    }

    @PostMapping("/save_order/{id}")
    public String deliveryOrder(@PathVariable(name = "id") Long orderId, @ModelAttribute(name = "delivery") String delivery, HttpServletRequest request) {
        Optional<Order> order = orderService.findById(orderId);
        order.get().setDelivery(delivery);
        orderService.save(order.get());
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @PostMapping("/delete-order/{id}")
    public String deleteOrder(@PathVariable(name = "id") Long orderId) {
        orderService.remove(orderId);
        return "redirect:/orderlist";
    }
}
