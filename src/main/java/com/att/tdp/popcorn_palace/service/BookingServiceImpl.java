package com.att.tdp.popcorn_palace.service;

import com.att.tdp.popcorn_palace.dto.BookingDTO;
import com.att.tdp.popcorn_palace.model.Booking;
import com.att.tdp.popcorn_palace.model.Showtime;
import com.att.tdp.popcorn_palace.repository.BookingRepository;
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ShowtimeRepository showtimeRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, ShowtimeRepository showtimeRepository) {
        this.bookingRepository = bookingRepository;
        this.showtimeRepository = showtimeRepository;
    }

    @Override
    public List<BookingDTO> getBookingsByShowtime(Long showtimeId) {
        return bookingRepository.findAll().stream()
                .filter(booking -> booking.getShowtime().getId().equals(showtimeId))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookingDTO bookSeat(BookingDTO bookingDTO) {
        // check if the showtime exists
        Showtime showtime = showtimeRepository.findById(bookingDTO.getShowtimeId())
                .orElseThrow(() -> new EntityNotFoundException("Showtime not found"));

        // check if the seat is already booked
        boolean isBooked = bookingRepository.existsByShowtimeIdAndSeatNumber(
                bookingDTO.getShowtimeId(), bookingDTO.getSeatNumber());

        if (isBooked) {
            throw new IllegalArgumentException("Seat already booked for this showtime");
        }

        // save booking
        Booking booking = toEntity(bookingDTO);
        booking.setShowtime(showtime);

        Booking savedBooking = bookingRepository.save(booking);

        // return only the booking ID
        return BookingDTO.builder()
                .bookingId(savedBooking.getBookingId())
                .build();
    }

    // Mapping Methods
    private BookingDTO toDTO(Booking booking) {
        return BookingDTO.builder()
                .bookingId(booking.getBookingId())
                .showtimeId(booking.getShowtime().getId())
                .seatNumber(booking.getSeatNumber())
                .userId(booking.getUserId())
                .build();
    }

    private Booking toEntity(BookingDTO dto) {
        return Booking.builder()
                .seatNumber(dto.getSeatNumber())
                .userId(dto.getUserId())
                .build();
    }
}
