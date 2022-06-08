package com.backend.vaccinebookingsystem.service;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.dao.VaccineTypeDao;
import com.backend.vaccinebookingsystem.domain.dto.VaccineTypeDto;
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
public class VaccineTypeService {

    @Autowired
    private VaccineTypeRepository vaccineTypeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<Object> createVaccineType(VaccineTypeDto vaccineTypeDto) {
        try {
            log.info("Creating new Vaccine Type");
            VaccineTypeDao vaccineTypeDao = modelMapper.map(vaccineTypeDto, VaccineTypeDao.class);
            vaccineTypeRepository.save(vaccineTypeDao);

            VaccineTypeDto typeDto = modelMapper.map(vaccineTypeDao, VaccineTypeDto.class);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, typeDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in creating new Vaccine Type. Error: {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getVaccineTypeById(Long id) {
        try {
            log.info("Getting a Vaccine Type by id");
            Optional<VaccineTypeDao> optionalVaccineTypeDao = vaccineTypeRepository.findById(id);

            if (optionalVaccineTypeDao.isEmpty()) {
                log.info("Vaccine Type not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Vaccine Type found");
            VaccineTypeDto typeDto = modelMapper.map(optionalVaccineTypeDao.get(), VaccineTypeDto.class);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, typeDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting a Vaccine Type by id. Error {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<Object> getAllVaccineTypes() {
        try {
            log.info("Getting all of Vaccine Types");
            List<VaccineTypeDao> vaccineTypeDaoList;
            List<VaccineTypeDto> vaccineTypeDtos = new ArrayList<>();

            vaccineTypeDaoList = vaccineTypeRepository.findAll();

            for (VaccineTypeDao vaccineTypeDao : vaccineTypeDaoList) {
                vaccineTypeDtos.add(modelMapper.map(vaccineTypeDao, VaccineTypeDto.class));
            }

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, vaccineTypeDtos, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting all of Vaccine Types. Error {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateVaccineTypeById(Long id, VaccineTypeDto vaccineTypeDto) {
        try {
            log.info("Updating a Vaccine Type by id");
            Optional<VaccineTypeDao> optionalVaccineTypeDao = vaccineTypeRepository.findById(id);
            if (optionalVaccineTypeDao.isEmpty()) {
                log.info("Vaccine Type not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Vaccine Type found");
            VaccineTypeDao vaccineTypeDao = optionalVaccineTypeDao.get();
            vaccineTypeDao.setVaccineName(vaccineTypeDto.getVaccineName());
            vaccineTypeRepository.save(vaccineTypeDao);

            VaccineTypeDto typeDto = modelMapper.map(vaccineTypeDao, VaccineTypeDto.class);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, typeDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in Updating Vaccine Type by id. Error {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<Object> deleteVaccineTypeById(Long id) {
        try {
            log.info("Deleting a Vaccine Type by id");
            Optional<VaccineTypeDao> optionalVaccineTypeDao = vaccineTypeRepository.findById(id);
            if (optionalVaccineTypeDao.isEmpty()) {
                log.info("Vaccine Type not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Vaccine Type found");
            vaccineTypeRepository.delete(optionalVaccineTypeDao.get());
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, null, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in deleting Vaccine Type by id. Error {}", e.getMessage());
            throw e;
        }
    }

}
