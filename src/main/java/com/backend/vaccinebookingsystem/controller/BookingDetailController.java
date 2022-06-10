package com.backend.vaccinebookingsystem.controller;

import com.backend.vaccinebookingsystem.domain.dto.BookingDetailDto;
import com.backend.vaccinebookingsystem.domain.dto.FacilityVaccineDto;
import com.backend.vaccinebookingsystem.service.BookingDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/detail", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class BookingDetailController {

    @Autowired
    private BookingDetailService bookingDetailService;

    @PostMapping(value = "")
    public ResponseEntity<Object> createNewStock(@RequestBody BookingDetailDto bookingDetailDto) {
        return bookingDetailService.createBookingDetail(bookingDetailDto);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Object> searchAStockById(@RequestParam(value = "facility_id") Long bookingId, @RequestParam(value = "vaccine_id") Long familyId) {
        return bookingDetailService.searchBookingDetailById(bookingId, familyId);
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllOfStocks() {
        return bookingDetailService.getAllBookingDetails();
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> updateAStockById(@RequestParam(value = "bookingId") Long bookingId, @RequestParam(value = "familyId") Long familyId, @RequestBody BookingDetailDto bookingDetailDto) {
        return bookingDetailService.updateBookingDetailById(bookingId, familyId, bookingDetailDto);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Object> deleteAStockById(@RequestParam(value = "facility_id") Long bookingId, @RequestParam(value = "vaccine_id") Long familyId) {
        return bookingDetailService.deleteBookingDetailById(bookingId, familyId);
    }
}
