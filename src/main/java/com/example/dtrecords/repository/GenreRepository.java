package com.example.dtrecords.repository;

import com.example.dtrecords.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre,Long> {
    Genre findByGenrename(String genrename);
}
