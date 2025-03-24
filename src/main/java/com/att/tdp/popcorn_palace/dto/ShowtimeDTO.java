package com.att.tdp.popcorn_palace.dto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowtimeDTO {

    private Long id;
    private String movieTitle;
    private Long movieId;
    private String theater;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double price;

}
