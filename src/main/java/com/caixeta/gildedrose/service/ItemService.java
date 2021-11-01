package com.caixeta.gildedrose.service;

import com.caixeta.gildedrose.dto.ItemResponse;

import java.util.List;

public interface ItemService {
    List<ItemResponse> getAllItems();
    ItemResponse getItemById(Long itemId);
    ItemResponse buyItem(Long itemId);
}
