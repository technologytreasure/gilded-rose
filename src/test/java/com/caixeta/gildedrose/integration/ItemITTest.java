package com.caixeta.gildedrose.integration;

import org.assertj.core.api.Assertions;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
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
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$.[0].name").value("Item 01 Name"));
    }

    @Test
    public void whenFindItemMoreViewedThanNineTimesById_thenReturnItemWithNewValue() throws Exception {
        mvc.perform(get("/v1/items/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Item 01 Name"))
                .andExpect(jsonPath("$.price").value(220));
    }

    @Test
    public void whenFindItemLessViewedThanNineTimesById_thenReturnItemWithSameValue() throws Exception {
        mvc.perform(get("/v1/items/3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.name").value("Item 03 Name"))
                .andExpect(jsonPath("$.price").value(135));
    }

    @Test
    public void whenFindItemMoreViewedThanNineTimesOutOfAnHourById_thenReturnItemWithSameValue() throws Exception {
        mvc.perform(get("/v1/items/4").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4L))
                .andExpect(jsonPath("$.name").value("Item 04 Name"))
                .andExpect(jsonPath("$.price").value(500));
    }

    @Test
    public void whenFindInvalidItemById_thenThrowEntityNotFoundException() throws Exception {
        try {
            mvc.perform(get("/v1/items/8").contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            Assertions.setMaxStackTraceElementsDisplayed(1);
            Assertions.assertThatExceptionOfType(EntityNotFoundException.class);
            assertThat(e).hasMessageContaining("Item not found or not available!");
        }
    }

    @Test
    public void whenBuyValidItem_thenReturnStatusOk() throws Exception {
        mvc.perform(post("/v1/items/1/buy")
                        .header("Authorization", "Basic YWRtaW46Z2lsZGVkcm9zZQ==")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.price").value(220));
    }

    @Test
    public void whenBuyUnauthorizedRequest_thenReturnStatusUnauthorized() throws Exception {
        mvc.perform(post("/v1/items/1/buy")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenBuyInvalidItem_thenReturnStatusExpectationFailed() throws Exception {
        try {
            mvc.perform(post("/v1/items/2/buy")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Basic YWRtaW46Z2lsZGVkcm9zZQ=="));
        } catch (Exception e) {
            Assertions.setMaxStackTraceElementsDisplayed(1);
            Assertions.assertThatExceptionOfType(EntityNotFoundException.class);
            assertThat(e).hasMessageContaining("Item not found or not available!");
        }
    }
}
