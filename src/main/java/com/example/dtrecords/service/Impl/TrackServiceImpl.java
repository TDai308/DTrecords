package com.example.dtrecords.service.Impl;

import com.example.dtrecords.model.Track;
import com.example.dtrecords.model.Vinyl;
import com.example.dtrecords.repository.TrackRepository;
import com.example.dtrecords.service.TrachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrackServiceImpl implements TrachService {

    @Autowired
    private TrackRepository trackRepository;

    @Override
    public Iterable<Track> findAll() {
        return trackRepository.findAll();
    }

    @Override
    public Optional<Track> findById(Long id) {
        return trackRepository.findById(id);
    }

    @Override
    public void save(Track track) {
        trackRepository.save(track);
    }

    @Override
    public void remove(Long trackID) {
        trackRepository.deleteById(trackID);
    }

    @Override
    public Iterable<Track> findAllByVinyl(Vinyl vinyl) {
        return trackRepository.findAllByVinyl(vinyl);
    }


}
