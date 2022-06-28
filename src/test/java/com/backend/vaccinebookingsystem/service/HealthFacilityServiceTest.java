package com.backend.vaccinebookingsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.common.ApiResponse;
import com.backend.vaccinebookingsystem.domain.common.ApiResponseStatus;
import com.backend.vaccinebookingsystem.domain.dao.HealthFacilityDao;
import com.backend.vaccinebookingsystem.domain.dao.ProfileDao;
import com.backend.vaccinebookingsystem.domain.dao.UserDao;
import com.backend.vaccinebookingsystem.domain.dto.HealthFacilityDto;
import com.backend.vaccinebookingsystem.domain.dto.ProfileDto;
import com.backend.vaccinebookingsystem.repository.HealthFacilityRepository;
import com.backend.vaccinebookingsystem.repository.ProfileRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {HealthFacilityService.class})
@ExtendWith(SpringExtension.class)
class HealthFacilityServiceTest {
    @MockBean
    private HealthFacilityRepository healthFacilityRepository;

    @Autowired
    private HealthFacilityService healthFacilityService;

    @MockBean
    private ProfileRepository profileRepository;

    @Test
    void createHealthFacilityException_Test() {
        ResponseEntity<Object> actualCreateHealthFacilityResult = healthFacilityService
                .createHealthFacility(new HealthFacilityDto());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualCreateHealthFacilityResult.getStatusCode());

