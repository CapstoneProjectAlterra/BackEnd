package com.backend.vaccinebookingsystem.controller;

import com.backend.vaccinebookingsystem.domain.dto.BookingDto;
import com.backend.vaccinebookingsystem.service.BookingService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/booking", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BookingDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @PostMapping(value = "")
    public ResponseEntity<Object> createNewBooking(@RequestBody BookingDto bookingDto) {
        return bookingService.createBooking(bookingDto);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BookingDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getABookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BookingDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @GetMapping(value = "")
    public ResponseEntity<Object> getAllOfBookings() {
        return bookingService.getAllBookings();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BookingDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateABookingById(@PathVariable Long id, @RequestBody BookingDto bookingDto) {
        return bookingService.updateBookingById(id, bookingDto);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BookingDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteABookingById(@PathVariable Long id) {
        return bookingService.deleteBookingById(id);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BookingDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @GetMapping(value = "/count")
    public ResponseEntity<Object> countForBookingByScheduleId(@RequestParam(value = "schedule_id") Long scheduleId) {
        return bookingService.countBookingByScheduleId(scheduleId);
    }
}
