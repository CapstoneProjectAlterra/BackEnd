package com.backend.vaccinebookingsystem.controller;

import com.backend.vaccinebookingsystem.domain.dto.BookingDto;
import com.backend.vaccinebookingsystem.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/booking", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping(value = "")
    public ResponseEntity<Object> createNewBooking(@RequestBody BookingDto bookingDto) {
        return bookingService.createBooking(bookingDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getABookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllOfBookings() {
        return bookingService.getAllBookings();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateABookingById(@PathVariable Long id, @RequestBody BookingDto bookingDto) {
        return bookingService.updateBookingById(id, bookingDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteABookingById(@PathVariable Long id) {
        return bookingService.deleteBookingById(id);
    }
}
