package com.example.dtrecords.service;

import com.example.dtrecords.model.Artist;
import com.example.dtrecords.model.Genre;
import com.example.dtrecords.model.Vinyl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface VinylService extends GeneralService<Vinyl>{
    Page<Vinyl> findAll(Pageable pageable);

    Page<Vinyl> findAllByRealPriceLessThanOrderByVinylIDDesc(Float realPrice, Pageable pageable);

    Page<Vinyl> findAllByRealPriceLessThanOrderByRealPriceAsc(Float realPrice, Pageable pageable);

    Page<Vinyl> findAllByRealPriceLessThanOrderByRealPriceDesc(Float realPrice, Pageable pageable);

    Page<Vinyl> findAllByRealPriceGreaterThanOrderByVinylIDDesc(Float realPrice, Pageable pageable);

    Page<Vinyl> findAllByRealPriceGreaterThanOrderByRealPriceAsc(Float realPrice, Pageable pageable);

    Page<Vinyl> findAllByRealPriceGreaterThanOrderByRealPriceDesc(Float realPrice, Pageable pageable);

    Page<Vinyl> findAllByGenreOrderByVinylIDDesc(Genre genre, Pageable pageable);

    Page<Vinyl> findAllByGenreOrderByRealPriceDesc(Genre genre, Pageable pageable);

    Page<Vinyl> findAllByGenreOrderByRealPriceAsc(Genre genre, Pageable pageable);

    Page<Vinyl> findAllByNationOrderByVinylIDDesc(String nation, Pageable pageable);

    Page<Vinyl> findAllByNationOrderByRealPriceDesc(String nation, Pageable pageable);

    Page<Vinyl> findAllByNationOrderByRealPriceAsc(String nation, Pageable pageable);

    Page<Vinyl> findTop10ByOrderByVinylIDDesc(Pageable pageable);

    Iterable<Vinyl> findTop10ByQuantityBetweenOrderByVinylIDDesc(Long quantity, Long quantity2);

    Iterable<Vinyl> findAllByOnSaleAndQuantityAfterOrderByVinylIDDesc(Boolean onSale, Long quantity);

    Page<Vinyl> findAllByOrderByVinylIDDesc(Pageable pageable);

    Page<Vinyl> findAllBydOrderByRealPriceDesc(Pageable pageable);

    Page<Vinyl> findAllBydOrderByRealPriceAsc(Pageable pageable);

    Page<Vinyl> findAllByName(String name, Pageable pageable);

    Iterable<Vinyl> findAllByArtistAndNameNotLikeAndQuantityAfter(Artist artisName, String name, Long quantity);

    Iterable<Vinyl> findAllByGenreAndNameNotLikeAndQuantityAfter(Genre genre, String name, Long quantity);
}
