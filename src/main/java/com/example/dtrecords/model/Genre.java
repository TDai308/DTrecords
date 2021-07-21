package com.example.dtrecords.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "genre")
public class Genre {

    @Id
    @GeneratedValue
    private Long genreID;
    private String genrename;

    public Genre() {
    }

    public Genre(String genrename) {
        this.genrename = genrename;
    }

    public Long getGenreID() {
        return genreID;
    }

    public void setGenreID(Long genreID) {
        this.genreID = genreID;
    }

    public String getGenrename() {
        return genrename;
    }

    public void setGenrename(String genrename) {
        this.genrename = genrename;
    }
}
