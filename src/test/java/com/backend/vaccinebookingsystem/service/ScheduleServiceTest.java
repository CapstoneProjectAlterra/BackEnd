package com.backend.vaccinebookingsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
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
import com.backend.vaccinebookingsystem.domain.dao.ScheduleDao;
import com.backend.vaccinebookingsystem.domain.dao.UserDao;
import com.backend.vaccinebookingsystem.domain.dao.VaccineTypeDao;
import com.backend.vaccinebookingsystem.domain.dto.ScheduleDto;
import com.backend.vaccinebookingsystem.repository.ScheduleRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ScheduleService.class})
@ExtendWith(SpringExtension.class)
class ScheduleServiceTest {
    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ScheduleService scheduleService;

    /**
     * Method under test: {@link ScheduleService#createSchedule(ScheduleDto)}
     */
    @Test
    void createSchedule_Test() {
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
        userDao.setId(123L);
        userDao.setIsDeleted(true);
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

        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
        healthFacilityDao.setCity("Oxford");
        healthFacilityDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityDao.setDistrict("District");
        healthFacilityDao.setFacilityName("Facility Name");
        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao.setId(123L);
        healthFacilityDao.setImgUrl("https://example.org/example");
        healthFacilityDao.setIsDeleted(true);
        healthFacilityDao.setOfficeNumber("42");
        healthFacilityDao.setPostalCode(1);
        healthFacilityDao.setProfile(profileDao1);
        healthFacilityDao.setProvince("Province");
        healthFacilityDao.setScheduleDaoList(new ArrayList<>());
        healthFacilityDao.setStreetName("Street Name");
        healthFacilityDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao.setVillageName("Village Name");

        VaccineTypeDao vaccineTypeDao = new VaccineTypeDao();
        vaccineTypeDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        vaccineTypeDao.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao.setId(123L);
        vaccineTypeDao.setIsDeleted(true);
        vaccineTypeDao.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao.setVaccineName("Vaccine Name");

        ScheduleDao scheduleDao = new ScheduleDao();
        scheduleDao.setBookingDaoList(new ArrayList<>());
        scheduleDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        scheduleDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        scheduleDao.setDose(AppConstant.Dose.DOSIS_1);
        scheduleDao.setFacility(healthFacilityDao);
        scheduleDao.setId(123L);
        scheduleDao.setIsDeleted(true);
        scheduleDao.setOperationalHourEnd(LocalTime.of(1, 1));
        scheduleDao.setOperationalHourStart(LocalTime.of(1, 1));
        scheduleDao.setQuota(1);
        scheduleDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        scheduleDao.setVaccinationDate(LocalDate.ofEpochDay(1L));
        scheduleDao.setVaccine(vaccineTypeDao);
        when(this.scheduleRepository.save((ScheduleDao) any())).thenReturn(scheduleDao);
        when(this.modelMapper.map((Object) any(), (Class<Object>) any())).thenReturn("Map");
        ResponseEntity<Object> actualCreateScheduleResult = this.scheduleService.createSchedule(new ScheduleDto());
        assertTrue(actualCreateScheduleResult.hasBody());
        assertTrue(actualCreateScheduleResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualCreateScheduleResult.getStatusCode());
        assertNull(((ApiResponse<Object>) actualCreateScheduleResult.getBody()).getData());
        ApiResponseStatus status = ((ApiResponse<Object>) actualCreateScheduleResult.getBody()).getStatus();
        assertEquals("Unknown Error", status.getMessage());
        assertEquals("UNKNOWN_ERROR", status.getCode());
        verify(this.modelMapper).map((Object) any(), (Class<ScheduleDao>) any());
    }

