package com.example.theater.data

import com.example.theater.domain.Performance
import org.springframework.data.jpa.repository.JpaRepository

interface PerformanceRepository : JpaRepository <Performance, Long> {
}