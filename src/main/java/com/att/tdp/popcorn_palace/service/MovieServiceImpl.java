package com.att.tdp.popcorn_palace.service;

import com.att.tdp.popcorn_palace.dto.MovieDTO;
import com.att.tdp.popcorn_palace.model.Movie;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<MovieDTO> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MovieDTO addMovie(MovieDTO movieDTO) {
        if (movieRepository.findByTitle(movieDTO.getTitle()).isPresent()) {
            throw new IllegalArgumentException("Movie with this title already exists");
        }

        Movie movie = toEntity(movieDTO);
        Movie saved = movieRepository.save(movie);
        return toDTO(saved);
    }

    @Override
    public MovieDTO updateMovie(String pathTitle, MovieDTO bodyDTO) {
        // 1. Check if the path param's title exists
        Movie existing = movieRepository.findByTitle(pathTitle)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found: " + pathTitle));

        if (!pathTitle.equals(bodyDTO.getTitle())) {
            throw new IllegalArgumentException(
                    "Title in path (" + pathTitle + ") does not match title in request body (" + bodyDTO.getTitle() + ")"
            );
        }


        existing.setGenre(bodyDTO.getGenre());
        existing.setDuration(bodyDTO.getDuration());
        existing.setRating(bodyDTO.getRating());
        existing.setReleaseYear(bodyDTO.getReleaseYear());

        Movie updated = movieRepository.save(existing);
        return toDTO(updated);
    }

    @Override
    public void deleteMovie(String title) {
        Movie existing = movieRepository.findByTitle(title)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found"));
        movieRepository.delete(existing);
    }

    // Mapping Methods
    private MovieDTO toDTO(Movie movie) {
        return MovieDTO.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .genre(movie.getGenre())
                .duration(movie.getDuration())
                .rating(movie.getRating())
                .releaseYear(movie.getReleaseYear())
                .build();
    }

    private Movie toEntity(MovieDTO dto) {
        return Movie.builder()
                .title(dto.getTitle())
                .genre(dto.getGenre())
                .duration(dto.getDuration())
                .rating(dto.getRating())
                .releaseYear(dto.getReleaseYear())
                .build();
    }
}
