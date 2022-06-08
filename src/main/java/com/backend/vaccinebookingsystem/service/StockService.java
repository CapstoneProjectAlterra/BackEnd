package com.backend.vaccinebookingsystem.service;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.dao.FacilityVaccineDao;
import com.backend.vaccinebookingsystem.domain.dto.FacilityVaccineDto;
import com.backend.vaccinebookingsystem.repository.StockRepository;
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
    private ModelMapper modelMapper;

    public ResponseEntity<Object> createStock(FacilityVaccineDto facilityVaccineDto) {
        try {
            log.info("Creating new Stock");
            FacilityVaccineDao facilityVaccineDao = modelMapper.map(facilityVaccineDto, FacilityVaccineDao.class);
            stockRepository.save(facilityVaccineDao);

            FacilityVaccineDto vaccineDto = modelMapper.map(facilityVaccineDao, FacilityVaccineDto.class);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, vaccineDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in creating new Stock. Error: {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getStockById(Long id) {
        try {
            log.info("Getting a Stock by id");
            Optional<FacilityVaccineDao> optionalFacilityVaccineDao = stockRepository.findById(id);

            if (optionalFacilityVaccineDao.isEmpty()) {
                log.info("Stock not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Stock found");
            FacilityVaccineDto vaccineDto = modelMapper.map(optionalFacilityVaccineDao.get(), FacilityVaccineDto.class);
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
                facilityVaccineDtos.add(modelMapper.map(facilityVaccineDao, FacilityVaccineDto.class));
            }

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, facilityVaccineDtos, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting all of Stocks. Error {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateStockById(Long id, FacilityVaccineDto facilityVaccineDto) {
        try {
            log.info("Updating a Stock by id");
            Optional<FacilityVaccineDao> optionalFacilityVaccineDao = stockRepository.findById(id);
            if (optionalFacilityVaccineDao.isEmpty()) {
                log.info("Stock not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Stock found");
            FacilityVaccineDao facilityVaccineDao = optionalFacilityVaccineDao.get();
            facilityVaccineDao.setStock(facilityVaccineDto.getStock());
            stockRepository.save(facilityVaccineDao);

            FacilityVaccineDto vaccineDto = modelMapper.map(facilityVaccineDao, FacilityVaccineDto.class);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, vaccineDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in Updating Stock by id. Error {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<Object> deleteStockById(Long id) {
        try {
            log.info("Deleting a Stock by id");
            Optional<FacilityVaccineDao> optionalFacilityVaccineDao = stockRepository.findById(id);
            if (optionalFacilityVaccineDao.isEmpty()) {
                log.info("Stock not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Stock found");
            stockRepository.delete(optionalFacilityVaccineDao.get());
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, null, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in deleting Stock by id. Error {}", e.getMessage());
            throw e;
        }
    }
}
