package com.att.tdp.popcorn_palace.service;

import com.att.tdp.popcorn_palace.dto.ShowtimeDTO;
import com.att.tdp.popcorn_palace.model.Movie;
import com.att.tdp.popcorn_palace.model.Showtime;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShowtimeServiceTest {

    @Mock
    private ShowtimeRepository showtimeRepository;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private ShowtimeServiceImpl showtimeService;

    private Movie sampleMovie;
    private ShowtimeDTO sampleShowtimeDTO;
    private Showtime sampleShowtime;

    @BeforeEach
    void setUp() {
        sampleMovie = Movie.builder()
                .id(1L)
                .title("Inception")
                .genre("Sci-Fi")
                .duration(148)
                .rating(8.8)
                .releaseYear(2010)
                .build();

        sampleShowtimeDTO = ShowtimeDTO.builder()
                .movieTitle("Inception")
                .theater("IMAX")
                .startTime(LocalDateTime.of(2025, 3, 22, 18, 30))
                .endTime(LocalDateTime.of(2025, 3, 22, 21, 0))
                .price(50.0)
                .build();

        sampleShowtime = Showtime.builder()
                .id(1L)
                .theater("IMAX")
                .startTime(LocalDateTime.of(2025, 3, 22, 18, 30))
                .endTime(LocalDateTime.of(2025, 3, 22, 21, 0))
                .price(50.0)
                .movie(sampleMovie)
                .build();
    }

    @Test
    void testAddShowtime_Success() {
        when(movieRepository.findByTitle("Inception")).thenReturn(Optional.of(sampleMovie));
        when(showtimeRepository.findOverlappingShowtimes(anyString(), any(), any())).thenReturn(Collections.emptyList());
        when(showtimeRepository.save(any(Showtime.class))).thenReturn(sampleShowtime);

        ShowtimeDTO result = showtimeService.addShowtime(sampleShowtimeDTO);

        assertNotNull(result);
        assertEquals("IMAX", result.getTheater());
        assertEquals("Inception", result.getMovieTitle());
        verify(showtimeRepository, times(1)).save(any(Showtime.class));
    }

    @Test
    void testUpdateShowtime_Success() {
        // Arrange: simulate existing showtime is found.
        when(showtimeRepository.findById(1L)).thenReturn(Optional.of(sampleShowtime));
        when(showtimeRepository.save(any(Showtime.class))).thenReturn(sampleShowtime);

        ShowtimeDTO updateDTO = ShowtimeDTO.builder()
                .theater("IMAX Updated")
                .startTime(LocalDateTime.of(2025, 3, 22, 19, 0))
                .endTime(LocalDateTime.of(2025, 3, 22, 21, 30))
                .price(60.0)
                .build();

        ShowtimeDTO result = showtimeService.updateShowtime(1L, updateDTO);

        assertNotNull(result);
        assertEquals("IMAX Updated", result.getTheater());
        assertEquals(60.0, result.getPrice());
        verify(showtimeRepository, times(1)).save(any(Showtime.class));
    }

    @Test
    void testGetShowtimeById_NotFound() {

        when(showtimeRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> showtimeService.getShowtimeById(1L));
    }
}