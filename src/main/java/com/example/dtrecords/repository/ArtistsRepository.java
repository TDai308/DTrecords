package com.example.dtrecords.repository;

import com.example.dtrecords.model.Artist;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistsRepository extends PagingAndSortingRepository<Artist,Long> {
    Iterable<Artist> findTop5ByOrderByNameAsc();
}
