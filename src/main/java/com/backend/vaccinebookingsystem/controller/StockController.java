package com.backend.vaccinebookingsystem.controller;

import com.backend.vaccinebookingsystem.domain.dto.FacilityVaccineDto;
import com.backend.vaccinebookingsystem.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/stock", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping(value = "")
    public ResponseEntity<Object> createNewStock(@RequestBody FacilityVaccineDto facilityVaccineDto) {
        return stockService.createStock(facilityVaccineDto);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Object> searchAStockById(@RequestParam(value = "facility_id") Long facilityId, @RequestParam(value = "vaccine_id") Long vaccineId) {
        return stockService.searchStockById(facilityId, vaccineId);
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllOfStocks() {
        return stockService.getAllStocks();
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> updateAStockById(@RequestParam(value = "facility_id") Long facilityId, @RequestParam(value = "vaccine_id") Long vaccineId, @RequestBody FacilityVaccineDto facilityVaccineDto) {
        return stockService.updateStockById(facilityId, vaccineId, facilityVaccineDto);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Object> deleteAStockById(@RequestParam(value = "facility_id") Long facilityId, @RequestParam(value = "vaccine_id") Long vaccineId) {
        return stockService.deleteStockById(facilityId, vaccineId);
    }
}
