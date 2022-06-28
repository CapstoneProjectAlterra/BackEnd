package com.backend.vaccinebookingsystem.service;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.common.ApiResponse;
import com.backend.vaccinebookingsystem.domain.common.ApiResponseStatus;
import com.backend.vaccinebookingsystem.domain.dao.*;
import com.backend.vaccinebookingsystem.domain.dao.HealthFacilityDao;
import com.backend.vaccinebookingsystem.domain.dao.ProfileDao;
import com.backend.vaccinebookingsystem.domain.dao.ScheduleDao;
import com.backend.vaccinebookingsystem.domain.dao.UserDao;
import com.backend.vaccinebookingsystem.domain.dao.VaccineTypeDao;
import com.backend.vaccinebookingsystem.domain.dto.HealthFacilityDto;
import com.backend.vaccinebookingsystem.domain.dto.ScheduleDto;
import com.backend.vaccinebookingsystem.repository.HealthFacilityRepository;
import com.backend.vaccinebookingsystem.repository.ScheduleRepository;
import com.backend.vaccinebookingsystem.repository.VaccineTypeRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Disabled;
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
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {ScheduleService.class})
@ExtendWith(SpringExtension.class)
class ScheduleServiceTest {
    @MockBean
    private HealthFacilityRepository healthFacilityRepository;

    @MockBean
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ScheduleService scheduleService;

    @MockBean
    private VaccineTypeRepository vaccineTypeRepository;

    @Test
    void createScheduleException_Test() {
        ResponseEntity<Object> actualCreateScheduleResult = scheduleService.createSchedule(new ScheduleDto());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualCreateScheduleResult.getStatusCode());

