package com.backend.vaccinebookingsystem.controller;

import com.backend.vaccinebookingsystem.domain.dto.BookingDetailDto;
import com.backend.vaccinebookingsystem.service.BookingDetailService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/detail", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class BookingDetailController {

    @Autowired
    private BookingDetailService bookingDetailService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BookingDetailDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @PostMapping(value = "")
    public ResponseEntity<Object> createNewStock(@RequestBody BookingDetailDto bookingDetailDto) {
        return bookingDetailService.createBookingDetail(bookingDetailDto);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BookingDetailDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @GetMapping(value = "/search")
    public ResponseEntity<Object> searchAStockById(@RequestParam(value = "facility_id") Long bookingId, @RequestParam(value = "vaccine_id") Long familyId) {
        return bookingDetailService.searchBookingDetailById(bookingId, familyId);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BookingDetailDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @GetMapping(value = "")
    public ResponseEntity<Object> getAllOfStocks() {
        return bookingDetailService.getAllBookingDetails();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BookingDetailDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @PutMapping(value = "/update")
    public ResponseEntity<Object> updateAStockById(@RequestParam(value = "bookingId") Long bookingId, @RequestParam(value = "familyId") Long familyId, @RequestBody BookingDetailDto bookingDetailDto) {
        return bookingDetailService.updateBookingDetailById(bookingId, familyId, bookingDetailDto);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BookingDetailDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Object> deleteAStockById(@RequestParam(value = "facility_id") Long bookingId, @RequestParam(value = "vaccine_id") Long familyId) {
        return bookingDetailService.deleteBookingDetailById(bookingId, familyId);
    }
}
