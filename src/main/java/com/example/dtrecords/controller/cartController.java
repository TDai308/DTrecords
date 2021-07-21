package com.example.dtrecords.controller;

import com.example.dtrecords.model.Cart;
import com.example.dtrecords.model.Customer;
import com.example.dtrecords.model.Order;
import com.example.dtrecords.model.Vinyl;
import com.example.dtrecords.service.CustomerService;
import com.example.dtrecords.service.MailService;
import com.example.dtrecords.service.OrderService;
import com.example.dtrecords.service.VinylService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
@SessionAttributes("cart")
public class cartController {

    @ModelAttribute("cart")
    public Cart setUpCart() {
        return new Cart();
    }

    @Autowired
    private VinylService vinylService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MailService mailService;

    @GetMapping("/addToCart/{vinylID}")
    public String addToCart(@ModelAttribute("cart") Cart cart, @PathVariable(name = "vinylID") Long vinylID, @RequestParam(value = "quantity",required = false) Integer number) {
        Integer quantity = number;
        for (Map.Entry<Long, Integer> entry : cart.getSelectedVinyl().entrySet()) {
            if (entry.getKey().equals(vinylID)) {
                quantity = number + entry.getValue();
            }
        }
        cart.setSelectedVinyl(vinylID, quantity);
        return "redirect:/vinyl/"+vinylID;
    }

    @GetMapping("/toCart")
    public ModelAndView getCartList(@ModelAttribute("cart") Cart cart,@CookieValue(value = "setCustomer", defaultValue = "") String setCustomer) {
        ModelAndView modelAndView = new ModelAndView("/cart");
        Map<Long, Integer> hashMap = cart.getSelectedVinyl();
        Map<Vinyl, Integer> selectedVinyl = new HashMap<>();
        int quantity = 0;
        float totalPrice = 0;
        for (Map.Entry<Long, Integer> product: hashMap.entrySet()) {
            selectedVinyl.put(vinylService.findById(product.getKey()).get(), product.getValue());
            quantity += product.getValue();
        }
        for (Map.Entry<Vinyl, Integer> product: selectedVinyl.entrySet()) {
            if (!product.getKey().getOnSale()) {
                totalPrice += (product.getKey().getPrice()*product.getValue());
            } else {
                totalPrice += (product.getKey().getSalePrice()*product.getValue());
            }
        }
        modelAndView.addObject("selectedVinyl", selectedVinyl);
        if (quantity != 0) {
            modelAndView.addObject("quantity", quantity);
        }
        modelAndView.addObject("totalPrice", (float) Math.round(totalPrice * 100)/100);
        modelAndView.addObject("setCustomer", setCustomer);
        return modelAndView;
    }

    @GetMapping("/remove/{vinylID}")
    public String removeFromCart(@ModelAttribute("cart") Cart cart, @PathVariable Long vinylID) {
        cart.removeProduct(vinylID);
        return "redirect:/toCart";
    }

    @GetMapping("/remove/home/{vinylID}")
    public String removeFromHome(@ModelAttribute("cart") Cart cart, @PathVariable Long vinylID) {
        cart.removeProduct(vinylID);
        return "redirect:/";
    }

