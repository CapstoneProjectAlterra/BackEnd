package com.backend.vaccinebookingsystem.service;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.dao.HealthFacilityDao;
import com.backend.vaccinebookingsystem.domain.dao.HealthFacilityImageDao;
import com.backend.vaccinebookingsystem.domain.dao.ProfileDao;
import com.backend.vaccinebookingsystem.domain.dto.HealthFacilityDto;
import com.backend.vaccinebookingsystem.domain.dto.HealthFacilityImageDto;
import com.backend.vaccinebookingsystem.domain.dto.ProfileDto;
import com.backend.vaccinebookingsystem.repository.HealthFacilityImageRepository;
import com.backend.vaccinebookingsystem.repository.HealthFacilityRepository;
import com.backend.vaccinebookingsystem.repository.ProfileRepository;
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
public class HealthFacilityService {

    @Autowired
    private HealthFacilityRepository healthFacilityRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private HealthFacilityImageRepository healthFacilityImageRepository;


    public ResponseEntity<Object> createHealthFacility(HealthFacilityDto healthFacilityDto) {
        try {
            log.info("Creating new health facility");
            Optional<HealthFacilityDao> optionalHealthFacilityDao = healthFacilityRepository.findHealthFacilityDaoByFacilityName(healthFacilityDto.getFacilityName());

            if (optionalHealthFacilityDao.isPresent()) {
                log.info("Health Facility already exists");
                return ResponseUtil.build(AppConstant.ResponseCode.ALREADY_EXISTS, null, HttpStatus.CONFLICT);
            }

            Optional<ProfileDao> optionalProfileDao = profileRepository.findById(healthFacilityDto.getProfile().getUserId());

            if (optionalProfileDao.isEmpty()) {
                log.info("Profile not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Profile found");

            HealthFacilityDao healthFacilityDao = HealthFacilityDao.builder()
                    .facilityName(healthFacilityDto.getFacilityName())
                    .streetName(healthFacilityDto.getStreetName())
                    .officeNumber(healthFacilityDto.getOfficeNumber())
                    .villageName(healthFacilityDto.getVillageName())
                    .district(healthFacilityDto.getDistrict())
                    .city(healthFacilityDto.getCity())
                    .province(healthFacilityDto.getProvince())
                    .postalCode(healthFacilityDto.getPostalCode())
                    .image(HealthFacilityImageDao.builder()
                            .base64(healthFacilityDto.getImage().getBase64())
                            .contentType(healthFacilityDto.getImage().getContentType())
                            .build())
                    .profile(optionalProfileDao.get())
                    .build();
            healthFacilityRepository.save(healthFacilityDao);

            ProfileDto profileDto = ProfileDto.builder()
                    .userId(healthFacilityDao.getProfile().getUserId())
                    .role(healthFacilityDao.getProfile().getRole())
                    .build();

            HealthFacilityImageDto healthFacilityImageDto = HealthFacilityImageDto.builder()
                    .facilityId(healthFacilityDao.getImage().getFacilityId())
                    .base64(healthFacilityDao.getImage().getBase64())
                    .contentType(healthFacilityDao.getImage().getContentType())
                    .build();

            HealthFacilityDto facilityDto = HealthFacilityDto.builder()
                    .id(healthFacilityDao.getId())
                    .facilityName(healthFacilityDao.getFacilityName())
                    .streetName(healthFacilityDao.getStreetName())
                    .officeNumber(healthFacilityDao.getOfficeNumber())
                    .villageName(healthFacilityDao.getVillageName())
                    .district(healthFacilityDao.getDistrict())
                    .city(healthFacilityDao.getCity())
                    .province(healthFacilityDao.getProvince())
                    .postalCode(healthFacilityDao.getPostalCode())
                    .image(healthFacilityImageDto)
                    .profile(profileDto)
                    .build();
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

            Optional<ProfileDao> optionalProfileDao = profileRepository.findById(optionalHealthFacilityDao.get().getProfile().getUserId());

            if (optionalHealthFacilityDao.isEmpty() || optionalProfileDao.isEmpty()) {
                log.info("Health Facility not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Health Facility found");
            ProfileDto profileDto = ProfileDto.builder()
                    .userId(optionalProfileDao.get().getUserId())
                    .role(optionalProfileDao.get().getRole())
                    .build();

            HealthFacilityImageDto healthFacilityImageDto = HealthFacilityImageDto.builder()
                    .facilityId(optionalHealthFacilityDao.get().getImage().getFacilityId())
                    .base64(optionalHealthFacilityDao.get().getImage().getBase64())
                    .contentType(optionalHealthFacilityDao.get().getImage().getContentType())
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
                    .image(healthFacilityImageDto)
                    .profile(profileDto)
                    .build();
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
                Optional<ProfileDao> optionalProfileDao = profileRepository.findById(healthFacilityDao.getProfile().getUserId());

                ProfileDto profileDto = ProfileDto.builder()
                        .userId(optionalProfileDao.get().getUserId())
                        .role(optionalProfileDao.get().getRole())
                        .build();

                HealthFacilityImageDto healthFacilityImageDto = HealthFacilityImageDto.builder()
                        .facilityId(healthFacilityDao.getImage().getFacilityId())
                        .base64(healthFacilityDao.getImage().getBase64())
                        .contentType(healthFacilityDao.getImage().getContentType())
                        .build();

                healthFacilityDtos.add(HealthFacilityDto.builder()
                        .id(healthFacilityDao.getId())
                        .facilityName(healthFacilityDao.getFacilityName())
                        .streetName(healthFacilityDao.getStreetName())
                        .officeNumber(healthFacilityDao.getOfficeNumber())
                        .villageName(healthFacilityDao.getVillageName())
                        .district(healthFacilityDao.getDistrict())
                        .city(healthFacilityDao.getCity())
                        .province(healthFacilityDao.getProvince())
                        .postalCode(healthFacilityDao.getPostalCode())
                        .image(healthFacilityImageDto)
                        .profile(profileDto)
                        .build());
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

            Optional<ProfileDao> optionalProfileDao = profileRepository.findById(optionalHealthFacilityDao.get().getProfile().getUserId());

            Optional<HealthFacilityImageDao> optionalHealthFacilityImageDao = healthFacilityImageRepository.findById(optionalHealthFacilityDao.get().getImage().getFacilityId());

            if (optionalProfileDao.isEmpty() || optionalHealthFacilityImageDao.isEmpty()) {
                log.info("Health Facility not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Health Facility found");

            HealthFacilityImageDao healthFacilityImageDao = optionalHealthFacilityImageDao.get();
            healthFacilityImageDao.setBase64(healthFacilityDto.getImage().getBase64());
            healthFacilityImageDao.setContentType(healthFacilityDto.getImage().getContentType());

            HealthFacilityDao healthFacilityDao = optionalHealthFacilityDao.get();
            healthFacilityDao.setFacilityName(healthFacilityDto.getFacilityName());
            healthFacilityDao.setStreetName(healthFacilityDto.getStreetName());
            healthFacilityDao.setOfficeNumber(healthFacilityDto.getOfficeNumber());
            healthFacilityDao.setVillageName(healthFacilityDto.getVillageName());
            healthFacilityDao.setDistrict(healthFacilityDto.getDistrict());
            healthFacilityDao.setCity(healthFacilityDto.getCity());
            healthFacilityDao.setProvince(healthFacilityDto.getProvince());
            healthFacilityDao.setPostalCode(healthFacilityDto.getPostalCode());
            healthFacilityDao.setImage(healthFacilityImageDao);
            healthFacilityDao.setProfile(optionalProfileDao.get());
            healthFacilityRepository.save(healthFacilityDao);

            ProfileDto profileDto = ProfileDto.builder()
                    .userId(optionalProfileDao.get().getUserId())
                    .role(optionalProfileDao.get().getRole())
                    .build();

            HealthFacilityImageDto healthFacilityImageDto = HealthFacilityImageDto.builder()
                    .facilityId(healthFacilityDao.getImage().getFacilityId())
                    .base64(healthFacilityDao.getImage().getBase64())
                    .contentType(healthFacilityDao.getImage().getContentType())
                    .build();

            HealthFacilityDto facilityDto = HealthFacilityDto.builder()
                    .id(healthFacilityDao.getId())
                    .facilityName(healthFacilityDao.getFacilityName())
                    .streetName(healthFacilityDao.getStreetName())
                    .officeNumber(healthFacilityDao.getOfficeNumber())
                    .villageName(healthFacilityDao.getVillageName())
                    .district(healthFacilityDao.getDistrict())
                    .city(healthFacilityDao.getCity())
                    .province(healthFacilityDao.getProvince())
                    .postalCode(healthFacilityDao.getPostalCode())
                    .image(healthFacilityImageDto)
                    .profile(profileDto)
                    .build();
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
}
