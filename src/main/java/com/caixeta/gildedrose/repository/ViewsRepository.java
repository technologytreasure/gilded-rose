package com.caixeta.gildedrose.repository;

import com.caixeta.gildedrose.data.Views;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ViewsRepository extends JpaRepository<Views, Long> {
    Integer countAllByItemIdAndCreatedAtBetween(Long itemId, LocalDateTime initialDate, LocalDateTime finalDate);
    void deleteViewsByItemId(Long itemId);
}
