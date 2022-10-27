package com.foodstuff.booking;

import com.foodstuff.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestBookingRepository extends JpaRepository<Booking,Integer> {
}