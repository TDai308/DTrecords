package com.example.dtrecords.controller;

import com.example.dtrecords.model.*;
import com.example.dtrecords.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@SessionAttributes({"customer", "cart"})
public class homeController {
    @Autowired
    private VinylService vinylService;

    @Autowired
    private TrachService trachService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private GenreService genreService;

    @ModelAttribute("cart")
    public Cart setUpCart() {
        return new Cart();
    }

    @GetMapping("/")
    public String Home(@CookieValue(value = "setCustomer", defaultValue = "") String setCustomer, Model model,@ModelAttribute("cart") Cart cart) {
        Map<Long, Integer> hashMap = cart.getSelectedVinyl();
        Map<Vinyl, Integer> selectedVinyl = new HashMap<>();
        int quantity = 0;
        for (Map.Entry<Long, Integer> product: hashMap.entrySet()) {
            selectedVinyl.put(vinylService.findById(product.getKey()).get(), product.getValue());
            quantity += product.getValue();
        }
        if (quantity != 0) {
            model.addAttribute("quantity", quantity);
        }
        Iterable<Artist> artists = artistService.findTop5ByOrderByNameAsc();
        model.addAttribute("top5Artists", artists);
        model.addAttribute("selectedVinyl", selectedVinyl);
        model.addAttribute("setCustomer", setCustomer);
        Iterable<Vinyl> topSellingVinyl = vinylService.findTop10ByQuantityBetweenOrderByVinylIDDesc(1L,10L);
        model.addAttribute("topSellingVinyl", topSellingVinyl);
        Iterable<Vinyl> OnSaleVinyls = vinylService.findAllByOnSaleAndQuantityAfterOrderByVinylIDDesc(true,0L);
        model.addAttribute("saleVinyls", OnSaleVinyls);
        return "home";
    }

    @GetMapping("/all-vinyl-product")
    public ModelAndView allProduct(@CookieValue(value = "setCustomer", defaultValue = "") String setCustomer, Pageable pageable, @ModelAttribute("cart") Cart cart, @RequestParam(name = "s") Optional<String> s, @RequestParam(required = false) String sortOption) {
        Page<Vinyl> vinyls;
        Map<Long, Integer> hashMap = cart.getSelectedVinyl();
        int quantity = 0;
        Map<Vinyl, Integer> selectedVinyl = new HashMap<>();
        for (Map.Entry<Long, Integer> product: hashMap.entrySet()) {
            selectedVinyl.put(vinylService.findById(product.getKey()).get(), product.getValue());
            quantity += product.getValue();
        }
        ModelAndView modelAndView = new ModelAndView("/productList");
        if (s.isPresent()) {
            vinyls = vinylService.findAllByName(s.get(),pageable);
            modelAndView.addObject("s",s.get());
        } else {
            if (sortOption.equals("vinylIDdesc")) {
                vinyls = vinylService.findAllByOrderByVinylIDDesc(pageable);
            } else if (sortOption.equals("realPriceasc")) {
                vinyls = vinylService.findAllBydOrderByRealPriceAsc(pageable);
            } else {
                vinyls = vinylService.findAllBydOrderByRealPriceDesc(pageable);
            }
        }
        modelAndView.addObject("vinyls",vinyls);
        modelAndView.addObject("selectedVinyl", selectedVinyl);
        if (quantity != 0) {
            modelAndView.addObject("quantity", quantity);
        }
        modelAndView.addObject("setCustomer", setCustomer);
        modelAndView.addObject("sortOption", sortOption);
        return modelAndView;
    }

