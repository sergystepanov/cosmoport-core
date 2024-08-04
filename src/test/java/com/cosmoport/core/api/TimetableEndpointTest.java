package com.cosmoport.core.api;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TimetableEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should be able to fetch events with the API call")
    void shouldGet() throws Exception {
        this.mockMvc.perform(get("/timetable"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":10,")));
    }

    @Test
    @DisplayName("Should be able to delete an event with the API call")
    void delete() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/timetable/1"))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/timetable"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.not(containsString("\"id\":1,"))));
    }
}
