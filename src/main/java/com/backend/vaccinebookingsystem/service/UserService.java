package com.backend.vaccinebookingsystem.service;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.dao.FamilyDao;
import com.backend.vaccinebookingsystem.domain.dao.ProfileDao;
import com.backend.vaccinebookingsystem.domain.dao.UserDao;
import com.backend.vaccinebookingsystem.domain.dto.ProfileDto;
import com.backend.vaccinebookingsystem.domain.dto.UserDto;
import com.backend.vaccinebookingsystem.repository.FamilyRepository;
import com.backend.vaccinebookingsystem.repository.ProfileRepository;
import com.backend.vaccinebookingsystem.repository.UserRepository;
import com.backend.vaccinebookingsystem.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<Object> getUserById(Long id) {
        try {
            log.info("Getting User by an id");
            Optional<UserDao> optionalUserDao = userRepository.findById(id);

            Optional<ProfileDao> optionalProfileDao = profileRepository.findById(id);

            if (optionalUserDao.isEmpty() || optionalProfileDao.isEmpty()) {
                log.info("User not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            ProfileDto profileDto = ProfileDto.builder()
                    .userId(optionalProfileDao.get().getUserId())
                    .role(optionalProfileDao.get().getRole())
                    .build();

            log.info("User found");
            UserDto dto = UserDto.builder()
                    .id(optionalUserDao.get().getId())
                    .username(optionalUserDao.get().getUsername())
                    .name(optionalUserDao.get().getName())
                    .email(optionalUserDao.get().getEmail())
                    .password(optionalUserDao.get().getPassword())
                    .profile(profileDto)
                    .build();

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, dto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting User by an id. Error {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<Object> getAllUsers() {
        try {
            log.info("Getting all Users");

            List<UserDao> userDaoList;
            List<UserDto> userDtos = new ArrayList<>();

            userDaoList = userRepository.findAll();

            for (UserDao userDao : userDaoList) {
                Optional<ProfileDao> optionalProfileDao = profileRepository.findById(userDao.getId());

                ProfileDto profileDto = ProfileDto.builder()
                        .userId(optionalProfileDao.get().getUserId())
                        .role(optionalProfileDao.get().getRole())
                        .build();

                userDtos.add(UserDto.builder()
                                .id(userDao.getId())
                                .username(userDao.getUsername())
                                .name(userDao.getName())
                                .email(userDao.getEmail())
                                .password(userDao.getPassword())
                                .profile(profileDto)
                        .build());
            }

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, userDtos, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting all Users. Error {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateUserById(Long id, UserDto userDto) {
        try {
            log.info("Updating User by id");

            Optional<UserDao> optionalUserDao = userRepository.findById(id);

            Optional<ProfileDao> optionalProfileDao = profileRepository.findById(id);

            if (optionalUserDao.isEmpty() || optionalProfileDao.isEmpty()) {
                log.info("User not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            Optional<UserDao> userDaoOptional = userRepository.findByUsername(userDto.getUsername());

            if (userDaoOptional.isPresent()) {
                log.info("Username already exists");
                return ResponseUtil.build(AppConstant.ResponseCode.ALREADY_EXISTS, null, HttpStatus.CONFLICT);
            }

            log.info("User found");
            ProfileDao profileDao = optionalProfileDao.get();
            profileDao.setRole(userDto.getProfile().getRole());

            profileRepository.save(profileDao);

            UserDao userDao = optionalUserDao.get();
            userDao.setUsername(userDto.getUsername());
            userDao.setName(userDto.getName());
            userDao.setEmail(userDto.getEmail());
            userDao.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userDao.setProfile(profileDao);

            userRepository.save(userDao);

            Optional<FamilyDao> optionalFamilyDao = familyRepository.findTopByNIK(optionalUserDao.get().getUsername());

            FamilyDao familyDao = optionalFamilyDao.get();
            familyDao.setNIK(userDto.getUsername());
            familyDao.setName(userDto.getName());
            familyDao.setEmail(userDto.getEmail());

            familyRepository.save(familyDao);

            ProfileDto profileDto = ProfileDto.builder()
                    .userId(profileDao.getUserId())
                    .role(profileDao.getRole())
                    .build();

            UserDto dto = UserDto.builder()
                    .id(userDao.getId())
                    .username(userDao.getUsername())
                    .name(userDao.getName())
                    .email(userDao.getEmail())
                    .password(userDao.getPassword())
                    .profile(profileDto)
                    .build();

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, dto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in updating User by id. Error {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<Object> deleteUserById(Long id) {
        try {
            log.info("Deleting User by id");

            Optional<UserDao> optionalUserDao = userRepository.findById(id);

            if (optionalUserDao.isEmpty()) {
                log.info("User not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("User found");

            userRepository.delete(optionalUserDao.get());

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, null, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in deleting User by id. Error {}",e.getMessage());
            throw e;
        }
    }
}