    @GetMapping("/all-vinyl-product/{producesOption}")
    public ModelAndView ProductType(@CookieValue(value = "setCustomer", defaultValue = "") String setCustomer, Pageable pageable, @ModelAttribute("cart") Cart cart, @PathVariable(name = "producesOption") String producesOption, @RequestParam(required = false) String sortOption) {
        Page<Vinyl> vinyls = null;
        Map<Long, Integer> hashMap = cart.getSelectedVinyl();
        int quantity = 0;
        Map<Vinyl, Integer> selectedVinyl = new HashMap<>();
        for (Map.Entry<Long, Integer> product: hashMap.entrySet()) {
            selectedVinyl.put(vinylService.findById(product.getKey()).get(), product.getValue());
            quantity += product.getValue();
        }
        ModelAndView modelAndView = new ModelAndView("/productList");
        if (producesOption.equals("justAdded")) {
            modelAndView.addObject("justAdded",producesOption);
            vinyls = vinylService.findTop10ByOrderByVinylIDDesc(pageable);
        } else if (producesOption.equals("under10Dollars") && sortOption.equals("vinylIDdesc")) {
            vinyls = vinylService.findAllByRealPriceLessThanOrderByVinylIDDesc(10F, pageable);
        } else if (producesOption.equals("under10Dollars") && sortOption.equals("realPriceasc")) {
            vinyls = vinylService.findAllByRealPriceLessThanOrderByRealPriceAsc(10F, pageable);
        } else if (producesOption.equals("under10Dollars") && sortOption.equals("realPricedesc")) {
            vinyls = vinylService.findAllByRealPriceLessThanOrderByRealPriceDesc(10F, pageable);
        } else if (producesOption.equals("under20Dollars") && sortOption.equals("vinylIDdesc")) {
            vinyls = vinylService.findAllByRealPriceLessThanOrderByVinylIDDesc(20F, pageable);
        } else if (producesOption.equals("under20Dollars") && sortOption.equals("realPriceasc")) {
            vinyls = vinylService.findAllByRealPriceLessThanOrderByRealPriceAsc(20F, pageable);
        } else if (producesOption.equals("under20Dollars") && sortOption.equals("realPricedesc")) {
            vinyls = vinylService.findAllByRealPriceLessThanOrderByRealPriceDesc(20F, pageable);
        } else if (producesOption.equals("under30Dollars") && sortOption.equals("vinylIDdesc")) {
            vinyls = vinylService.findAllByRealPriceLessThanOrderByVinylIDDesc(30F, pageable);
        } else if (producesOption.equals("under30Dollars") && sortOption.equals("realPriceasc")) {
            vinyls = vinylService.findAllByRealPriceLessThanOrderByRealPriceAsc(30F, pageable);
        } else if (producesOption.equals("under30Dollars") && sortOption.equals("realPricedesc")) {
            vinyls = vinylService.findAllByRealPriceLessThanOrderByRealPriceDesc(30F, pageable);
        } else if (producesOption.equals("over30Dollars") && sortOption.equals("vinylIDdesc")) {
            vinyls = vinylService.findAllByRealPriceGreaterThanOrderByVinylIDDesc(30F, pageable);
        } else if (producesOption.equals("over30Dollars") && sortOption.equals("realPriceasc")) {
            vinyls = vinylService.findAllByRealPriceGreaterThanOrderByRealPriceAsc(30F, pageable);
        } else if (producesOption.equals("over30Dollars") && sortOption.equals("realPricedesc")) {
            vinyls = vinylService.findAllByRealPriceGreaterThanOrderByRealPriceDesc(30F, pageable);
        } else if (producesOption.equals("popVinyl") && sortOption.equals("vinylIDdesc")) {
            Genre genre = genreService.findByGenrename("Pop");
            vinyls = vinylService.findAllByGenreOrderByVinylIDDesc(genre, pageable);
        } else if (producesOption.equals("popVinyl") && sortOption.equals("realPriceasc")) {
            Genre genre = genreService.findByGenrename("Pop");
            vinyls = vinylService.findAllByGenreOrderByRealPriceAsc(genre, pageable);
        } else if (producesOption.equals("popVinyl") && sortOption.equals("realPricedesc")) {
            Genre genre = genreService.findByGenrename("Pop");
            vinyls = vinylService.findAllByGenreOrderByRealPriceDesc(genre, pageable);
        } else if (producesOption.equals("rockVinyl") && sortOption.equals("vinylIDdesc")) {
            Genre genre = genreService.findByGenrename("Rock");
            vinyls = vinylService.findAllByGenreOrderByVinylIDDesc(genre, pageable);
        } else if (producesOption.equals("rockVinyl") && sortOption.equals("realPriceasc")) {
            Genre genre = genreService.findByGenrename("Rock");
            vinyls = vinylService.findAllByGenreOrderByRealPriceAsc(genre, pageable);
        } else if (producesOption.equals("rockVinyl") && sortOption.equals("realPricedesc")) {
            Genre genre = genreService.findByGenrename("Rock");
            vinyls = vinylService.findAllByGenreOrderByRealPriceDesc(genre, pageable);
        } else if (producesOption.equals("r&bVinyl") && sortOption.equals("vinylIDdesc")) {
            Genre genre = genreService.findByGenrename("R&B");
            vinyls = vinylService.findAllByGenreOrderByVinylIDDesc(genre, pageable);
        } else if (producesOption.equals("r&bVinyl") && sortOption.equals("realPriceasc")) {
            Genre genre = genreService.findByGenrename("R&B");
            vinyls = vinylService.findAllByGenreOrderByRealPriceAsc(genre, pageable);
        } else if (producesOption.equals("r&bVinyl") && sortOption.equals("realPricedesc")) {
            Genre genre = genreService.findByGenrename("R&B");
            vinyls = vinylService.findAllByGenreOrderByRealPriceDesc(genre, pageable);
        } else if (producesOption.equals("hiphopVinyl") && sortOption.equals("vinylIDdesc")) {
            Genre genre = genreService.findByGenrename("HipHop");
            vinyls = vinylService.findAllByGenreOrderByVinylIDDesc(genre, pageable);
        } else if (producesOption.equals("hiphopVinyl") && sortOption.equals("realPriceasc")) {
            Genre genre = genreService.findByGenrename("HipHop");
            vinyls = vinylService.findAllByGenreOrderByRealPriceAsc(genre, pageable);
        } else if (producesOption.equals("hiphopVinyl") && sortOption.equals("realPricedesc")) {
            Genre genre = genreService.findByGenrename("HipHop");
            vinyls = vinylService.findAllByGenreOrderByRealPriceDesc(genre, pageable);
        } else if (producesOption.equals("countryVinyl") && sortOption.equals("vinylIDdesc")) {
            Genre genre = genreService.findByGenrename("Country");
            vinyls = vinylService.findAllByGenreOrderByVinylIDDesc(genre, pageable);
        } else if (producesOption.equals("countryVinyl") && sortOption.equals("realPriceasc")) {
            Genre genre = genreService.findByGenrename("Country");
            vinyls = vinylService.findAllByGenreOrderByRealPriceAsc(genre, pageable);
        } else if (producesOption.equals("countryVinyl") && sortOption.equals("realPricedesc")) {
            Genre genre = genreService.findByGenrename("Country");
            vinyls = vinylService.findAllByGenreOrderByRealPriceDesc(genre, pageable);
        } else if (producesOption.equals("edmVinyl") && sortOption.equals("vinylIDdesc")) {
            Genre genre = genreService.findByGenrename("EDM");
            vinyls = vinylService.findAllByGenreOrderByVinylIDDesc(genre, pageable);
        } else if (producesOption.equals("edmVinyl") && sortOption.equals("realPriceasc")) {
            Genre genre = genreService.findByGenrename("EDM");
            vinyls = vinylService.findAllByGenreOrderByRealPriceAsc(genre, pageable);
        } else if (producesOption.equals("edmVinyl") && sortOption.equals("realPricedesc")) {
            Genre genre = genreService.findByGenrename("EDM");
            vinyls = vinylService.findAllByGenreOrderByRealPriceDesc(genre, pageable);
        } else if (producesOption.equals("indieVinyl") && sortOption.equals("vinylIDdesc")) {
            Genre genre = genreService.findByGenrename("Indie");
            vinyls = vinylService.findAllByGenreOrderByVinylIDDesc(genre, pageable);
        } else if (producesOption.equals("indieVinyl") && sortOption.equals("realPriceasc")) {
            Genre genre = genreService.findByGenrename("Indie");
            vinyls = vinylService.findAllByGenreOrderByRealPriceAsc(genre, pageable);
        } else if (producesOption.equals("indieVinyl") && sortOption.equals("realPricedesc")) {
            Genre genre = genreService.findByGenrename("Indie");
            vinyls = vinylService.findAllByGenreOrderByRealPriceDesc(genre, pageable);
        } else if (producesOption.equals("jazzVinyl") && sortOption.equals("vinylIDdesc")) {
            Genre genre = genreService.findByGenrename("Jazz");
            vinyls = vinylService.findAllByGenreOrderByVinylIDDesc(genre, pageable);
        } else if (producesOption.equals("jazzVinyl") && sortOption.equals("realPriceasc")) {
            Genre genre = genreService.findByGenrename("Jazz");
            vinyls = vinylService.findAllByGenreOrderByRealPriceAsc(genre, pageable);
        } else if (producesOption.equals("jazzVinyl") && sortOption.equals("realPricedesc")) {
            Genre genre = genreService.findByGenrename("Jazz");
            vinyls = vinylService.findAllByGenreOrderByRealPriceDesc(genre, pageable);
        } else if (producesOption.equals("VietnamNation") && sortOption.equals("vinylIDdesc")) {
            vinyls = vinylService.findAllByNationOrderByVinylIDDesc("Việt Nam", pageable);
        } else if (producesOption.equals("VietnamNation") && sortOption.equals("realPriceasc")) {
            vinyls = vinylService.findAllByNationOrderByRealPriceAsc("Việt Nam", pageable);
        } else if (producesOption.equals("VietnamNation") && sortOption.equals("realPricedesc")) {
            vinyls = vinylService.findAllByNationOrderByRealPriceDesc("Việt Nam", pageable);
        } else if (producesOption.equals("UsukNation") && sortOption.equals("vinylIDdesc")) {
            vinyls = vinylService.findAllByNationOrderByVinylIDDesc("Mỹ", pageable);
        } else if (producesOption.equals("UsukNation") && sortOption.equals("realPriceasc")) {
            vinyls = vinylService.findAllByNationOrderByRealPriceAsc("Mỹ", pageable);
        } else if (producesOption.equals("UsukNation") && sortOption.equals("realPricedesc")) {
            vinyls = vinylService.findAllByNationOrderByRealPriceDesc("Mỹ", pageable);
        } else if (producesOption.equals("KoreanNation") && sortOption.equals("vinylIDdesc")) {
            vinyls = vinylService.findAllByNationOrderByVinylIDDesc("Hàn Quốc", pageable);
        } else if (producesOption.equals("KoreanNation") && sortOption.equals("realPriceasc")) {
            vinyls = vinylService.findAllByNationOrderByRealPriceAsc("Hàn Quốc", pageable);
        } else if (producesOption.equals("KoreanNation") && sortOption.equals("realPricedesc")) {
            vinyls = vinylService.findAllByNationOrderByRealPriceDesc("Hàn Quốc", pageable);
        }
        modelAndView.addObject("vinyls",vinyls);
        modelAndView.addObject("selectedVinyl", selectedVinyl);
        if (quantity != 0) {
            modelAndView.addObject("quantity", quantity);
        }
        modelAndView.addObject("setCustomer", setCustomer);
        modelAndView.addObject("sortOption", sortOption);
        return modelAndView;
    }

