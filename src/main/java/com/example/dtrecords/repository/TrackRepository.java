package com.example.dtrecords.repository;

import com.example.dtrecords.model.Track;
import com.example.dtrecords.model.Vinyl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends JpaRepository<Track,Long> {
    Iterable<Track> findAllByVinyl(Vinyl vinyl);
}
