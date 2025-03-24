package com.att.tdp.popcorn_palace.service;

import com.att.tdp.popcorn_palace.dto.MovieDTO;
import java.util.List;

public interface MovieService {

    List<MovieDTO> getAllMovies();
    MovieDTO addMovie(MovieDTO movie);
    MovieDTO updateMovie(String title, MovieDTO movie);
    void deleteMovie(String title);
}
