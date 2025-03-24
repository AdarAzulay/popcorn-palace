package com.att.tdp.popcorn_palace.repository;

import com.att.tdp.popcorn_palace.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Before booking a movie , if a record already exists with the same showtimeId and seatNumber
    //prevent double booking
    boolean existsByShowtimeIdAndSeatNumber(Long showtimeId, Integer seatNumber);

}
