package com.backend.vaccinebookingsystem.controller;

import com.backend.vaccinebookingsystem.domain.dto.BookingDetailDto;
import com.backend.vaccinebookingsystem.service.BookingDetailService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/detail", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class BookingDetailController {

    @Autowired
    private BookingDetailService bookingDetailService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BookingDetailDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @PostMapping(value = "")
    @PreAuthorize("hasAuthority('HEALTH_ADMIN') or hasAuthority('ADMIN')")
    public ResponseEntity<Object> createNewBookingDetail(@RequestBody BookingDetailDto bookingDetailDto) {
        return bookingDetailService.createBookingDetail(bookingDetailDto);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BookingDetailDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @GetMapping(value = "/search")
    @PreAuthorize("hasAuthority('HEALTH_ADMIN') or hasAuthority('ADMIN')")
    public ResponseEntity<Object> searchABookingDetailById(@RequestParam(value = "booking_id") Long bookingId, @RequestParam(value = "family_id") Long familyId) {
        return bookingDetailService.searchBookingDetailById(bookingId, familyId);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BookingDetailDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @GetMapping(value = "")
    @PreAuthorize("hasAuthority('HEALTH_ADMIN') or hasAuthority('ADMIN')")
    public ResponseEntity<Object> getAllOfStocks() {
        return bookingDetailService.getAllBookingDetails();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BookingDetailDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @PutMapping(value = "/update")
    @PreAuthorize("hasAuthority('HEALTH_ADMIN') or hasAuthority('ADMIN')")
    public ResponseEntity<Object> updateABookingDetailById(@RequestParam(value = "booking_id") Long bookingId, @RequestParam(value = "family_id") Long familyId, @RequestBody BookingDetailDto bookingDetailDto) {
        return bookingDetailService.updateBookingDetailById(bookingId, familyId, bookingDetailDto);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BookingDetailDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @DeleteMapping(value = "/delete")
    @PreAuthorize("hasAuthority('HEALTH_ADMIN') or hasAuthority('ADMIN')")
    public ResponseEntity<Object> deleteAStockById(@RequestParam(value = "booking_id") Long bookingId, @RequestParam(value = "family_id") Long familyId) {
        return bookingDetailService.deleteBookingDetailById(bookingId, familyId);
    }
}
