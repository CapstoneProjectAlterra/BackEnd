package com.backend.vaccinebookingsystem.service;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.common.ApiResponse;
import com.backend.vaccinebookingsystem.domain.common.ApiResponseStatus;
import com.backend.vaccinebookingsystem.domain.dao.HealthFacilityDao;
import com.backend.vaccinebookingsystem.domain.dao.HealthFacilityImageDao;
import com.backend.vaccinebookingsystem.domain.dao.ProfileDao;
import com.backend.vaccinebookingsystem.domain.dao.UserDao;
import com.backend.vaccinebookingsystem.domain.dto.HealthFacilityDto;
import com.backend.vaccinebookingsystem.domain.dto.HealthFacilityImageDto;
import com.backend.vaccinebookingsystem.domain.dto.ProfileDto;
import com.backend.vaccinebookingsystem.repository.HealthFacilityImageRepository;
import com.backend.vaccinebookingsystem.repository.HealthFacilityRepository;
import com.backend.vaccinebookingsystem.repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {HealthFacilityService.class})
@ExtendWith(SpringExtension.class)
class HealthFacilityServiceTest {
    @MockBean
    private HealthFacilityImageRepository healthFacilityImageRepository;

    @MockBean
    private HealthFacilityRepository healthFacilityRepository;

    @Autowired
    private HealthFacilityService healthFacilityService;

    @MockBean
    private ProfileRepository profileRepository;

