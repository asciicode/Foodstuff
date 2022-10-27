package com.foodstuff.booking.service;

import com.foodstuff.booking.entity.Booking;
import com.foodstuff.booking.model.BookingModel;
import com.foodstuff.booking.repository.BookingRepository;
import org.springframework.stereotype.Service;
import com.foodstuff.booking.util.Mapper;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.function.Function;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private static final int MAX_USER_SLOT = 8;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public boolean canBeBook(int userId, String dayOfWeek, int hour) {
        DayOfWeek dayOfWeek1 = DayOfWeek.valueOf(dayOfWeek.toUpperCase());
        int day = dayOfWeek1.getValue();
        long count = bookingRepository.countByUserIdAndDay1(userId, day);
        if (count > 0) return false;
        List<Booking> bookingsByDayAndHour = bookingRepository.findByDay1AndTime1(day, hour);
        Map<Integer, Long> counted = bookingsByDayAndHour.stream()
                .collect(Collectors.groupingBy(Booking::getUserId, Collectors.counting()));
        if (counted.size() > MAX_USER_SLOT) return false;

        return true;

    }

    public List<BookingModel> listByUserid(int userId) {
        List<Booking> entities = bookingRepository.findByUserIdOrderByDay1AscTime1Asc(userId);
        return entities.stream().map((Function<Booking, BookingModel>) entity -> {
            return new Mapper().mapBookingToModel(entity);
        }).collect(Collectors.toList());
    }

    public Map<Integer, Integer> timeSlotByDayOfWeek(String dayOfWeek) {
        DayOfWeek dayOfWeek1 = DayOfWeek.valueOf(dayOfWeek.toUpperCase());
        List<Booking> entities = bookingRepository.findByDay1OrderByTime1(dayOfWeek1.getValue());
        Map<Integer, Integer> returnMap = new HashMap<>();
        for (Booking booking : entities) {
            if (returnMap.containsKey(booking.getTime1())) {
                returnMap.put(booking.getTime1(), returnMap.get(booking.getTime1()) + 1);
            } else {
                returnMap.put(booking.getTime1(), 1);
            }
        }
        return returnMap;
    }

}
