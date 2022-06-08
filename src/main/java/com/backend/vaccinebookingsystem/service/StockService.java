package com.backend.vaccinebookingsystem.service;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.dao.FacilityVaccineDao;
import com.backend.vaccinebookingsystem.domain.dto.FacilityVaccineDto;
import com.backend.vaccinebookingsystem.repository.HealthFacilityRepository;
import com.backend.vaccinebookingsystem.repository.StockRepository;
import com.backend.vaccinebookingsystem.repository.VaccineTypeRepository;
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
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private HealthFacilityRepository healthFacilityRepository;

    @Autowired
    private VaccineTypeRepository vaccineTypeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<Object> createStock(FacilityVaccineDto facilityVaccineDto) {
        try {
            log.info("Creating new Stock");
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
                    .build();

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, vaccineDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in creating new Stock. Error: {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> searchStockById(Long facilityId, Long vaccineId) {
        try {
            log.info("Getting a Stock by id");
            Optional<FacilityVaccineDao> optionalFacilityVaccineDaoFacility = stockRepository.findByFacilityId(facilityId);
            Optional<FacilityVaccineDao> optionalFacilityVaccineDaoVaccine = stockRepository.findByVaccineId(vaccineId);

            if (optionalFacilityVaccineDaoFacility.isEmpty() || optionalFacilityVaccineDaoVaccine.isEmpty()) {
                log.info("Stock not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Stock found");
            FacilityVaccineDto vaccineDto = FacilityVaccineDto.builder()
                    .facilityId(optionalFacilityVaccineDaoFacility.get().getFacilityId())
                    .vaccineId(optionalFacilityVaccineDaoVaccine.get().getVaccineId())
                    .stock(optionalFacilityVaccineDaoFacility.get().getStock())
                    .build();
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, vaccineDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting a Stock by id. Error {}", e.getMessage());
            throw e;
        }
    }

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
                        .build());
            }

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, facilityVaccineDtos, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting all of Stocks. Error {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateStockById(Long facilityId, Long vaccineId, FacilityVaccineDto facilityVaccineDto) {
        try {
            log.info("Updating a Stock by id");
            Optional<FacilityVaccineDao> optionalFacilityVaccineDaoFacility = stockRepository.findByFacilityId(facilityId);
            Optional<FacilityVaccineDao> optionalFacilityVaccineDaoVaccine = stockRepository.findByVaccineId(vaccineId);

            if (optionalFacilityVaccineDaoFacility.isEmpty() || optionalFacilityVaccineDaoVaccine.isEmpty()) {
                log.info("Stock not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Stock found");
            FacilityVaccineDao facilityVaccineDao = optionalFacilityVaccineDaoFacility.get();
            facilityVaccineDao.setStock(facilityVaccineDto.getStock());
            stockRepository.save(facilityVaccineDao);

            FacilityVaccineDto vaccineDto = FacilityVaccineDto.builder()
                    .facilityId(optionalFacilityVaccineDaoFacility.get().getVaccineId())
                    .vaccineId(optionalFacilityVaccineDaoVaccine.get().getVaccineId())
                    .stock(facilityVaccineDao.getStock())
                    .build();
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, vaccineDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in Updating Stock by id. Error {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<Object> deleteStockById(Long facilityId, Long vaccineId) {
        try {
            log.info("Deleting a Stock by id");
            Optional<FacilityVaccineDao> optionalFacilityVaccineDaoFacility = stockRepository.findByFacilityId(facilityId);
            Optional<FacilityVaccineDao> optionalFacilityVaccineDaoVaccine = stockRepository.findByVaccineId(vaccineId);

            if (optionalFacilityVaccineDaoFacility.isEmpty() || optionalFacilityVaccineDaoVaccine.isEmpty()) {
                log.info("Stock not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Stock found");
            stockRepository.delete(optionalFacilityVaccineDaoFacility.get());
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, null, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in deleting Stock by id. Error {}", e.getMessage());
            throw e;
        }
    }
}