    /**
     * Method under test: {@link ScheduleService#getScheduleById(Long)}
     */
    @Test
    void getScheduleById_Test() {
        UserDao userDao = new UserDao();
        userDao.setBookingDaoList(new ArrayList<>());
        userDao.setCreatedAt(null);
        userDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDao.setId(123L);
        userDao.setIsDeleted(true);
        userDao.setPassword("iloveyou");
        userDao.setProfile(new ProfileDao());
        userDao.setUpdatedAt(null);
        userDao.setUsername("janedoe");

        ProfileDao profileDao = new ProfileDao();
        profileDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        profileDao.setFamilyDaoList(new ArrayList<>());
        profileDao.setHealthFacilityDaoList(new ArrayList<>());
        profileDao.setIsDeleted(true);
        profileDao.setRole(AppConstant.ProfileRole.USER);
        profileDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao.setUser(userDao);
        profileDao.setUserId(123L);

        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
        healthFacilityDao.setCity("Oxford");
        healthFacilityDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityDao.setDistrict("District");
        healthFacilityDao.setFacilityName("Facility Name");
        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao.setId(123L);
        healthFacilityDao.setImgUrl("https://example.org/example");
        healthFacilityDao.setIsDeleted(true);
        healthFacilityDao.setOfficeNumber("42");
        healthFacilityDao.setPostalCode(1);
        healthFacilityDao.setProfile(profileDao);
        healthFacilityDao.setProvince("Province");
        healthFacilityDao.setScheduleDaoList(new ArrayList<>());
        healthFacilityDao.setStreetName("Street Name");
        healthFacilityDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao.setVillageName("Village Name");

        VaccineTypeDao vaccineTypeDao = new VaccineTypeDao();
        vaccineTypeDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        vaccineTypeDao.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao.setId(123L);
        vaccineTypeDao.setIsDeleted(true);
        vaccineTypeDao.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao.setVaccineName("Vaccine Name");

        ScheduleDao scheduleDao = new ScheduleDao();
        scheduleDao.setBookingDaoList(new ArrayList<>());
        scheduleDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        scheduleDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        scheduleDao.setDose(AppConstant.Dose.DOSIS_1);
        scheduleDao.setFacility(healthFacilityDao);
        scheduleDao.setId(123L);
        scheduleDao.setIsDeleted(true);
        scheduleDao.setOperationalHourEnd(LocalTime.of(1, 1));
        scheduleDao.setOperationalHourStart(LocalTime.of(1, 1));
        scheduleDao.setQuota(1);
        scheduleDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        scheduleDao.setVaccinationDate(LocalDate.ofEpochDay(1L));
        scheduleDao.setVaccine(vaccineTypeDao);
        Optional<ScheduleDao> ofResult = Optional.of(scheduleDao);
        when(this.scheduleRepository.findById((Long) any())).thenReturn(ofResult);
        ScheduleDto scheduleDto = new ScheduleDto();
        when(this.modelMapper.map((Object) any(), (Class<ScheduleDto>) any())).thenReturn(scheduleDto);
        ResponseEntity<Object> actualScheduleById = this.scheduleService.getScheduleById(123L);
        assertTrue(actualScheduleById.hasBody());
        assertTrue(actualScheduleById.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualScheduleById.getStatusCode());
        assertSame(scheduleDto, ((ApiResponse<Object>) actualScheduleById.getBody()).getData());
        ApiResponseStatus status = ((ApiResponse<Object>) actualScheduleById.getBody()).getStatus();
        assertEquals("Success", status.getMessage());
        assertEquals("SUCCESS", status.getCode());
        verify(this.scheduleRepository).findById((Long) any());
        verify(this.modelMapper).map((Object) any(), (Class<ScheduleDto>) any());
    }

    /**
     * Method under test: {@link ScheduleService#getAllSchedules()}
     */
    @Test
    void getAllSchedules_Test() {
        ArrayList<ScheduleDao> scheduleDaoList = new ArrayList<>();
        when(this.scheduleRepository.findAll()).thenReturn(scheduleDaoList);
        ResponseEntity<Object> actualAllSchedules = this.scheduleService.getAllSchedules();
        assertTrue(actualAllSchedules.hasBody());
        assertTrue(actualAllSchedules.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualAllSchedules.getStatusCode());
        assertEquals(scheduleDaoList, ((ApiResponse<Object>) actualAllSchedules.getBody()).getData());
        ApiResponseStatus status = ((ApiResponse<Object>) actualAllSchedules.getBody()).getStatus();
        assertEquals("Success", status.getMessage());
        assertEquals("SUCCESS", status.getCode());
        verify(this.scheduleRepository).findAll();
    }

