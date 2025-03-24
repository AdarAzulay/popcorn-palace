package com.att.tdp.popcorn_palace.controller;

import com.att.tdp.popcorn_palace.dto.MovieDTO;
import com.att.tdp.popcorn_palace.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MovieService movieService;

    @Test
    void testAddMovie() throws Exception {
        MovieDTO request = MovieDTO.builder()
                .id(null)
                .title("Inception")
                .genre("Sci-Fi")
                .duration(148)
                .rating(8.8)
                .releaseYear(2010)
                .build();

        MovieDTO response = MovieDTO.builder()
                .id(1L)
                .title("Inception")
                .genre("Sci-Fi")
                .duration(148)
                .rating(8.8)
                .releaseYear(2010)
                .build();

        when(movieService.addMovie(any(MovieDTO.class))).thenReturn(response);

        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Inception"));
    }

    @Test
    void testGetAllMovies() throws Exception {
        MovieDTO movie = MovieDTO.builder()
                .id(1L)
                .title("Inception")
                .genre("Sci-Fi")
                .duration(148)
                .rating(8.8)
                .releaseYear(2010)
                .build();

        when(movieService.getAllMovies()).thenReturn(List.of(movie));

        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Inception"));
    }
}