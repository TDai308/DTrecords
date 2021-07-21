package com.example.dtrecords.controller;

import com.example.dtrecords.model.Genre;
import com.example.dtrecords.service.GenreService;
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
public class genreController {

    @Autowired
    private GenreService genreService;

    @ModelAttribute("genre")
    public Genre genreNew() {
        return new Genre();
    }

    @GetMapping("/genrelist")
    public ModelAndView showGenreList(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        Cookie[] cookies = request.getCookies();
        for (Cookie ck : cookies) {
            if (ck.getName().equals("setAdmin")) {
                modelAndView.setViewName("/genre/genreList");
                Iterable<Genre> genres = genreService.findAll();
                modelAndView.addObject("genres",genres);
                break;
            } else {
                modelAndView.setViewName("/admin/error");
            }
        }
        return modelAndView;
    }

    @PostMapping("/edit-genre/{id}")
    public String saveGenreEdit(HttpServletRequest request, @ModelAttribute(name = "genreName") String genreName, @PathVariable(name = "id") Long genreid) {
        Optional<Genre> genre = genreService.findById(genreid);
        genre.get().setGenrename(genreName);
        genreService.save(genre.get());
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @PostMapping("/delete-genre/{id}")
    public String deleteArtist(HttpServletRequest request,@PathVariable(name = "id") Long genreid) {
        genreService.remove(genreid);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @PostMapping("/create-genre")
    public String addNewArtist(HttpServletRequest request, @ModelAttribute(name = "genre") Genre genre) {
        genreService.save(genre);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}
