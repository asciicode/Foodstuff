package com.foodstuff.booking.repository;

import com.foodstuff.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByDayOrderByTime(int day);

    List<Booking> findByDayAndTime(int day, int time);

    List<Booking> findByUserIdOrderByDayAscTimeAsc(int userId);

    long countByUserIdAndDayAndTime(int userId, int day, int time);
}
