package com.backend.vaccinebookingsystem.service;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.dao.ScheduleDao;
import com.backend.vaccinebookingsystem.domain.dto.ScheduleDto;
import com.backend.vaccinebookingsystem.repository.ScheduleRepository;
import com.backend.vaccinebookingsystem.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ModelMapper modelMapper;


    public ResponseEntity<Object> createSchedule(ScheduleDto scheduleDto) {
        try {
            log.info("Creating new Schedule");
            ScheduleDao scheduleDao = modelMapper.map(scheduleDto, ScheduleDao.class);
            scheduleRepository.save(scheduleDao);

            ScheduleDto dto = modelMapper.map(scheduleDao, ScheduleDto.class);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, dto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in creating new Schedule. Error: {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getScheduleById(Long id) {
        try {
            log.info("Getting a Schedule by id");
            Optional<ScheduleDao> optionalScheduleDao = scheduleRepository.findById(id);

            if (optionalScheduleDao.isEmpty()) {
                log.info("Schedule not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Schedule found");
            ScheduleDto dto = modelMapper.map(optionalScheduleDao.get(), ScheduleDto.class);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, dto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting a Schedule by id. Error {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<Object> getAllSchedules() {
        try {
            log.info("Getting all of Schedules");
            List<ScheduleDao> scheduleDaoList;
            List<ScheduleDto> scheduleDtos = new ArrayList<>();

            scheduleDaoList = scheduleRepository.findAll();

            for (ScheduleDao scheduleDao : scheduleDaoList) {
                scheduleDtos.add(modelMapper.map(scheduleDao, ScheduleDto.class));
            }

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, scheduleDtos, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting all of Schedules. Error {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateScheduleById(Long id, ScheduleDto scheduleDto) {
        try {
            log.info("Updating a Schedule by id");
            Optional<ScheduleDao> optionalScheduleDao = scheduleRepository.findById(id);
            if (optionalScheduleDao.isEmpty()) {
                log.info("Schedule not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Schedule found");
            ScheduleDao scheduleDao = optionalScheduleDao.get();
            scheduleDao.setVaccinationDate(scheduleDto.getVaccinationDate());
            scheduleDao.setOperationalHourStart(scheduleDto.getOperationalHourStart());
            scheduleDao.setOperationalHourEnd(scheduleDto.getOperationalHourEnd());
            scheduleDao.setQuota(scheduleDto.getQuota());
            scheduleDao.setDose(scheduleDto.getDose());
            scheduleRepository.save(scheduleDao);

            ScheduleDto dto = modelMapper.map(scheduleDao, ScheduleDto.class);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, dto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in Updating Schedule by id. Error {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<Object> deleteScheduleById(Long id) {
        try {
            log.info("Deleting a Schedule by id");
            Optional<ScheduleDao> optionalScheduleDao = scheduleRepository.findById(id);
            if (optionalScheduleDao.isEmpty()) {
                log.info("Schedule not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Schedule found");
            scheduleRepository.delete(optionalScheduleDao.get());
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, null, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in deleting Schedule by id. Error {}", e.getMessage());
            throw e;
        }
    }
}
