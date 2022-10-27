package com.foodstuff.booking.util;

import com.foodstuff.booking.entity.Booking;
import com.foodstuff.booking.model.BookingModel;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

public class Mapper {
    // not use
    public Booking mapBookingModelToEntity(BookingModel bookingModel){
        Booking booking = new Booking();
//        booking.setId(bookingModel.getId());
//        booking.setUserId(bookingModel.getUserId());
//        booking.setDay1(bookingModel.getDay1());
        booking.setTime1(bookingModel.getTime());
        return booking;
    }

    public BookingModel mapBookingToModel(Booking booking){
        BookingModel bookingModel = new BookingModel();
//        bookingModel.setId(booking.getId());
//        bookingModel.setUserId(booking.getUserId());
        bookingModel.setDay(DayOfWeek.of(booking.getDay1()).getDisplayName(TextStyle.FULL, Locale.US));
        bookingModel.setTime(booking.getTime1());
        return bookingModel;
    }


}
