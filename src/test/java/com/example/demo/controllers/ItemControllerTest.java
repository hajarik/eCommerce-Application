package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {
    private ItemController itemController;
    private ItemRepository itemRepo = mock(ItemRepository.class);


    @Before
    public void setUp(){
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepo);
    }
    @Test
    public void testGetItems() {
        Item item1 = new Item();
        item1.setId(1L);
        item1.setName("Item 1");
        item1.setDescription("Description of Item 1");
        item1.setPrice(BigDecimal.valueOf(10.00));

        Item item2 = new Item();
        item2.setId(2L);
        item2.setName("Item 2");
        item2.setDescription("Description of Item 2");
        item2.setPrice(BigDecimal.valueOf(20.00));

        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        when(itemRepo.findAll()).thenReturn(items);

        ResponseEntity<List<Item>> responseEntity = itemController.getItems();
        List<Item> responseItems = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseItems);
        assertEquals(2, responseItems.size());
        assertEquals(item1, responseItems.get(0));
        assertEquals(item2, responseItems.get(1));
    }

    @Test
    public void testGetItemById() {
        // create a test item
        Item item = new Item();
        item.setId(1L);
        item.setName("Test Item");
        item.setDescription("This is a test item");
        item.setPrice(BigDecimal.valueOf(9.99));

        // configure the item repository mock to return the test item
        when(itemRepo.findById(1L)).thenReturn(Optional.of(item));

        // call the getItemById method with id 1
        ResponseEntity<Item> response = itemController.getItemById(1L);

        // verify that the response is successful
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // verify that the returned item is the same as the test item
        assertEquals(item, response.getBody());
    }


}
