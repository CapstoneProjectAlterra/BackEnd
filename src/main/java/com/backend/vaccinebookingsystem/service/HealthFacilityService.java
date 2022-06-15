package com.backend.vaccinebookingsystem.service;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.dao.HealthFacilityDao;
import com.backend.vaccinebookingsystem.domain.dto.HealthFacilityDto;
import com.backend.vaccinebookingsystem.repository.HealthFacilityRepository;
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
public class HealthFacilityService {

    @Autowired
    private HealthFacilityRepository healthFacilityRepository;

    @Autowired
    private ModelMapper modelMapper;


    public ResponseEntity<Object> createHealthFacility(HealthFacilityDto healthFacilityDto) {
        try {
            log.info("Creating new health facility");
            HealthFacilityDao healthFacilityDao = modelMapper.map(healthFacilityDto, HealthFacilityDao.class);
            healthFacilityRepository.save(healthFacilityDao);

            HealthFacilityDto facilityDto = modelMapper.map(healthFacilityDao, HealthFacilityDto.class);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, facilityDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in creating new Health Facility. Error: {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getHealthFacilityById(Long id) {
        try {
            log.info("Getting a Health Facility by id");
            Optional<HealthFacilityDao> optionalHealthFacilityDao = healthFacilityRepository.findById(id);

            if (optionalHealthFacilityDao.isEmpty()) {
                log.info("Health Facility not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Health Facility found");
            HealthFacilityDto facilityDto = modelMapper.map(optionalHealthFacilityDao.get(), HealthFacilityDto.class);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, facilityDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting a Health Facility by id. Error {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<Object> getAllHealthFacilities() {
        try {
            log.info("Getting all of Health Facilities");
            List<HealthFacilityDao> healthFacilityDaoList;
            List<HealthFacilityDto> healthFacilityDtos = new ArrayList<>();

            healthFacilityDaoList = healthFacilityRepository.findAll();

            for (HealthFacilityDao healthFacilityDao : healthFacilityDaoList) {
                healthFacilityDtos.add(modelMapper.map(healthFacilityDao, HealthFacilityDto.class));
            }

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, healthFacilityDtos, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting all of Health Facilities. Error {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateHealthFacilityById(Long id, HealthFacilityDto healthFacilityDto) {
        try {
            log.info("Updating a Health Facility by id");
            Optional<HealthFacilityDao> optionalHealthFacilityDao = healthFacilityRepository.findById(id);
            if (optionalHealthFacilityDao.isEmpty()) {
                log.info("Health Facility not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Health Facility found");
            HealthFacilityDao healthFacilityDao = optionalHealthFacilityDao.get();
            healthFacilityDao.setFacilityName(healthFacilityDto.getFacilityName());
            healthFacilityDao.setImgUrl(healthFacilityDto.getImgUrl());
            healthFacilityDao.setStreetName(healthFacilityDto.getStreetName());
            healthFacilityDao.setOfficeNumber(healthFacilityDto.getOfficeNumber());
            healthFacilityDao.setVillageName(healthFacilityDto.getVillageName());
            healthFacilityDao.setDistrict(healthFacilityDto.getDistrict());
            healthFacilityDao.setCity(healthFacilityDto.getCity());
            healthFacilityDao.setProvince(healthFacilityDto.getProvince());
            healthFacilityDao.setPostalCode(healthFacilityDto.getPostalCode());
            healthFacilityRepository.save(healthFacilityDao);

            HealthFacilityDto facilityDto = modelMapper.map(healthFacilityDao, HealthFacilityDto.class);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, facilityDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in Updating Health Facility by id. Error {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<Object> deleteHealthFacilityById(Long id) {
        try {
            log.info("Deleting a Health Facility by id");
            Optional<HealthFacilityDao> optionalHealthFacilityDao = healthFacilityRepository.findById(id);
            if (optionalHealthFacilityDao.isEmpty()) {
                log.info("Health Facility not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Health Facility found");
            healthFacilityRepository.delete(optionalHealthFacilityDao.get());
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, null, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in deleting Health Facility by id. Error {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<Object> searchHealthFacility(String request) {
        log.info("Searching Health Facility");
        try {
            List<HealthFacilityDao> healthFacilityDaoList;
            List<HealthFacilityDto> facilityDtoList = new ArrayList<>();

            log.info("Search by city");
            healthFacilityDaoList = healthFacilityRepository.findAllByCity(request);
            if (!healthFacilityDaoList.isEmpty()) {
                log.info("Health Facility found by city");
                for (HealthFacilityDao healthFacilityDao : healthFacilityDaoList) {
                    facilityDtoList.add(modelMapper.map(healthFacilityDao, HealthFacilityDto.class));
                }
                return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, facilityDtoList, HttpStatus.OK);
            }

            log.info("Search by province");
            healthFacilityDaoList = healthFacilityRepository.findAllByProvince(request);
            if (!healthFacilityDaoList.isEmpty()) {
                log.info("Health Facility found by province");
                for (HealthFacilityDao healthFacilityDao : healthFacilityDaoList) {
                    facilityDtoList.add(modelMapper.map(healthFacilityDao, HealthFacilityDto.class));
                }
                return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, facilityDtoList, HttpStatus.OK);
            }

            log.info("Search by postal code");
            healthFacilityDaoList = healthFacilityRepository.findAllByPostalCode(request);
            if (!healthFacilityDaoList.isEmpty()) {
                log.info("Health Facility found by postal code");
                for (HealthFacilityDao healthFacilityDao : healthFacilityDaoList) {
                    facilityDtoList.add(modelMapper.map(healthFacilityDao, HealthFacilityDto.class));
                }
                return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, facilityDtoList, HttpStatus.OK);
            }

            log.info("Health Facility not found");
            return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("An error occurred in searching Health Facility by city, province, or postal code. Error {}", e.getMessage());
            throw e;
        }
    }
}
