package com.caixeta.gildedrose.service;

import com.caixeta.gildedrose.data.Item;
import com.caixeta.gildedrose.dto.ItemResponse;
import com.caixeta.gildedrose.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ViewsServiceImpl viewsService;

    public ItemServiceImpl(ItemRepository itemRepository, ViewsServiceImpl viewsService) {
        this.itemRepository = itemRepository;
        this.viewsService = viewsService;
    }

    @Override
    public List<ItemResponse> getAllItems() {
        var items = itemRepository.findAllByActive(Boolean.TRUE);
        return items
                .stream()
                .map(item -> new ItemResponse(item.getId(), item.getName(), item.getDescription(), item.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ItemResponse getItemById(Long itemId) {
        var item = findActiveItemById(itemId);

        viewsService.insertNewView(item);
        var quantityViewsItem = viewsService.findCountViewsLastHourByItemId(item.getId());

        if (quantityViewsItem >= 10) {
            var increaseValue = (item.getPrice() * 10) / 100;
            item.setPrice(item.getPrice() + increaseValue);
            itemRepository.save(item);
            viewsService.deleteAllViewsByItemId(item.getId());
        }
        return new ItemResponse(item.getId(), item.getName(), item.getDescription(), item.getPrice());
    }

    @Override
    @Transactional
    public ItemResponse buyItem(Long itemId) {
        var item = findActiveItemById(itemId);
        item.setActive(Boolean.FALSE);
        itemRepository.save(item);
        return new ItemResponse(item.getId(), item.getName(), item.getDescription(), item.getPrice());
    }

    private Item findActiveItemById(Long itemId) {
        return itemRepository
                .findItemByIdAndActive(itemId, Boolean.TRUE)
                .orElseThrow(() -> new EntityNotFoundException());
    }

}
