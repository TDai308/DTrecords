package com.example.dtrecords.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "artist")
public class Artist {

    @Id
    @GeneratedValue
    private Long IDartist;
    private String name;

    public Artist() {
    }

    public Artist(String name) {
        this.name = name;
    }

    public Long getIDartist() {
        return IDartist;
    }

    public void setIDartist(Long IDartist) {
        this.IDartist = IDartist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
