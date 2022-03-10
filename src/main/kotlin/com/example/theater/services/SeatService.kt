package com.example.theater.services

import com.example.theater.data.SeatRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SeatService {
    @Autowired
    lateinit var seatRepository: SeatRepository

    fun findSeat( row : Char, num : Int) = seatRepository.findAll().firstOrNull { it.rowOfSeat == row && it.num == num }


}