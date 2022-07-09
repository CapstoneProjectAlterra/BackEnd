package com.backend.vaccinebookingsystem.service;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.dao.HealthFacilityDao;
import com.backend.vaccinebookingsystem.domain.dao.ScheduleDao;
import com.backend.vaccinebookingsystem.domain.dao.VaccineTypeDao;
import com.backend.vaccinebookingsystem.domain.dto.HealthFacilityDto;
import com.backend.vaccinebookingsystem.domain.dto.ProfileDto;
import com.backend.vaccinebookingsystem.domain.dto.ScheduleDto;
import com.backend.vaccinebookingsystem.domain.dto.VaccineTypeDto;
import com.backend.vaccinebookingsystem.repository.HealthFacilityRepository;
import com.backend.vaccinebookingsystem.repository.ScheduleRepository;
import com.backend.vaccinebookingsystem.repository.VaccineTypeRepository;
import com.backend.vaccinebookingsystem.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
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
    private HealthFacilityRepository healthFacilityRepository;

    @Autowired
    private VaccineTypeRepository vaccineTypeRepository;


    public ResponseEntity<Object> createSchedule(ScheduleDto scheduleDto) {
        try {
            log.info("Creating new Schedule");

            Optional<HealthFacilityDao> optionalHealthFacilityDao = healthFacilityRepository.findById(scheduleDto.getFacility().getId());

            Optional<VaccineTypeDao> optionalVaccineTypeDao = vaccineTypeRepository.findById(scheduleDto.getVaccine().getId());

            if (optionalHealthFacilityDao.isEmpty() || optionalVaccineTypeDao.isEmpty()) {
                log.info("Profile not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            ScheduleDao scheduleDao = ScheduleDao.builder()
                    .vaccinationDate(scheduleDto.getVaccinationDate())
                    .operationalHourStart(scheduleDto.getOperationalHourStart())
                    .operationalHourEnd(scheduleDto.getOperationalHourEnd())
                    .quota(scheduleDto.getQuota())
                    .dose(scheduleDto.getDose())
                    .facility(optionalHealthFacilityDao.get())
                    .vaccine(optionalVaccineTypeDao.get())
                    .build();
            scheduleRepository.save(scheduleDao);

            ProfileDto profileDto = ProfileDto.builder()
                    .userId(optionalHealthFacilityDao.get().getProfile().getUserId())
                    .role(optionalHealthFacilityDao.get().getProfile().getRole())
                    .build();

            HealthFacilityDto facilityDto = HealthFacilityDto.builder()
                    .id(optionalHealthFacilityDao.get().getId())
                    .facilityName(optionalHealthFacilityDao.get().getFacilityName())
                    .streetName(optionalHealthFacilityDao.get().getStreetName())
                    .officeNumber(optionalHealthFacilityDao.get().getOfficeNumber())
                    .villageName(optionalHealthFacilityDao.get().getVillageName())
                    .district(optionalHealthFacilityDao.get().getDistrict())
                    .city(optionalHealthFacilityDao.get().getCity())
                    .province(optionalHealthFacilityDao.get().getProvince())
                    .postalCode(optionalHealthFacilityDao.get().getPostalCode())
                    .profile(profileDto)
                    .build();

            VaccineTypeDto typeDto = VaccineTypeDto.builder()
                    .id(optionalVaccineTypeDao.get().getId())
                    .vaccineName(optionalVaccineTypeDao.get().getVaccineName())
                    .build();

            ScheduleDto dto = ScheduleDto.builder()
                    .id(scheduleDao.getId())
                    .vaccinationDate(scheduleDao.getVaccinationDate())
                    .operationalHourStart(scheduleDao.getOperationalHourStart())
                    .operationalHourEnd(scheduleDao.getOperationalHourEnd())
                    .quota(scheduleDao.getQuota())
                    .dose(scheduleDao.getDose())
                    .facility(facilityDto)
                    .vaccine(typeDto)
                    .build();

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

            Optional<HealthFacilityDao> optionalHealthFacilityDao = healthFacilityRepository.findById(optionalScheduleDao.get().getFacility().getId());

            Optional<VaccineTypeDao> optionalVaccineTypeDao = vaccineTypeRepository.findById(optionalScheduleDao.get().getVaccine().getId());

            if (optionalScheduleDao.isEmpty() || optionalHealthFacilityDao.isEmpty() || optionalVaccineTypeDao.isEmpty()) {
                log.info("Schedule not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Schedule found");

            ProfileDto profileDto = ProfileDto.builder()
                    .userId(optionalHealthFacilityDao.get().getProfile().getUserId())
                    .role(optionalHealthFacilityDao.get().getProfile().getRole())
                    .build();

            HealthFacilityDto facilityDto = HealthFacilityDto.builder()
                    .id(optionalHealthFacilityDao.get().getId())
                    .facilityName(optionalHealthFacilityDao.get().getFacilityName())
                    .streetName(optionalHealthFacilityDao.get().getStreetName())
                    .officeNumber(optionalHealthFacilityDao.get().getOfficeNumber())
                    .villageName(optionalHealthFacilityDao.get().getVillageName())
                    .district(optionalHealthFacilityDao.get().getDistrict())
                    .city(optionalHealthFacilityDao.get().getCity())
                    .province(optionalHealthFacilityDao.get().getProvince())
                    .postalCode(optionalHealthFacilityDao.get().getPostalCode())
                    .profile(profileDto)
                    .build();

            VaccineTypeDto typeDto = VaccineTypeDto.builder()
                    .id(optionalVaccineTypeDao.get().getId())
                    .vaccineName(optionalVaccineTypeDao.get().getVaccineName())
                    .build();

            ScheduleDto dto = ScheduleDto.builder()
                    .id(optionalScheduleDao.get().getId())
                    .vaccinationDate(optionalScheduleDao.get().getVaccinationDate())
                    .operationalHourStart(optionalScheduleDao.get().getOperationalHourStart())
                    .operationalHourEnd(optionalScheduleDao.get().getOperationalHourEnd())
                    .quota(optionalScheduleDao.get().getQuota())
                    .dose(optionalScheduleDao.get().getDose())
                    .facility(facilityDto)
                    .vaccine(typeDto)
                    .build();
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
                Optional<HealthFacilityDao> optionalHealthFacilityDao = healthFacilityRepository.findById(scheduleDao.getFacility().getId());

                Optional<VaccineTypeDao> optionalVaccineTypeDao = vaccineTypeRepository.findById(scheduleDao.getVaccine().getId());

                ProfileDto profileDto = ProfileDto.builder()
                        .userId(optionalHealthFacilityDao.get().getProfile().getUserId())
                        .role(optionalHealthFacilityDao.get().getProfile().getRole())
                        .build();

                HealthFacilityDto facilityDto = HealthFacilityDto.builder()
                        .id(optionalHealthFacilityDao.get().getId())
                        .facilityName(optionalHealthFacilityDao.get().getFacilityName())
                        .streetName(optionalHealthFacilityDao.get().getStreetName())
                        .officeNumber(optionalHealthFacilityDao.get().getOfficeNumber())
                        .villageName(optionalHealthFacilityDao.get().getVillageName())
                        .district(optionalHealthFacilityDao.get().getDistrict())
                        .city(optionalHealthFacilityDao.get().getCity())
                        .province(optionalHealthFacilityDao.get().getProvince())
                        .postalCode(optionalHealthFacilityDao.get().getPostalCode())
                        .profile(profileDto)
                        .build();

                VaccineTypeDto typeDto = VaccineTypeDto.builder()
                        .id(optionalVaccineTypeDao.get().getId())
                        .vaccineName(optionalVaccineTypeDao.get().getVaccineName())
                        .build();

                scheduleDtos.add(ScheduleDto.builder()
                        .id(scheduleDao.getId())
                        .vaccinationDate(scheduleDao.getVaccinationDate())
                        .operationalHourStart(scheduleDao.getOperationalHourStart())
                        .operationalHourEnd(scheduleDao.getOperationalHourEnd())
                        .quota(scheduleDao.getQuota())
                        .dose(scheduleDao.getDose())
                        .facility(facilityDto)
                        .vaccine(typeDto)
                        .build());
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

            Optional<HealthFacilityDao> optionalHealthFacilityDao = healthFacilityRepository.findById(scheduleDto.getFacility().getId());

            Optional<VaccineTypeDao> optionalVaccineTypeDao = vaccineTypeRepository.findById(scheduleDto.getVaccine().getId());

            if (optionalScheduleDao.isEmpty() || optionalHealthFacilityDao.isEmpty() || optionalVaccineTypeDao.isEmpty()) {
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
            scheduleDao.setFacility(optionalHealthFacilityDao.get());
            scheduleDao.setVaccine(optionalVaccineTypeDao.get());
            scheduleRepository.save(scheduleDao);

            ProfileDto profileDto = ProfileDto.builder()
                    .userId(optionalHealthFacilityDao.get().getProfile().getUserId())
                    .role(optionalHealthFacilityDao.get().getProfile().getRole())
                    .build();

            HealthFacilityDto facilityDto = HealthFacilityDto.builder()
                    .id(optionalHealthFacilityDao.get().getId())
                    .facilityName(optionalHealthFacilityDao.get().getFacilityName())
                    .streetName(optionalHealthFacilityDao.get().getStreetName())
                    .officeNumber(optionalHealthFacilityDao.get().getOfficeNumber())
                    .villageName(optionalHealthFacilityDao.get().getVillageName())
                    .district(optionalHealthFacilityDao.get().getDistrict())
                    .city(optionalHealthFacilityDao.get().getCity())
                    .province(optionalHealthFacilityDao.get().getProvince())
                    .postalCode(optionalHealthFacilityDao.get().getPostalCode())
                    .profile(profileDto)
                    .build();

            VaccineTypeDto typeDto = VaccineTypeDto.builder()
                    .id(optionalVaccineTypeDao.get().getId())
                    .vaccineName(optionalVaccineTypeDao.get().getVaccineName())
                    .build();

            ScheduleDto dto = ScheduleDto.builder()
                    .id(scheduleDao.getId())
                    .vaccinationDate(scheduleDao.getVaccinationDate())
                    .operationalHourStart(scheduleDao.getOperationalHourStart())
                    .operationalHourEnd(scheduleDao.getOperationalHourEnd())
                    .quota(scheduleDao.getQuota())
                    .dose(scheduleDao.getDose())
                    .facility(facilityDto)
                    .vaccine(typeDto)
                    .build();
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
