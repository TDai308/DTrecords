package com.example.dtrecords.formatter;

import com.example.dtrecords.model.Artist;
import com.example.dtrecords.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class Artistformatter implements Formatter<Artist> {

    private final ArtistService artistService;

    @Autowired
    public Artistformatter(ArtistService artistService) {
        this.artistService = artistService;
    }

    @Override
    public Artist parse(String text, Locale locale) throws ParseException {
        return artistService.findById(Long.parseLong(text)).get();
    }

    @Override
    public String print(Artist object, Locale locale) {
        return "[" + object.getIDartist() + ", " +object.getName() +"]";
    }
}