    /**
     * Method under test: {@link ScheduleService#getAllSchedules()}
     */
    @Test
    void getAllSchedules_Test2() {
        UserDao userDao = new UserDao();
        userDao.setBookingDaoList(new ArrayList<>());
        userDao.setCreatedAt(null);
        userDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDao.setId(123L);
        userDao.setIsDeleted(true);
        userDao.setPassword("iloveyou");
        userDao.setProfile(new ProfileDao());
        userDao.setUpdatedAt(null);
        userDao.setUsername("janedoe");

        ProfileDao profileDao = new ProfileDao();
        profileDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        profileDao.setFamilyDaoList(new ArrayList<>());
        profileDao.setHealthFacilityDaoList(new ArrayList<>());
        profileDao.setIsDeleted(true);
        profileDao.setRole(AppConstant.ProfileRole.USER);
        profileDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao.setUser(userDao);
        profileDao.setUserId(123L);

        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
        healthFacilityDao.setCity("Oxford");
        healthFacilityDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityDao.setDistrict("Getting all of Schedules");
        healthFacilityDao.setFacilityName("Getting all of Schedules");
        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao.setId(123L);
        healthFacilityDao.setImgUrl("https://example.org/example");
        healthFacilityDao.setIsDeleted(true);
        healthFacilityDao.setOfficeNumber("42");
        healthFacilityDao.setPostalCode(1);
        healthFacilityDao.setProfile(profileDao);
        healthFacilityDao.setProvince("Getting all of Schedules");
        healthFacilityDao.setScheduleDaoList(new ArrayList<>());
        healthFacilityDao.setStreetName("Getting all of Schedules");
        healthFacilityDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao.setVillageName("Getting all of Schedules");

        VaccineTypeDao vaccineTypeDao = new VaccineTypeDao();
        vaccineTypeDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        vaccineTypeDao.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao.setId(123L);
        vaccineTypeDao.setIsDeleted(true);
        vaccineTypeDao.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao.setVaccineName("Getting all of Schedules");

        ScheduleDao scheduleDao = new ScheduleDao();
        scheduleDao.setBookingDaoList(new ArrayList<>());
        scheduleDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        scheduleDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        scheduleDao.setDose(AppConstant.Dose.DOSIS_1);
        scheduleDao.setFacility(healthFacilityDao);
        scheduleDao.setId(123L);
        scheduleDao.setIsDeleted(true);
        scheduleDao.setOperationalHourEnd(LocalTime.of(1, 1));
        scheduleDao.setOperationalHourStart(LocalTime.of(1, 1));
        scheduleDao.setQuota(1);
        scheduleDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        scheduleDao.setVaccinationDate(LocalDate.ofEpochDay(1L));
        scheduleDao.setVaccine(vaccineTypeDao);

        ArrayList<ScheduleDao> scheduleDaoList = new ArrayList<>();
        scheduleDaoList.add(scheduleDao);
        when(this.scheduleRepository.findAll()).thenReturn(scheduleDaoList);
        when(this.modelMapper.map((Object) any(), (Class<ScheduleDto>) any())).thenReturn(new ScheduleDto());
        ResponseEntity<Object> actualAllSchedules = this.scheduleService.getAllSchedules();
        assertTrue(actualAllSchedules.hasBody());
        assertTrue(actualAllSchedules.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualAllSchedules.getStatusCode());
        assertEquals(1, ((Collection<ScheduleDto>) ((ApiResponse<Object>) actualAllSchedules.getBody()).getData()).size());
        ApiResponseStatus status = ((ApiResponse<Object>) actualAllSchedules.getBody()).getStatus();
        assertEquals("Success", status.getMessage());
        assertEquals("SUCCESS", status.getCode());
        verify(this.scheduleRepository).findAll();
        verify(this.modelMapper).map((Object) any(), (Class<ScheduleDto>) any());
    }