    @Test
    void createHealthFacilityAlreadyExists_Test() {
        HealthFacilityImageDao healthFacilityImageDao = new HealthFacilityImageDao();
        healthFacilityImageDao.setBase64("Base64");
        healthFacilityImageDao.setContentType("text/plain");
        healthFacilityImageDao.setFacility(new HealthFacilityDao());
        healthFacilityImageDao.setFacilityId(123L);

        ProfileDao profileDao = new ProfileDao();
        profileDao.setFamilyDaoList(new ArrayList<>());
        profileDao.setHealthFacilityDaoList(new ArrayList<>());
        profileDao.setRole(AppConstant.ProfileRole.USER);
        profileDao.setUser(new UserDao());
        profileDao.setUserId(123L);

        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
        healthFacilityDao.setFacilityName("Facility Name");
        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao.setId(123L);
        healthFacilityDao.setImage(healthFacilityImageDao);
        healthFacilityDao.setPostalCode(1);
        healthFacilityDao.setProfile(profileDao);
        healthFacilityDao.setScheduleDaoList(new ArrayList<>());

        HealthFacilityImageDao healthFacilityImageDao1 = new HealthFacilityImageDao();
        healthFacilityImageDao1.setBase64("Base64");
        healthFacilityImageDao1.setContentType("text/plain");
        healthFacilityImageDao1.setFacility(healthFacilityDao);
        healthFacilityImageDao1.setFacilityId(123L);

        ProfileDao profileDao1 = new ProfileDao();
        profileDao1.setFamilyDaoList(new ArrayList<>());
        profileDao1.setHealthFacilityDaoList(new ArrayList<>());
        profileDao1.setRole(AppConstant.ProfileRole.USER);
        profileDao1.setUser(new UserDao());
        profileDao1.setUserId(123L);

        UserDao userDao = new UserDao();
        userDao.setBookingDaoList(new ArrayList<>());
        userDao.setId(123L);
        userDao.setName("Name");
        userDao.setProfile(profileDao1);

        ProfileDao profileDao2 = new ProfileDao();
        profileDao2.setFamilyDaoList(new ArrayList<>());
        profileDao2.setHealthFacilityDaoList(new ArrayList<>());
        profileDao2.setRole(AppConstant.ProfileRole.USER);
        profileDao2.setUser(userDao);
        profileDao2.setUserId(123L);

        HealthFacilityDao healthFacilityDao1 = new HealthFacilityDao();
        healthFacilityDao1.setFacilityName("Facility Name");
        healthFacilityDao1.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao1.setId(123L);
        healthFacilityDao1.setImage(healthFacilityImageDao1);
        healthFacilityDao1.setProfile(profileDao2);
        healthFacilityDao1.setScheduleDaoList(new ArrayList<>());

        Optional<HealthFacilityDao> ofResult = Optional.of(healthFacilityDao1);
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
        HealthFacilityImageDao healthFacilityImageDao = new HealthFacilityImageDao();
        healthFacilityImageDao.setBase64("Base64");
        healthFacilityImageDao.setContentType("text/plain");
        healthFacilityImageDao.setFacility(new HealthFacilityDao());
        healthFacilityImageDao.setFacilityId(123L);

        ProfileDao profileDao = new ProfileDao();
        profileDao.setFamilyDaoList(new ArrayList<>());
        profileDao.setHealthFacilityDaoList(new ArrayList<>());
        profileDao.setRole(AppConstant.ProfileRole.USER);
        profileDao.setUser(new UserDao());
        profileDao.setUserId(123L);

        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
        healthFacilityDao.setFacilityName("Facility Name");
        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao.setId(123L);
        healthFacilityDao.setImage(healthFacilityImageDao);
        healthFacilityDao.setProfile(profileDao);
        healthFacilityDao.setScheduleDaoList(new ArrayList<>());

        HealthFacilityImageDao healthFacilityImageDao1 = new HealthFacilityImageDao();
        healthFacilityImageDao1.setBase64("Base64");
        healthFacilityImageDao1.setContentType("text/plain");
        healthFacilityImageDao1.setFacility(healthFacilityDao);
        healthFacilityImageDao1.setFacilityId(123L);

        ProfileDao profileDao1 = new ProfileDao();
        profileDao1.setFamilyDaoList(new ArrayList<>());
        profileDao1.setHealthFacilityDaoList(new ArrayList<>());
        profileDao1.setRole(AppConstant.ProfileRole.USER);
        profileDao1.setUser(new UserDao());
        profileDao1.setUserId(123L);

        UserDao userDao = new UserDao();
        userDao.setBookingDaoList(new ArrayList<>());
        userDao.setId(123L);
        userDao.setName("Name");
        userDao.setProfile(profileDao1);

        ProfileDao profileDao2 = new ProfileDao();
        profileDao2.setFamilyDaoList(new ArrayList<>());
        profileDao2.setHealthFacilityDaoList(new ArrayList<>());
        profileDao2.setRole(AppConstant.ProfileRole.USER);
        profileDao2.setUser(userDao);
        profileDao2.setUserId(123L);

        HealthFacilityDao healthFacilityDao1 = new HealthFacilityDao();
        healthFacilityDao1.setFacilityName("Facility Name");
        healthFacilityDao1.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao1.setId(123L);
        healthFacilityDao1.setImage(healthFacilityImageDao1);
        healthFacilityDao1.setProfile(profileDao2);
        healthFacilityDao1.setScheduleDaoList(new ArrayList<>());

        Optional<HealthFacilityDao> ofResult = Optional.of(healthFacilityDao1);
        when(healthFacilityRepository.findById((Long) any())).thenReturn(ofResult);

        UserDao userDao1 = new UserDao();
        userDao1.setBookingDaoList(new ArrayList<>());
        userDao1.setId(123L);
        userDao1.setName("Name");
        userDao1.setProfile(new ProfileDao());

        ProfileDao profileDao3 = new ProfileDao();
        profileDao3.setFamilyDaoList(new ArrayList<>());
        profileDao3.setHealthFacilityDaoList(new ArrayList<>());
        profileDao3.setRole(AppConstant.ProfileRole.USER);
        profileDao3.setUser(userDao1);
        profileDao3.setUserId(123L);

        UserDao userDao2 = new UserDao();
        userDao2.setBookingDaoList(new ArrayList<>());
        userDao2.setId(123L);
        userDao2.setName("Name");
        userDao2.setProfile(profileDao3);

        ProfileDao profileDao4 = new ProfileDao();
        profileDao4.setFamilyDaoList(new ArrayList<>());
        profileDao4.setHealthFacilityDaoList(new ArrayList<>());
        profileDao4.setRole(AppConstant.ProfileRole.USER);
        profileDao4.setUser(userDao2);
        profileDao4.setUserId(123L);

        Optional<ProfileDao> ofResult1 = Optional.of(profileDao4);
        when(profileRepository.findById((Long) any())).thenReturn(ofResult1);

        ResponseEntity<Object> actualHealthFacilityById = healthFacilityService.getHealthFacilityById(123L);

        assertEquals(HttpStatus.OK, actualHealthFacilityById.getStatusCode());

        Object data = ((ApiResponse<Object>) actualHealthFacilityById.getBody()).getData();

        ApiResponseStatus status = ((ApiResponse<Object>) actualHealthFacilityById.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());

        HealthFacilityImageDto image = ((HealthFacilityDto) data).getImage();
        assertEquals("text/plain", image.getContentType());
        assertEquals("Base64", image.getBase64());
        assertEquals(123L, image.getFacilityId().longValue());

        ProfileDto profile = ((HealthFacilityDto) data).getProfile();
        assertEquals(123L, profile.getUserId().longValue());
        assertEquals(AppConstant.ProfileRole.USER, profile.getRole());

        verify(healthFacilityRepository).findById((Long) any());
        verify(profileRepository).findById((Long) any());
    }