        ApiResponseStatus status = ((ApiResponse<Object>) actualCreateHealthFacilityResult.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), status.getCode());
    }

    @Test
    void createHealthFacilitySuccess_Test() {
        UserDao userDao = new UserDao();
        userDao.setBookingDaoList(new ArrayList<>());
        userDao.setId(123L);
        userDao.setName("Name");
        userDao.setProfile(new ProfileDao());

        ProfileDao profileDao = new ProfileDao();
        profileDao.setFamilyDaoList(new ArrayList<>());
        profileDao.setHealthFacilityDaoList(new ArrayList<>());
        profileDao.setRole(AppConstant.ProfileRole.USER);
        profileDao.setUser(userDao);
        profileDao.setUserId(123L);

        UserDao userDao1 = new UserDao();
        userDao1.setBookingDaoList(new ArrayList<>());
        userDao1.setId(123L);
        userDao1.setName("Name");
        userDao1.setProfile(profileDao);

        ProfileDao profileDao1 = new ProfileDao();
        profileDao1.setFamilyDaoList(new ArrayList<>());
        profileDao1.setHealthFacilityDaoList(new ArrayList<>());
        profileDao1.setRole(AppConstant.ProfileRole.USER);
        profileDao1.setUser(userDao1);
        profileDao1.setUserId(123L);

        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
        healthFacilityDao.setFacilityName("Facility Name");
        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao.setId(123L);
        healthFacilityDao.setProfile(profileDao1);
        healthFacilityDao.setScheduleDaoList(new ArrayList<>());

        when(healthFacilityRepository.save((HealthFacilityDao) any())).thenReturn(healthFacilityDao);

        UserDao userDao2 = new UserDao();
        userDao2.setBookingDaoList(new ArrayList<>());
        userDao2.setId(123L);
        userDao2.setName("Name");
        userDao2.setProfile(new ProfileDao());

        ProfileDao profileDao2 = new ProfileDao();
        profileDao2.setFamilyDaoList(new ArrayList<>());
        profileDao2.setHealthFacilityDaoList(new ArrayList<>());
        profileDao2.setRole(AppConstant.ProfileRole.USER);
        profileDao2.setUser(userDao2);
        profileDao2.setUserId(123L);

        UserDao userDao3 = new UserDao();
        userDao3.setBookingDaoList(new ArrayList<>());
        userDao3.setId(123L);
        userDao3.setName("Name");
        userDao3.setProfile(profileDao2);

        ProfileDao profileDao3 = new ProfileDao();
        profileDao3.setFamilyDaoList(new ArrayList<>());
        profileDao3.setHealthFacilityDaoList(new ArrayList<>());
        profileDao3.setRole(AppConstant.ProfileRole.USER);
        profileDao3.setUser(userDao3);
        profileDao3.setUserId(123L);

        Optional<ProfileDao> ofResult = Optional.of(profileDao3);
        when(profileRepository.findById((Long) any())).thenReturn(ofResult);

        HealthFacilityDto healthFacilityDto = new HealthFacilityDto();
        healthFacilityDto.setProfile(new ProfileDto());

        ResponseEntity<Object> actualCreateHealthFacilityResult = healthFacilityService
                .createHealthFacility(healthFacilityDto);

        assertEquals(HttpStatus.OK, actualCreateHealthFacilityResult.getStatusCode());

        Object data = ((ApiResponse<Object>) actualCreateHealthFacilityResult.getBody()).getData();

        ApiResponseStatus status = ((ApiResponse<Object>) actualCreateHealthFacilityResult.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());

        ProfileDto profile = ((HealthFacilityDto) data).getProfile();

        assertEquals(AppConstant.ProfileRole.USER, profile.getRole());
        assertEquals(123L, profile.getUserId().longValue());

        verify(healthFacilityRepository).save((HealthFacilityDao) any());
        verify(profileRepository).findById((Long) any());
    }

    @Test
    void createHealthFacilityAlreadyExists_Test() {
        ProfileDao profileDao = new ProfileDao();
        profileDao.setFamilyDaoList(new ArrayList<>());
        profileDao.setHealthFacilityDaoList(new ArrayList<>());
        profileDao.setRole(AppConstant.ProfileRole.USER);
        profileDao.setUser(new UserDao());
        profileDao.setUserId(123L);

        UserDao userDao = new UserDao();
        userDao.setBookingDaoList(new ArrayList<>());
        userDao.setId(123L);
        userDao.setName("Name");
        userDao.setProfile(profileDao);

        ProfileDao profileDao1 = new ProfileDao();
        profileDao1.setFamilyDaoList(new ArrayList<>());
        profileDao1.setHealthFacilityDaoList(new ArrayList<>());
        profileDao1.setRole(AppConstant.ProfileRole.USER);
        profileDao1.setUser(userDao);
        profileDao1.setUserId(123L);

        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao.setId(123L);
        healthFacilityDao.setProfile(profileDao1);
        healthFacilityDao.setScheduleDaoList(new ArrayList<>());

        Optional<HealthFacilityDao> ofResult = Optional.of(healthFacilityDao);
        when(healthFacilityRepository.findHealthFacilityDaoByFacilityName((String) any())).thenReturn(ofResult);

        ResponseEntity<Object> actualCreateHealthFacilityResult = healthFacilityService
                .createHealthFacility(new HealthFacilityDto());

        assertEquals(HttpStatus.CONFLICT, actualCreateHealthFacilityResult.getStatusCode());

        ApiResponseStatus status = ((ApiResponse<Object>) actualCreateHealthFacilityResult.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.ALREADY_EXISTS.getCode(), status.getCode());

        verify(healthFacilityRepository).findHealthFacilityDaoByFacilityName((String) any());
    }

    @Test
    void getHealthFacilityByIdSuccess_Test() {
        ProfileDao profileDao = new ProfileDao();
        profileDao.setFamilyDaoList(new ArrayList<>());
        profileDao.setHealthFacilityDaoList(new ArrayList<>());
        profileDao.setRole(AppConstant.ProfileRole.USER);
        profileDao.setUser(new UserDao());
        profileDao.setUserId(123L);

        UserDao userDao = new UserDao();
        userDao.setBookingDaoList(new ArrayList<>());
        userDao.setId(123L);
        userDao.setName("Name");
        userDao.setProfile(profileDao);

        ProfileDao profileDao1 = new ProfileDao();
        profileDao1.setFamilyDaoList(new ArrayList<>());
        profileDao1.setHealthFacilityDaoList(new ArrayList<>());
        profileDao1.setRole(AppConstant.ProfileRole.USER);
        profileDao1.setUser(userDao);
        profileDao1.setUserId(123L);

        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
        healthFacilityDao.setFacilityName("Facility Name");
        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao.setId(123L);
        healthFacilityDao.setProfile(profileDao1);
        healthFacilityDao.setScheduleDaoList(new ArrayList<>());

        Optional<HealthFacilityDao> ofResult = Optional.of(healthFacilityDao);
        when(healthFacilityRepository.findById((Long) any())).thenReturn(ofResult);

        UserDao userDao1 = new UserDao();
        userDao1.setBookingDaoList(new ArrayList<>());
        userDao1.setId(123L);
        userDao1.setName("Name");
        userDao1.setProfile(new ProfileDao());

        ProfileDao profileDao2 = new ProfileDao();
        profileDao2.setFamilyDaoList(new ArrayList<>());
        profileDao2.setHealthFacilityDaoList(new ArrayList<>());
        profileDao2.setRole(AppConstant.ProfileRole.USER);
        profileDao2.setUser(userDao1);
        profileDao2.setUserId(123L);

        UserDao userDao2 = new UserDao();
        userDao2.setBookingDaoList(new ArrayList<>());
        userDao2.setId(123L);
        userDao2.setName("Name");
        userDao2.setProfile(profileDao2);

        ProfileDao profileDao3 = new ProfileDao();
        profileDao3.setFamilyDaoList(new ArrayList<>());
        profileDao3.setHealthFacilityDaoList(new ArrayList<>());
        profileDao3.setRole(AppConstant.ProfileRole.USER);
        profileDao3.setUser(userDao2);
        profileDao3.setUserId(123L);

        Optional<ProfileDao> ofResult1 = Optional.of(profileDao3);
        when(profileRepository.findById((Long) any())).thenReturn(ofResult1);

        ResponseEntity<Object> actualHealthFacilityById = healthFacilityService.getHealthFacilityById(123L);

        assertEquals(HttpStatus.OK, actualHealthFacilityById.getStatusCode());

        Object data = ((ApiResponse<Object>) actualHealthFacilityById.getBody()).getData();

        ApiResponseStatus status = ((ApiResponse<Object>) actualHealthFacilityById.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());

        ProfileDto profile = ((HealthFacilityDto) data).getProfile();

        assertEquals(AppConstant.ProfileRole.USER, profile.getRole());
        assertEquals(123L, profile.getUserId().longValue());

        verify(healthFacilityRepository).findById((Long) any());
        verify(profileRepository).findById((Long) any());
    }

    @Test
    void getAllHealthFacilitiesSuccess_Test() {
        ProfileDao profileDao = new ProfileDao();
        profileDao.setFamilyDaoList(new ArrayList<>());
        profileDao.setHealthFacilityDaoList(new ArrayList<>());
        profileDao.setRole(AppConstant.ProfileRole.USER);
        profileDao.setUser(new UserDao());
        profileDao.setUserId(123L);

        UserDao userDao = new UserDao();
        userDao.setBookingDaoList(new ArrayList<>());
        userDao.setId(123L);
        userDao.setName("Getting all of Health Facilities");
        userDao.setProfile(profileDao);

        ProfileDao profileDao1 = new ProfileDao();
        profileDao1.setFamilyDaoList(new ArrayList<>());
        profileDao1.setHealthFacilityDaoList(new ArrayList<>());
        profileDao1.setRole(AppConstant.ProfileRole.USER);
        profileDao1.setUser(userDao);
        profileDao1.setUserId(123L);

        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
        healthFacilityDao.setFacilityName("Getting all of Health Facilities");
        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao.setId(123L);
        healthFacilityDao.setProfile(profileDao1);
        healthFacilityDao.setScheduleDaoList(new ArrayList<>());

        ArrayList<HealthFacilityDao> healthFacilityDaoList = new ArrayList<>();
        healthFacilityDaoList.add(healthFacilityDao);
        when(healthFacilityRepository.findAll()).thenReturn(healthFacilityDaoList);

        UserDao userDao1 = new UserDao();
        userDao1.setBookingDaoList(new ArrayList<>());
        userDao1.setId(123L);
        userDao1.setName("Name");
        userDao1.setProfile(new ProfileDao());

        ProfileDao profileDao2 = new ProfileDao();
        profileDao2.setFamilyDaoList(new ArrayList<>());
        profileDao2.setHealthFacilityDaoList(new ArrayList<>());
        profileDao2.setRole(AppConstant.ProfileRole.USER);
        profileDao2.setUser(userDao1);
        profileDao2.setUserId(123L);

        UserDao userDao2 = new UserDao();
        userDao2.setBookingDaoList(new ArrayList<>());
        userDao2.setId(123L);
        userDao2.setName("Name");
        userDao2.setProfile(profileDao2);

        ProfileDao profileDao3 = new ProfileDao();
        profileDao3.setFamilyDaoList(new ArrayList<>());
        profileDao3.setHealthFacilityDaoList(new ArrayList<>());
        profileDao3.setRole(AppConstant.ProfileRole.USER);
        profileDao3.setUser(userDao2);
        profileDao3.setUserId(123L);

        Optional<ProfileDao> ofResult = Optional.of(profileDao3);
        when(profileRepository.findById((Long) any())).thenReturn(ofResult);

        ResponseEntity<Object> actualAllHealthFacilities = healthFacilityService.getAllHealthFacilities();

        assertEquals(HttpStatus.OK, actualAllHealthFacilities.getStatusCode());
        assertEquals(1,
                ((Collection<HealthFacilityDto>) ((ApiResponse<Object>) actualAllHealthFacilities.getBody()).getData()).size());

        ApiResponseStatus status = ((ApiResponse<Object>) actualAllHealthFacilities.getBody()).getStatus();

        assertEquals("Success", status.getMessage());
        assertEquals("SUCCESS", status.getCode());

        verify(healthFacilityRepository).findAll();
        verify(profileRepository).findById((Long) any());
    }

    @Test
    void updateHealthFacilityByIdSuccess_Test() {
        ProfileDao profileDao = new ProfileDao();
        profileDao.setFamilyDaoList(new ArrayList<>());
        profileDao.setHealthFacilityDaoList(new ArrayList<>());
        profileDao.setRole(AppConstant.ProfileRole.USER);
        profileDao.setUser(new UserDao());
        profileDao.setUserId(123L);

        UserDao userDao = new UserDao();
        userDao.setBookingDaoList(new ArrayList<>());
        userDao.setId(123L);
        userDao.setName("Name");
        userDao.setProfile(profileDao);

        ProfileDao profileDao1 = new ProfileDao();
        profileDao1.setFamilyDaoList(new ArrayList<>());
        profileDao1.setHealthFacilityDaoList(new ArrayList<>());
        profileDao1.setRole(AppConstant.ProfileRole.USER);
        profileDao1.setUser(userDao);
        profileDao1.setUserId(123L);

        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
        healthFacilityDao.setFacilityName("Facility Name");
        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao.setId(123L);
        healthFacilityDao.setProfile(profileDao1);
        healthFacilityDao.setScheduleDaoList(new ArrayList<>());

        Optional<HealthFacilityDao> ofResult = Optional.of(healthFacilityDao);

        UserDao userDao1 = new UserDao();
        userDao1.setBookingDaoList(new ArrayList<>());
        userDao1.setId(123L);
        userDao1.setName("Name");
        userDao1.setProfile(new ProfileDao());

        ProfileDao profileDao2 = new ProfileDao();
        profileDao2.setFamilyDaoList(new ArrayList<>());
        profileDao2.setHealthFacilityDaoList(new ArrayList<>());
        profileDao2.setRole(AppConstant.ProfileRole.USER);
        profileDao2.setUser(userDao1);
        profileDao2.setUserId(123L);

        UserDao userDao2 = new UserDao();
        userDao2.setBookingDaoList(new ArrayList<>());
        userDao2.setId(123L);
        userDao2.setName("Name");
        userDao2.setProfile(profileDao2);

        ProfileDao profileDao3 = new ProfileDao();
        profileDao3.setFamilyDaoList(new ArrayList<>());
        profileDao3.setHealthFacilityDaoList(new ArrayList<>());
        profileDao3.setRole(AppConstant.ProfileRole.USER);
        profileDao3.setUser(userDao2);
        profileDao3.setUserId(123L);

        HealthFacilityDao healthFacilityDao1 = new HealthFacilityDao();
        healthFacilityDao1.setFacilityName("Facility Name");
        healthFacilityDao1.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao1.setId(123L);
        healthFacilityDao1.setProfile(profileDao3);
        healthFacilityDao1.setScheduleDaoList(new ArrayList<>());

        when(healthFacilityRepository.save((HealthFacilityDao) any())).thenReturn(healthFacilityDao1);
        when(healthFacilityRepository.findById((Long) any())).thenReturn(ofResult);

        UserDao userDao3 = new UserDao();
        userDao3.setBookingDaoList(new ArrayList<>());
        userDao3.setId(123L);
        userDao3.setName("Name");
        userDao3.setProfile(new ProfileDao());

        ProfileDao profileDao4 = new ProfileDao();
        profileDao4.setFamilyDaoList(new ArrayList<>());
        profileDao4.setHealthFacilityDaoList(new ArrayList<>());
        profileDao4.setRole(AppConstant.ProfileRole.USER);
        profileDao4.setUser(userDao3);
        profileDao4.setUserId(123L);

        UserDao userDao4 = new UserDao();
        userDao4.setBookingDaoList(new ArrayList<>());
        userDao4.setId(123L);
        userDao4.setName("Name");
        userDao4.setProfile(profileDao4);

        ProfileDao profileDao5 = new ProfileDao();
        profileDao5.setFamilyDaoList(new ArrayList<>());
        profileDao5.setHealthFacilityDaoList(new ArrayList<>());
        profileDao5.setRole(AppConstant.ProfileRole.USER);
        profileDao5.setUser(userDao4);
        profileDao5.setUserId(123L);

        Optional<ProfileDao> ofResult1 = Optional.of(profileDao5);
        when(profileRepository.findById((Long) any())).thenReturn(ofResult1);

        HealthFacilityDto healthFacilityDto = new HealthFacilityDto();
        healthFacilityDto.setProfile(new ProfileDto());

        ResponseEntity<Object> actualUpdateHealthFacilityByIdResult = healthFacilityService.updateHealthFacilityById(123L,
                healthFacilityDto);

        assertEquals(HttpStatus.OK, actualUpdateHealthFacilityByIdResult.getStatusCode());

        Object data = ((ApiResponse<Object>) actualUpdateHealthFacilityByIdResult.getBody()).getData();

        assertEquals(123L, ((HealthFacilityDto) data).getId().longValue());

        ApiResponseStatus status = ((ApiResponse<Object>) actualUpdateHealthFacilityByIdResult.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());

        verify(healthFacilityRepository).save((HealthFacilityDao) any());
        verify(healthFacilityRepository).findById((Long) any());
        verify(profileRepository).findById((Long) any());
    }

    @Test
    void deleteHealthFacilityByIdSuccess_Test() {
        ProfileDao profileDao = new ProfileDao();
        profileDao.setFamilyDaoList(new ArrayList<>());
        profileDao.setHealthFacilityDaoList(new ArrayList<>());
        profileDao.setRole(AppConstant.ProfileRole.USER);
        profileDao.setUser(new UserDao());
        profileDao.setUserId(123L);

        UserDao userDao = new UserDao();
        userDao.setBookingDaoList(new ArrayList<>());
        userDao.setId(123L);
        userDao.setName("Name");
        userDao.setProfile(profileDao);

        ProfileDao profileDao1 = new ProfileDao();
        profileDao1.setFamilyDaoList(new ArrayList<>());
        profileDao1.setHealthFacilityDaoList(new ArrayList<>());
        profileDao1.setRole(AppConstant.ProfileRole.USER);
        profileDao1.setUser(userDao);
        profileDao1.setUserId(123L);

        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
        healthFacilityDao.setFacilityName("Facility Name");
        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao.setId(123L);
        healthFacilityDao.setProfile(profileDao1);
        healthFacilityDao.setScheduleDaoList(new ArrayList<>());

        Optional<HealthFacilityDao> ofResult = Optional.of(healthFacilityDao);
        doNothing().when(healthFacilityRepository).delete((HealthFacilityDao) any());
        when(healthFacilityRepository.findById((Long) any())).thenReturn(ofResult);

        ResponseEntity<Object> actualDeleteHealthFacilityByIdResult = healthFacilityService.deleteHealthFacilityById(123L);

        assertEquals(HttpStatus.OK, actualDeleteHealthFacilityByIdResult.getStatusCode());

        ApiResponseStatus status = ((ApiResponse<Object>) actualDeleteHealthFacilityByIdResult.getBody()).getStatus();

        assertEquals("Success", status.getMessage());
        assertEquals("SUCCESS", status.getCode());

        verify(healthFacilityRepository).findById((Long) any());
        verify(healthFacilityRepository).delete((HealthFacilityDao) any());
    }
}

