package com.example.theater.services

import com.example.theater.data.PerformanceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PerformanceService {
    @Autowired
    lateinit var performanceRepository: PerformanceRepository
}