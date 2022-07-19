package com.backend.vaccinebookingsystem.service;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.common.ApiResponse;
import com.backend.vaccinebookingsystem.domain.common.ApiResponseStatus;
import com.backend.vaccinebookingsystem.domain.dao.FamilyDao;
import com.backend.vaccinebookingsystem.domain.dao.ProfileDao;
import com.backend.vaccinebookingsystem.domain.dao.UserDao;
import com.backend.vaccinebookingsystem.domain.dto.FamilyDto;
import com.backend.vaccinebookingsystem.domain.dto.ProfileDto;
import com.backend.vaccinebookingsystem.repository.FamilyRepository;
import com.backend.vaccinebookingsystem.repository.ProfileRepository;
import com.backend.vaccinebookingsystem.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = FamilyService.class)
class FamilyServiceTest {
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private FamilyRepository familyRepository;

    @Autowired
    private FamilyService familyService;

    @MockBean
    private ProfileRepository profileRepository;

    @Test
    void createFamilyException_Test() {
        ResponseEntity<Object> actualCreateFamilyResult = familyService.createFamily(new FamilyDto());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualCreateFamilyResult.getStatusCode());

        ApiResponseStatus status = ((ApiResponse<Object>) actualCreateFamilyResult.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), status.getCode());
    }

    @Test
    void createFamilySuccess_Test() {
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

        FamilyDao familyDao = new FamilyDao();
        familyDao.setBookingDaoList(new ArrayList<>());
        familyDao.setGender(AppConstant.Gender.LAKI_LAKI);
        familyDao.setId(123L);
        familyDao.setNIK("NIK");
        familyDao.setProfile(profileDao1);

        when(familyRepository.save((FamilyDao) any())).thenReturn(familyDao);

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

        FamilyDto familyDto = new FamilyDto();
        familyDto.setProfile(new ProfileDto());

        ResponseEntity<Object> actualCreateFamilyResult = familyService.createFamily(familyDto);

        assertEquals(HttpStatus.OK, actualCreateFamilyResult.getStatusCode());

        Object data = ((ApiResponse<Object>) actualCreateFamilyResult.getBody()).getData();

        ApiResponseStatus status = ((ApiResponse<Object>) actualCreateFamilyResult.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());

        ProfileDto profile = ((FamilyDto) data).getProfile();

        assertEquals(AppConstant.ProfileRole.USER, profile.getRole());
        assertEquals(123L, profile.getUserId().longValue());

        verify(familyRepository).save((FamilyDao) any());
        verify(profileRepository).findById((Long) any());
    }

    @Test
    void getFamilyByIdSuccess_Test() {
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

        FamilyDao familyDao = new FamilyDao();
        familyDao.setBookingDaoList(new ArrayList<>());
        familyDao.setId(123L);
        familyDao.setNIK("NIK");
        familyDao.setProfile(profileDao1);

        Optional<FamilyDao> ofResult = Optional.of(familyDao);
        when(familyRepository.findById((Long) any())).thenReturn(ofResult);

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

        ResponseEntity<Object> actualFamilyById = familyService.getFamilyById(123L);

        assertEquals(HttpStatus.OK, actualFamilyById.getStatusCode());

        Object data = ((ApiResponse<Object>) actualFamilyById.getBody()).getData();

        ApiResponseStatus status = ((ApiResponse<Object>) actualFamilyById.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());

        ProfileDto profile = ((FamilyDto) data).getProfile();

        assertEquals(AppConstant.ProfileRole.USER, profile.getRole());
        assertEquals(123L, profile.getUserId().longValue());

        verify(familyRepository).findById((Long) any());
        verify(profileRepository).findById((Long) any());
    }

    @Test
    void getAllFamiliesSuccess_Test() {
        ProfileDao profileDao = new ProfileDao();
        profileDao.setFamilyDaoList(new ArrayList<>());
        profileDao.setHealthFacilityDaoList(new ArrayList<>());
        profileDao.setRole(AppConstant.ProfileRole.USER);
        profileDao.setUser(new UserDao());
        profileDao.setUserId(123L);

        UserDao userDao = new UserDao();
        userDao.setBookingDaoList(new ArrayList<>());
        userDao.setId(123L);
        userDao.setIsDeleted(true);
        userDao.setName("Getting all of Families");
        userDao.setProfile(profileDao);

        ProfileDao profileDao1 = new ProfileDao();
        profileDao1.setFamilyDaoList(new ArrayList<>());
        profileDao1.setHealthFacilityDaoList(new ArrayList<>());
        profileDao1.setRole(AppConstant.ProfileRole.USER);
        profileDao1.setUser(userDao);
        profileDao1.setUserId(123L);

        FamilyDao familyDao = new FamilyDao();
        familyDao.setBookingDaoList(new ArrayList<>());
        familyDao.setId(123L);
        familyDao.setNIK("Getting all of Families");
        familyDao.setProfile(profileDao1);

        ArrayList<FamilyDao> familyDaoList = new ArrayList<>();
        familyDaoList.add(familyDao);
        when(familyRepository.findAll()).thenReturn(familyDaoList);

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

        ResponseEntity<Object> actualAllFamilies = familyService.getAllFamilies();

        assertEquals(HttpStatus.OK, actualAllFamilies.getStatusCode());
        assertEquals(1, ((Collection<FamilyDto>) ((ApiResponse<Object>) actualAllFamilies.getBody()).getData()).size());

        ApiResponseStatus status = ((ApiResponse<Object>) actualAllFamilies.getBody()).getStatus();

        assertEquals("Success", status.getMessage());
        assertEquals("SUCCESS", status.getCode());

        verify(familyRepository).findAll();
        verify(profileRepository).findById((Long) any());
    }

    @Test
    void deleteFamilyByIdSuccess_Test() {
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

        FamilyDao familyDao = new FamilyDao();
        familyDao.setBookingDaoList(new ArrayList<>());
        familyDao.setId(123L);
        familyDao.setNIK("NIK");
        familyDao.setProfile(profileDao1);

        Optional<FamilyDao> ofResult = Optional.of(familyDao);

        doNothing().when(familyRepository).delete((FamilyDao) any());
        when(familyRepository.findById((Long) any())).thenReturn(ofResult);

        ResponseEntity<Object> actualDeleteFamilyByIdResult = familyService.deleteFamilyById(123L);

        assertEquals(HttpStatus.OK, actualDeleteFamilyByIdResult.getStatusCode());

        ApiResponseStatus status = ((ApiResponse<Object>) actualDeleteFamilyByIdResult.getBody()).getStatus();

        assertEquals("Success", status.getMessage());
        assertEquals("SUCCESS", status.getCode());

        verify(familyRepository).findById((Long) any());
        verify(familyRepository).delete((FamilyDao) any());
    }
}

