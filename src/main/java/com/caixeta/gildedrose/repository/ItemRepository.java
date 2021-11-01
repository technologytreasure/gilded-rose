package com.caixeta.gildedrose.repository;

import com.caixeta.gildedrose.data.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository  extends JpaRepository<Item, Long> {

    Optional<Item> findItemByIdAndActive(Long itemId, Boolean active);
    List<Item> findAllByActive(Boolean active);

}
