package com.backend.vaccinebookingsystem.controller;

import com.backend.vaccinebookingsystem.domain.dto.ScheduleDto;
import com.backend.vaccinebookingsystem.service.ScheduleService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/schedule", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//@CrossOrigin(origins = "*")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ScheduleDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @PostMapping(value = "")
    @PreAuthorize("hasAuthority('HEALTH_ADMIN') or hasAuthority('ADMIN')")
    public ResponseEntity<Object> createSchedule(@RequestBody ScheduleDto scheduleDto) {
        return scheduleService.createSchedule(scheduleDto);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ScheduleDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('HEALTH_ADMIN') or hasAuthority('ADMIN')")
    public ResponseEntity<Object> getAScheduleById(@PathVariable Long id) {
        return scheduleService.getScheduleById(id);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ScheduleDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @GetMapping(value = "")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('HEALTH_ADMIN') or hasAuthority('ADMIN')")
    public ResponseEntity<Object> getAllOfSchedules() {
        return scheduleService.getAllSchedules();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ScheduleDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('HEALTH_ADMIN') or hasAuthority('ADMIN')")
    public ResponseEntity<Object> updateAScheduleById(@PathVariable Long id, @RequestBody ScheduleDto scheduleDto) {
        return scheduleService.updateScheduleById(id, scheduleDto);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ScheduleDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('HEALTH_ADMIN') or hasAuthority('ADMIN')")
    public ResponseEntity<Object> deleteAScheduleById(@PathVariable Long id) {
        return scheduleService.deleteScheduleById(id);
    }
}