    /**
     * Method under test: {@link ScheduleService#updateScheduleById(Long, ScheduleDto)}
     */
    @Test
    void updateScheduleById_Test() {
        UserDao userDao = new UserDao();
        userDao.setBookingDaoList(new ArrayList<>());
        userDao.setCreatedAt(null);
        userDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDao.setId(123L);
        userDao.setIsDeleted(true);
        userDao.setPassword("iloveyou");
        userDao.setProfile(new ProfileDao());
        userDao.setUpdatedAt(null);
        userDao.setUsername("janedoe");

        ProfileDao profileDao = new ProfileDao();
        profileDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        profileDao.setFamilyDaoList(new ArrayList<>());
        profileDao.setHealthFacilityDaoList(new ArrayList<>());
        profileDao.setIsDeleted(true);
        profileDao.setRole(AppConstant.ProfileRole.USER);
        profileDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao.setUser(userDao);
        profileDao.setUserId(123L);

        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
        healthFacilityDao.setCity("Oxford");
        healthFacilityDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityDao.setDistrict("District");
        healthFacilityDao.setFacilityName("Facility Name");
        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao.setId(123L);
        healthFacilityDao.setImgUrl("https://example.org/example");
        healthFacilityDao.setIsDeleted(true);
        healthFacilityDao.setOfficeNumber("42");
        healthFacilityDao.setPostalCode(1);
        healthFacilityDao.setProfile(profileDao);
        healthFacilityDao.setProvince("Province");
        healthFacilityDao.setScheduleDaoList(new ArrayList<>());
        healthFacilityDao.setStreetName("Street Name");
        healthFacilityDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao.setVillageName("Village Name");

        VaccineTypeDao vaccineTypeDao = new VaccineTypeDao();
        vaccineTypeDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        vaccineTypeDao.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao.setId(123L);
        vaccineTypeDao.setIsDeleted(true);
        vaccineTypeDao.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao.setVaccineName("Vaccine Name");

        ScheduleDao scheduleDao = new ScheduleDao();
        scheduleDao.setBookingDaoList(new ArrayList<>());
        scheduleDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        scheduleDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        scheduleDao.setDose(AppConstant.Dose.DOSIS_1);
        scheduleDao.setFacility(healthFacilityDao);
        scheduleDao.setId(123L);
        scheduleDao.setIsDeleted(true);
        scheduleDao.setOperationalHourEnd(LocalTime.of(1, 1));
        scheduleDao.setOperationalHourStart(LocalTime.of(1, 1));
        scheduleDao.setQuota(1);
        scheduleDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        scheduleDao.setVaccinationDate(LocalDate.ofEpochDay(1L));
        scheduleDao.setVaccine(vaccineTypeDao);
        Optional<ScheduleDao> ofResult = Optional.of(scheduleDao);

        ProfileDao profileDao1 = new ProfileDao();
        profileDao1.setCreatedAt(null);
        profileDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        profileDao1.setFamilyDaoList(new ArrayList<>());
        profileDao1.setHealthFacilityDaoList(new ArrayList<>());
        profileDao1.setIsDeleted(true);
        profileDao1.setRole(AppConstant.ProfileRole.USER);
        profileDao1.setUpdatedAt(null);
        profileDao1.setUser(new UserDao());
        profileDao1.setUserId(123L);

        UserDao userDao1 = new UserDao();
        userDao1.setBookingDaoList(new ArrayList<>());
        userDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDao1.setId(123L);
        userDao1.setIsDeleted(true);
        userDao1.setPassword("iloveyou");
        userDao1.setProfile(profileDao1);
        userDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDao1.setUsername("janedoe");

        ProfileDao profileDao2 = new ProfileDao();
        profileDao2.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        profileDao2.setFamilyDaoList(new ArrayList<>());
        profileDao2.setHealthFacilityDaoList(new ArrayList<>());
        profileDao2.setIsDeleted(true);
        profileDao2.setRole(AppConstant.ProfileRole.USER);
        profileDao2.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao2.setUser(userDao1);
        profileDao2.setUserId(123L);

        HealthFacilityDao healthFacilityDao1 = new HealthFacilityDao();
        healthFacilityDao1.setCity("Oxford");
        healthFacilityDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityDao1.setDistrict("District");
        healthFacilityDao1.setFacilityName("Facility Name");
        healthFacilityDao1.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao1.setId(123L);
        healthFacilityDao1.setImgUrl("https://example.org/example");
        healthFacilityDao1.setIsDeleted(true);
        healthFacilityDao1.setOfficeNumber("42");
        healthFacilityDao1.setPostalCode(1);
        healthFacilityDao1.setProfile(profileDao2);
        healthFacilityDao1.setProvince("Province");
        healthFacilityDao1.setScheduleDaoList(new ArrayList<>());
        healthFacilityDao1.setStreetName("Street Name");
        healthFacilityDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao1.setVillageName("Village Name");

        VaccineTypeDao vaccineTypeDao1 = new VaccineTypeDao();
        vaccineTypeDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        vaccineTypeDao1.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao1.setId(123L);
        vaccineTypeDao1.setIsDeleted(true);
        vaccineTypeDao1.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao1.setVaccineName("Vaccine Name");

        ScheduleDao scheduleDao1 = new ScheduleDao();
        scheduleDao1.setBookingDaoList(new ArrayList<>());
        scheduleDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        scheduleDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        scheduleDao1.setDose(AppConstant.Dose.DOSIS_1);
        scheduleDao1.setFacility(healthFacilityDao1);
        scheduleDao1.setId(123L);
        scheduleDao1.setIsDeleted(true);
        scheduleDao1.setOperationalHourEnd(LocalTime.of(1, 1));
        scheduleDao1.setOperationalHourStart(LocalTime.of(1, 1));
        scheduleDao1.setQuota(1);
        scheduleDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        scheduleDao1.setVaccinationDate(LocalDate.ofEpochDay(1L));
        scheduleDao1.setVaccine(vaccineTypeDao1);
        when(this.scheduleRepository.save((ScheduleDao) any())).thenReturn(scheduleDao1);
        when(this.scheduleRepository.findById((Long) any())).thenReturn(ofResult);
        ScheduleDto scheduleDto = new ScheduleDto();
        when(this.modelMapper.map((Object) any(), (Class<ScheduleDto>) any())).thenReturn(scheduleDto);
        ResponseEntity<Object> actualUpdateScheduleByIdResult = this.scheduleService.updateScheduleById(123L,
                new ScheduleDto());
        assertTrue(actualUpdateScheduleByIdResult.hasBody());
        assertTrue(actualUpdateScheduleByIdResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualUpdateScheduleByIdResult.getStatusCode());
        assertSame(scheduleDto, ((ApiResponse<Object>) actualUpdateScheduleByIdResult.getBody()).getData());
        ApiResponseStatus status = ((ApiResponse<Object>) actualUpdateScheduleByIdResult.getBody()).getStatus();
        assertEquals("Success", status.getMessage());
        assertEquals("SUCCESS", status.getCode());
        verify(this.scheduleRepository).save((ScheduleDao) any());
        verify(this.scheduleRepository).findById((Long) any());
        verify(this.modelMapper).map((Object) any(), (Class<ScheduleDto>) any());
    }

