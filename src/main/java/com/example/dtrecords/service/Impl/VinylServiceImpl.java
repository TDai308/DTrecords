package com.example.dtrecords.service.Impl;

import com.example.dtrecords.model.Artist;
import com.example.dtrecords.model.Genre;
import com.example.dtrecords.model.Vinyl;
import com.example.dtrecords.repository.VinylRepository;
import com.example.dtrecords.service.VinylService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VinylServiceImpl implements VinylService {

    @Autowired
    private VinylRepository vinylRepository;

    @Override
    public Page<Vinyl> findAll(Pageable pageable) {
        return vinylRepository.findAll(pageable);
    }

    @Override
    public Page<Vinyl> findAllByRealPriceLessThanOrderByVinylIDDesc(Float realPrice, Pageable pageable) {
        return vinylRepository.findAllByRealPriceLessThanOrderByVinylIDDesc(realPrice,pageable);
    }

    @Override
    public Page<Vinyl> findAllByRealPriceLessThanOrderByRealPriceAsc(Float realPrice, Pageable pageable) {
        return vinylRepository.findAllByRealPriceLessThanOrderByRealPriceAsc(realPrice,pageable);
    }

    @Override
    public Page<Vinyl> findAllByRealPriceLessThanOrderByRealPriceDesc(Float realPrice, Pageable pageable) {
        return vinylRepository.findAllByRealPriceLessThanOrderByRealPriceDesc(realPrice,pageable);
    }

    @Override
    public Page<Vinyl> findAllByRealPriceGreaterThanOrderByVinylIDDesc(Float realPrice, Pageable pageable) {
        return vinylRepository.findAllByRealPriceGreaterThanOrderByVinylIDDesc(realPrice,pageable);
    }

    @Override
    public Page<Vinyl> findAllByRealPriceGreaterThanOrderByRealPriceAsc(Float realPrice, Pageable pageable) {
        return vinylRepository.findAllByRealPriceGreaterThanOrderByRealPriceAsc(realPrice, pageable);
    }

    @Override
    public Page<Vinyl> findAllByRealPriceGreaterThanOrderByRealPriceDesc(Float realPrice, Pageable pageable) {
        return vinylRepository.findAllByRealPriceGreaterThanOrderByRealPriceDesc(realPrice,pageable);
    }

    @Override
    public Page<Vinyl> findAllByGenreOrderByVinylIDDesc(Genre genre, Pageable pageable) {
        return vinylRepository.findAllByGenreOrderByVinylIDDesc(genre,pageable);
    }

    @Override
    public Page<Vinyl> findAllByGenreOrderByRealPriceDesc(Genre genre, Pageable pageable) {
        return vinylRepository.findAllByGenreOrderByRealPriceDesc(genre,pageable);
    }

    @Override
    public Page<Vinyl> findAllByGenreOrderByRealPriceAsc(Genre genre, Pageable pageable) {
        return vinylRepository.findAllByGenreOrderByRealPriceAsc(genre,pageable);
    }

    @Override
    public Page<Vinyl> findAllByNationOrderByVinylIDDesc(String nation, Pageable pageable) {
        return vinylRepository.findAllByNationOrderByVinylIDDesc(nation,pageable);
    }

    @Override
    public Page<Vinyl> findAllByNationOrderByRealPriceDesc(String nation, Pageable pageable) {
        return vinylRepository.findAllByNationOrderByRealPriceDesc(nation,pageable);
    }

    @Override
    public Page<Vinyl> findAllByNationOrderByRealPriceAsc(String nation, Pageable pageable) {
        return vinylRepository.findAllByNationOrderByRealPriceAsc(nation,pageable);
    }

    @Override
    public Page<Vinyl> findTop10ByOrderByVinylIDDesc(Pageable pageable) {
        return vinylRepository.findTop10ByOrderByVinylIDDesc(pageable);
    }

    @Override
    public Iterable<Vinyl> findTop10ByQuantityBetweenOrderByVinylIDDesc(Long quantity, Long quantity2) {
        return vinylRepository.findTop10ByQuantityBetweenOrderByVinylIDDesc(quantity,quantity2);
    }

    @Override
    public Iterable<Vinyl> findAllByOnSaleAndQuantityAfterOrderByVinylIDDesc(Boolean onSale, Long quantity) {
        return vinylRepository.findAllByOnSaleAndQuantityAfterOrderByVinylIDDesc(onSale,quantity);
    }

    @Override
    public Page<Vinyl> findAllByOrderByVinylIDDesc(Pageable pageable) {
        return vinylRepository.findAllByOrderByVinylIDDesc(pageable);
    }

    @Override
    public Page<Vinyl> findAllBydOrderByRealPriceDesc(Pageable pageable) {
        return vinylRepository.findAllByOrderByRealPriceDesc(pageable);
    }

    @Override
    public Page<Vinyl> findAllBydOrderByRealPriceAsc(Pageable pageable) {
        return vinylRepository.findAllByOrderByRealPriceAsc(pageable);
    }

    @Override
    public Iterable<Vinyl> findAll() {
        return vinylRepository.findAll();
    }

    @Override
    public Optional<Vinyl> findById(Long vinylID) {
        return vinylRepository.findById(vinylID);
    }

    @Override
    public void save(Vinyl vinyl) {
        vinylRepository.save(vinyl);
    }

    @Override
    public void remove(Long vinylID) {
        vinylRepository.deleteById(vinylID);
    }

    @Override
    public Page<Vinyl> findAllByName(String name, Pageable pageable) {
        return vinylRepository.findAllByName(name, pageable);
    }

    @Override
    public Iterable<Vinyl> findAllByArtistAndNameNotLikeAndQuantityAfter(Artist artisName, String name, Long quantity) {
        return vinylRepository.findAllByArtistAndNameNotLikeAndQuantityAfter(artisName, name, quantity);
    }

    @Override
    public Iterable<Vinyl> findAllByGenreAndNameNotLikeAndQuantityAfter(Genre genre, String name, Long quantity) {
        return vinylRepository.findAllByGenreAndNameNotLikeAndQuantityAfter(genre,name,quantity);
    }
}
