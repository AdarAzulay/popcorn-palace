package com.att.tdp.popcorn_palace.service;

import com.att.tdp.popcorn_palace.dto.ShowtimeDTO;
import java.util.List;

public interface ShowtimeService {
    List<ShowtimeDTO> getAllShowtimes();
    ShowtimeDTO getShowtimeById(Long id);
    ShowtimeDTO updateShowtime(Long showtimeId, ShowtimeDTO showtimeDTO);
    ShowtimeDTO addShowtime(ShowtimeDTO showtimeDTO);
    void deleteShowtime(Long showtimeId);
}