    @Test
    void getAllHealthFacilitiesSuccess_Test() {
        HealthFacilityImageDao healthFacilityImageDao = new HealthFacilityImageDao();
        healthFacilityImageDao.setBase64("Getting all of Health Facilities");
        healthFacilityImageDao.setContentType("text/plain");
        healthFacilityImageDao.setFacility(new HealthFacilityDao());
        healthFacilityImageDao.setFacilityId(123L);

        ProfileDao profileDao = new ProfileDao();
        profileDao.setFamilyDaoList(new ArrayList<>());
        profileDao.setHealthFacilityDaoList(new ArrayList<>());
        profileDao.setRole(AppConstant.ProfileRole.USER);
        profileDao.setUser(new UserDao());
        profileDao.setUserId(123L);

        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
        healthFacilityDao.setFacilityName("Facility Name");
        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao.setId(123L);
        healthFacilityDao.setImage(healthFacilityImageDao);
        healthFacilityDao.setProfile(profileDao);
        healthFacilityDao.setScheduleDaoList(new ArrayList<>());

        HealthFacilityImageDao healthFacilityImageDao1 = new HealthFacilityImageDao();
        healthFacilityImageDao1.setBase64("Getting all of Health Facilities");
        healthFacilityImageDao1.setContentType("text/plain");
        healthFacilityImageDao1.setFacility(healthFacilityDao);
        healthFacilityImageDao1.setFacilityId(123L);

        ProfileDao profileDao1 = new ProfileDao();
        profileDao1.setFamilyDaoList(new ArrayList<>());
        profileDao1.setHealthFacilityDaoList(new ArrayList<>());
        profileDao1.setRole(AppConstant.ProfileRole.USER);
        profileDao1.setUser(new UserDao());
        profileDao1.setUserId(123L);

        UserDao userDao = new UserDao();
        userDao.setBookingDaoList(new ArrayList<>());
        userDao.setId(123L);
        userDao.setName("Name");
        userDao.setProfile(profileDao1);

        ProfileDao profileDao2 = new ProfileDao();
        profileDao2.setFamilyDaoList(new ArrayList<>());
        profileDao2.setHealthFacilityDaoList(new ArrayList<>());
        profileDao2.setRole(AppConstant.ProfileRole.USER);
        profileDao2.setUser(userDao);
        profileDao2.setUserId(123L);

        HealthFacilityDao healthFacilityDao1 = new HealthFacilityDao();
        healthFacilityDao1.setFacilityName("Facility Name");
        healthFacilityDao1.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao1.setId(123L);
        healthFacilityDao1.setImage(healthFacilityImageDao1);
        healthFacilityDao1.setProfile(profileDao2);
        healthFacilityDao1.setScheduleDaoList(new ArrayList<>());

        ArrayList<HealthFacilityDao> healthFacilityDaoList = new ArrayList<>();
        healthFacilityDaoList.add(healthFacilityDao1);
        when(healthFacilityRepository.findAll()).thenReturn(healthFacilityDaoList);

        UserDao userDao1 = new UserDao();
        userDao1.setBookingDaoList(new ArrayList<>());
        userDao1.setId(123L);
        userDao1.setName("Name");
        userDao1.setProfile(new ProfileDao());

        ProfileDao profileDao3 = new ProfileDao();
        profileDao3.setFamilyDaoList(new ArrayList<>());
        profileDao3.setHealthFacilityDaoList(new ArrayList<>());
        profileDao3.setRole(AppConstant.ProfileRole.USER);
        profileDao3.setUser(userDao1);
        profileDao3.setUserId(123L);

        UserDao userDao2 = new UserDao();
        userDao2.setBookingDaoList(new ArrayList<>());
        userDao2.setId(123L);
        userDao2.setName("Name");
        userDao2.setProfile(profileDao3);

        ProfileDao profileDao4 = new ProfileDao();
        profileDao4.setFamilyDaoList(new ArrayList<>());
        profileDao4.setHealthFacilityDaoList(new ArrayList<>());
        profileDao4.setRole(AppConstant.ProfileRole.USER);
        profileDao4.setUser(userDao2);
        profileDao4.setUserId(123L);

        Optional<ProfileDao> ofResult = Optional.of(profileDao4);
        when(profileRepository.findById((Long) any())).thenReturn(ofResult);

        ResponseEntity<Object> actualAllHealthFacilities = healthFacilityService.getAllHealthFacilities();

        assertEquals(HttpStatus.OK, actualAllHealthFacilities.getStatusCode());
        assertEquals(1,
                ((Collection<HealthFacilityDto>) ((ApiResponse<Object>) actualAllHealthFacilities.getBody()).getData()).size());

        ApiResponseStatus status = ((ApiResponse<Object>) actualAllHealthFacilities.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());

        verify(healthFacilityRepository).findAll();
        verify(profileRepository).findById((Long) any());
    }

