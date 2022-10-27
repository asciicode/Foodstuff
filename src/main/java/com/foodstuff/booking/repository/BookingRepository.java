package com.foodstuff.booking.repository;

import com.foodstuff.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByDay1OrderByTime1(int day);

    List<Booking> findByDay1AndTime1(int day, int hour);

    List<Booking> findByUserIdOrderByDay1AscTime1Asc(int userId);

    long countByUserIdAndDay1(int userId, int day);
}
