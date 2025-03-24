package com.att.tdp.popcorn_palace.controller;

import com.att.tdp.popcorn_palace.dto.BookingDTO;
import com.att.tdp.popcorn_palace.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    // this is for testing
    @GetMapping("/showtime/{showtimeId}")
    public List<BookingDTO> getBookingsByShowtime(@PathVariable Long showtimeId) {
        return bookingService.getBookingsByShowtime(showtimeId);
    }

    @PostMapping
    public BookingDTO bookSeat(@RequestBody BookingDTO bookingDTO) {
        return bookingService.bookSeat(bookingDTO);
    }
}