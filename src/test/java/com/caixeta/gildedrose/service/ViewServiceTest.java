package com.caixeta.gildedrose.service;

import com.caixeta.gildedrose.data.Item;
import com.caixeta.gildedrose.repository.ViewsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ViewServiceTest {

    @Mock
    private ViewsRepository viewsRepository;

    @InjectMocks
    private ViewsServiceImpl viewsService;

    @Test
    public void whenCreateNewItem_thenSaveItemSuccess() {
        //given
        var item = mockItem();

        //when
       viewsService.insertNewView(item);

        //then
        verify(viewsRepository).save(any());
        verify(viewsRepository, times(1)).save(argThat(v -> v.getItemId().equals(item.getId())));
    }

    @Test
    public void whenFindLastOneHourViews_thenReturnViewsList() {
        //given
        var item = mockItem();

        //when
        when(viewsRepository
                .countAllByItemIdAndCreatedAtBetween(Mockito.eq(item.getId()), Mockito.any(), Mockito.any()))
                .thenReturn(1);
        var quantityViews = viewsService.findCountViewsLastHourByItemId(item.getId());

        //then
        assertNotNull(quantityViews);
        assertEquals(1, quantityViews);
    }

    @Test
    public void whenDeleteViewsByItem_thenRemoveViewsSuccess() {
        //given
        var item = mockItem();

        //when
        viewsService.deleteAllViewsByItemId(item.getId());

        //then
        verify(viewsRepository, times(1)).deleteViewsByItemId(item.getId());
    }

    private Item mockItem() {
        var item = new Item();
        item.setId(1L);
        item.setName("Item 01");
        item.setDescription("Item 01");
        item.setPrice(50);
        item.setActive(Boolean.TRUE);
        return item;
    }

}