    @Test
    void deleteHealthFacilityByIdSuccess_Test() {
        HealthFacilityImageDao healthFacilityImageDao = new HealthFacilityImageDao();
        healthFacilityImageDao.setBase64("Base64");
        healthFacilityImageDao.setContentType("text/plain");
        healthFacilityImageDao.setFacility(new HealthFacilityDao());
        healthFacilityImageDao.setFacilityId(123L);

        ProfileDao profileDao = new ProfileDao();
        profileDao.setFamilyDaoList(new ArrayList<>());
        profileDao.setHealthFacilityDaoList(new ArrayList<>());
        profileDao.setRole(AppConstant.ProfileRole.USER);
        profileDao.setUser(new UserDao());
        profileDao.setUserId(123L);

        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
        healthFacilityDao.setFacilityName("Facility Name");
        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao.setId(123L);
        healthFacilityDao.setImage(healthFacilityImageDao);
        healthFacilityDao.setProfile(profileDao);
        healthFacilityDao.setScheduleDaoList(new ArrayList<>());

        HealthFacilityImageDao healthFacilityImageDao1 = new HealthFacilityImageDao();
        healthFacilityImageDao1.setBase64("Base64");
        healthFacilityImageDao1.setContentType("text/plain");
        healthFacilityImageDao1.setFacility(healthFacilityDao);
        healthFacilityImageDao1.setFacilityId(123L);

        ProfileDao profileDao1 = new ProfileDao();
        profileDao1.setFamilyDaoList(new ArrayList<>());
        profileDao1.setHealthFacilityDaoList(new ArrayList<>());
        profileDao1.setRole(AppConstant.ProfileRole.USER);
        profileDao1.setUser(new UserDao());
        profileDao1.setUserId(123L);

        UserDao userDao = new UserDao();
        userDao.setBookingDaoList(new ArrayList<>());
        userDao.setId(123L);
        userDao.setName("Name");
        userDao.setProfile(profileDao1);

        ProfileDao profileDao2 = new ProfileDao();
        profileDao2.setFamilyDaoList(new ArrayList<>());
        profileDao2.setHealthFacilityDaoList(new ArrayList<>());
        profileDao2.setRole(AppConstant.ProfileRole.USER);
        profileDao2.setUser(userDao);
        profileDao2.setUserId(123L);

        HealthFacilityDao healthFacilityDao1 = new HealthFacilityDao();
        healthFacilityDao1.setFacilityName("Facility Name");
        healthFacilityDao1.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao1.setId(123L);
        healthFacilityDao1.setImage(healthFacilityImageDao1);
        healthFacilityDao1.setProfile(profileDao2);
        healthFacilityDao1.setScheduleDaoList(new ArrayList<>());

        Optional<HealthFacilityDao> ofResult = Optional.of(healthFacilityDao1);
        doNothing().when(healthFacilityRepository).delete((HealthFacilityDao) any());
        when(healthFacilityRepository.findById((Long) any())).thenReturn(ofResult);

        ResponseEntity<Object> actualDeleteHealthFacilityByIdResult = healthFacilityService.deleteHealthFacilityById(123L);

        assertEquals(HttpStatus.OK, actualDeleteHealthFacilityByIdResult.getStatusCode());

        ApiResponseStatus status = ((ApiResponse<Object>) actualDeleteHealthFacilityByIdResult.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());

        verify(healthFacilityRepository).findById((Long) any());
        verify(healthFacilityRepository).delete((HealthFacilityDao) any());
    }
}

