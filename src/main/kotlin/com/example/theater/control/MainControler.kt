package com.example.theater.control

import com.example.theater.data.SeatRepository
import com.example.theater.services.BookingService
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
    lateinit var seatRepository : SeatRepository

    @RequestMapping("")
    fun homePage(): ModelAndView {
        return ModelAndView("seatBooking","bean",CheckAvailabilityBackingBean())
    }

    @RequestMapping("checkAvailability", method = arrayOf(RequestMethod.POST))
    fun checkAvailability(bean: CheckAvailabilityBackingBean):ModelAndView{
        val selectedSeat = theaterService.findSeat(bean.selectedSeatNum, bean.selectedSeatRow)
        val result = bookingService.isSeatFree(selectedSeat)

        bean.result = "seat $selectedSeat is ${if (result) "available" else "booked"}"

        return ModelAndView("seatBooking","bean",bean)
    }

    @RequestMapping("bootstrap")
    fun createInitialData() : ModelAndView {
        val seats = theaterService.seats
        seatRepository.saveAll(seats)
        return homePage()
    }
}


class CheckAvailabilityBackingBean(){
    val seatNums = 1 .. 36
    val seatRows = 'A'..'O'
    var selectedSeatNum : Int = 1
    var selectedSeatRow : Char = 'A'
    var result : String = ""

}