    @GetMapping("/remove/productList/{vinylID}")
    public String removeFromProductList(@ModelAttribute("cart") Cart cart, @PathVariable Long vinylID, HttpServletRequest request) {
        cart.removeProduct(vinylID);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("/remove/editCustomer/{vinylID}")
    public String removeFromEditForm(@ModelAttribute("cart") Cart cart, @PathVariable Long vinylID, HttpServletRequest request) {
        cart.removeProduct(vinylID);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("/remove/viewVinyl/{vinylID}")
    public String removeFromViewVinyl(@ModelAttribute("cart") Cart cart,@PathVariable(name = "vinylID") Long  vinylID,@RequestParam(name = "delete") Long vinylID_delete) {
        cart.removeProduct(vinylID_delete);
        return "redirect:/vinyl/"+vinylID;
    }

    @GetMapping("/changeNumber/{vinylID}")
    public ModelAndView changeNumber(@ModelAttribute("cart") Cart cart, @PathVariable Long vinylID, @RequestParam int number) {
        cart.setSelectedVinyl(vinylID, number);
        return new ModelAndView("redirect:/toCart");
    }

    @GetMapping("/ProceedToCheckout")
    public ModelAndView showCheckoutForm(HttpServletRequest request, @ModelAttribute("cart") Cart cart) {
        ModelAndView modelAndView = new ModelAndView();
        if (cart.empty()) {
            modelAndView.setViewName("redirect:/");
        }
        else {
            String CustomerName = null;
            String CustomerPhone = null;
            String CustomerEmail = null;
            String CustomerAddress = null;
            Cookie[] cookies = request.getCookies();
            for (Cookie ck : cookies) {
                if (ck.getName().equals("setCustomer")) {
                    Customer customer = customerService.findCustomerByEmail(ck.getValue());
                    CustomerName = customer.getName();
                    CustomerPhone = customer.getPhonenumber();
                    CustomerEmail = customer.getEmail();
                    CustomerAddress = customer.getAddress();
                    break;
                }
            }
            Map<Long, Integer> hashMap = cart.getSelectedVinyl();
            Map<Vinyl, Integer> selectedVinyl = new HashMap<>();
            float totalPrice = 0;
            for (Map.Entry<Long, Integer> product: hashMap.entrySet()) {
                selectedVinyl.put(vinylService.findById(product.getKey()).get(), product.getValue());
            }
            for (Map.Entry<Vinyl, Integer> product: selectedVinyl.entrySet()) {
                totalPrice += (product.getKey().getRealPrice()*product.getValue());
            }
            modelAndView.setViewName("/proceedtocheckout");
            modelAndView.addObject("selectedVinyl",selectedVinyl);
            modelAndView.addObject("customerName",CustomerName);
            modelAndView.addObject("customerPhone",CustomerPhone);
            modelAndView.addObject("customerEmail",CustomerEmail);
            modelAndView.addObject("customerAddress",CustomerAddress);
            modelAndView.addObject("totalPrice",(float) Math.round(totalPrice * 100)/100);
        }
        return modelAndView;
    }

    @PostMapping("/ProceedToCheckout")
    public String ConfirmOrder(@RequestParam String customerName, @RequestParam String customerPhone, @RequestParam String customerEmail, @RequestParam String customerAddress, @ModelAttribute("cart") Cart cart) throws MessagingException {
        Map<Long, Integer> hashMap = cart.getSelectedVinyl();
        Map<Vinyl, Integer> selectedVinyl = new HashMap<>();
        for (Map.Entry<Long, Integer> product: hashMap.entrySet()) {
            selectedVinyl.put(vinylService.findById(product.getKey()).get(), product.getValue());
        }
        Map.Entry<Vinyl, Integer> firstEntry = selectedVinyl.entrySet().iterator().next();
        System.out.println(firstEntry);
        Long OrderId = null;
        float totalPrice = 0;
        for (Map.Entry<Vinyl, Integer> product: selectedVinyl.entrySet()) {
            System.out.println(product);
            Order order = new Order();
            order.setName(customerName);
            order.setPhone(customerPhone);
            order.setEmail(customerEmail);
            order.setAddress(customerAddress);
            order.setPrice((float) Math.round((product.getKey().getRealPrice()*product.getValue()) * 100)/100);
            totalPrice += (product.getKey().getRealPrice()*product.getValue());
            order.setDelivery("Đang xử lý");
            order.setDateTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
            order.setVinyl(product.getKey());
            order.setQuantity(product.getValue());
            product.getKey().setQuantity(product.getKey().getQuantity() - product.getValue());
            vinylService.save(product.getKey());
            if (product == firstEntry) {
                orderService.save(order);
                OrderId = order.getId();
            }
            order.setOrdercode(DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now())+OrderId);
            orderService.save(order);
        }
        mailService.sendEmail("Notice of successful order from ","Your order is being received and is in the process of being processed. Below is the order information, Your order will be shipped within 3 to 5 days, so keep an eye on your phone when the shipper calls.", customerEmail,customerName,customerPhone,customerAddress,(float) Math.round(totalPrice * 100)/100,selectedVinyl);
        cart.removeAll();
        return "redirect:/success";
    }

    @GetMapping("/success")
    public String showSuccess() {
        return "success";
    }
}
