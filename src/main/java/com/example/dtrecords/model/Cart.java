package com.example.dtrecords.model;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private final Map<Long, Integer> selectedVinyl = new HashMap<>();

    public Map<Long, Integer> getSelectedVinyl() {
        return selectedVinyl;
    }

    public void setSelectedVinyl(Long vinylId, Integer number) {
        selectedVinyl.put(vinylId, number);
    }

    public void removeProduct(Long vinylId) {
        selectedVinyl.remove(vinylId);
    }

    public void removeAll() { selectedVinyl.clear(); }

    public boolean empty() {return selectedVinyl.isEmpty();}
}
