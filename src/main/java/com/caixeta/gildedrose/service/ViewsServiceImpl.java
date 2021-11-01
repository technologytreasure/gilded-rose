package com.caixeta.gildedrose.service;

import com.caixeta.gildedrose.data.Item;
import com.caixeta.gildedrose.data.Views;
import com.caixeta.gildedrose.repository.ViewsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
    public Integer findCountViewsLastHourByItemId(Long itemId) {
        return viewsRepository
                .countAllByItemIdAndCreatedAtBetween(itemId, LocalDateTime.now().minusHours(1), LocalDateTime.now());
    }

    @Override
    @Transactional
    public void deleteAllViewsByItemId(Long itemId) {
        viewsRepository.deleteViewsByItemId(itemId);
    }

}
