package com.caixeta.gildedrose.repository;

import com.caixeta.gildedrose.data.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository  extends JpaRepository<Item, Long> {
}
