package com.backend.vaccinebookingsystem.controller;

import com.backend.vaccinebookingsystem.domain.dto.HealthFacilityDto;
import com.backend.vaccinebookingsystem.service.HealthFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/facility", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class HealthFacilityController {

    @Autowired
    private HealthFacilityService healthFacilityService;

    @PostMapping(value = "")
    public ResponseEntity<Object> createNewHealthFacility(@RequestBody HealthFacilityDto healthFacilityDto) {
        return healthFacilityService.createHealthFacility(healthFacilityDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getAHealthFacilityById(@PathVariable Long id) {
        return healthFacilityService.getHealthFacilityById(id);
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllOfHealthFacilities() {
        return healthFacilityService.getAllHealthFacilities();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateAHealthFacilityById(@PathVariable Long id, @RequestBody HealthFacilityDto healthFacilityDto) {
        return healthFacilityService.updateHealthFacilityById(id, healthFacilityDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteAHealthFacilityById(@PathVariable Long id) {
        return healthFacilityService.deleteHealthFacilityById(id);
    }
}
