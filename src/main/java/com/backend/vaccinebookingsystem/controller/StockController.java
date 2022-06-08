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

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getAStockById(@PathVariable Long id) {
        return stockService.getStockById(id);
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllOfStocks() {
        return stockService.getAllStocks();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateAStockById(@PathVariable Long id, @RequestBody FacilityVaccineDto facilityVaccineDto) {
        return stockService.updateStockById(id, facilityVaccineDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteAStockById(@PathVariable Long id) {
        return stockService.deleteStockById(id);
    }
}
