package com.att.tdp.popcorn_palace.controller;

import com.att.tdp.popcorn_palace.dto.MovieDTO;
import com.att.tdp.popcorn_palace.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<MovieDTO> addMovie(@RequestBody MovieDTO movieDTO) {
        return ResponseEntity.ok(movieService.addMovie(movieDTO));
    }

    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @PostMapping("/update/{movieTitle}")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable String movieTitle, @RequestBody MovieDTO movieDTO) {
        return ResponseEntity.ok(movieService.updateMovie(movieTitle, movieDTO));
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<String> deleteMovie(@PathVariable String title) {
        movieService.deleteMovie(title);
        return ResponseEntity.ok("Movie deleted successfully.");
    }
}