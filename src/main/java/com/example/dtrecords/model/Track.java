package com.example.dtrecords.model;

import javax.persistence.*;

@Entity
@Table(name = "track")
public class Track {
    @Id
    @GeneratedValue
    private Long trackID;
    private String name;
    private String artistName;

    @ManyToOne
    @JoinColumn(name = "vinyl_id")
    private Vinyl vinyl;

    public Track() {
    }

    public Track(String name, String artistName, Vinyl vinyl) {
        this.name = name;
        this.artistName = artistName;
        this.vinyl = vinyl;
    }

    public Long getTrackID() {
        return trackID;
    }

    public void setTrackID(Long trackID) {
        this.trackID = trackID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public Vinyl getVinyl() {
        return vinyl;
    }

    public void setVinyl(Vinyl vinyl) {
        this.vinyl = vinyl;
    }
}
