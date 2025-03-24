package com.att.tdp.popcorn_palace.service;

import com.att.tdp.popcorn_palace.dto.MovieDTO;
import com.att.tdp.popcorn_palace.model.Movie;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    @Test
    void testAddMovie_SimpleSuccess() {
        // mock object set up
        MovieDTO movieDTO = new MovieDTO("Inception", "Sci-Fi", 148, 8.8, 2010);
        Movie movieEntity = Movie.builder()
                .id(1L)
                .title("Inception")
                .genre("Sci-Fi")
                .duration(148)
                .rating(8.8)
                .releaseYear(2010)
                .build();

        when(movieRepository.findByTitle("Inception")).thenReturn(Optional.empty());
        when(movieRepository.save(any(Movie.class))).thenReturn(movieEntity);
        MovieDTO result = movieService.addMovie(movieDTO);

        assertNotNull(result, "Result should not be null");
        assertEquals("Inception", result.getTitle(), "Title should match");
        verify(movieRepository).save(any(Movie.class)); // ensure save was called
    }

    @Test
    void testAddMovie_AlreadyExists() {

        MovieDTO movieDTO = new MovieDTO("Inception", "Sci-Fi", 148, 8.8, 2010);
        Movie existingMovie = Movie.builder()
                .id(1L)
                .title("Inception")
                .genre("Sci-Fi")
                .duration(148)
                .rating(8.8)
                .releaseYear(2010)
                .build();

        when(movieRepository.findByTitle("Inception")).thenReturn(Optional.of(existingMovie));
        assertThrows(IllegalArgumentException.class, () -> movieService.addMovie(movieDTO));
    }

    @Test
    void testUpdateMovie_MovieNotFound() {

        when(movieRepository.findByTitle("Nonexistent"))
                .thenReturn(Optional.empty());
        MovieDTO updateDTO = new MovieDTO("Nonexistent", "Action", 120, 8.0, 2015);
        assertThrows(EntityNotFoundException.class, () ->
                movieService.updateMovie("Nonexistent", updateDTO));
    }
}
