package com.att.tdp.popcorn_palace.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue
    private UUID bookingId;

    @Column(nullable = false)
    private Integer seatNumber;

    @Column(nullable = false)
    private UUID userId;

    @ManyToOne
    @JoinColumn(name = "showtime_id", nullable = false)
    private Showtime showtime;
}