package com.foodstuff.booking.util;

import com.foodstuff.booking.entity.Booking;
import com.foodstuff.booking.model.BookingModel;
public class Mapper {
    // not use
    public Booking mapBookingModelToEntity(BookingModel bookingModel){
        Booking booking = new Booking();
        booking.setId(bookingModel.getId());
        booking.setUserId(bookingModel.getUserId());
        booking.setDay(bookingModel.getDay());
        booking.setTime(bookingModel.getTime());
        return booking;
    }

    public BookingModel mapBookingToModel(Booking booking){
        BookingModel bookingModel = new BookingModel();
        bookingModel.setId(booking.getId());
        bookingModel.setUserId(booking.getUserId());
        bookingModel.setDay(booking.getDay());
        bookingModel.setTime(booking.getTime());
        return bookingModel;
    }


}
