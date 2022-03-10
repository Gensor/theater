package com.example.theater.control

import com.example.theater.data.BookingRepository
import com.example.theater.data.PerformanceRepository
import com.example.theater.data.SeatRepository
import com.example.theater.domain.Booking
import com.example.theater.domain.Performance
import com.example.theater.domain.Seat
import com.example.theater.services.BookingService
import com.example.theater.services.SeatService
import com.example.theater.services.TheaterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

@Controller
class MainControler {

    @Autowired
    lateinit var theaterService : TheaterService
    @Autowired
    lateinit var bookingService: BookingService
    @Autowired
    lateinit var seatService: SeatService

    @Autowired
    lateinit var seatRepository : SeatRepository
    @Autowired
    lateinit var performanceRepository: PerformanceRepository

    @RequestMapping("")
    fun homePage(): ModelAndView {
        val model = mapOf("bean" to CheckAvailabilityBackingBean(),
            "performances" to performanceRepository.findAll(),
            "seatNums" to 1 .. 36,
            "seatRows" to 'A'..'O')
        return ModelAndView("seatBooking",model)
    }

    @RequestMapping("checkAvailability", method = arrayOf(RequestMethod.POST))
    fun checkAvailability(bean : CheckAvailabilityBackingBean) : ModelAndView{
        val selectedSeat : Seat = seatService.findSeat(bean.selectedSeatRow, bean.selectedSeatNum)!!
        val selectedPerformance = performanceRepository.findById(bean.selectedPerformance!!).get()
        val isSeatAvailable = bookingService.isSeatFree(selectedSeat, selectedPerformance)

        bean.available = isSeatAvailable
        bean.seat = selectedSeat
        bean.performance = selectedPerformance

        val model = mapOf("bean" to bean,
            "performances" to performanceRepository.findAll(),
            "seatNums" to 1 .. 36,
            "seatRows" to 'A'..'O')
        return ModelAndView("seatBooking",model)
    }

    @RequestMapping("bootstrap")
    fun createInitialData() : ModelAndView {
        val seats = theaterService.seats
        seatRepository.saveAll(seats)
        return homePage()
    }
}


class CheckAvailabilityBackingBean(){
    var selectedSeatNum : Int = 1
    var selectedSeatRow : Char = 'A'
    var selectedPerformance : Long? = null

    var customerName : String = ""
    var available : Boolean? = null
    var seat : Seat? = null
    var performance : Performance? = null
    var booking : Booking? = null
}