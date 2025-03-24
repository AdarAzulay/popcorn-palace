package com.att.tdp.popcorn_palace.controller;

import com.att.tdp.popcorn_palace.dto.BookingDTO;
import com.att.tdp.popcorn_palace.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // deprecation warning on MockBean
    @MockBean
    private BookingService bookingService;

    @Test
    void testBookSeat_Success() throws Exception {
        BookingDTO bookingRequest = BookingDTO.builder()
                .showtimeId(1L)
                .seatNumber(15)
                .userId(UUID.fromString("84438967-f68f-4fa0-b620-0f08217e76af"))
                .build();

        BookingDTO bookingResponse = BookingDTO.builder()
                .bookingId(UUID.randomUUID())
                .build();

        when(bookingService.bookSeat(any(BookingDTO.class))).thenReturn(bookingResponse);

        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingId").exists());
    }

    @Test
    void testGetBookingsByShowtime_Success() throws Exception {
        BookingDTO booking = BookingDTO.builder()
                .bookingId(UUID.randomUUID())
                .showtimeId(1L)
                .seatNumber(15)
                .userId(UUID.fromString("84438967-f68f-4fa0-b620-0f08217e76af"))
                .build();

        when(bookingService.getBookingsByShowtime(1L)).thenReturn(List.of(booking));
        mockMvc.perform(get("/bookings/showtime/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookingId").exists());
    }
}