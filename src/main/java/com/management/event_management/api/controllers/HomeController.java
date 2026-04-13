package com.management.event_management.api.controllers;

import com.management.event_management.api.dto.booking.response.BookingResponse;
import com.management.event_management.domain.entities.booking.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController   {
    @GetMapping
    public String getBookings() {
//        return new ResponseEntity(HttpStatus.OK);
        return "Ok";
    }

}