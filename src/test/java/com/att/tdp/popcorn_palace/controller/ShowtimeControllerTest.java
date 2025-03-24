package com.att.tdp.popcorn_palace.controller;

import com.att.tdp.popcorn_palace.dto.ShowtimeDTO;
import com.att.tdp.popcorn_palace.service.ShowtimeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ShowtimeController.class)
class ShowtimeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    // deprecation warning on MockBean
    @MockBean
    ShowtimeService showtimeService;

    @Test
    void testAddShowtime() throws Exception {
        ShowtimeDTO request = ShowtimeDTO.builder()
                .movieTitle("Inception")
                .theater("IMAX")
                .startTime(LocalDateTime.of(2025, 3, 22, 18, 30))
                .endTime(LocalDateTime.of(2025, 3, 22, 21, 0))
                .price(50.0)
                .build();

        ShowtimeDTO response = ShowtimeDTO.builder()
                .id(1L)
                .movieTitle("Inception")
                .theater("IMAX")
                .startTime(LocalDateTime.of(2025, 3, 22, 18, 30))
                .endTime(LocalDateTime.of(2025, 3, 22, 21, 0))
                .price(50.0)
                .build();

        when(showtimeService.addShowtime(any(ShowtimeDTO.class))).thenReturn(response);

        mockMvc.perform(post("/showtimes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testGetAllShowtimes() throws Exception {
        ShowtimeDTO showtime = ShowtimeDTO.builder()
                .id(1L)
                .movieTitle("Inception")
                .theater("IMAX")
                .startTime(LocalDateTime.of(2025, 3, 22, 18, 30))
                .endTime(LocalDateTime.of(2025, 3, 22, 21, 0))
                .price(50.0)
                .build();

        when(showtimeService.getAllShowtimes()).thenReturn(List.of(showtime));

        mockMvc.perform(get("/showtimes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }
}