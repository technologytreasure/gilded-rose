package com.caixeta.gildedrose.repository;

import com.caixeta.gildedrose.data.Views;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.View;
import java.time.LocalDateTime;
import java.util.List;

public interface ViewsRepository extends JpaRepository<Views, Long> {

    Integer countAllByItemIdAndCreatedAtBetween(Long itemId, LocalDateTime initalDate, LocalDateTime finalDate);

    void deleteViewsByItemId(Long itemId);
}
