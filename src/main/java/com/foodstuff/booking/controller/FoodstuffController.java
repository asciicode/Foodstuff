package com.foodstuff.booking.controller;

import com.foodstuff.booking.model.BookingModel;
import com.foodstuff.booking.service.BookingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/booking")
public class FoodstuffController {
    private final BookingService bookingService;

    public FoodstuffController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/book/{userId}/{day}/{time}")
    boolean checkIfCanBook(@PathVariable int userId, @PathVariable int day, @PathVariable int time) {
        return bookingService.canBeBook(userId, day, time);
    }

    @GetMapping("/list/{userId}")
    List<BookingModel> bookByUserId(@PathVariable int userId) {
        return  bookingService.listByUserid(userId);
    }

    @GetMapping("/time-slot/{dayOfWeek}")
    Map<Integer, Integer> timeSlotByDayOfWeek(@PathVariable String dayOfWeek) {
        return bookingService.hourSlotByDayOfWeek(dayOfWeek);
    }
}
