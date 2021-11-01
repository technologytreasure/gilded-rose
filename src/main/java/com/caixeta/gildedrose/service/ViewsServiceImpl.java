package com.caixeta.gildedrose.service;

import com.caixeta.gildedrose.data.Item;
import com.caixeta.gildedrose.data.Views;
import com.caixeta.gildedrose.dto.ItemResponse;
import com.caixeta.gildedrose.repository.ItemRepository;
import com.caixeta.gildedrose.repository.ViewsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.swing.text.View;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ViewsServiceImpl implements ViewService {

    private final ViewsRepository viewsRepository;

    public ViewsServiceImpl(ViewsRepository viewsRepository) {
        this.viewsRepository = viewsRepository;
    }

    @Override
    @Transactional
    public void insertNewView(Item item) {
        var views = new Views();
        views.setItemId(item.getId());
        viewsRepository.save(views);
    }

    @Override
    public Integer findAllLastHourByItemId(Long itemId) {
        return viewsRepository
                .countAllByItemIdAndCreatedAtBetween(itemId, LocalDateTime.now().minusHours(1), LocalDateTime.now());
    }

    @Override
    @Transactional
    public void deleteAllViewsByItemId(Long itemId) {
        viewsRepository.deleteViewsByItemId(itemId);
    }

}
