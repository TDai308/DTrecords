package com.example.dtrecords.service.Impl;

import com.example.dtrecords.model.Artist;
import com.example.dtrecords.repository.ArtistsRepository;
import com.example.dtrecords.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArtistServiceImpl implements ArtistService {
    @Autowired
    private ArtistsRepository artistsRepository;

    @Override
    public Iterable<Artist> findAll() {
        return artistsRepository.findAll();
    }

    @Override
    public Optional<Artist> findById(Long IDartist) {
        return artistsRepository.findById(IDartist);
    }

    @Override
    public void save(Artist artist) {
        artistsRepository.save(artist);
    }

    @Override
    public void remove(Long IDartist) {
        artistsRepository.deleteById(IDartist);
    }

    @Override
    public Iterable<Artist> findTop5ByOrderByNameAsc() {
        return artistsRepository.findTop5ByOrderByNameAsc();
    }

}
