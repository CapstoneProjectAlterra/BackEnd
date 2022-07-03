package com.backend.vaccinebookingsystem.service;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.dao.FamilyDao;
import com.backend.vaccinebookingsystem.domain.dao.ProfileDao;
import com.backend.vaccinebookingsystem.domain.dao.UserDao;
import com.backend.vaccinebookingsystem.domain.dto.FamilyDto;
import com.backend.vaccinebookingsystem.domain.dto.ProfileDto;
import com.backend.vaccinebookingsystem.repository.FamilyRepository;
import com.backend.vaccinebookingsystem.repository.ProfileRepository;
import com.backend.vaccinebookingsystem.repository.UserRepository;
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
public class FamilyService {

    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Object> createFamily(FamilyDto familyDto) {
        try {
            log.info("Creating new Family");
            Optional<ProfileDao> optionalProfileDao = profileRepository.findById(familyDto.getProfile().getUserId());

            if (optionalProfileDao.isEmpty()) {
                log.info("Profile not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Profile found");

            FamilyDao familyDao = FamilyDao.builder()
                    .statusInFamily(familyDto.getStatusInFamily())
                    .NIK(familyDto.getNIK())
                    .name(familyDto.getName())
                    .email(familyDto.getEmail())
                    .phoneNumber(familyDto.getPhoneNumber())
                    .gender(familyDto.getGender())
                    .placeOfBirth((familyDto.getPlaceOfBirth()))
                    .dateOfBirth(familyDto.getDateOfBirth())
                    .residenceAddress(familyDto.getResidenceAddress())
                    .idCardAddress(familyDto.getIdCardAddress())
                    .profile(optionalProfileDao.get())
                    .build();
            familyRepository.save(familyDao);

            ProfileDto profileDto = ProfileDto.builder()
                    .userId(optionalProfileDao.get().getUserId())
                    .role(optionalProfileDao.get().getRole())
                    .build();

            FamilyDto dto = FamilyDto.builder()
                    .id(familyDao.getId())
                    .statusInFamily(familyDao.getStatusInFamily())
                    .NIK(familyDao.getNIK())
                    .name(familyDao.getName())
                    .email(familyDao.getEmail())
                    .phoneNumber(familyDao.getPhoneNumber())
                    .gender(familyDao.getGender())
                    .placeOfBirth(familyDao.getPlaceOfBirth())
                    .dateOfBirth(familyDao.getDateOfBirth())
                    .residenceAddress(familyDao.getResidenceAddress())
                    .idCardAddress(familyDao.getIdCardAddress())
                    .profile(profileDto)
                    .build();
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, dto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in creating new Family. Error: {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getFamilyById(Long id) {
        try {
            log.info("Getting a Family by id");
            Optional<FamilyDao> optionalFamilyDao = familyRepository.findById(id);

            Optional<ProfileDao> optionalProfileDao = profileRepository.findById(optionalFamilyDao.get().getProfile().getUserId());

            if (optionalFamilyDao.isEmpty() || optionalProfileDao.isEmpty()) {
                log.info("Family not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Family found");

            ProfileDto profileDto = ProfileDto.builder()
                    .userId(optionalProfileDao.get().getUserId())
                    .role(optionalProfileDao.get().getRole())
                    .build();

            FamilyDto dto = FamilyDto.builder()
                    .id(optionalFamilyDao.get().getId())
                    .statusInFamily(optionalFamilyDao.get().getStatusInFamily())
                    .NIK(optionalFamilyDao.get().getNIK())
                    .name(optionalFamilyDao.get().getName())
                    .email(optionalFamilyDao.get().getEmail())
                    .phoneNumber(optionalFamilyDao.get().getPhoneNumber())
                    .gender(optionalFamilyDao.get().getGender())
                    .placeOfBirth(optionalFamilyDao.get().getPlaceOfBirth())
                    .dateOfBirth(optionalFamilyDao.get().getDateOfBirth())
                    .residenceAddress(optionalFamilyDao.get().getResidenceAddress())
                    .idCardAddress(optionalFamilyDao.get().getIdCardAddress())
                    .profile(profileDto)
                    .build();

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, dto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting a Family by id. Error {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<Object> getAllFamilies() {
        try {
            log.info("Getting all of Families");
            List<FamilyDao> familyDaoList;
            List<FamilyDto> familyDtos = new ArrayList<>();

            familyDaoList = familyRepository.findAll();

            for (FamilyDao familyDao : familyDaoList) {
                Optional<ProfileDao> optionalProfileDao = profileRepository.findById(familyDao.getProfile().getUserId());

                ProfileDto profileDto = ProfileDto.builder()
                        .userId(optionalProfileDao.get().getUserId())
                        .role(optionalProfileDao.get().getRole())
                        .build();

                familyDtos.add(FamilyDto.builder()
                                .id(familyDao.getId())
                                .statusInFamily(familyDao.getStatusInFamily())
                                .NIK(familyDao.getNIK())
                                .name(familyDao.getName())
                                .email(familyDao.getEmail())
                                .phoneNumber(familyDao.getPhoneNumber())
                                .gender(familyDao.getGender())
                                .placeOfBirth(familyDao.getPlaceOfBirth())
                                .dateOfBirth(familyDao.getDateOfBirth())
                                .residenceAddress(familyDao.getResidenceAddress())
                                .idCardAddress(familyDao.getIdCardAddress())
                                .profile(profileDto)
                        .build());
            }

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, familyDtos, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting all of Families. Error {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateFamilyById(Long id, FamilyDto familyDto) {
        try {
            log.info("Updating a Family by id");
            Optional<FamilyDao> optionalFamilyDao = familyRepository.findById(id);

            Optional<ProfileDao> optionalProfileDao = profileRepository.findById(familyDto.getProfile().getUserId());

            if (optionalFamilyDao.isEmpty() || optionalProfileDao.isEmpty()) {
                log.info("Family not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            Optional<UserDao> optionalUserDao = userRepository.findByUsername(optionalFamilyDao.get().getNIK());

            log.info("Family found");
            FamilyDao familyDao = optionalFamilyDao.get();
            familyDao.setStatusInFamily(familyDto.getStatusInFamily());
            familyDao.setNIK(familyDto.getNIK());
            familyDao.setName(familyDto.getName());
            familyDao.setEmail(familyDto.getEmail());
            familyDao.setPhoneNumber(familyDto.getPhoneNumber());
            familyDao.setGender(familyDto.getGender());
            familyDao.setPlaceOfBirth(familyDto.getPlaceOfBirth());
            familyDao.setDateOfBirth(familyDto.getDateOfBirth());
            familyDao.setResidenceAddress(familyDto.getResidenceAddress());
            familyDao.setIdCardAddress(familyDto.getIdCardAddress());
            familyDao.setProfile(optionalProfileDao.get());
            familyRepository.save(familyDao);

            if (optionalUserDao.isPresent()) {
                log.info("User found");
                UserDao userDao = optionalUserDao.get();
                userDao.setUsername(familyDto.getNIK());
                userDao.setName(familyDto.getName());
                userDao.setEmail(familyDto.getEmail());

                userRepository.save(userDao);
            }

            ProfileDto profileDto = ProfileDto.builder()
                    .userId(optionalProfileDao.get().getUserId())
                    .role(optionalProfileDao.get().getRole())
                    .build();

            FamilyDto dto = FamilyDto.builder()
                    .id(familyDao.getId())
                    .statusInFamily(familyDao.getStatusInFamily())
                    .NIK(familyDao.getNIK())
                    .name(familyDao.getName())
                    .email(familyDao.getEmail())
                    .phoneNumber(familyDao.getPhoneNumber())
                    .gender(familyDao.getGender())
                    .placeOfBirth(familyDao.getPlaceOfBirth())
                    .dateOfBirth(familyDao.getDateOfBirth())
                    .residenceAddress(familyDao.getResidenceAddress())
                    .idCardAddress(familyDao.getIdCardAddress())
                    .profile(profileDto)
                    .build();
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, dto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in Updating Family by id. Error {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<Object> deleteFamilyById(Long id) {
        try {
            log.info("Deleting a Family by id");
            Optional<FamilyDao> optionalFamilyDao = familyRepository.findById(id);
            if (optionalFamilyDao.isEmpty()) {
                log.info("Family not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Family found");
            familyRepository.delete(optionalFamilyDao.get());
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, null, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in deleting Family by id. Error {}", e.getMessage());
            throw e;
        }
    }
}
