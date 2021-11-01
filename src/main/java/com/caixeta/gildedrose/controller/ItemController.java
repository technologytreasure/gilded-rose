package com.caixeta.gildedrose.controller;

import com.caixeta.gildedrose.dto.ItemResponse;
import com.caixeta.gildedrose.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/items", produces = "application/json")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping()
    public ResponseEntity<List<ItemResponse>> getAllItems() {
        return new ResponseEntity<>(itemService.getAllItems(), HttpStatus.OK);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponse> findItemById(@PathVariable Long itemId) {
        return new ResponseEntity<>(itemService.getItemById(itemId), HttpStatus.OK);
    }

    @PostMapping("/{itemId}/buy")
    public ResponseEntity<ItemResponse> buyItemById(@PathVariable Long itemId) {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.buyItem(itemId));
    }

}
