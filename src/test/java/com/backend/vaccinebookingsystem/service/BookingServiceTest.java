package com.backend.vaccinebookingsystem.service;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.common.ApiResponse;
import com.backend.vaccinebookingsystem.domain.common.ApiResponseStatus;
import com.backend.vaccinebookingsystem.domain.dao.*;
import com.backend.vaccinebookingsystem.domain.dao.BookingDao;
import com.backend.vaccinebookingsystem.domain.dao.HealthFacilityDao;
import com.backend.vaccinebookingsystem.domain.dao.HealthFacilityImageDao;
import com.backend.vaccinebookingsystem.domain.dao.ProfileDao;
import com.backend.vaccinebookingsystem.domain.dao.ScheduleDao;
import com.backend.vaccinebookingsystem.domain.dao.UserDao;
import com.backend.vaccinebookingsystem.domain.dao.VaccineTypeDao;
import com.backend.vaccinebookingsystem.domain.dto.BookingDto;
import com.backend.vaccinebookingsystem.domain.dto.ScheduleDto;
import com.backend.vaccinebookingsystem.repository.BookingRepository;
import com.backend.vaccinebookingsystem.repository.ScheduleRepository;
import com.backend.vaccinebookingsystem.repository.UserRepository;

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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {BookingService.class})
@ExtendWith(SpringExtension.class)
class BookingServiceTest {
    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private ScheduleRepository scheduleRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private BookingService bookingService;

    @Test
    void createBookingException_Test() {
        ResponseEntity<Object> actualCreateBookingResult = bookingService.createBooking(new BookingDto());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualCreateBookingResult.getStatusCode());

        ApiResponseStatus status = ((ApiResponse<Object>) actualCreateBookingResult.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), status.getCode());
    }

    @Test
    void getBookingByIdSuccess_Test() {
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
        healthFacilityDao.setProfile(profileDao);
        healthFacilityDao.setScheduleDaoList(new ArrayList<>());

        VaccineTypeDao vaccineTypeDao = new VaccineTypeDao();
        vaccineTypeDao.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao.setId(123L);
        vaccineTypeDao.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao.setVaccineName("Vaccine Name");

        ScheduleDao scheduleDao = new ScheduleDao();
        scheduleDao.setBookingDaoList(new ArrayList<>());
        scheduleDao.setDose(AppConstant.Dose.DOSIS_1);
        scheduleDao.setFacility(healthFacilityDao);
        scheduleDao.setId(123L);
        scheduleDao.setQuota(1);
        scheduleDao.setVaccine(vaccineTypeDao);

        UserDao userDao = new UserDao();
        userDao.setBookingDaoList(new ArrayList<>());
        userDao.setId(123L);
        userDao.setName("Name");
        userDao.setProfile(new ProfileDao());

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

        BookingDao bookingDao = new BookingDao();
        bookingDao.setBookingDetailDaos(new ArrayList<>());
        bookingDao.setBookingPass(1);
        bookingDao.setId(123L);
        bookingDao.setSchedule(scheduleDao);
        bookingDao.setUser(userDao1);

        Optional<BookingDao> ofResult = Optional.of(bookingDao);
        when(bookingRepository.findById((Long) any())).thenReturn(ofResult);

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

        HealthFacilityDao healthFacilityDao1 = new HealthFacilityDao();
        healthFacilityDao1.setFacilityName("Facility Name");
        healthFacilityDao1.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao1.setProfile(profileDao2);
        healthFacilityDao1.setScheduleDaoList(new ArrayList<>());

        VaccineTypeDao vaccineTypeDao1 = new VaccineTypeDao();
        vaccineTypeDao1.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao1.setId(123L);
        vaccineTypeDao1.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao1.setVaccineName("Vaccine Name");

        ScheduleDao scheduleDao1 = new ScheduleDao();
        scheduleDao1.setBookingDaoList(new ArrayList<>());
        scheduleDao1.setFacility(healthFacilityDao1);
        scheduleDao1.setId(123L);
        scheduleDao1.setQuota(1);
        scheduleDao1.setVaccine(vaccineTypeDao1);

        Optional<ScheduleDao> ofResult1 = Optional.of(scheduleDao1);
        when(scheduleRepository.findById((Long) any())).thenReturn(ofResult1);

        ProfileDao profileDao3 = new ProfileDao();
        profileDao3.setFamilyDaoList(new ArrayList<>());
        profileDao3.setHealthFacilityDaoList(new ArrayList<>());
        profileDao3.setRole(AppConstant.ProfileRole.USER);
        profileDao3.setUser(new UserDao());
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

        UserDao userDao4 = new UserDao();
        userDao4.setBookingDaoList(new ArrayList<>());
        userDao4.setId(123L);
        userDao4.setName("Name");
        userDao4.setProfile(profileDao4);

        Optional<UserDao> ofResult2 = Optional.of(userDao4);
        when(userRepository.findById((Long) any())).thenReturn(ofResult2);

        ResponseEntity<Object> actualBookingById = bookingService.getBookingById(123L);

        assertEquals(HttpStatus.OK, actualBookingById.getStatusCode());

        Object data = ((ApiResponse<Object>) actualBookingById.getBody()).getData();

        assertEquals(123L, ((BookingDto) data).getId().longValue());

        ApiResponseStatus status = ((ApiResponse<Object>) actualBookingById.getBody()).getStatus();

        assertEquals("SUCCESS", status.getCode());

        verify(bookingRepository).findById((Long) any());
        verify(scheduleRepository).findById((Long) any());
        verify(userRepository).findById((Long) any());
    }

    @Test
    void getAllBookingsSuccess_Test() {
        ArrayList<BookingDao> bookingDaoList = new ArrayList<>();

        when(bookingRepository.findAll()).thenReturn(bookingDaoList);

        ResponseEntity<Object> actualAllBookings = bookingService.getAllBookings();

        assertEquals(HttpStatus.OK, actualAllBookings.getStatusCode());
        assertEquals(bookingDaoList, ((ApiResponse<Object>) actualAllBookings.getBody()).getData());

        ApiResponseStatus status = ((ApiResponse<Object>) actualAllBookings.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());

        verify(bookingRepository).findAll();
    }

    @Test
    void deleteBookingByIdSuccess_Test() {
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

        UserDao userDao = new UserDao();
        userDao.setBookingDaoList(new ArrayList<>());
        userDao.setId(123L);
        userDao.setName("Name");
        userDao.setProfile(new ProfileDao());

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

        BookingDao bookingDao = new BookingDao();
        bookingDao.setBookingDetailDaos(new ArrayList<>());
        bookingDao.setBookingPass(1);
        bookingDao.setId(123L);
        bookingDao.setSchedule(scheduleDao);
        bookingDao.setUser(userDao1);

        Optional<BookingDao> ofResult = Optional.of(bookingDao);
        doNothing().when(bookingRepository).delete((BookingDao) any());
        when(bookingRepository.findById((Long) any())).thenReturn(ofResult);

        ResponseEntity<Object> actualDeleteBookingByIdResult = bookingService.deleteBookingById(123L);

        assertEquals(HttpStatus.OK, actualDeleteBookingByIdResult.getStatusCode());

        ApiResponseStatus status = ((ApiResponse<Object>) actualDeleteBookingByIdResult.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());

        verify(bookingRepository).findById((Long) any());
        verify(bookingRepository).delete((BookingDao) any());
    }
}

