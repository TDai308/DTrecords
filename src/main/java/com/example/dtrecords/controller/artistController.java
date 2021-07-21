package com.example.dtrecords.controller;

import com.example.dtrecords.model.Artist;
import com.example.dtrecords.service.ArtistService;
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
public class artistController {

    @Autowired
    private ArtistService artistService;

    @ModelAttribute("artist")
    public Artist artistNew() {
        return new Artist();
    }

    @GetMapping("/artistlist")
    public ModelAndView showArtistList(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        Cookie[] cookies = request.getCookies();
        for (Cookie ck : cookies) {
            if (ck.getName().equals("setAdmin")) {
                modelAndView.setViewName("/artist/artistList");
                Iterable<Artist> artists = artistService.findAll();
                modelAndView.addObject("artists",artists);
                break;
            } else {
                modelAndView.setViewName("/admin/error");
            }
        }
        return modelAndView;
    }

    @PostMapping("/edit-artist/{IDartist}")
    public String saveArtistEdit(HttpServletRequest request, @ModelAttribute(name = "artistName") String artistName, @PathVariable(name = "IDartist") Long IDartist) {
        Optional<Artist> artist = artistService.findById(IDartist);
        artist.get().setName(artistName);
        artistService.save(artist.get());
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @PostMapping("/delete-artist/{IDartist}")
    public String deleteArtist(HttpServletRequest request,@PathVariable(name = "IDartist") Long IDartist) {
        artistService.remove(IDartist);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @PostMapping("/create-artist")
    public String addNewArtist(HttpServletRequest request, @ModelAttribute(name = "artist") Artist artist) {
        artistService.save(artist);
        return "redirect:/artistlist";
    }
}
