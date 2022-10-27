package com.foodstuff.booking;

import com.foodstuff.booking.entity.Booking;
import com.foodstuff.booking.model.BookingModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class FoodstuffApplicationTests {
    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    @Autowired
    private TestBookingRepository testBookingRepository;
    private String bookingEndpoint;
    private String bookByUserIdEndpoint;
    private String timeSlotEndpoint;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        bookingEndpoint = baseUrl.concat(":").concat(port + "").concat("/api/v1/booking/book/1/monday/1");
        bookByUserIdEndpoint = baseUrl.concat(":").concat(port + "").concat("/api/v1/booking/list/1");
        timeSlotEndpoint = baseUrl.concat(":").concat(port + "").concat("/api/v1/booking/time-slot/tuesday");
    }

    @Test
    @Sql(statements = "INSERT INTO booking (id,user_id,day1,time1) VALUES (1,1,1,1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO booking (id,user_id,day1,time1) VALUES (2,1,2,1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO booking (id,user_id,day1,time1) VALUES (3,2,1,1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM booking", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testBooking() {
        boolean canBook = restTemplate.getForObject(bookingEndpoint, Boolean.class);
        System.out.println(canBook);
        assertEquals(false, canBook);
    }

    @Test
    @Sql(statements = "INSERT INTO booking (id,user_id,day1,time1) VALUES (1,1,1,1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO booking (id,user_id,day1,time1) VALUES (2,1,2,1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM booking", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testBookByUserIdEndpoint() {
        HttpEntity requestEntity = new HttpEntity<>(null, null);
        ResponseEntity<List<BookingModel>> response = restTemplate.exchange(bookByUserIdEndpoint,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<BookingModel>>() {
                });

        List<BookingModel> bookings = response.getBody();
        assertEquals(2, 2);
        assertEquals(DayOfWeek.MONDAY.getDisplayName(TextStyle.FULL, Locale.US), bookings.get(0).getDay());
        assertEquals(DayOfWeek.TUESDAY.getDisplayName(TextStyle.FULL, Locale.US), bookings.get(1).getDay());
    }
    @Test
    @Sql(statements = "INSERT INTO booking (id,user_id,day1,time1) VALUES (1,1,2,1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO booking (id,user_id,day1,time1) VALUES (2,2,2,1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO booking (id,user_id,day1,time1) VALUES (3,3,2,1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM booking", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testTimeSlotEndpoint() {
        Map<Integer, Integer> timeSlot = restTemplate.getForObject(timeSlotEndpoint, Map.class);
//        System.out.println(timeSlot.keySet()+"----"+timeSlot.get("1"));
        assertEquals(3, timeSlot.get("1"));
    }

    @Test
    public void testGroupingCountByUserId(){
        List<Booking> list = new ArrayList<>();
        Booking booking = new Booking();
        booking.setUserId(2);
        booking.setDay1(1);
        booking.setTime1(1);
        list.add(booking);

        booking = new Booking();
        booking.setUserId(2);
        booking.setDay1(1);
        booking.setTime1(1);
        list.add(booking);

        booking = new Booking();
        booking.setUserId(3);
        booking.setDay1(1);
        booking.setTime1(1);
        list.add(booking);

        Map<Integer, Long> counted = list.stream()
                .collect(Collectors.groupingBy(Booking::getUserId, Collectors.counting()));
        assertEquals(counted.get(2), 2);
        assertEquals(counted.get(3), 1);

    }
}
