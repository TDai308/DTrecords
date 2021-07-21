package com.example.dtrecords.controller;

import com.example.dtrecords.model.Artist;
import com.example.dtrecords.model.Genre;
import com.example.dtrecords.model.Track;
import com.example.dtrecords.model.Vinyl;
import com.example.dtrecords.service.ArtistService;
import com.example.dtrecords.service.GenreService;
import com.example.dtrecords.service.TrachService;
import com.example.dtrecords.service.VinylService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class vinylController {

    @Autowired
    private VinylService vinylService;

    @Autowired
    private TrachService trachService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private GenreService genreService;

    @ModelAttribute("artistList")
    public Iterable<Artist> artists() {
        return artistService.findAll();
    }

    @ModelAttribute("genreList")
    public Iterable<Genre> genres() {
        return genreService.findAll();
    }

    @GetMapping("/vinyllist")
    public ModelAndView listVinyl(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        Cookie[] cookies = request.getCookies();
        for (Cookie ck : cookies) {
            if (ck.getName().equals("setAdmin")) {
                modelAndView.setViewName("/vinyl/vinyllist");
                Iterable<Vinyl> vinyls = vinylService.findAll();
                modelAndView.addObject("vinyls", vinyls);
                break;
            } else {
                modelAndView.setViewName("/admin/error");
            }
        }
        return modelAndView;
    }
    @GetMapping("/create-vinyl")
    public ModelAndView showCreateForm(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        Cookie[] cookies = request.getCookies();
        for (Cookie ck : cookies) {
            if (ck.getName().equals("setAdmin")) {
                modelAndView.setViewName("/vinyl/create");
                modelAndView.addObject("vinyl", new Vinyl());
                break;
            } else {
                modelAndView.setViewName("/admin/error");
            }
        }
        return modelAndView;
    }

    @PostMapping("/create-vinyl")
    public ModelAndView saveProvince(@ModelAttribute("vinyl") Vinyl vinyl){
        if (vinyl.getOnSale()) {
            vinyl.setRealPrice(vinyl.getSalePrice());
        } else vinyl.setRealPrice(vinyl.getPrice());
        vinylService.save(vinyl);
        ModelAndView modelAndView = new ModelAndView("redirect:/create-vinyl");
        modelAndView.addObject("vinyl", new Vinyl());
        modelAndView.addObject("message", "New vinyl created successfully");
        return modelAndView;
    }

    @GetMapping("/edit-vinyl/{vinylID}")
    public ModelAndView showEditForm(@PathVariable Long vinylID, HttpServletRequest request){
        Vinyl vinyl = vinylService.findById(vinylID).get();
        ModelAndView modelAndView = new ModelAndView();
        Cookie[] cookies = request.getCookies();
        for (Cookie ck : cookies) {
            if (ck.getName().equals("setAdmin")) {
                modelAndView.setViewName("/vinyl/edit");
                modelAndView.addObject("vinyl", vinyl);
                break;
            } else {
                modelAndView.setViewName("/admin/error");
            }
        }
        return modelAndView;
    }

    @PostMapping("/edit-vinyl")
    public ModelAndView updateVinyl(@ModelAttribute("vinyl") Vinyl vinyl){
        if (vinyl.getOnSale()) {
            vinyl.setRealPrice(vinyl.getSalePrice());
        } else vinyl.setRealPrice(vinyl.getPrice());
        vinylService.save(vinyl);
        ModelAndView modelAndView = new ModelAndView("/vinyl/edit");
        modelAndView.addObject("vinyl", vinyl);
        modelAndView.addObject("message", "Thông tin đĩa nhạc đã được cập nhật thành công");
        return modelAndView;
    }

    @GetMapping("/view-vinyl/{id}")
    public ModelAndView viewVinyl(@PathVariable("id") Long vinylID, HttpServletRequest request) {
        Vinyl vinyl = vinylService.findById(vinylID).get();
        Iterable<Track> tracks = trachService.findAllByVinyl(vinyl);
        ModelAndView modelAndView = new ModelAndView();
        Cookie[] cookies = request.getCookies();
        for (Cookie ck : cookies) {
            if (ck.getName().equals("setAdmin")) {
                modelAndView.setViewName("/vinyl/view");
                modelAndView.addObject("vinyl", vinyl);
                modelAndView.addObject("tracks", tracks);
                break;
            } else {
                modelAndView.setViewName("/admin/error");
            }
        }
        return modelAndView;
    }

    @PostMapping("/delete-vinyl/{id}")
    public String deleteVinyl(@PathVariable(name = "id") Long id) {
        vinylService.remove(id);
        return "redirect:/vinyllist";
    }
}
