package com.caixeta.gildedrose.service;

import com.caixeta.gildedrose.data.Item;

public interface ViewService {

    void insertNewView(Item item);
    void deleteAllViewsByItemId(Long itemId);
    Integer findAllLastHourByItemId(Long itemId);
}
