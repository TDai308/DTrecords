package com.example.dtrecords.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "vinyl")
public class Vinyl {
    @Id
    @GeneratedValue
    private Long vinylID;
    private String name;

    @ManyToOne
    @JoinColumn(name = "artist")
    private Artist artist;
    private String img1;
    private String img2;
    private Long quantity;
    private Float price;
    @ManyToOne
    @JoinColumn(name = "genre")
    private Genre genre;
    private String nation;
    private Boolean onSale;
    private Float salePrice;
    private Float realPrice;

    @OneToMany(targetEntity = Track.class)
    private List<Track> trackList;

    public Vinyl() {
    }

    public Vinyl(String name, Artist artist, String img1, String img2, Long quantity, Float price, Genre genre, String nation, Boolean onSale, Float salePrice, Float realPrice, List<Track> trackList) {
        this.name = name;
        this.artist = artist;
        this.img1 = img1;
        this.img2 = img2;
        this.quantity = quantity;
        this.price = price;
        this.genre = genre;
        this.nation = nation;
        this.onSale = onSale;
        this.salePrice = salePrice;
        this.realPrice = realPrice;
        this.trackList = trackList;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public List<Track> getTrackList() {
        return trackList;
    }

    public void setTrackList(List<Track> trackList) {
        this.trackList = trackList;
    }

    public Long getVinylID() {
        return vinylID;
    }

    public void setVinylID(Long vinylID) {
        this.vinylID = vinylID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Float salePrice) {
        this.salePrice = salePrice;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public Boolean getOnSale() {
        return onSale;
    }

    public void setOnSale(Boolean onSale) {
        this.onSale = onSale;
    }

    public Float getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(Float realPrice) {
        this.realPrice = realPrice;
    }
}
