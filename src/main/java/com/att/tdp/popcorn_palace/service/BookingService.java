package com.att.tdp.popcorn_palace.service;

import com.att.tdp.popcorn_palace.dto.BookingDTO;
import java.util.List;
import java.util.UUID;

public interface BookingService {
    List<BookingDTO> getBookingsByShowtime(Long showtimeId);
    BookingDTO bookSeat(BookingDTO bookingDTO);
}