    @GetMapping("/vinyl/{id}")
    public ModelAndView viewVinyl(@PathVariable(name = "id") Long id, @CookieValue(value = "setCustomer", defaultValue = "") String setCustomer,@ModelAttribute("cart") Cart cart) {
        Vinyl vinyl = vinylService.findById(id).get();
        Iterable<Track> tracks = trachService.findAllByVinyl(vinyl);
        Map<Long, Integer> hashMap = cart.getSelectedVinyl();
        int quantity = 0;
        Map<Vinyl, Integer> selectedVinyl = new HashMap<>();
        for (Map.Entry<Long, Integer> product: hashMap.entrySet()) {
            selectedVinyl.put(vinylService.findById(product.getKey()).get(), product.getValue());
            quantity += product.getValue();
        }
        ModelAndView modelAndView = new ModelAndView("/viewVinyl");
        modelAndView.addObject("vinyl", vinyl);
        modelAndView.addObject("artistVinyls", vinylService.findAllByArtistAndNameNotLikeAndQuantityAfter(vinyl.getArtist(),vinyl.getName(), 0L));
        modelAndView.addObject("theSameGenreVinyls", vinylService.findAllByGenreAndNameNotLikeAndQuantityAfter(vinyl.getGenre(),vinyl.getName(),0L));
        modelAndView.addObject("tracklist", tracks);
        if (quantity != 0) {
            modelAndView.addObject("quantity", quantity);
        }
        modelAndView.addObject("selectedVinyl", selectedVinyl);
        modelAndView.addObject("setCustomer", setCustomer);
        return modelAndView;
    }

