package com.backend.vaccinebookingsystem.controller;

import com.backend.vaccinebookingsystem.domain.dto.VaccineTypeDto;
import com.backend.vaccinebookingsystem.service.VaccineTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/vaccine", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class VaccineTypeController {

    @Autowired
    private VaccineTypeService vaccineTypeService;

    @PostMapping(value = "")
    public ResponseEntity<Object> createNewVaccineType(@RequestBody VaccineTypeDto vaccineTypeDto) {
        return vaccineTypeService.createVaccineType(vaccineTypeDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getAVaccineTypeById(@PathVariable Long id) {
        return vaccineTypeService.getVaccineTypeById(id);
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllOfVaccineTypes() {
        return vaccineTypeService.getAllVaccineTypes();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateAVaccineTypeById(@PathVariable Long id, @RequestBody VaccineTypeDto vaccineTypeDto) {
        return vaccineTypeService.updateVaccineTypeById(id, vaccineTypeDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteAVaccineTypeById(@PathVariable Long id) {
        return vaccineTypeService.deleteVaccineTypeById(id);
    }
}
