package com.backend.vaccinebookingsystem.controller;

import com.backend.vaccinebookingsystem.domain.dto.VaccineTypeDto;
import com.backend.vaccinebookingsystem.service.VaccineTypeService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/vaccine", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class VaccineTypeController {

    @Autowired
    private VaccineTypeService vaccineTypeService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = VaccineTypeDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @PostMapping(value = "")
    @PreAuthorize("hasAuthority('HEALTH_ADMIN') or hasAuthority('ADMIN')")
    public ResponseEntity<Object> createNewVaccineType(@RequestBody VaccineTypeDto vaccineTypeDto) {
        return vaccineTypeService.createVaccineType(vaccineTypeDto);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = VaccineTypeDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('HEALTH_ADMIN') or hasAuthority('ADMIN')")
    public ResponseEntity<Object> getAVaccineTypeById(@PathVariable Long id) {
        return vaccineTypeService.getVaccineTypeById(id);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = VaccineTypeDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @GetMapping(value = "")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('HEALTH_ADMIN') or hasAuthority('ADMIN')")
    public ResponseEntity<Object> getAllOfVaccineTypes() {
        return vaccineTypeService.getAllVaccineTypes();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = VaccineTypeDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('HEALTH_ADMIN') or hasAuthority('ADMIN')")
    public ResponseEntity<Object> updateAVaccineTypeById(@PathVariable Long id, @RequestBody VaccineTypeDto vaccineTypeDto) {
        return vaccineTypeService.updateVaccineTypeById(id, vaccineTypeDto);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = VaccineTypeDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('HEALTH_ADMIN') or hasAuthority('ADMIN')")
    public ResponseEntity<Object> deleteAVaccineTypeById(@PathVariable Long id) {
        return vaccineTypeService.deleteVaccineTypeById(id);
    }
}
