package com.att.tdp.popcorn_palace.service;

import com.att.tdp.popcorn_palace.dto.ShowtimeDTO;
import com.att.tdp.popcorn_palace.model.Movie;
import com.att.tdp.popcorn_palace.model.Showtime;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowtimeServiceImpl implements ShowtimeService {

    private final ShowtimeRepository showtimeRepository;
    private final MovieRepository movieRepository;

    public ShowtimeServiceImpl(ShowtimeRepository showtimeRepository, MovieRepository movieRepository) {
        this.showtimeRepository = showtimeRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public List<ShowtimeDTO> getAllShowtimes() {
        return showtimeRepository.findAll().stream()
                .map(this::toDTO) // Convert each Showtime entity to DTO
                .collect(Collectors.toList());
    }

    @Override
    public ShowtimeDTO addShowtime(ShowtimeDTO showtimeDTO) {
        Movie movie = movieRepository.findById(showtimeDTO.getMovieId())
                .orElseThrow(() -> new EntityNotFoundException("Movie not found with id: " + showtimeDTO.getMovieId()));

        if (showtimeDTO.getEndTime() == null) {
            showtimeDTO.setEndTime(showtimeDTO.getStartTime().plusMinutes(movie.getDuration()));
        }
        if (!showtimeDTO.getStartTime().isBefore(showtimeDTO.getEndTime())) {
            throw new IllegalArgumentException("startTime must be before endTime");
        }
        boolean conflict = !showtimeRepository.findOverlappingShowtimes(
                showtimeDTO.getTheater(), showtimeDTO.getStartTime(), showtimeDTO.getEndTime()
        ).isEmpty();
        if (conflict) {
            throw new IllegalArgumentException("Overlapping showtime in the same theater");
        }
        Showtime showtime = toEntity(showtimeDTO);
        showtime.setMovie(movie);
        Showtime savedShowtime = showtimeRepository.save(showtime);
        return toDTO(savedShowtime);
    }

    @Override
    public ShowtimeDTO getShowtimeById(Long id) {
        Showtime showtime = showtimeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Showtime not found"));
        return toDTO(showtime);
    }

    @Override
    public ShowtimeDTO updateShowtime(Long showtimeId, ShowtimeDTO showtimeDTO) {
        // Find existing showtime by ID
        Showtime existing = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new EntityNotFoundException("Showtime not found: " + showtimeId));
        if (!showtimeDTO.getStartTime().isBefore(showtimeDTO.getEndTime())) {
            throw new IllegalArgumentException("startTime must be before endTime");
        }

        boolean conflict = showtimeRepository.findOverlappingShowtimes(
                showtimeDTO.getTheater(), showtimeDTO.getStartTime(), showtimeDTO.getEndTime()
        ).stream().anyMatch(s -> !s.getId().equals(showtimeId));
        if (conflict) {
            throw new IllegalArgumentException("Overlapping showtime in the same theater");
        }
        // Update fields
        existing.setPrice(showtimeDTO.getPrice());
        existing.setTheater(showtimeDTO.getTheater());
        existing.setStartTime(showtimeDTO.getStartTime());
        existing.setEndTime(showtimeDTO.getEndTime());
        Showtime updated = showtimeRepository.save(existing);
        return toDTO(updated);
    }

    @Override
    public void deleteShowtime(Long showtimeId) {
        if (!showtimeRepository.existsById(showtimeId)) {
            throw new EntityNotFoundException("Showtime not found");
        }
        showtimeRepository.deleteById(showtimeId);
    }

    // Mapping Methods

    private ShowtimeDTO toDTO(Showtime showtime) {
        return ShowtimeDTO.builder()
                .movieTitle(showtime.getMovie().getTitle())
                .theater(showtime.getTheater())
                .startTime(showtime.getStartTime())
                .endTime(showtime.getEndTime())
                .price(showtime.getPrice())
                .build();
    }

    private Showtime toEntity(ShowtimeDTO dto) {
        return Showtime.builder()
                .theater(dto.getTheater())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .price(dto.getPrice())
                .build();
    }
}