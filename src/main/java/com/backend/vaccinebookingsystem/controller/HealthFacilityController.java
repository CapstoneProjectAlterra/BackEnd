package com.backend.vaccinebookingsystem.controller;

import com.backend.vaccinebookingsystem.domain.dto.HealthFacilityDto;
import com.backend.vaccinebookingsystem.service.HealthFacilityService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/facility", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class HealthFacilityController {

    @Autowired
    private HealthFacilityService healthFacilityService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = HealthFacilityDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @PostMapping(value = "")
    public ResponseEntity<Object> createNewHealthFacility(@RequestBody HealthFacilityDto healthFacilityDto) {
        return healthFacilityService.createHealthFacility(healthFacilityDto);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = HealthFacilityDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getAHealthFacilityById(@PathVariable Long id) {
        return healthFacilityService.getHealthFacilityById(id);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = HealthFacilityDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @GetMapping(value = "")
    public ResponseEntity<Object> getAllOfHealthFacilities() {
        return healthFacilityService.getAllHealthFacilities();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = HealthFacilityDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateAHealthFacilityById(@PathVariable Long id, @RequestBody HealthFacilityDto healthFacilityDto) {
        return healthFacilityService.updateHealthFacilityById(id, healthFacilityDto);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = HealthFacilityDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteAHealthFacilityById(@PathVariable Long id) {
        return healthFacilityService.deleteHealthFacilityById(id);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = HealthFacilityDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @GetMapping(value = "/search")
    public ResponseEntity<Object> searchHealthFacility(@RequestParam(value = "filter") String filter) {
        return healthFacilityService.searchHealthFacility(filter);
    }
}
