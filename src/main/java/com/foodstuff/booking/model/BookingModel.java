package com.foodstuff.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingModel {
    private Long id;
    private Integer userId;
    private Integer day;
    private Integer time;
}
