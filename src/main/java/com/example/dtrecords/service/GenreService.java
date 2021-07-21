package com.example.dtrecords.service;

import com.example.dtrecords.model.Genre;

public interface GenreService  extends GeneralService<Genre>{

    Genre findByGenrename(String genrename);
}