    @ModelAttribute("customer")
    public Customer setUpCustomerForm() {
        return new Customer();
    }

    @RequestMapping("/showlogin")
    public ModelAndView showloginForm(@CookieValue(value = "setCustomer", defaultValue = "") String setCustomer, HttpServletRequest request) {
        Cookie cookie = new Cookie("setCustomer", setCustomer);
        ModelAndView modelAndView;
        if (setCustomer.equals("")) {
            modelAndView = new ModelAndView("/login");
        } else {
            modelAndView = new ModelAndView("wannaLogOut");
            modelAndView.addObject("cookieValue", cookie);
        }
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView logIn(@ModelAttribute("customer") Customer customer, @CookieValue(value = "setCustomer", defaultValue = "") String setCustomer, HttpServletResponse response) {
        Iterable<Customer> customers = customerService.findAll();
        ModelAndView modelAndView = null;
        for (Customer customer1 : customers) {
            if (customer.getEmail().equals(customer1.getEmail())){
                if (!customer.getPassword().equals(customer1.getPassword())) {
                    modelAndView = new ModelAndView("/login");
                    Cookie cookie = new Cookie("setCustomer", setCustomer);
                    modelAndView.addObject("cookieValue", cookie);
                    modelAndView.addObject("message", "sai mật khẩu");
                } else {
                    modelAndView = new ModelAndView("redirect:/");
                    if (customer.getEmail() != null)
                        setCustomer = customer.getEmail();

                    Cookie cookie = new Cookie("setCustomer", setCustomer);
                    cookie.setMaxAge(24 * 60 * 60);
                    response.addCookie(cookie);
                }
                break;
            } else {
                modelAndView = new ModelAndView("/login");
                Cookie cookie = new Cookie("setCustomer", setCustomer);
                modelAndView.addObject("cookieValue", cookie);
                modelAndView.addObject("message", "tài khoản không tồn tại");
            }
        }
        return modelAndView;
    }

    @GetMapping("/log-out")
    public ModelAndView LogOut(@CookieValue(value = "setCustomer", defaultValue = "") String setCustomer, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        Cookie cookie = new Cookie("setCustomer", setCustomer);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return modelAndView;
    }

    @GetMapping("/signup")
    public ModelAndView showSignUpForm() {
        ModelAndView modelAndView = new ModelAndView("/signup");
        modelAndView.addObject("newCustomer", new Customer());
        modelAndView.addObject("messageEmailError", "");
        return modelAndView;
    }

    @PostMapping("/signup")
    public String SignUp(@Validated @ModelAttribute(name = "newCustomer") Customer customer, BindingResult bindingResult, ModelMap modelMap) {
        try {
            new Customer().validate(customer,bindingResult);
            if (bindingResult.hasFieldErrors()) {
                return "signup";
            } else {
                customerService.save(customer);
                return "redirect:/showlogin";
            }
        } catch (Exception e) {
            customer.setEmail("");
            modelMap.addAttribute("newCustomer", customer);
            modelMap.addAttribute("messageEmailError", "(Email đã được sử dụng hãy sử dụng email khác)");
            return "signup";
        }
    }

    @GetMapping("/showEditForm")
    public ModelAndView shotEditCustomerForm(HttpServletRequest request,@CookieValue(value = "setCustomer", defaultValue = "") String setCustomer,@ModelAttribute("cart") Cart cart,@ModelAttribute(name = "message") String message) {
        ModelAndView modelAndView = new ModelAndView("/editCustomer");
        Map<Long, Integer> hashMap = cart.getSelectedVinyl();
        Map<Vinyl, Integer> selectedVinyl = new HashMap<>();
        int quantity = 0;
        for (Map.Entry<Long, Integer> product: hashMap.entrySet()) {
            selectedVinyl.put(vinylService.findById(product.getKey()).get(), product.getValue());
            quantity += product.getValue();
        }
        if (quantity != 0) {
            modelAndView.addObject("quantity", quantity);
        }
        Cookie[] cookies = request.getCookies();
        for (Cookie ck : cookies) {
            if (ck.getName().equals("setCustomer")) {
                Customer customer = customerService.findCustomerByEmail(ck.getValue());
                modelAndView.addObject("customer", customer);
                break;
            }
        }
        modelAndView.addObject("message",message);
        modelAndView.addObject("selectedVinyl", selectedVinyl);
        modelAndView.addObject("setCustomer", setCustomer);
        return modelAndView;
    }

    @PostMapping("/editCustomerInfor")
    public ModelAndView editCustomerInfor(@ModelAttribute(name = "customer") Customer customer, ModelMap modelMap, RedirectAttributes redirectAttributes) {
        try {
            ModelAndView modelAndView = new ModelAndView("redirect:/showEditForm");
            customerService.save(customer);
            modelAndView.addObject("customer",customer);
            redirectAttributes.addAttribute("message","Thông tin khách hàng đã được chỉnh sửa");
            return modelAndView;
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("/editCustomer");
            modelMap.addAttribute("newCustomer", customer);
            modelMap.addAttribute("messageEmailError", "(Email đã được sử dụng hãy sử dụng email khác)");
            return modelAndView;
        }
    }
}
