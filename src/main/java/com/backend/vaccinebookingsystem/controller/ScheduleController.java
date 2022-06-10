package com.backend.vaccinebookingsystem.controller;

import com.backend.vaccinebookingsystem.domain.dto.ScheduleDto;
import com.backend.vaccinebookingsystem.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/schedule", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping(value = "")
    public ResponseEntity<Object> createSchedule(@RequestBody ScheduleDto scheduleDto) {
        return scheduleService.createSchedule(scheduleDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getAScheduleById(@PathVariable Long id) {
        return scheduleService.getScheduleById(id);
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllOfSchedules() {
        return scheduleService.getAllSchedules();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateAScheduleById(@PathVariable Long id, @RequestBody ScheduleDto scheduleDto) {
        return scheduleService.updateScheduleById(id, scheduleDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteAScheduleById(@PathVariable Long id) {
        return scheduleService.deleteScheduleById(id);
    }
}
