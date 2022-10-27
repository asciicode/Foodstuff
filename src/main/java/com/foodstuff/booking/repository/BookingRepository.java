package com.foodstuff.booking.repository;

import com.foodstuff.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByDayOrderByHour(int day);

    List<Booking> findByDayAndHour(int day, int hour);

    List<Booking> findByUserIdOrderByDayAscHourAsc(int userId);

    long countByUserIdAndDay(int userId, int day);
}
