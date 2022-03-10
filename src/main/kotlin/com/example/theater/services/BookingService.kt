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
}