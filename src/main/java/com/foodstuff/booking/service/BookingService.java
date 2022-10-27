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

    public boolean canBeBook(int userId, int day, int hour) {
        long count = bookingRepository.countByUserIdAndDayAndHour(userId, day, hour);
        if (count > 0) return false;
        List<Booking> bookingsByDayAndHour = bookingRepository.findByDayAndHour(day, hour);
        Map<Integer, Long> counted = bookingsByDayAndHour.stream()
                .collect(Collectors.groupingBy(Booking::getUserId, Collectors.counting()));
        if (counted.size() > MAX_USER_SLOT) return false;

        return true;

    }

    public List<BookingModel> listByUserid(int userId) {
        List<Booking> entities = bookingRepository.findByUserIdOrderByDayAscHourAsc(userId);
        return entities.stream().map((Function<Booking, BookingModel>) entity -> {
            return new Mapper().mapBookingToModel(entity);
        }).collect(Collectors.toList());
    }

    public Map<Integer, Integer> hourSlotByDayOfWeek(String dayOfWeek) {
        DayOfWeek dayOfWeek1 = DayOfWeek.valueOf(dayOfWeek.toUpperCase());
        List<Booking> entities = bookingRepository.findByDayOrderByHour(dayOfWeek1.ordinal());
        Map<Integer, Integer> returnMap = new HashMap<>();
        for (Booking booking : entities) {
            if (returnMap.containsKey(booking.getHour())) {
                returnMap.put(booking.getHour(), returnMap.get(booking.getHour()) + 1);
            } else {
                returnMap.put(booking.getHour(), 1);
            }
        }
        return returnMap;
    }

    public static void main(String[] args) {
        List<Booking> list = new ArrayList<>();
        Booking booking = new Booking();
        booking.setUserId(2);
        booking.setDay(1);
        booking.setHour(1);
        list.add(booking);

        booking = new Booking();
        booking.setUserId(2);
        booking.setDay(1);
        booking.setHour(1);
        list.add(booking);

        booking = new Booking();
        booking.setUserId(3);
        booking.setDay(1);
        booking.setHour(1);
        list.add(booking);

        Map<Integer, Long> counted = list.stream()
                .collect(Collectors.groupingBy(Booking::getUserId, Collectors.counting()));

        System.out.println(counted);

        String da = "Monday";
        System.out.println(DayOfWeek.valueOf(da.toUpperCase()));

    }
}
