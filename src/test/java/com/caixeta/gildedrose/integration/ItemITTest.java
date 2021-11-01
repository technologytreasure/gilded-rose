package com.caixeta.gildedrose.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@PropertySource("classpath:application.properties")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ItemITTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void whenFindAllItems_thenReturnValidListItems() throws Exception {
        mvc.perform(get("/v1/items").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].name").value("Item 01 Name"))
                .andExpect(jsonPath("$.[0].price").value(200));
    }

    @Test
    public void whenFindItemById_thenReturnItem() throws Exception {
        mvc.perform(get("/v1/items/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Item 01 Name"))
                .andExpect(jsonPath("$.price").value(220));
    }

    @Test
    public void whenFindInvalidItemById_thenThrowEntityNotFoundException() throws Exception {
        assertThatThrownBy(() -> mvc.perform(get("/v1/items/8").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()))
                .hasCause(new EntityNotFoundException("Item not found"));
    }

    @Test
    public void whenBuyValidItem_thenReturnStatusOk() throws Exception {
        mvc.perform(post("/v1/items/1/buy")
                        .header("Authorization", "Basic YWRtaW46Z2lsZGVkcm9zZQ==")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.price").value(200));
    }

    @Test
    public void whenBuyUnauthorizedRequest_thenReturnStatusUnauthorized() throws Exception {
        mvc.perform(post("/v1/items/1/buy")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenBuyInvalidValidItem_thenReturnStatusExpectationFailed() throws Exception {
        mvc.perform(post("/v1/items/2/buy")
                        .header("Authorization", "Basic YWRtaW46Z2lsZGVkcm9zZQ==")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isExpectationFailed());
    }
}
