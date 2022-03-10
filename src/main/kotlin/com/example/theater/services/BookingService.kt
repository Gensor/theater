package com.example.theater.services

import com.example.theater.data.BookingRepository
import com.example.theater.domain.Booking
import com.example.theater.domain.Performance
import com.example.theater.domain.Seat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BookingService {

    @Autowired
    lateinit var bookingRepository: BookingRepository

    fun isSeatFree(seat:Seat, performance: Performance):Boolean {
        val bookings = bookingRepository.findAll()
            .firstOrNull { it.seat == seat && it.performance == performance }
        return bookings !is Booking
    }

    fun reserveSeat(seat: Seat, performance: Performance, customerName: String): Booking {
        val booking = Booking(0L, customerName)
        booking.seat = seat
        booking.performance = performance
        bookingRepository.save(booking)
        return booking
    }

    fun findBooking(selectedSeat: Seat, selectedPerformance: Performance) =
        bookingRepository.findAll()
            .firstOrNull { it.seat == selectedSeat && it.performance == selectedPerformance }

}