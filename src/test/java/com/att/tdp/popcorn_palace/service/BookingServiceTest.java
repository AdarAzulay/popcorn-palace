package com.att.tdp.popcorn_palace.service;

import com.att.tdp.popcorn_palace.dto.BookingDTO;
import com.att.tdp.popcorn_palace.model.Booking;
import com.att.tdp.popcorn_palace.model.Movie;
import com.att.tdp.popcorn_palace.model.Showtime;
import com.att.tdp.popcorn_palace.repository.BookingRepository;
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private ShowtimeRepository showtimeRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private Showtime sampleShowtime;
    private BookingDTO sampleBookingDTO;
    private Booking sampleBooking;

    @BeforeEach
    void setUp() {
        Movie sampleMovie = Movie.builder()
                .id(1L)
                .title("Inception")
                .build();

        sampleShowtime = Showtime.builder()
                .id(1L)
                .theater("IMAX")
                .startTime(LocalDateTime.of(2025, 3, 22, 18, 30))
                .endTime(LocalDateTime.of(2025, 3, 22, 21, 0))
                .price(50.0)
                .movie(sampleMovie)
                .build();

        sampleBookingDTO = BookingDTO.builder()
                .showtimeId(1L)
                .seatNumber(10)
                .userId(UUID.fromString("84438967-f68f-4fa0-b620-0f08217e76af"))
                .build();

        // Booking entity
        sampleBooking = Booking.builder()
                .bookingId(UUID.randomUUID())
                .seatNumber(10)
                .userId(UUID.fromString("84438967-f68f-4fa0-b620-0f08217e76af"))
                .showtime(sampleShowtime)
                .build();
    }

    @Test
    void testBookSeat_Success() {

        when(showtimeRepository.findById(1L)).thenReturn(Optional.of(sampleShowtime));
        when(bookingRepository.existsByShowtimeIdAndSeatNumber(1L, 10)).thenReturn(false);
        when(bookingRepository.save(any(Booking.class))).thenReturn(sampleBooking);
        BookingDTO result = bookingService.bookSeat(sampleBookingDTO);
        //  bookingId is not null - booking succeeded
        assertNotNull(result.getBookingId());
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void testBookSeat_DoubleBooking_ThrowsException() {

        when(showtimeRepository.findById(1L)).thenReturn(Optional.of(sampleShowtime));
        when(bookingRepository.existsByShowtimeIdAndSeatNumber(1L, 10)).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> bookingService.bookSeat(sampleBookingDTO));
    }

    @Test
    void testGetBookingsByShowtime() {

        when(bookingRepository.findAll()).thenReturn(List.of(sampleBooking));
        // get bookings for showtime with id 1
        List<BookingDTO> bookings = bookingService.getBookingsByShowtime(1L);
        assertEquals(1, bookings.size());
        assertEquals(1L, bookings.get(0).getShowtimeId());
    }
}
