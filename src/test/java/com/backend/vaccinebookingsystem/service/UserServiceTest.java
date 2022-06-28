package com.backend.vaccinebookingsystem.service;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.common.ApiResponse;
import com.backend.vaccinebookingsystem.domain.common.ApiResponseStatus;
import com.backend.vaccinebookingsystem.domain.dao.ProfileDao;
import com.backend.vaccinebookingsystem.domain.dao.UserDao;
import com.backend.vaccinebookingsystem.domain.dto.ProfileDto;
import com.backend.vaccinebookingsystem.domain.dto.UserDto;
import com.backend.vaccinebookingsystem.repository.ProfileRepository;
import com.backend.vaccinebookingsystem.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @MockBean
    private ProfileRepository profileRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void getUserByIdSuccess_Test() {
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

        Optional<ProfileDao> ofResult = Optional.of(profileDao1);
        when(profileRepository.findById((Long) any())).thenReturn(ofResult);

        ProfileDao profileDao2 = new ProfileDao();
        profileDao2.setFamilyDaoList(new ArrayList<>());
        profileDao2.setHealthFacilityDaoList(new ArrayList<>());
        profileDao2.setRole(AppConstant.ProfileRole.USER);
        profileDao2.setUser(new UserDao());
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

        UserDao userDao3 = new UserDao();
        userDao3.setBookingDaoList(new ArrayList<>());
        userDao3.setId(123L);
        userDao3.setName("Name");
        userDao3.setProfile(profileDao3);

        Optional<UserDao> ofResult1 = Optional.of(userDao3);
        when(userRepository.findById((Long) any())).thenReturn(ofResult1);

        ResponseEntity<Object> actualUserById = userService.getUserById(123L);

        assertEquals(HttpStatus.OK, actualUserById.getStatusCode());

        Object data = ((ApiResponse<Object>) actualUserById.getBody()).getData();

        ApiResponseStatus status = ((ApiResponse<Object>) actualUserById.getBody()).getStatus();

        assertEquals("SUCCESS", status.getCode());

        verify(profileRepository).findById((Long) any());
        verify(userRepository).findById((Long) any());
    }

    @Test
    void getAllUsersSuccess_Test() {
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

        Optional<ProfileDao> ofResult = Optional.of(profileDao1);
        when(profileRepository.findById((Long) any())).thenReturn(ofResult);

        ProfileDao profileDao2 = new ProfileDao();
        profileDao2.setFamilyDaoList(new ArrayList<>());
        profileDao2.setHealthFacilityDaoList(new ArrayList<>());
        profileDao2.setRole(AppConstant.ProfileRole.USER);
        profileDao2.setUser(new UserDao());
        profileDao2.setUserId(123L);

        UserDao userDao2 = new UserDao();
        userDao2.setBookingDaoList(new ArrayList<>());
        userDao2.setId(123L);
        userDao2.setName("Getting all Users");
        userDao2.setProfile(profileDao2);

        ProfileDao profileDao3 = new ProfileDao();
        profileDao3.setFamilyDaoList(new ArrayList<>());
        profileDao3.setHealthFacilityDaoList(new ArrayList<>());
        profileDao3.setRole(AppConstant.ProfileRole.USER);
        profileDao3.setUser(userDao2);
        profileDao3.setUserId(123L);

        UserDao userDao3 = new UserDao();
        userDao3.setBookingDaoList(new ArrayList<>());
        userDao3.setId(123L);
        userDao3.setName("Getting all Users");
        userDao3.setProfile(profileDao3);

        ArrayList<UserDao> userDaoList = new ArrayList<>();
        userDaoList.add(userDao3);

        when(userRepository.findAll()).thenReturn(userDaoList);

        ResponseEntity<Object> actualAllUsers = userService.getAllUsers();

        assertEquals(HttpStatus.OK, actualAllUsers.getStatusCode());
        assertEquals(1, ((Collection<UserDto>) ((ApiResponse<Object>) actualAllUsers.getBody()).getData()).size());

        ApiResponseStatus status = ((ApiResponse<Object>) actualAllUsers.getBody()).getStatus();

        assertEquals("SUCCESS", status.getCode());

        verify(profileRepository).findById((Long) any());
        verify(userRepository).findAll();
    }

    @Test
    void updateUserById_Test() {
        when(profileRepository.findById((Long) any())).thenReturn(Optional.empty());

        ProfileDao profileDao = new ProfileDao();
        profileDao.setCreatedAt(null);
        profileDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        profileDao.setFamilyDaoList(new ArrayList<>());
        profileDao.setHealthFacilityDaoList(new ArrayList<>());
        profileDao.setIsDeleted(true);
        profileDao.setRole(AppConstant.ProfileRole.USER);
        profileDao.setUpdatedAt(null);
        profileDao.setUser(new UserDao());
        profileDao.setUserId(123L);

        UserDao userDao = new UserDao();
        userDao.setBookingDaoList(new ArrayList<>());
        userDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDao.setEmail("jane.doe@example.org");
        userDao.setId(123L);
        userDao.setIsDeleted(true);
        userDao.setName("Name");
        userDao.setPassword("iloveyou");
        userDao.setProfile(profileDao);
        userDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDao.setUsername("janedoe");

        ProfileDao profileDao1 = new ProfileDao();
        profileDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        profileDao1.setFamilyDaoList(new ArrayList<>());
        profileDao1.setHealthFacilityDaoList(new ArrayList<>());
        profileDao1.setIsDeleted(true);
        profileDao1.setRole(AppConstant.ProfileRole.USER);
        profileDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao1.setUser(userDao);
        profileDao1.setUserId(123L);

        UserDao userDao1 = new UserDao();
        userDao1.setBookingDaoList(new ArrayList<>());
        userDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDao1.setEmail("jane.doe@example.org");
        userDao1.setId(123L);
        userDao1.setIsDeleted(true);
        userDao1.setName("Name");
        userDao1.setPassword("iloveyou");
        userDao1.setProfile(profileDao1);
        userDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDao1.setUsername("janedoe");
        Optional<UserDao> ofResult = Optional.of(userDao1);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        ResponseEntity<Object> actualUpdateUserByIdResult = userService.updateUserById(123L, new UserDto());
        assertTrue(actualUpdateUserByIdResult.hasBody());
        assertTrue(actualUpdateUserByIdResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.BAD_REQUEST, actualUpdateUserByIdResult.getStatusCode());
        assertNull(((ApiResponse<Object>) actualUpdateUserByIdResult.getBody()).getData());
        ApiResponseStatus status = ((ApiResponse<Object>) actualUpdateUserByIdResult.getBody()).getStatus();
        assertEquals("Data Not Found", status.getMessage());
        assertEquals("DATA_NOT_FOUND", status.getCode());

        verify(profileRepository).findById((Long) any());
        verify(userRepository).findById((Long) any());
    }

    @Test
    void updateUserByIdIsEmpty_Test() {
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

        Optional<ProfileDao> ofResult = Optional.of(profileDao1);

        when(profileRepository.findById((Long) any())).thenReturn(ofResult);
        when(userRepository.findById((Long) any())).thenReturn(Optional.empty());

        ResponseEntity<Object> actualUpdateUserByIdResult = userService.updateUserById(123L, new UserDto());

        assertEquals(HttpStatus.BAD_REQUEST, actualUpdateUserByIdResult.getStatusCode());

        ApiResponseStatus status = ((ApiResponse<Object>) actualUpdateUserByIdResult.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), status.getCode());

        verify(profileRepository).findById((Long) any());
        verify(userRepository).findById((Long) any());
    }

    @Test
    void updateUserByIdSuccess_Test() {
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

        Optional<ProfileDao> ofResult = Optional.of(profileDao1);

        ProfileDao profileDao2 = new ProfileDao();
        profileDao2.setFamilyDaoList(new ArrayList<>());
        profileDao2.setHealthFacilityDaoList(new ArrayList<>());
        profileDao2.setRole(AppConstant.ProfileRole.USER);
        profileDao2.setUser(new UserDao());
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

        UserDao userDao3 = new UserDao();
        userDao3.setBookingDaoList(new ArrayList<>());
        userDao3.setId(123L);
        userDao3.setName("Name");
        userDao3.setProfile(profileDao3);

        ProfileDao profileDao4 = new ProfileDao();
        profileDao4.setFamilyDaoList(new ArrayList<>());
        profileDao4.setHealthFacilityDaoList(new ArrayList<>());
        profileDao4.setRole(AppConstant.ProfileRole.USER);
        profileDao4.setUser(userDao3);
        profileDao4.setUserId(123L);

        when(profileRepository.save((ProfileDao) any())).thenReturn(profileDao4);
        when(profileRepository.findById((Long) any())).thenReturn(ofResult);

        ProfileDao profileDao5 = new ProfileDao();
        profileDao5.setFamilyDaoList(new ArrayList<>());
        profileDao5.setHealthFacilityDaoList(new ArrayList<>());
        profileDao5.setRole(AppConstant.ProfileRole.USER);
        profileDao5.setUser(new UserDao());
        profileDao5.setUserId(123L);

        UserDao userDao4 = new UserDao();
        userDao4.setBookingDaoList(new ArrayList<>());
        userDao4.setId(123L);
        userDao4.setName("Name");
        userDao4.setProfile(profileDao5);

        ProfileDao profileDao6 = new ProfileDao();
        profileDao6.setFamilyDaoList(new ArrayList<>());
        profileDao6.setHealthFacilityDaoList(new ArrayList<>());
        profileDao6.setRole(AppConstant.ProfileRole.USER);
        profileDao6.setUser(userDao4);
        profileDao6.setUserId(123L);

        UserDao userDao5 = new UserDao();
        userDao5.setBookingDaoList(new ArrayList<>());
        userDao5.setId(123L);
        userDao5.setName("Name");
        userDao5.setProfile(profileDao6);

        Optional<UserDao> ofResult1 = Optional.of(userDao5);

        UserDao userDao6 = new UserDao();
        userDao6.setBookingDaoList(new ArrayList<>());
        userDao6.setId(123L);
        userDao6.setName("Name");
        userDao6.setProfile(new ProfileDao());

        ProfileDao profileDao7 = new ProfileDao();
        profileDao7.setFamilyDaoList(new ArrayList<>());
        profileDao7.setHealthFacilityDaoList(new ArrayList<>());
        profileDao7.setRole(AppConstant.ProfileRole.USER);
        profileDao7.setUser(userDao6);
        profileDao7.setUserId(123L);

        UserDao userDao7 = new UserDao();
        userDao7.setBookingDaoList(new ArrayList<>());
        userDao7.setId(123L);
        userDao7.setName("Name");
        userDao7.setProfile(profileDao7);

        ProfileDao profileDao8 = new ProfileDao();
        profileDao8.setFamilyDaoList(new ArrayList<>());
        profileDao8.setHealthFacilityDaoList(new ArrayList<>());
        profileDao8.setRole(AppConstant.ProfileRole.USER);
        profileDao8.setUser(userDao7);
        profileDao8.setUserId(123L);

        UserDao userDao8 = new UserDao();
        userDao8.setBookingDaoList(new ArrayList<>());
        userDao8.setId(123L);
        userDao8.setName("Name");
        userDao8.setProfile(profileDao8);

        when(userRepository.save((UserDao) any())).thenReturn(userDao8);
        when(userRepository.findById((Long) any())).thenReturn(ofResult1);

        UserDto userDto = new UserDto();
        userDto.setProfile(new ProfileDto());

        ResponseEntity<Object> actualUpdateUserByIdResult = userService.updateUserById(123L, userDto);

        assertEquals(HttpStatus.OK, actualUpdateUserByIdResult.getStatusCode());

        Object data = ((ApiResponse<Object>) actualUpdateUserByIdResult.getBody()).getData();

        assertEquals(123L, ((UserDto) data).getId().longValue());

        ApiResponseStatus status = ((ApiResponse<Object>) actualUpdateUserByIdResult.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());

        verify(profileRepository).save((ProfileDao) any());
        verify(profileRepository).findById((Long) any());
        verify(userRepository).save((UserDao) any());
        verify(userRepository).findById((Long) any());
    }

    @Test
    void deleteUserByIdSuccess_Test() {
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
        userDao.setName("Name");
        userDao.setProfile(profileDao);

        ProfileDao profileDao1 = new ProfileDao();
        profileDao1.setFamilyDaoList(new ArrayList<>());
        profileDao1.setHealthFacilityDaoList(new ArrayList<>());
        profileDao1.setRole(AppConstant.ProfileRole.USER);
        profileDao1.setUser(userDao);
        profileDao1.setUserId(123L);

        UserDao userDao1 = new UserDao();
        userDao1.setBookingDaoList(new ArrayList<>());
        userDao1.setId(123L);
        userDao1.setName("Name");
        userDao1.setProfile(profileDao1);

        Optional<UserDao> ofResult = Optional.of(userDao1);
        doNothing().when(userRepository).delete((UserDao) any());
        when(userRepository.findById((Long) any())).thenReturn(ofResult);

        ResponseEntity<Object> actualDeleteUserByIdResult = userService.deleteUserById(123L);

        assertEquals(HttpStatus.OK, actualDeleteUserByIdResult.getStatusCode());

        ApiResponseStatus status = ((ApiResponse<Object>) actualDeleteUserByIdResult.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());

        verify(userRepository).findById((Long) any());
        verify(userRepository).delete((UserDao) any());
    }
}

