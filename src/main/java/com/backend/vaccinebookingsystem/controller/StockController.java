package com.backend.vaccinebookingsystem.controller;

import com.backend.vaccinebookingsystem.domain.dto.FacilityVaccineDto;
import com.backend.vaccinebookingsystem.service.StockService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/stock", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class StockController {

    @Autowired
    private StockService stockService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = FacilityVaccineDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @PostMapping(value = "")
    public ResponseEntity<Object> createNewStock(@RequestBody FacilityVaccineDto facilityVaccineDto) {
        return stockService.createStock(facilityVaccineDto);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = FacilityVaccineDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @GetMapping(value = "/search")
    public ResponseEntity<Object> searchAStockById(@RequestParam(value = "facility_id") Long facilityId, @RequestParam(value = "vaccine_id") Long vaccineId) {
        return stockService.searchStockById(facilityId, vaccineId);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = FacilityVaccineDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @GetMapping(value = "")
    public ResponseEntity<Object> getAllOfStocks() {
        return stockService.getAllStocks();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = FacilityVaccineDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @PutMapping(value = "/update")
    public ResponseEntity<Object> updateAStockById(@RequestParam(value = "facility_id") Long facilityId, @RequestParam(value = "vaccine_id") Long vaccineId, @RequestBody FacilityVaccineDto facilityVaccineDto) {
        return stockService.updateStockById(facilityId, vaccineId, facilityVaccineDto);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = FacilityVaccineDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Object> deleteAStockById(@RequestParam(value = "facility_id") Long facilityId, @RequestParam(value = "vaccine_id") Long vaccineId) {
        return stockService.deleteStockById(facilityId, vaccineId);
    }
}
