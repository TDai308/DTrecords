package com.example.dtrecords.formatter;

import com.example.dtrecords.model.Genre;
import com.example.dtrecords.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;


@Component
public class Genreformatter implements Formatter<Genre> {

    private final GenreService genreService;

    @Autowired
    public Genreformatter(GenreService genreService) {
        this.genreService = genreService;
    }

    @Override
    public Genre parse(String text, Locale locale) throws ParseException {
        return genreService.findById(Long.parseLong(text)).get();
    }

    @Override
    public String print(Genre object, Locale locale) {
        return "[" + object.getGenreID() + ", " +object.getGenrename() +"]";
    }
}
