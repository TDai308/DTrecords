package com.example.dtrecords.service;

import com.example.dtrecords.model.Track;
import com.example.dtrecords.model.Vinyl;

public interface TrachService extends GeneralService<Track>{

    Iterable<Track> findAllByVinyl(Vinyl vinyl);
}
