package com.backend.vaccinebookingsystem.service;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.common.ApiResponse;
import com.backend.vaccinebookingsystem.domain.common.ApiResponseStatus;
import com.backend.vaccinebookingsystem.domain.dao.*;
import com.backend.vaccinebookingsystem.domain.dto.BookingDto;
import com.backend.vaccinebookingsystem.domain.dto.ScheduleDto;
import com.backend.vaccinebookingsystem.repository.BookingRepository;
import com.backend.vaccinebookingsystem.repository.ScheduleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {BookingService.class})
@ExtendWith(SpringExtension.class)
class BookingServiceTest {
    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private ScheduleRepository scheduleRepository;

    @Autowired
    private BookingService bookingService;

    @Test
    void createBookingException_Test() {
        ResponseEntity<Object> actualCreateBookingResult = this.bookingService.createBooking(new BookingDto());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualCreateBookingResult.getStatusCode());

        ApiResponseStatus status = ((ApiResponse<Object>) actualCreateBookingResult.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), status.getCode());
    }

    @Test
    void createBookingSuccess_Test() {
        UserDao userDao = new UserDao();
        userDao.setBookingDaoList(new ArrayList<>());
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

        UserDao userDao1 = new UserDao();
        userDao1.setBookingDaoList(new ArrayList<>());
        userDao1.setCreatedAt(null);
        userDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDao1.setId(123L);
        userDao1.setIsDeleted(true);
        userDao1.setPassword("iloveyou");
        userDao1.setProfile(new ProfileDao());
        userDao1.setUpdatedAt(null);
        userDao1.setUsername("janedoe");

        ProfileDao profileDao1 = new ProfileDao();
        profileDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        profileDao1.setFamilyDaoList(new ArrayList<>());
        profileDao1.setHealthFacilityDaoList(new ArrayList<>());
        profileDao1.setIsDeleted(true);
        profileDao1.setRole(AppConstant.ProfileRole.USER);
        profileDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao1.setUser(userDao1);
        profileDao1.setUserId(123L);

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
        healthFacilityDao1.setProfile(profileDao1);
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

        ProfileDao profileDao2 = new ProfileDao();
        profileDao2.setCreatedAt(null);
        profileDao2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        profileDao2.setFamilyDaoList(new ArrayList<>());
        profileDao2.setHealthFacilityDaoList(new ArrayList<>());
        profileDao2.setIsDeleted(true);
        profileDao2.setRole(AppConstant.ProfileRole.USER);
        profileDao2.setUpdatedAt(null);
        profileDao2.setUser(new UserDao());
        profileDao2.setUserId(123L);

        UserDao userDao2 = new UserDao();
        userDao2.setBookingDaoList(new ArrayList<>());
        userDao2.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDao2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDao2.setId(123L);
        userDao2.setIsDeleted(true);
        userDao2.setPassword("iloveyou");
        userDao2.setProfile(profileDao2);
        userDao2.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDao2.setUsername("janedoe");

        ProfileDao profileDao3 = new ProfileDao();
        profileDao3.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao3.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        profileDao3.setFamilyDaoList(new ArrayList<>());
        profileDao3.setHealthFacilityDaoList(new ArrayList<>());
        profileDao3.setIsDeleted(true);
        profileDao3.setRole(AppConstant.ProfileRole.USER);
        profileDao3.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao3.setUser(userDao2);
        profileDao3.setUserId(123L);

        UserDao userDao3 = new UserDao();
        userDao3.setBookingDaoList(new ArrayList<>());
        userDao3.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDao3.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDao3.setId(123L);
        userDao3.setIsDeleted(true);
        userDao3.setPassword("iloveyou");
        userDao3.setProfile(profileDao3);
        userDao3.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDao3.setUsername("janedoe");

        BookingDao bookingDao = new BookingDao();
        bookingDao.setBookingDate(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao.setBookingDetailDaos(new ArrayList<>());
        bookingDao.setBookingPass(1);
        bookingDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        bookingDao.setId(123L);
        bookingDao.setIsDeleted(true);
        bookingDao.setSchedule(scheduleDao1);
        bookingDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao.setUser(userDao3);

        ProfileDao profileDao4 = new ProfileDao();
        profileDao4.setCreatedAt(null);
        profileDao4.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        profileDao4.setFamilyDaoList(new ArrayList<>());
        profileDao4.setHealthFacilityDaoList(new ArrayList<>());
        profileDao4.setIsDeleted(true);
        profileDao4.setRole(AppConstant.ProfileRole.USER);
        profileDao4.setUpdatedAt(null);
        profileDao4.setUser(new UserDao());
        profileDao4.setUserId(123L);

        HealthFacilityDao healthFacilityDao2 = new HealthFacilityDao();
        healthFacilityDao2.setCity("Oxford");
        healthFacilityDao2.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityDao2.setDistrict("District");
        healthFacilityDao2.setFacilityName("Facility Name");
        healthFacilityDao2.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao2.setId(123L);
        healthFacilityDao2.setImgUrl("https://example.org/example");
        healthFacilityDao2.setIsDeleted(true);
        healthFacilityDao2.setOfficeNumber("42");
        healthFacilityDao2.setPostalCode(1);
        healthFacilityDao2.setProfile(profileDao4);
        healthFacilityDao2.setProvince("Province");
        healthFacilityDao2.setScheduleDaoList(new ArrayList<>());
        healthFacilityDao2.setStreetName("Street Name");
        healthFacilityDao2.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao2.setVillageName("Village Name");

        VaccineTypeDao vaccineTypeDao2 = new VaccineTypeDao();
        vaccineTypeDao2.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        vaccineTypeDao2.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao2.setId(123L);
        vaccineTypeDao2.setIsDeleted(true);
        vaccineTypeDao2.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao2.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao2.setVaccineName("Vaccine Name");

        ScheduleDao scheduleDao2 = new ScheduleDao();
        scheduleDao2.setBookingDaoList(new ArrayList<>());
        scheduleDao2.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        scheduleDao2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        scheduleDao2.setDose(AppConstant.Dose.DOSIS_1);
        scheduleDao2.setFacility(healthFacilityDao2);
        scheduleDao2.setId(123L);
        scheduleDao2.setIsDeleted(true);
        scheduleDao2.setOperationalHourEnd(LocalTime.of(1, 1));
        scheduleDao2.setOperationalHourStart(LocalTime.of(1, 1));
        scheduleDao2.setQuota(1);
        scheduleDao2.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        scheduleDao2.setVaccinationDate(LocalDate.ofEpochDay(1L));
        scheduleDao2.setVaccine(vaccineTypeDao2);

        UserDao userDao4 = new UserDao();
        userDao4.setBookingDaoList(new ArrayList<>());
        userDao4.setCreatedAt(null);
        userDao4.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDao4.setId(123L);
        userDao4.setIsDeleted(true);
        userDao4.setPassword("iloveyou");
        userDao4.setProfile(new ProfileDao());
        userDao4.setUpdatedAt(null);
        userDao4.setUsername("janedoe");

        ProfileDao profileDao5 = new ProfileDao();
        profileDao5.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao5.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        profileDao5.setFamilyDaoList(new ArrayList<>());
        profileDao5.setHealthFacilityDaoList(new ArrayList<>());
        profileDao5.setIsDeleted(true);
        profileDao5.setRole(AppConstant.ProfileRole.USER);
        profileDao5.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao5.setUser(userDao4);
        profileDao5.setUserId(123L);

        UserDao userDao5 = new UserDao();
        userDao5.setBookingDaoList(new ArrayList<>());
        userDao5.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDao5.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDao5.setId(123L);
        userDao5.setIsDeleted(true);
        userDao5.setPassword("iloveyou");
        userDao5.setProfile(profileDao5);
        userDao5.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDao5.setUsername("janedoe");

        BookingDao bookingDao1 = new BookingDao();
        bookingDao1.setBookingDate(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao1.setBookingDetailDaos(new ArrayList<>());
        bookingDao1.setBookingPass(1);
        bookingDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        bookingDao1.setId(123L);
        bookingDao1.setIsDeleted(true);
        bookingDao1.setSchedule(scheduleDao2);
        bookingDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao1.setUser(userDao5);
        Optional<BookingDao> ofResult1 = Optional.of(bookingDao1);
        when(this.bookingRepository.save((BookingDao) any())).thenReturn(bookingDao);
        when(this.bookingRepository.findFirstByScheduleIdOrderByIdDesc((Long) any())).thenReturn(ofResult1);

        BookingDto bookingDto = new BookingDto();
        bookingDto.setSchedule(new ScheduleDto());

        ResponseEntity<Object> actualCreateBookingResult = this.bookingService.createBooking(bookingDto);

        assertTrue(actualCreateBookingResult.hasBody());
        assertTrue(actualCreateBookingResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualCreateBookingResult.getStatusCode());

        Object data = ((ApiResponse<Object>) actualCreateBookingResult.getBody()).getData();
        assertTrue(data instanceof BookingDto);

        ApiResponseStatus status = ((ApiResponse<Object>) actualCreateBookingResult.getBody()).getStatus();

        assertEquals("SUCCESS", status.getCode());
        assertNull(((BookingDto) data).getId());
        assertEquals(2, ((BookingDto) data).getBookingPass().intValue());
        assertEquals("Success", status.getMessage());
        assertNull(((BookingDto) data).getBookingDate());

        ScheduleDto schedule = ((BookingDto) data).getSchedule();

        assertNull(schedule.getVaccine());
        assertEquals("1970-01-02", schedule.getVaccinationDate().toString());
        assertEquals(1, schedule.getQuota().intValue());
        assertEquals("01:01", schedule.getOperationalHourStart().toString());
        assertEquals("01:01", schedule.getOperationalHourEnd().toString());
        assertEquals(123L, schedule.getId().longValue());
        assertNull(schedule.getFacility());
        assertEquals(AppConstant.Dose.DOSIS_1, schedule.getDose());

        verify(this.scheduleRepository).findById((Long) any());
        verify(this.bookingRepository).save((BookingDao) any());
        verify(this.bookingRepository).findFirstByScheduleIdOrderByIdDesc((Long) any());
    }

    @Test
    void getBookingByIdSuccess_Test() {
        BookingDto bookingDto = new BookingDto();
        when(this.modelMapper.map((Object) any(), (Class<BookingDto>) any())).thenReturn(bookingDto);

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
        userDao1.setId(123L);
        userDao1.setIsDeleted(true);
        userDao1.setPassword("iloveyou");
        userDao1.setProfile(profileDao1);
        userDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDao1.setUsername("janedoe");

        BookingDao bookingDao = new BookingDao();
        bookingDao.setBookingDate(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao.setBookingDetailDaos(new ArrayList<>());
        bookingDao.setBookingPass(1);
        bookingDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        bookingDao.setId(123L);
        bookingDao.setIsDeleted(true);
        bookingDao.setSchedule(scheduleDao);
        bookingDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao.setUser(userDao1);

        Optional<BookingDao> ofResult = Optional.of(bookingDao);
        when(this.bookingRepository.findById((Long) any())).thenReturn(ofResult);

        ResponseEntity<Object> actualBookingById = this.bookingService.getBookingById(123L);

        assertTrue(actualBookingById.hasBody());
        assertTrue(actualBookingById.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualBookingById.getStatusCode());
        assertSame(bookingDto, ((ApiResponse<Object>) actualBookingById.getBody()).getData());

        ApiResponseStatus status = ((ApiResponse<Object>) actualBookingById.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());

        verify(this.modelMapper).map((Object) any(), (Class<BookingDto>) any());
        verify(this.bookingRepository).findById((Long) any());
    }

    @Test
    void getAllBookings_Test() {
        ArrayList<BookingDao> bookingDaoList = new ArrayList<>();
        when(this.bookingRepository.findAll()).thenReturn(bookingDaoList);

        ResponseEntity<Object> actualAllBookings = this.bookingService.getAllBookings();

        assertTrue(actualAllBookings.hasBody());
        assertTrue(actualAllBookings.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualAllBookings.getStatusCode());
        assertEquals(bookingDaoList, ((ApiResponse<Object>) actualAllBookings.getBody()).getData());

        ApiResponseStatus status = ((ApiResponse<Object>) actualAllBookings.getBody()).getStatus();

        assertEquals("Success", status.getMessage());
        assertEquals("SUCCESS", status.getCode());
        verify(this.bookingRepository).findAll();
    }

    @Test
    void getAllBookings_Test2() {
        when(this.modelMapper.map((Object) any(), (Class<BookingDto>) any())).thenReturn(new BookingDto());

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

        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
        healthFacilityDao.setCity("Oxford");
        healthFacilityDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityDao.setDistrict("Getting all of Bookings");
        healthFacilityDao.setFacilityName("Getting all of Bookings");
        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao.setId(123L);
        healthFacilityDao.setImgUrl("https://example.org/example");
        healthFacilityDao.setIsDeleted(true);
        healthFacilityDao.setOfficeNumber("42");
        healthFacilityDao.setPostalCode(1);
        healthFacilityDao.setProfile(profileDao);
        healthFacilityDao.setProvince("Getting all of Bookings");
        healthFacilityDao.setScheduleDaoList(new ArrayList<>());
        healthFacilityDao.setStreetName("Getting all of Bookings");
        healthFacilityDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao.setVillageName("Getting all of Bookings");

        VaccineTypeDao vaccineTypeDao = new VaccineTypeDao();
        vaccineTypeDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        vaccineTypeDao.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao.setId(123L);
        vaccineTypeDao.setIsDeleted(true);
        vaccineTypeDao.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao.setVaccineName("Getting all of Bookings");

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
        userDao1.setId(123L);
        userDao1.setIsDeleted(true);
        userDao1.setPassword("iloveyou");
        userDao1.setProfile(profileDao1);
        userDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDao1.setUsername("janedoe");

        BookingDao bookingDao = new BookingDao();
        bookingDao.setBookingDate(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao.setBookingDetailDaos(new ArrayList<>());
        bookingDao.setBookingPass(1);
        bookingDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        bookingDao.setId(123L);
        bookingDao.setIsDeleted(true);
        bookingDao.setSchedule(scheduleDao);
        bookingDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao.setUser(userDao1);

        ArrayList<BookingDao> bookingDaoList = new ArrayList<>();
        bookingDaoList.add(bookingDao);

        when(this.bookingRepository.findAll()).thenReturn(bookingDaoList);

        ResponseEntity<Object> actualAllBookings = this.bookingService.getAllBookings();

        assertTrue(actualAllBookings.hasBody());
        assertTrue(actualAllBookings.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualAllBookings.getStatusCode());
        assertEquals(1, ((Collection<BookingDto>) ((ApiResponse<Object>) actualAllBookings.getBody()).getData()).size());

        ApiResponseStatus status = ((ApiResponse<Object>) actualAllBookings.getBody()).getStatus();

        assertEquals("SUCCESS", status.getCode());
        assertEquals("Success", status.getMessage());

        verify(this.modelMapper).map((Object) any(), (Class<BookingDto>) any());
        verify(this.bookingRepository).findAll();
    }

    @Test
    void updateBookingById_Test() {
        BookingDto bookingDto = new BookingDto();
        when(this.modelMapper.map((Object) any(), (Class<BookingDto>) any())).thenReturn(bookingDto);

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
        userDao1.setId(123L);
        userDao1.setIsDeleted(true);
        userDao1.setPassword("iloveyou");
        userDao1.setProfile(profileDao1);
        userDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDao1.setUsername("janedoe");

        BookingDao bookingDao = new BookingDao();
        bookingDao.setBookingDate(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao.setBookingDetailDaos(new ArrayList<>());
        bookingDao.setBookingPass(1);
        bookingDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        bookingDao.setId(123L);
        bookingDao.setIsDeleted(true);
        bookingDao.setSchedule(scheduleDao);
        bookingDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao.setUser(userDao1);
        Optional<BookingDao> ofResult = Optional.of(bookingDao);

        UserDao userDao2 = new UserDao();
        userDao2.setBookingDaoList(new ArrayList<>());
        userDao2.setCreatedAt(null);
        userDao2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDao2.setId(123L);
        userDao2.setIsDeleted(true);
        userDao2.setPassword("iloveyou");
        userDao2.setProfile(new ProfileDao());
        userDao2.setUpdatedAt(null);
        userDao2.setUsername("janedoe");

        ProfileDao profileDao2 = new ProfileDao();
        profileDao2.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        profileDao2.setFamilyDaoList(new ArrayList<>());
        profileDao2.setHealthFacilityDaoList(new ArrayList<>());
        profileDao2.setIsDeleted(true);
        profileDao2.setRole(AppConstant.ProfileRole.USER);
        profileDao2.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao2.setUser(userDao2);
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

        ProfileDao profileDao3 = new ProfileDao();
        profileDao3.setCreatedAt(null);
        profileDao3.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        profileDao3.setFamilyDaoList(new ArrayList<>());
        profileDao3.setHealthFacilityDaoList(new ArrayList<>());
        profileDao3.setIsDeleted(true);
        profileDao3.setRole(AppConstant.ProfileRole.USER);
        profileDao3.setUpdatedAt(null);
        profileDao3.setUser(new UserDao());
        profileDao3.setUserId(123L);

        UserDao userDao3 = new UserDao();
        userDao3.setBookingDaoList(new ArrayList<>());
        userDao3.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDao3.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDao3.setId(123L);
        userDao3.setIsDeleted(true);
        userDao3.setPassword("iloveyou");
        userDao3.setProfile(profileDao3);
        userDao3.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDao3.setUsername("janedoe");

        ProfileDao profileDao4 = new ProfileDao();
        profileDao4.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao4.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        profileDao4.setFamilyDaoList(new ArrayList<>());
        profileDao4.setHealthFacilityDaoList(new ArrayList<>());
        profileDao4.setIsDeleted(true);
        profileDao4.setRole(AppConstant.ProfileRole.USER);
        profileDao4.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao4.setUser(userDao3);
        profileDao4.setUserId(123L);

        UserDao userDao4 = new UserDao();
        userDao4.setBookingDaoList(new ArrayList<>());
        userDao4.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDao4.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDao4.setId(123L);
        userDao4.setIsDeleted(true);
        userDao4.setPassword("iloveyou");
        userDao4.setProfile(profileDao4);
        userDao4.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDao4.setUsername("janedoe");

        BookingDao bookingDao1 = new BookingDao();
        bookingDao1.setBookingDate(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao1.setBookingDetailDaos(new ArrayList<>());
        bookingDao1.setBookingPass(1);
        bookingDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        bookingDao1.setId(123L);
        bookingDao1.setIsDeleted(true);
        bookingDao1.setSchedule(scheduleDao1);
        bookingDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao1.setUser(userDao4);

        when(this.bookingRepository.save((BookingDao) any())).thenReturn(bookingDao1);
        when(this.bookingRepository.findById((Long) any())).thenReturn(ofResult);

        ResponseEntity<Object> actualUpdateBookingByIdResult = this.bookingService.updateBookingById(123L,
                new BookingDto());

        assertTrue(actualUpdateBookingByIdResult.hasBody());
        assertTrue(actualUpdateBookingByIdResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualUpdateBookingByIdResult.getStatusCode());
        assertSame(bookingDto, ((ApiResponse<Object>) actualUpdateBookingByIdResult.getBody()).getData());

        ApiResponseStatus status = ((ApiResponse<Object>) actualUpdateBookingByIdResult.getBody()).getStatus();

        assertEquals("SUCCESS", status.getCode());
        assertEquals("Success", status.getMessage());

        verify(this.modelMapper).map((Object) any(), (Class<BookingDto>) any());
        verify(this.bookingRepository).save((BookingDao) any());
        verify(this.bookingRepository).findById((Long) any());
    }

    @Test
    void deleteBookingById_Test() {
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
        userDao1.setId(123L);
        userDao1.setIsDeleted(true);
        userDao1.setPassword("iloveyou");
        userDao1.setProfile(profileDao1);
        userDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDao1.setUsername("janedoe");

        BookingDao bookingDao = new BookingDao();
        bookingDao.setBookingDate(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao.setBookingDetailDaos(new ArrayList<>());
        bookingDao.setBookingPass(1);
        bookingDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        bookingDao.setId(123L);
        bookingDao.setIsDeleted(true);
        bookingDao.setSchedule(scheduleDao);
        bookingDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao.setUser(userDao1);

        Optional<BookingDao> ofResult = Optional.of(bookingDao);
        doNothing().when(this.bookingRepository).delete((BookingDao) any());

        when(this.bookingRepository.findById((Long) any())).thenReturn(ofResult);

        ResponseEntity<Object> actualDeleteBookingByIdResult = this.bookingService.deleteBookingById(123L);

        assertTrue(actualDeleteBookingByIdResult.hasBody());
        assertTrue(actualDeleteBookingByIdResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualDeleteBookingByIdResult.getStatusCode());
        assertNull(((ApiResponse<Object>) actualDeleteBookingByIdResult.getBody()).getData());

        ApiResponseStatus status = ((ApiResponse<Object>) actualDeleteBookingByIdResult.getBody()).getStatus();

        assertEquals("Success", status.getMessage());
        assertEquals("SUCCESS", status.getCode());

        verify(this.bookingRepository).findById((Long) any());
        verify(this.bookingRepository).delete((BookingDao) any());
    }
}

