package com.example.dtrecords.service.Impl;

import com.example.dtrecords.model.Genre;
import com.example.dtrecords.repository.GenreRepository;
import com.example.dtrecords.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public Iterable<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Optional<Genre> findById(Long genreID) {
        return genreRepository.findById(genreID);
    }

    @Override
    public void save(Genre genre) {
        genreRepository.save(genre);
    }

    @Override
    public void remove(Long genreID) {
        genreRepository.deleteById(genreID);
    }

    @Override
    public Genre findByGenrename(String genrename) {
        return genreRepository.findByGenrename(genrename);
    }
}
