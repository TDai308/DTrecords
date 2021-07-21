package com.example.dtrecords.service;

import com.example.dtrecords.model.Artist;

import java.util.Optional;

public interface ArtistService extends GeneralService<Artist>{
    Iterable<Artist> findTop5ByOrderByNameAsc();
}
