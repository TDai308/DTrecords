package com.example.dtrecords.controller;

import com.example.dtrecords.model.Track;
import com.example.dtrecords.model.Vinyl;
import com.example.dtrecords.service.TrachService;
import com.example.dtrecords.service.VinylService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@Controller
public class trackController {

    @Autowired
    private TrachService trachService;

    @Autowired
    private VinylService vinylService;

    @ModelAttribute("vinyls")
    public Page<Vinyl> vinyls(Pageable pageable) {
        return vinylService.findAll(pageable);
    }

    @ModelAttribute("vinyllist")
    public Iterable<Vinyl> vinyllist() {
        return vinylService.findAll();
    }

    @GetMapping("/tracklist")
    public ModelAndView listTrack(HttpServletRequest request) {
        Iterable<Track> trackList = trachService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        Cookie[] cookies = request.getCookies();
        for (Cookie ck : cookies) {
            if (ck.getName().equals("setAdmin")) {
                modelAndView.setViewName("/track/tracklist");
                modelAndView.addObject("tracks", trackList);
                break;
            } else {
                modelAndView.setViewName("/admin/error");
            }
        }
        return modelAndView;
    }

    @GetMapping("/create-track")
    public ModelAndView showCreateForm(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        Cookie[] cookies = request.getCookies();
        for (Cookie ck : cookies) {
            if (ck.getName().equals("setAdmin")) {
                modelAndView.setViewName("/track/create");
                modelAndView.addObject("track", new Track());
                break;
            } else {
                modelAndView.setViewName("/admin/error");
            }
        }
        return modelAndView;
    }

    @PostMapping("/create-track")
    public ModelAndView createTrack(@ModelAttribute(name = "track") Track track) {
        trachService.save(track);
        ModelAndView modelAndView = new ModelAndView("/track/create");
        modelAndView.addObject("track", new Track());
        modelAndView.addObject("message", "Bài hát mới đã được thêm");
        return modelAndView;
    }

    @GetMapping("/edit-track/{id}")
    public ModelAndView showEditForm(@PathVariable(name = "id") Long id, HttpServletRequest request) {
        Track track = trachService.findById(id).get();
        ModelAndView modelAndView = new ModelAndView();
        Cookie[] cookies = request.getCookies();
        for (Cookie ck : cookies) {
            if (ck.getName().equals("setAdmin")) {
                modelAndView.setViewName("/track/edit");
                modelAndView.addObject("track", track);
                break;
            } else {
                modelAndView.setViewName("/admin/error");
            }
        }
        return modelAndView;
    }

    @PostMapping("/edit-track")
    public ModelAndView editTrack(@ModelAttribute(name = "track") Track track) {
        trachService.save(track);
        ModelAndView modelAndView = new ModelAndView("/track/edit");
        modelAndView.addObject("track", track);
        modelAndView.addObject("message", "Thông tin bài hát dược cập nhật thành công");
        return modelAndView;
    }

    @PostMapping("/delete-track/{id}")
    public String deleteTrack(@PathVariable(name = "id") Long id) {
        trachService.remove(id);
        return "redirect:/tracklist";
    }
}
