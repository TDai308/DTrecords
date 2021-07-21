package com.example.dtrecords.formatter;

import com.example.dtrecords.model.Vinyl;
import com.example.dtrecords.service.VinylService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class Vinylformatter implements Formatter<Vinyl> {

    private final VinylService vinylService;

    @Autowired
    public Vinylformatter(VinylService vinylService) {
        this.vinylService = vinylService;
    }

    @Override
    public Vinyl parse(String text, Locale locale) throws ParseException {
        return vinylService.findById(Long.parseLong(text)).get();
    }

    @Override
    public String print(Vinyl object, Locale locale) {
        return "[" + object.getVinylID() + ", " +object.getName() + ", " +object.getArtist() + ", " +object.getImg1() + ", " +object.getImg2() + ", " +object.getQuantity() + ", " +object.getPrice() + ", " +object.getGenre() + ", " +object.getNation() + ", " +object.getOnSale() + ", " +object.getSalePrice() +"]";
    }
}
