package com.caixeta.gildedrose.service;

import com.caixeta.gildedrose.data.Item;
import com.caixeta.gildedrose.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ViewsServiceImpl viewsService;

    @InjectMocks
    private ItemServiceImpl itemService;

    @Test
    public void whenFindAllItems_thenReturnListItems() {
        //given
        var itemList = mockItemList();

        //when
        Mockito.when(itemRepository.findAllByActive(Mockito.eq(Boolean.TRUE))).thenReturn(itemList);
        var itemListResponse = itemService.getAllItems();

        //then
        assertNotNull(itemListResponse);
        assertEquals("Item 01", itemListResponse.get(0).getName());
        assertEquals(3, itemListResponse.size());
    }

    @Test
    public void whenFindValidItemById_thenReturnValidItem() {
        //given
        var item = mockItem(Boolean.TRUE);

        //when
        Mockito.when(itemRepository.findItemByIdAndActive(Mockito.eq(1L), Mockito.eq(Boolean.TRUE))).thenReturn(item);
        var itemResponse = itemService.getItemById(1L);

        //then
        assertNotNull(itemResponse);
        assertEquals("Item 01", itemResponse.getName());
    }

    @Test
    public void whenFindInvalidItemById_thenReturnException() {
        //when
        Mockito.when(itemRepository.findItemByIdAndActive(Mockito.eq(1L), Mockito.eq(Boolean.TRUE)))
                .thenThrow(EntityNotFoundException.class);

        //then
        assertThrows(EntityNotFoundException.class, () -> itemService.getItemById(1L));
    }

    @Test
    public void whenFindItemLessViewedThanNineTimesById_thenReturnItemWithSameValue() {
        //given
        var item = mockItem(Boolean.TRUE);

        //when
        Mockito.when(itemRepository.findItemByIdAndActive(Mockito.eq(1L), Mockito.eq(Boolean.TRUE)))
                .thenReturn(item);
        var itemResponse = itemService.getItemById(1L);

        //then
        assertNotNull(itemResponse);
        assertEquals(item.get().getId(), itemResponse.getId());
        assertEquals(item.get().getName(), itemResponse.getName());
        assertEquals(item.get().getPrice(), itemResponse.getPrice());
    }

    @Test
    public void whenFindItemMoreViewedThanNineTimesById_thenReturnItemWithNewValue() {
        //given
        var item = mockItem(Boolean.TRUE);

        //when
        Mockito.when(itemRepository.findItemByIdAndActive(Mockito.eq(1L), Mockito.eq(Boolean.TRUE))).thenReturn(item);
        Mockito.when(viewsService.findAllLastHourByItemId(1L)).thenReturn(10);
        var itemResponse = itemService.getItemById(1L);

        //then
        assertNotNull(itemResponse);
        assertEquals(item.get().getId(), itemResponse.getId());
        assertEquals(item.get().getName(), itemResponse.getName());
        assertEquals(55, itemResponse.getPrice());
    }

    @Test
    public void whenBuyValidItem_thenReturnItemBought() {
        //given
        var item = mockItem(Boolean.TRUE);

        //when
        Mockito.when(itemRepository.findItemByIdAndActive(Mockito.eq(1L), Mockito.eq(Boolean.TRUE))).thenReturn(item);
        var itemBought = itemService.buyItem(1L);

        //then
        assertNotNull(itemBought);
        assertEquals(item.get().getId(), itemBought.getId());
    }

    private List<Item> mockItemList() {
        var itemList = new ArrayList<Item>();

        var item = new Item();
        item.setId(1L);
        item.setName("Item 01");
        item.setDescription("Item 01");
        item.setPrice(50);
        item.setActive(Boolean.TRUE);
        itemList.add(item);

        item = new Item();
        item.setId(1L);
        item.setName("Item 02");
        item.setDescription("Item 02");
        item.setPrice(100);
        item.setActive(Boolean.TRUE);
        itemList.add(item);

        item = new Item();
        item.setId(3L);
        item.setName("Item 03");
        item.setDescription("Item 03");
        item.setPrice(150);
        item.setActive(Boolean.TRUE);
        itemList.add(item);

        return itemList;
    }

    private Optional<Item> mockItem(Boolean active) {
        var item = new Item();
        item.setId(1L);
        item.setName("Item 01");
        item.setDescription("Item 01");
        item.setPrice(50);
        item.setActive(active);

        return Optional.of(item);
    }

}
