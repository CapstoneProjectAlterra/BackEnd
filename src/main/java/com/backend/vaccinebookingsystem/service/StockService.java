package com.backend.vaccinebookingsystem.service;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.dao.FacilityVaccineDao;
import com.backend.vaccinebookingsystem.domain.dao.HealthFacilityDao;
import com.backend.vaccinebookingsystem.domain.dao.VaccineTypeDao;
import com.backend.vaccinebookingsystem.domain.dto.*;
import com.backend.vaccinebookingsystem.repository.HealthFacilityRepository;
import com.backend.vaccinebookingsystem.repository.StockRepository;
import com.backend.vaccinebookingsystem.repository.VaccineTypeRepository;
import com.backend.vaccinebookingsystem.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private HealthFacilityRepository healthFacilityRepository;

    @Autowired
    private VaccineTypeRepository vaccineTypeRepository;

    public ResponseEntity<Object> createStock(FacilityVaccineDto facilityVaccineDto) {
        try {
            log.info("Creating new Stock");
            Optional<FacilityVaccineDao> optionalFacilityVaccineDao = stockRepository.findByFacilityIdAndAndVaccineId(facilityVaccineDto.getFacilityId(), facilityVaccineDto.getVaccineId());

            if (optionalFacilityVaccineDao.isPresent()) {
                log.info("Stock already exists");
                FacilityVaccineDao facilityVaccineDao = optionalFacilityVaccineDao.get();
                facilityVaccineDao.setStock(facilityVaccineDto.getStock());
                stockRepository.save(facilityVaccineDao);

                FacilityVaccineDto vaccineDto = FacilityVaccineDto.builder()
                        .facilityId(optionalFacilityVaccineDao.get().getFacilityId())
                        .vaccineId(optionalFacilityVaccineDao.get().getVaccineId())
                        .stock(facilityVaccineDao.getStock())
                        .facility(HealthFacilityDto.builder()
                                .id(optionalFacilityVaccineDao.get().getFacility().getId())
                                .facilityName(optionalFacilityVaccineDao.get().getFacility().getFacilityName())
                                .streetName(optionalFacilityVaccineDao.get().getFacility().getStreetName())
                                .officeNumber(optionalFacilityVaccineDao.get().getFacility().getOfficeNumber())
                                .villageName(optionalFacilityVaccineDao.get().getFacility().getVillageName())
                                .district(optionalFacilityVaccineDao.get().getFacility().getDistrict())
                                .city(optionalFacilityVaccineDao.get().getFacility().getCity())
                                .province(optionalFacilityVaccineDao.get().getFacility().getProvince())
                                .postalCode(optionalFacilityVaccineDao.get().getFacility().getPostalCode())
                                .image(HealthFacilityImageDto.builder()
                                        .facilityId(optionalFacilityVaccineDao.get().getFacility().getImage().getFacilityId())
                                        .base64(optionalFacilityVaccineDao.get().getFacility().getImage().getBase64())
                                        .contentType(optionalFacilityVaccineDao.get().getFacility().getImage().getContentType())
                                        .build())
                                .profile(ProfileDto.builder()
                                        .userId(optionalFacilityVaccineDao.get().getFacility().getProfile().getUserId())
                                        .role(optionalFacilityVaccineDao.get().getFacility().getProfile().getRole())
                                        .build())
                                .build())
                        .vaccine(VaccineTypeDto.builder()
                                .id(optionalFacilityVaccineDao.get().getVaccine().getId())
                                .vaccineName(optionalFacilityVaccineDao.get().getVaccine().getVaccineName())
                                .build())
                        .build();
                return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, vaccineDto, HttpStatus.OK);
            }

            log.info("New Stock");
            Optional<HealthFacilityDao> optionalHealthFacilityDao = healthFacilityRepository.findById(facilityVaccineDto.getFacilityId());
            Optional<VaccineTypeDao> optionalVaccineTypeDao = vaccineTypeRepository.findById(facilityVaccineDto.getVaccineId());

            if (optionalHealthFacilityDao.isEmpty() || optionalVaccineTypeDao.isEmpty()) {
                log.info("Not Found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Found");
            FacilityVaccineDao facilityVaccineDao = FacilityVaccineDao.builder()
                    .facilityId(facilityVaccineDto.getFacilityId())
                    .vaccineId(facilityVaccineDto.getVaccineId())
                    .stock(facilityVaccineDto.getStock())
                    .build();

            stockRepository.save(facilityVaccineDao);

            FacilityVaccineDto vaccineDto = FacilityVaccineDto.builder()
                    .facilityId(facilityVaccineDao.getFacilityId())
                    .vaccineId(facilityVaccineDao.getVaccineId())
                    .stock(facilityVaccineDao.getStock())
                    .facility(HealthFacilityDto.builder()
                            .id(optionalHealthFacilityDao.get().getId())
                            .facilityName(optionalHealthFacilityDao.get().getFacilityName())
                            .streetName(optionalHealthFacilityDao.get().getStreetName())
                            .officeNumber(optionalHealthFacilityDao.get().getOfficeNumber())
                            .villageName(optionalHealthFacilityDao.get().getVillageName())
                            .district(optionalHealthFacilityDao.get().getDistrict())
                            .city(optionalHealthFacilityDao.get().getCity())
                            .province(optionalHealthFacilityDao.get().getProvince())
                            .postalCode(optionalHealthFacilityDao.get().getPostalCode())
                            .image(HealthFacilityImageDto.builder()
                                    .facilityId(optionalHealthFacilityDao.get().getImage().getFacilityId())
                                    .base64(optionalHealthFacilityDao.get().getImage().getBase64())
                                    .contentType(optionalHealthFacilityDao.get().getImage().getContentType())
                                    .build())
                            .profile(ProfileDto.builder()
                                    .userId(optionalHealthFacilityDao.get().getProfile().getUserId())
                                    .role(optionalHealthFacilityDao.get().getProfile().getRole())
                                    .build())
                            .build())
                    .vaccine(VaccineTypeDto.builder()
                            .id(optionalVaccineTypeDao.get().getId())
                            .vaccineName(optionalVaccineTypeDao.get().getVaccineName())
                            .build())
                    .build();

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, vaccineDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in creating new Stock. Error: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional
    public ResponseEntity<Object> searchStockById(Long facilityId, Long vaccineId) {
        try {
            log.info("Getting a Stock by id");
            Optional<FacilityVaccineDao> optionalFacilityVaccineDao = stockRepository.findByFacilityIdAndAndVaccineId(facilityId, vaccineId);

            if (optionalFacilityVaccineDao.isEmpty()) {
                log.info("Stock not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Stock found");
            FacilityVaccineDto vaccineDto = FacilityVaccineDto.builder()
                    .facilityId(optionalFacilityVaccineDao.get().getFacilityId())
                    .vaccineId(optionalFacilityVaccineDao.get().getVaccineId())
                    .stock(optionalFacilityVaccineDao.get().getStock())
                    .facility(HealthFacilityDto.builder()
                            .id(optionalFacilityVaccineDao.get().getFacility().getId())
                            .facilityName(optionalFacilityVaccineDao.get().getFacility().getFacilityName())
                            .streetName(optionalFacilityVaccineDao.get().getFacility().getStreetName())
                            .officeNumber(optionalFacilityVaccineDao.get().getFacility().getOfficeNumber())
                            .villageName(optionalFacilityVaccineDao.get().getFacility().getVillageName())
                            .district(optionalFacilityVaccineDao.get().getFacility().getDistrict())
                            .city(optionalFacilityVaccineDao.get().getFacility().getCity())
                            .province(optionalFacilityVaccineDao.get().getFacility().getProvince())
                            .postalCode(optionalFacilityVaccineDao.get().getFacility().getPostalCode())
                            .image(HealthFacilityImageDto.builder()
                                    .facilityId(optionalFacilityVaccineDao.get().getFacility().getImage().getFacilityId())
                                    .base64(optionalFacilityVaccineDao.get().getFacility().getImage().getBase64())
                                    .contentType(optionalFacilityVaccineDao.get().getFacility().getImage().getContentType())
                                    .build())
                            .profile(ProfileDto.builder()
                                    .userId(optionalFacilityVaccineDao.get().getFacility().getProfile().getUserId())
                                    .role(optionalFacilityVaccineDao.get().getFacility().getProfile().getRole())
                                    .build())
                            .build())
                    .vaccine(VaccineTypeDto.builder()
                            .id(optionalFacilityVaccineDao.get().getVaccine().getId())
                            .vaccineName(optionalFacilityVaccineDao.get().getVaccine().getVaccineName())
                            .build())
                    .build();
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, vaccineDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting a Stock by id. Error {}", e.getMessage());
            throw e;
        }
    }

    @Transactional
    public ResponseEntity<Object> getAllStocks() {
        try {
            log.info("Getting all of Stocks");
            List<FacilityVaccineDao> facilityVaccineDaoList;
            List<FacilityVaccineDto> facilityVaccineDtos = new ArrayList<>();

            facilityVaccineDaoList = stockRepository.findAll();

            for (FacilityVaccineDao facilityVaccineDao : facilityVaccineDaoList) {
                facilityVaccineDtos.add(FacilityVaccineDto.builder()
                                .facilityId(facilityVaccineDao.getFacilityId())
                                .vaccineId(facilityVaccineDao.getVaccineId())
                                .stock(facilityVaccineDao.getStock())
                                .facility(HealthFacilityDto.builder()
                                        .id(facilityVaccineDao.getFacility().getId())
                                        .facilityName(facilityVaccineDao.getFacility().getFacilityName())
                                        .streetName(facilityVaccineDao.getFacility().getStreetName())
                                        .officeNumber(facilityVaccineDao.getFacility().getOfficeNumber())
                                        .villageName(facilityVaccineDao.getFacility().getVillageName())
                                        .district(facilityVaccineDao.getFacility().getDistrict())
                                        .city(facilityVaccineDao.getFacility().getCity())
                                        .province(facilityVaccineDao.getFacility().getProvince())
                                        .postalCode(facilityVaccineDao.getFacility().getPostalCode())
                                        .image(HealthFacilityImageDto.builder()
                                                .facilityId(facilityVaccineDao.getFacility().getImage().getFacilityId())
                                                .base64(facilityVaccineDao.getFacility().getImage().getBase64())
                                                .contentType(facilityVaccineDao.getFacility().getImage().getContentType())
                                                .build())
                                        .profile(ProfileDto.builder()
                                                .userId(facilityVaccineDao.getFacility().getProfile().getUserId())
                                                .role(facilityVaccineDao.getFacility().getProfile().getRole())
                                                .build())
                                        .build())
                                .vaccine(VaccineTypeDto.builder()
                                        .id(facilityVaccineDao.getVaccine().getId())
                                        .vaccineName(facilityVaccineDao.getVaccine().getVaccineName())
                                        .build())
                        .build());
            }

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, facilityVaccineDtos, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting all of Stocks. Error {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<Object> updateStockById(Long facilityId, Long vaccineId, FacilityVaccineDto facilityVaccineDto) {
        try {
            log.info("Updating a Stock by id");
            Optional<FacilityVaccineDao> optionalFacilityVaccineDao = stockRepository.findByFacilityIdAndAndVaccineId(facilityId, vaccineId);

            if (optionalFacilityVaccineDao.isEmpty()) {
                log.info("Stock not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Stock found");
            FacilityVaccineDao facilityVaccineDao = optionalFacilityVaccineDao.get();
            facilityVaccineDao.setStock(facilityVaccineDto.getStock());
            stockRepository.save(facilityVaccineDao);

            FacilityVaccineDto vaccineDto = FacilityVaccineDto.builder()
                    .facilityId(optionalFacilityVaccineDao.get().getFacilityId())
                    .vaccineId(optionalFacilityVaccineDao.get().getVaccineId())
                    .stock(facilityVaccineDao.getStock())
                    .facility(HealthFacilityDto.builder()
                            .id(optionalFacilityVaccineDao.get().getFacility().getId())
                            .facilityName(optionalFacilityVaccineDao.get().getFacility().getFacilityName())
                            .streetName(optionalFacilityVaccineDao.get().getFacility().getStreetName())
                            .officeNumber(optionalFacilityVaccineDao.get().getFacility().getOfficeNumber())
                            .villageName(optionalFacilityVaccineDao.get().getFacility().getVillageName())
                            .district(optionalFacilityVaccineDao.get().getFacility().getDistrict())
                            .city(optionalFacilityVaccineDao.get().getFacility().getCity())
                            .province(optionalFacilityVaccineDao.get().getFacility().getProvince())
                            .postalCode(optionalFacilityVaccineDao.get().getFacility().getPostalCode())
                            .image(HealthFacilityImageDto.builder()
                                    .facilityId(optionalFacilityVaccineDao.get().getFacility().getImage().getFacilityId())
                                    .base64(optionalFacilityVaccineDao.get().getFacility().getImage().getBase64())
                                    .contentType(optionalFacilityVaccineDao.get().getFacility().getImage().getContentType())
                                    .build())
                            .profile(ProfileDto.builder()
                                    .userId(optionalFacilityVaccineDao.get().getFacility().getProfile().getUserId())
                                    .role(optionalFacilityVaccineDao.get().getFacility().getProfile().getRole())
                                    .build())
                            .build())
                    .vaccine(VaccineTypeDto.builder()
                            .id(optionalFacilityVaccineDao.get().getVaccine().getId())
                            .vaccineName(optionalFacilityVaccineDao.get().getVaccine().getVaccineName())
                            .build())
                    .build();
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, vaccineDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in Updating Stock by id. Error {}", e.getMessage());
            throw e;
        }
    }

    @Transactional
    public ResponseEntity<Object> deleteStockById(Long facilityId, Long vaccineId) {
        try {
            log.info("Deleting a Stock by id");

            Optional<FacilityVaccineDao> optionalFacilityVaccineDao = stockRepository.findByFacilityIdAndAndVaccineId(facilityId, vaccineId);

            if (optionalFacilityVaccineDao.isEmpty()) {
                log.info("Stock not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Stock found");
            FacilityVaccineDao facilityVaccineDao = optionalFacilityVaccineDao.get();
            facilityVaccineDao.setStock(0);
            stockRepository.save(facilityVaccineDao);

            FacilityVaccineDto vaccineDto = FacilityVaccineDto.builder()
                    .facilityId(optionalFacilityVaccineDao.get().getFacilityId())
                    .vaccineId(optionalFacilityVaccineDao.get().getVaccineId())
                    .stock(facilityVaccineDao.getStock())
                    .facility(HealthFacilityDto.builder()
                            .id(optionalFacilityVaccineDao.get().getFacility().getId())
                            .facilityName(optionalFacilityVaccineDao.get().getFacility().getFacilityName())
                            .streetName(optionalFacilityVaccineDao.get().getFacility().getStreetName())
                            .officeNumber(optionalFacilityVaccineDao.get().getFacility().getOfficeNumber())
                            .villageName(optionalFacilityVaccineDao.get().getFacility().getVillageName())
                            .district(optionalFacilityVaccineDao.get().getFacility().getDistrict())
                            .city(optionalFacilityVaccineDao.get().getFacility().getCity())
                            .province(optionalFacilityVaccineDao.get().getFacility().getProvince())
                            .postalCode(optionalFacilityVaccineDao.get().getFacility().getPostalCode())
                            .image(HealthFacilityImageDto.builder()
                                    .facilityId(optionalFacilityVaccineDao.get().getFacility().getImage().getFacilityId())
                                    .base64(optionalFacilityVaccineDao.get().getFacility().getImage().getBase64())
                                    .contentType(optionalFacilityVaccineDao.get().getFacility().getImage().getContentType())
                                    .build())
                            .profile(ProfileDto.builder()
                                    .userId(optionalFacilityVaccineDao.get().getFacility().getProfile().getUserId())
                                    .role(optionalFacilityVaccineDao.get().getFacility().getProfile().getRole())
                                    .build())
                            .build())
                    .vaccine(VaccineTypeDto.builder()
                            .id(optionalFacilityVaccineDao.get().getVaccine().getId())
                            .vaccineName(optionalFacilityVaccineDao.get().getVaccine().getVaccineName())
                            .build())
                    .build();

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, vaccineDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in deleting Stock by id. Error {}", e.getMessage());
            throw e;
        }
    }
}
