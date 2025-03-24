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
        // Check if movie exists
        Movie movie = movieRepository.findByTitle(showtimeDTO.getMovieTitle())
                .orElseThrow(() -> new EntityNotFoundException("Movie not found: " + showtimeDTO.getMovieTitle()));


        // Prevent overlapping showtimes in the same theater
        boolean conflict = !showtimeRepository.findOverlappingShowtimes(
                showtimeDTO.getTheater(), showtimeDTO.getStartTime(), showtimeDTO.getEndTime()
        ).isEmpty();

        if (conflict) {
            throw new IllegalArgumentException("Overlapping showtime in the same theater");
        }

        // Convert DTO to entity and save
        Showtime showtime = toEntity(showtimeDTO);
        showtime.setMovie(movie); // Set the movie reference

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
        // 1. Find existing showtime by ID
        Showtime existing = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new EntityNotFoundException("Showtime not found: " + showtimeId));

        // 2. Update fields
        existing.setPrice(showtimeDTO.getPrice());
        existing.setTheater(showtimeDTO.getTheater());
        existing.setStartTime(showtimeDTO.getStartTime());
        existing.setEndTime(showtimeDTO.getEndTime());

        // 4. Save
        Showtime updated = showtimeRepository.save(existing);

        // 5. Return updated showtime as DTO
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
//                .movieId(showtime.getMovie().getId()) // Convert movie entity to movie ID
                .id(showtime.getId())
                .movieTitle(showtime.getMovie().getTitle())  // ✅ not ID
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