    /**
     * Method under test: {@link ScheduleService#deleteScheduleById(Long)}
     */
    @Test
    void deleteScheduleById_Test() {
        UserDao userDao = new UserDao();
        userDao.setBookingDaoList(new ArrayList<>());
        userDao.setCreatedAt(null);
        userDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDao.setId(123L);
        userDao.setIsDeleted(true);
        userDao.setPassword("iloveyou");
        userDao.setProfile(new ProfileDao());
        userDao.setUpdatedAt(null);
        userDao.setUsername("janedoe");

        ProfileDao profileDao = new ProfileDao();
        profileDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        profileDao.setFamilyDaoList(new ArrayList<>());
        profileDao.setHealthFacilityDaoList(new ArrayList<>());
        profileDao.setIsDeleted(true);
        profileDao.setRole(AppConstant.ProfileRole.USER);
        profileDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao.setUser(userDao);
        profileDao.setUserId(123L);

        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
        healthFacilityDao.setCity("Oxford");
        healthFacilityDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityDao.setDistrict("District");
        healthFacilityDao.setFacilityName("Facility Name");
        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao.setId(123L);
        healthFacilityDao.setImgUrl("https://example.org/example");
        healthFacilityDao.setIsDeleted(true);
        healthFacilityDao.setOfficeNumber("42");
        healthFacilityDao.setPostalCode(1);
        healthFacilityDao.setProfile(profileDao);
        healthFacilityDao.setProvince("Province");
        healthFacilityDao.setScheduleDaoList(new ArrayList<>());
        healthFacilityDao.setStreetName("Street Name");
        healthFacilityDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao.setVillageName("Village Name");

        VaccineTypeDao vaccineTypeDao = new VaccineTypeDao();
        vaccineTypeDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        vaccineTypeDao.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao.setId(123L);
        vaccineTypeDao.setIsDeleted(true);
        vaccineTypeDao.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao.setVaccineName("Vaccine Name");

        ScheduleDao scheduleDao = new ScheduleDao();
        scheduleDao.setBookingDaoList(new ArrayList<>());
        scheduleDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        scheduleDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        scheduleDao.setDose(AppConstant.Dose.DOSIS_1);
        scheduleDao.setFacility(healthFacilityDao);
        scheduleDao.setId(123L);
        scheduleDao.setIsDeleted(true);
        scheduleDao.setOperationalHourEnd(LocalTime.of(1, 1));
        scheduleDao.setOperationalHourStart(LocalTime.of(1, 1));
        scheduleDao.setQuota(1);
        scheduleDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        scheduleDao.setVaccinationDate(LocalDate.ofEpochDay(1L));
        scheduleDao.setVaccine(vaccineTypeDao);
        Optional<ScheduleDao> ofResult = Optional.of(scheduleDao);
        doNothing().when(this.scheduleRepository).delete((ScheduleDao) any());
        when(this.scheduleRepository.findById((Long) any())).thenReturn(ofResult);
        ResponseEntity<Object> actualDeleteScheduleByIdResult = this.scheduleService.deleteScheduleById(123L);
        assertTrue(actualDeleteScheduleByIdResult.hasBody());
        assertTrue(actualDeleteScheduleByIdResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualDeleteScheduleByIdResult.getStatusCode());
        assertNull(((ApiResponse<Object>) actualDeleteScheduleByIdResult.getBody()).getData());
        ApiResponseStatus status = ((ApiResponse<Object>) actualDeleteScheduleByIdResult.getBody()).getStatus();
        assertEquals("Success", status.getMessage());
        assertEquals("SUCCESS", status.getCode());
        verify(this.scheduleRepository).findById((Long) any());
        verify(this.scheduleRepository).delete((ScheduleDao) any());
    }
}

