package com.att.tdp.popcorn_palace.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDTO {

    private UUID bookingId;

    @NotNull(message = "Showtime ID is required")
    private Long showtimeId;

    @NotNull(message = "Seat number is required")
    @Positive(message = "Seat number must be positive")
    private Integer seatNumber;

    @NotNull(message = "User ID is required")
    private UUID userId;

}
