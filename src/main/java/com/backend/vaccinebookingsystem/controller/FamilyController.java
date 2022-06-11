package com.backend.vaccinebookingsystem.controller;

import com.backend.vaccinebookingsystem.domain.dto.FamilyDto;
import com.backend.vaccinebookingsystem.service.FamilyService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/family", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class FamilyController {

    @Autowired
    private FamilyService familyService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = FamilyDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @PostMapping(value = "")
    public ResponseEntity<Object> createNewFamily(@RequestBody FamilyDto familyDto) {
        return familyService.createFamily(familyDto);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = FamilyDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getAFamilyById(@PathVariable Long id) {
        return familyService.getFamilyById(id);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = FamilyDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @GetMapping(value = "")
    public ResponseEntity<Object> getAllOfFamilies() {
        return familyService.getAllFamilies();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = FamilyDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateAFamilyById(@PathVariable Long id, @RequestBody FamilyDto familyDto) {
        return familyService.updateFamilyById(id, familyDto);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = FamilyDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteAFamilyById(@PathVariable Long id) {
        return familyService.deleteFamilyById(id);
    }
}
