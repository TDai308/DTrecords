package com.example.dtrecords.configrations;

import com.example.dtrecords.formatter.Artistformatter;
import com.example.dtrecords.formatter.Genreformatter;
import com.example.dtrecords.formatter.Vinylformatter;
import com.example.dtrecords.service.ArtistService;
import com.example.dtrecords.service.GenreService;
import com.example.dtrecords.service.VinylService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new Vinylformatter(applicationContext.getBean(VinylService.class)));
        registry.addFormatter(new Artistformatter(applicationContext.getBean(ArtistService.class)));
        registry.addFormatter(new Genreformatter(applicationContext.getBean(GenreService.class)));
    }
}
