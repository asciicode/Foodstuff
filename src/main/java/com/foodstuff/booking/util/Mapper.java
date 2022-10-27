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
//        booking.setDay(bookingModel.getDay());
        booking.setHour(bookingModel.getHour());
        return booking;
    }

    public BookingModel mapBookingToModel(Booking booking){
        BookingModel bookingModel = new BookingModel();
//        bookingModel.setId(booking.getId());
//        bookingModel.setUserId(booking.getUserId());
        bookingModel.setDay(DayOfWeek.of(booking.getDay()).getDisplayName(TextStyle.SHORT, Locale.US));
        bookingModel.setHour(booking.getHour());
        return bookingModel;
    }


}