        ApiResponseStatus status = ((ApiResponse<Object>) actualCreateScheduleResult.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), status.getCode());
    }

    @Test
    void getScheduleByIdSuccess_Test() {
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

        HealthFacilityDao healthFacilityDao1 = new HealthFacilityDao();
        healthFacilityDao1.setFacilityName("Facility Name");
        healthFacilityDao1.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao1.setId(123L);
        healthFacilityDao1.setScheduleDaoList(new ArrayList<>());

        VaccineTypeDao vaccineTypeDao = new VaccineTypeDao();
        vaccineTypeDao.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao.setId(123L);
        vaccineTypeDao.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao.setVaccineName("Vaccine Name");

        ScheduleDao scheduleDao = new ScheduleDao();
        scheduleDao.setBookingDaoList(new ArrayList<>());
        scheduleDao.setFacility(healthFacilityDao1);
        scheduleDao.setId(123L);
        scheduleDao.setQuota(1);
        scheduleDao.setVaccine(vaccineTypeDao);

        Optional<ScheduleDao> ofResult1 = Optional.of(scheduleDao);
        when(scheduleRepository.findById((Long) any())).thenReturn(ofResult1);

        VaccineTypeDao vaccineTypeDao1 = new VaccineTypeDao();
        vaccineTypeDao1.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao1.setId(123L);
        vaccineTypeDao1.setScheduleDaoList(new ArrayList<>());

        Optional<VaccineTypeDao> ofResult2 = Optional.of(vaccineTypeDao1);
        when(vaccineTypeRepository.findById((Long) any())).thenReturn(ofResult2);

        ResponseEntity<Object> actualScheduleById = scheduleService.getScheduleById(123L);

        assertEquals(HttpStatus.OK, actualScheduleById.getStatusCode());

        Object data = ((ApiResponse<Object>) actualScheduleById.getBody()).getData();

        assertEquals(1, ((ScheduleDto) data).getQuota().intValue());

        ApiResponseStatus status = ((ApiResponse<Object>) actualScheduleById.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());

        verify(healthFacilityRepository).findById((Long) any());
        verify(scheduleRepository).findById((Long) any());
        verify(vaccineTypeRepository).findById((Long) any());
    }

    @Test
    void getAllSchedulesSuccess_Test() {
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
        userDao1.setName("Getting all of Schedules");
        userDao1.setProfile(new ProfileDao());

        ProfileDao profileDao2 = new ProfileDao();
        profileDao2.setFamilyDaoList(new ArrayList<>());
        profileDao2.setHealthFacilityDaoList(new ArrayList<>());
        profileDao2.setRole(AppConstant.ProfileRole.USER);
        profileDao2.setUser(userDao1);
        profileDao2.setUserId(123L);

        HealthFacilityDao healthFacilityDao1 = new HealthFacilityDao();
        healthFacilityDao1.setFacilityName("Getting all of Schedules");
        healthFacilityDao1.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao1.setId(123L);
        healthFacilityDao1.setProfile(profileDao2);
        healthFacilityDao1.setScheduleDaoList(new ArrayList<>());

        VaccineTypeDao vaccineTypeDao = new VaccineTypeDao();
        vaccineTypeDao.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao.setId(123L);
        vaccineTypeDao.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao.setVaccineName("Getting all of Schedules");

        ScheduleDao scheduleDao = new ScheduleDao();
        scheduleDao.setBookingDaoList(new ArrayList<>());
        scheduleDao.setFacility(healthFacilityDao1);
        scheduleDao.setId(123L);
        scheduleDao.setQuota(1);
        scheduleDao.setVaccine(vaccineTypeDao);

        ArrayList<ScheduleDao> scheduleDaoList = new ArrayList<>();
        scheduleDaoList.add(scheduleDao);
        when(scheduleRepository.findAll()).thenReturn(scheduleDaoList);

        VaccineTypeDao vaccineTypeDao1 = new VaccineTypeDao();
        vaccineTypeDao1.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao1.setId(123L);
        vaccineTypeDao1.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao1.setVaccineName("Vaccine Name");

        Optional<VaccineTypeDao> ofResult1 = Optional.of(vaccineTypeDao1);
        when(vaccineTypeRepository.findById((Long) any())).thenReturn(ofResult1);

        ResponseEntity<Object> actualAllSchedules = scheduleService.getAllSchedules();

        assertEquals(HttpStatus.OK, actualAllSchedules.getStatusCode());
        assertEquals(1, ((Collection<ScheduleDto>) ((ApiResponse<Object>) actualAllSchedules.getBody()).getData()).size());

        ApiResponseStatus status = ((ApiResponse<Object>) actualAllSchedules.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());

        verify(healthFacilityRepository).findById((Long) any());
        verify(scheduleRepository).findAll();
        verify(vaccineTypeRepository).findById((Long) any());
    }

    @Test
    void deleteScheduleById_Test() {
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

        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
        healthFacilityDao.setFacilityName("Facility Name");
        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao.setId(123L);
        healthFacilityDao.setProfile(profileDao);
        healthFacilityDao.setScheduleDaoList(new ArrayList<>());

        VaccineTypeDao vaccineTypeDao = new VaccineTypeDao();
        vaccineTypeDao.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao.setId(123L);
        vaccineTypeDao.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao.setVaccineName("Vaccine Name");

        ScheduleDao scheduleDao = new ScheduleDao();
        scheduleDao.setBookingDaoList(new ArrayList<>());
        scheduleDao.setFacility(healthFacilityDao);
        scheduleDao.setId(123L);
        scheduleDao.setQuota(1);
        scheduleDao.setVaccine(vaccineTypeDao);

        Optional<ScheduleDao> ofResult = Optional.of(scheduleDao);
        doNothing().when(scheduleRepository).delete((ScheduleDao) any());

        when(scheduleRepository.findById((Long) any())).thenReturn(ofResult);

        ResponseEntity<Object> actualDeleteScheduleByIdResult = scheduleService.deleteScheduleById(123L);

        assertEquals(HttpStatus.OK, actualDeleteScheduleByIdResult.getStatusCode());

        ApiResponseStatus status = ((ApiResponse<Object>) actualDeleteScheduleByIdResult.getBody()).getStatus();

        assertEquals("SUCCESS", status.getCode());

        verify(scheduleRepository).findById((Long) any());
        verify(scheduleRepository).delete((ScheduleDao) any());
    }
}