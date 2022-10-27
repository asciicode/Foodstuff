package com.foodstuff.booking.repository;

import com.foodstuff.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByDayOrderByTime(int day);

    @Query(value = "SELECT e FROM Booking as e WHERE (e.day = ?1) and (e.time = ?2) "
    )
    List<Booking> findByDayAndTime(int day, int time);
    List<Booking> findByUserIdOrderByDayAscTimeAsc(int userId);
    @Query(value = "SELECT count(e.id) FROM Booking as e WHERE (e.userId = ?1) and  (e.day = ?2) and (e.time = ?3) "
    )
    long countByUserIdAndDayAndTime(int userId, int day, int time);
}
