package com.att.tdp.popcorn_palace.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDTO {

    private String title;
    private String genre;
    private Integer duration; // mins
    private Double rating;
    private Integer releaseYear;

}
