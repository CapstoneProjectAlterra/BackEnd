package com.backend.vaccinebookingsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.common.ApiResponse;
import com.backend.vaccinebookingsystem.domain.common.ApiResponseStatus;
import com.backend.vaccinebookingsystem.domain.dao.BookingDao;
import com.backend.vaccinebookingsystem.domain.dao.BookingDetailDao;
import com.backend.vaccinebookingsystem.domain.dao.FamilyDao;
import com.backend.vaccinebookingsystem.domain.dao.HealthFacilityDao;
import com.backend.vaccinebookingsystem.domain.dao.ProfileDao;
import com.backend.vaccinebookingsystem.domain.dao.ScheduleDao;
import com.backend.vaccinebookingsystem.domain.dao.UserDao;
import com.backend.vaccinebookingsystem.domain.dao.VaccineTypeDao;
import com.backend.vaccinebookingsystem.domain.dto.BookingDetailDto;
import com.backend.vaccinebookingsystem.repository.BookingDetailRepository;
import com.backend.vaccinebookingsystem.repository.BookingRepository;
import com.backend.vaccinebookingsystem.repository.FamilyRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

@ContextConfiguration(classes = {BookingDetailService.class})
@ExtendWith(SpringExtension.class)
class BookingDetailServiceTest {
    @MockBean
    private BookingDetailRepository bookingDetailRepository;

    @Autowired
    private BookingDetailService bookingDetailService;

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private FamilyRepository familyRepository;

    @MockBean
    private ModelMapper modelMapper;

    /**
     * Method under test: {@link BookingDetailService#createBookingDetail(BookingDetailDto)}
     */
    @Test
    void createBookingDetail_Test() {
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

        FamilyDao familyDao = new FamilyDao();
        familyDao.setBookingDaoList(new ArrayList<>());
        familyDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        familyDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        familyDao.setDateOfBirth(LocalDate.ofEpochDay(1L));
        familyDao.setEmail("jane.doe@example.org");
        familyDao.setFullName("Dr Jane Doe");
        familyDao.setGender(AppConstant.Gender.LAKI_LAKI);
        familyDao.setId(123L);
        familyDao.setIdCardAddress("42 Main St");
        familyDao.setIsDeleted(true);
        familyDao.setNIK("NIK");
        familyDao.setPhoneNumber("4105551212");
        familyDao.setProfile(profileDao1);
        familyDao.setResidenceAddress("42 Main St");
        familyDao.setStatusInFamily(AppConstant.FamilyStatus.AYAH);
        familyDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        Optional<FamilyDao> ofResult = Optional.of(familyDao);
        when(this.familyRepository.findById((Long) any())).thenReturn(ofResult);

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
        healthFacilityDao.setProfile(profileDao2);
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

        ProfileDao profileDao3 = new ProfileDao();
        profileDao3.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao3.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        profileDao3.setFamilyDaoList(new ArrayList<>());
        profileDao3.setHealthFacilityDaoList(new ArrayList<>());
        profileDao3.setIsDeleted(true);
        profileDao3.setRole(AppConstant.ProfileRole.USER);
        profileDao3.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao3.setUser(userDao1);
        profileDao3.setUserId(123L);

        UserDao userDao2 = new UserDao();
        userDao2.setBookingDaoList(new ArrayList<>());
        userDao2.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDao2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDao2.setId(123L);
        userDao2.setIsDeleted(true);
        userDao2.setPassword("iloveyou");
        userDao2.setProfile(profileDao3);
        userDao2.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDao2.setUsername("janedoe");

        BookingDao bookingDao = new BookingDao();
        bookingDao.setBookingDate(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao.setBookingPass(1);
        bookingDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        bookingDao.setFamilyDaoList(new ArrayList<>());
        bookingDao.setId(123L);
        bookingDao.setIsDeleted(true);
        bookingDao.setSchedule(scheduleDao);
        bookingDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao.setUser(userDao2);
        Optional<BookingDao> ofResult1 = Optional.of(bookingDao);
        when(this.bookingRepository.findById((Long) any())).thenReturn(ofResult1);

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
        healthFacilityDao1.setProfile(profileDao4);
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

        UserDao userDao3 = new UserDao();
        userDao3.setBookingDaoList(new ArrayList<>());
        userDao3.setCreatedAt(null);
        userDao3.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDao3.setId(123L);
        userDao3.setIsDeleted(true);
        userDao3.setPassword("iloveyou");
        userDao3.setProfile(new ProfileDao());
        userDao3.setUpdatedAt(null);
        userDao3.setUsername("janedoe");

        ProfileDao profileDao5 = new ProfileDao();
        profileDao5.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao5.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        profileDao5.setFamilyDaoList(new ArrayList<>());
        profileDao5.setHealthFacilityDaoList(new ArrayList<>());
        profileDao5.setIsDeleted(true);
        profileDao5.setRole(AppConstant.ProfileRole.USER);
        profileDao5.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao5.setUser(userDao3);
        profileDao5.setUserId(123L);

        UserDao userDao4 = new UserDao();
        userDao4.setBookingDaoList(new ArrayList<>());
        userDao4.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDao4.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDao4.setId(123L);
        userDao4.setIsDeleted(true);
        userDao4.setPassword("iloveyou");
        userDao4.setProfile(profileDao5);
        userDao4.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDao4.setUsername("janedoe");

        BookingDao bookingDao1 = new BookingDao();
        bookingDao1.setBookingDate(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao1.setBookingPass(1);
        bookingDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        bookingDao1.setFamilyDaoList(new ArrayList<>());
        bookingDao1.setId(123L);
        bookingDao1.setIsDeleted(true);
        bookingDao1.setSchedule(scheduleDao1);
        bookingDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao1.setUser(userDao4);

        ProfileDao profileDao6 = new ProfileDao();
        profileDao6.setCreatedAt(null);
        profileDao6.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        profileDao6.setFamilyDaoList(new ArrayList<>());
        profileDao6.setHealthFacilityDaoList(new ArrayList<>());
        profileDao6.setIsDeleted(true);
        profileDao6.setRole(AppConstant.ProfileRole.USER);
        profileDao6.setUpdatedAt(null);
        profileDao6.setUser(new UserDao());
        profileDao6.setUserId(123L);

        UserDao userDao5 = new UserDao();
        userDao5.setBookingDaoList(new ArrayList<>());
        userDao5.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDao5.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDao5.setId(123L);
        userDao5.setIsDeleted(true);
        userDao5.setPassword("iloveyou");
        userDao5.setProfile(profileDao6);
        userDao5.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDao5.setUsername("janedoe");

        ProfileDao profileDao7 = new ProfileDao();
        profileDao7.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao7.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        profileDao7.setFamilyDaoList(new ArrayList<>());
        profileDao7.setHealthFacilityDaoList(new ArrayList<>());
        profileDao7.setIsDeleted(true);
        profileDao7.setRole(AppConstant.ProfileRole.USER);
        profileDao7.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao7.setUser(userDao5);
        profileDao7.setUserId(123L);

        FamilyDao familyDao1 = new FamilyDao();
        familyDao1.setBookingDaoList(new ArrayList<>());
        familyDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        familyDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        familyDao1.setDateOfBirth(LocalDate.ofEpochDay(1L));
        familyDao1.setEmail("jane.doe@example.org");
        familyDao1.setFullName("Dr Jane Doe");
        familyDao1.setGender(AppConstant.Gender.LAKI_LAKI);
        familyDao1.setId(123L);
        familyDao1.setIdCardAddress("42 Main St");
        familyDao1.setIsDeleted(true);
        familyDao1.setNIK("NIK");
        familyDao1.setPhoneNumber("4105551212");
        familyDao1.setProfile(profileDao7);
        familyDao1.setResidenceAddress("42 Main St");
        familyDao1.setStatusInFamily(AppConstant.FamilyStatus.AYAH);
        familyDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));

        BookingDetailDao bookingDetailDao = new BookingDetailDao();
        bookingDetailDao.setBooking(bookingDao1);
        bookingDetailDao.setBookingId(123L);
        bookingDetailDao.setBookingStatus(AppConstant.BookingStatus.PENDING);
        bookingDetailDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDetailDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        bookingDetailDao.setFamily(familyDao1);
        bookingDetailDao.setFamilyId(123L);
        bookingDetailDao.setIsDeleted(true);
        bookingDetailDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        when(this.bookingDetailRepository.save((BookingDetailDao) any())).thenReturn(bookingDetailDao);
        ResponseEntity<Object> actualCreateBookingDetailResult = this.bookingDetailService
                .createBookingDetail(new BookingDetailDto());
        assertTrue(actualCreateBookingDetailResult.hasBody());
        assertTrue(actualCreateBookingDetailResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualCreateBookingDetailResult.getStatusCode());
        Object data = ((ApiResponse<Object>) actualCreateBookingDetailResult.getBody()).getData();
        assertTrue(data instanceof BookingDetailDto);
        ApiResponseStatus status = ((ApiResponse<Object>) actualCreateBookingDetailResult.getBody()).getStatus();
        assertEquals("SUCCESS", status.getCode());
        assertNull(((BookingDetailDto) data).getBookingStatus());
        assertNull(((BookingDetailDto) data).getBookingId());
        assertNull(((BookingDetailDto) data).getFamilyId());
        assertEquals("Success", status.getMessage());
        verify(this.familyRepository).findById((Long) any());
        verify(this.bookingRepository).findById((Long) any());
        verify(this.bookingDetailRepository).save((BookingDetailDao) any());
    }

    /**
     * Method under test: {@link BookingDetailService#searchBookingDetailById(Long, Long)}
     */
    @Test
    void searchBookingDetailById_Test() {
        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
        healthFacilityDao.setCity("Oxford");
        healthFacilityDao.setCreatedAt(null);
        healthFacilityDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityDao.setDistrict("District");
        healthFacilityDao.setFacilityName("Facility Name");
        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao.setId(123L);
        healthFacilityDao.setImgUrl("https://example.org/example");
        healthFacilityDao.setIsDeleted(true);
        healthFacilityDao.setOfficeNumber("42");
        healthFacilityDao.setPostalCode(1);
        healthFacilityDao.setProfile(new ProfileDao());
        healthFacilityDao.setProvince("Province");
        healthFacilityDao.setScheduleDaoList(new ArrayList<>());
        healthFacilityDao.setStreetName("Street Name");
        healthFacilityDao.setUpdatedAt(null);
        healthFacilityDao.setVillageName("Village Name");

        VaccineTypeDao vaccineTypeDao = new VaccineTypeDao();
        vaccineTypeDao.setCreatedAt(null);
        vaccineTypeDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        vaccineTypeDao.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao.setId(123L);
        vaccineTypeDao.setIsDeleted(true);
        vaccineTypeDao.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao.setUpdatedAt(null);
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

        BookingDao bookingDao = new BookingDao();
        bookingDao.setBookingDate(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao.setBookingPass(1);
        bookingDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        bookingDao.setFamilyDaoList(new ArrayList<>());
        bookingDao.setId(123L);
        bookingDao.setIsDeleted(true);
        bookingDao.setSchedule(scheduleDao);
        bookingDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao.setUser(userDao);

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

        FamilyDao familyDao = new FamilyDao();
        familyDao.setBookingDaoList(new ArrayList<>());
        familyDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        familyDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        familyDao.setDateOfBirth(LocalDate.ofEpochDay(1L));
        familyDao.setEmail("jane.doe@example.org");
        familyDao.setFullName("Dr Jane Doe");
        familyDao.setGender(AppConstant.Gender.LAKI_LAKI);
        familyDao.setId(123L);
        familyDao.setIdCardAddress("42 Main St");
        familyDao.setIsDeleted(true);
        familyDao.setNIK("NIK");
        familyDao.setPhoneNumber("4105551212");
        familyDao.setProfile(profileDao1);
        familyDao.setResidenceAddress("42 Main St");
        familyDao.setStatusInFamily(AppConstant.FamilyStatus.AYAH);
        familyDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));

        BookingDetailDao bookingDetailDao = new BookingDetailDao();
        bookingDetailDao.setBooking(bookingDao);
        bookingDetailDao.setBookingId(123L);
        bookingDetailDao.setBookingStatus(AppConstant.BookingStatus.PENDING);
        bookingDetailDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDetailDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        bookingDetailDao.setFamily(familyDao);
        bookingDetailDao.setFamilyId(123L);
        bookingDetailDao.setIsDeleted(true);
        bookingDetailDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        Optional<BookingDetailDao> ofResult = Optional.of(bookingDetailDao);

        HealthFacilityDao healthFacilityDao1 = new HealthFacilityDao();
        healthFacilityDao1.setCity("Oxford");
        healthFacilityDao1.setCreatedAt(null);
        healthFacilityDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityDao1.setDistrict("District");
        healthFacilityDao1.setFacilityName("Facility Name");
        healthFacilityDao1.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao1.setId(123L);
        healthFacilityDao1.setImgUrl("https://example.org/example");
        healthFacilityDao1.setIsDeleted(true);
        healthFacilityDao1.setOfficeNumber("42");
        healthFacilityDao1.setPostalCode(1);
        healthFacilityDao1.setProfile(new ProfileDao());
        healthFacilityDao1.setProvince("Province");
        healthFacilityDao1.setScheduleDaoList(new ArrayList<>());
        healthFacilityDao1.setStreetName("Street Name");
        healthFacilityDao1.setUpdatedAt(null);
        healthFacilityDao1.setVillageName("Village Name");

        VaccineTypeDao vaccineTypeDao1 = new VaccineTypeDao();
        vaccineTypeDao1.setCreatedAt(null);
        vaccineTypeDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        vaccineTypeDao1.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao1.setId(123L);
        vaccineTypeDao1.setIsDeleted(true);
        vaccineTypeDao1.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao1.setUpdatedAt(null);
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

        BookingDao bookingDao1 = new BookingDao();
        bookingDao1.setBookingDate(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao1.setBookingPass(1);
        bookingDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        bookingDao1.setFamilyDaoList(new ArrayList<>());
        bookingDao1.setId(123L);
        bookingDao1.setIsDeleted(true);
        bookingDao1.setSchedule(scheduleDao1);
        bookingDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao1.setUser(userDao2);

        UserDao userDao3 = new UserDao();
        userDao3.setBookingDaoList(new ArrayList<>());
        userDao3.setCreatedAt(null);
        userDao3.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDao3.setId(123L);
        userDao3.setIsDeleted(true);
        userDao3.setPassword("iloveyou");
        userDao3.setProfile(new ProfileDao());
        userDao3.setUpdatedAt(null);
        userDao3.setUsername("janedoe");

        ProfileDao profileDao3 = new ProfileDao();
        profileDao3.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao3.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        profileDao3.setFamilyDaoList(new ArrayList<>());
        profileDao3.setHealthFacilityDaoList(new ArrayList<>());
        profileDao3.setIsDeleted(true);
        profileDao3.setRole(AppConstant.ProfileRole.USER);
        profileDao3.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao3.setUser(userDao3);
        profileDao3.setUserId(123L);

        FamilyDao familyDao1 = new FamilyDao();
        familyDao1.setBookingDaoList(new ArrayList<>());
        familyDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        familyDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        familyDao1.setDateOfBirth(LocalDate.ofEpochDay(1L));
        familyDao1.setEmail("jane.doe@example.org");
        familyDao1.setFullName("Dr Jane Doe");
        familyDao1.setGender(AppConstant.Gender.LAKI_LAKI);
        familyDao1.setId(123L);
        familyDao1.setIdCardAddress("42 Main St");
        familyDao1.setIsDeleted(true);
        familyDao1.setNIK("NIK");
        familyDao1.setPhoneNumber("4105551212");
        familyDao1.setProfile(profileDao3);
        familyDao1.setResidenceAddress("42 Main St");
        familyDao1.setStatusInFamily(AppConstant.FamilyStatus.AYAH);
        familyDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));

        BookingDetailDao bookingDetailDao1 = new BookingDetailDao();
        bookingDetailDao1.setBooking(bookingDao1);
        bookingDetailDao1.setBookingId(123L);
        bookingDetailDao1.setBookingStatus(AppConstant.BookingStatus.PENDING);
        bookingDetailDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDetailDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        bookingDetailDao1.setFamily(familyDao1);
        bookingDetailDao1.setFamilyId(123L);
        bookingDetailDao1.setIsDeleted(true);
        bookingDetailDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        Optional<BookingDetailDao> ofResult1 = Optional.of(bookingDetailDao1);
        when(this.bookingDetailRepository.findByBookingId((Long) any())).thenReturn(ofResult);
        when(this.bookingDetailRepository.findByFamilyId((Long) any())).thenReturn(ofResult1);
        ResponseEntity<Object> actualSearchBookingDetailByIdResult = this.bookingDetailService.searchBookingDetailById(123L,
                123L);
        assertTrue(actualSearchBookingDetailByIdResult.hasBody());
        assertTrue(actualSearchBookingDetailByIdResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualSearchBookingDetailByIdResult.getStatusCode());
        Object data = ((ApiResponse<Object>) actualSearchBookingDetailByIdResult.getBody()).getData();
        assertTrue(data instanceof BookingDetailDto);
        ApiResponseStatus status = ((ApiResponse<Object>) actualSearchBookingDetailByIdResult.getBody()).getStatus();
        assertEquals("SUCCESS", status.getCode());
        assertEquals(AppConstant.BookingStatus.PENDING, ((BookingDetailDto) data).getBookingStatus());
        assertEquals(123L, ((BookingDetailDto) data).getBookingId().longValue());
        assertEquals(123L, ((BookingDetailDto) data).getFamilyId().longValue());
        assertEquals("Success", status.getMessage());
        verify(this.bookingDetailRepository).findByBookingId((Long) any());
        verify(this.bookingDetailRepository).findByFamilyId((Long) any());
    }

    /**
     * Method under test: {@link BookingDetailService#getAllBookingDetails()}
     */
    @Test
    void getAllBookingDetails_Test() {
        ArrayList<BookingDetailDao> bookingDetailDaoList = new ArrayList<>();
        when(this.bookingDetailRepository.findAll()).thenReturn(bookingDetailDaoList);
        ResponseEntity<Object> actualAllBookingDetails = this.bookingDetailService.getAllBookingDetails();
        assertTrue(actualAllBookingDetails.hasBody());
        assertTrue(actualAllBookingDetails.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualAllBookingDetails.getStatusCode());
        assertEquals(bookingDetailDaoList, ((ApiResponse<Object>) actualAllBookingDetails.getBody()).getData());
        ApiResponseStatus status = ((ApiResponse<Object>) actualAllBookingDetails.getBody()).getStatus();
        assertEquals("Success", status.getMessage());
        assertEquals("SUCCESS", status.getCode());
        verify(this.bookingDetailRepository).findAll();
    }

    /**
     * Method under test: {@link BookingDetailService#getAllBookingDetails()}
     */
    @Test
    void getAllBookingDetails_Test2() {
        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
        healthFacilityDao.setCity("Oxford");
        healthFacilityDao.setCreatedAt(null);
        healthFacilityDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityDao.setDistrict("Getting all of Booking Details");
        healthFacilityDao.setFacilityName("Getting all of Booking Details");
        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao.setId(123L);
        healthFacilityDao.setImgUrl("https://example.org/example");
        healthFacilityDao.setIsDeleted(true);
        healthFacilityDao.setOfficeNumber("42");
        healthFacilityDao.setPostalCode(1);
        healthFacilityDao.setProfile(new ProfileDao());
        healthFacilityDao.setProvince("Getting all of Booking Details");
        healthFacilityDao.setScheduleDaoList(new ArrayList<>());
        healthFacilityDao.setStreetName("Getting all of Booking Details");
        healthFacilityDao.setUpdatedAt(null);
        healthFacilityDao.setVillageName("Getting all of Booking Details");

        VaccineTypeDao vaccineTypeDao = new VaccineTypeDao();
        vaccineTypeDao.setCreatedAt(null);
        vaccineTypeDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        vaccineTypeDao.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao.setId(123L);
        vaccineTypeDao.setIsDeleted(true);
        vaccineTypeDao.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao.setUpdatedAt(null);
        vaccineTypeDao.setVaccineName("Getting all of Booking Details");

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

        BookingDao bookingDao = new BookingDao();
        bookingDao.setBookingDate(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao.setBookingPass(1);
        bookingDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        bookingDao.setFamilyDaoList(new ArrayList<>());
        bookingDao.setId(123L);
        bookingDao.setIsDeleted(true);
        bookingDao.setSchedule(scheduleDao);
        bookingDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao.setUser(userDao);

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

        FamilyDao familyDao = new FamilyDao();
        familyDao.setBookingDaoList(new ArrayList<>());
        familyDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        familyDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        familyDao.setDateOfBirth(LocalDate.ofEpochDay(1L));
        familyDao.setEmail("jane.doe@example.org");
        familyDao.setFullName("Dr Jane Doe");
        familyDao.setGender(AppConstant.Gender.LAKI_LAKI);
        familyDao.setId(123L);
        familyDao.setIdCardAddress("42 Main St");
        familyDao.setIsDeleted(true);
        familyDao.setNIK("Getting all of Booking Details");
        familyDao.setPhoneNumber("4105551212");
        familyDao.setProfile(profileDao1);
        familyDao.setResidenceAddress("42 Main St");
        familyDao.setStatusInFamily(AppConstant.FamilyStatus.AYAH);
        familyDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));

        BookingDetailDao bookingDetailDao = new BookingDetailDao();
        bookingDetailDao.setBooking(bookingDao);
        bookingDetailDao.setBookingId(123L);
        bookingDetailDao.setBookingStatus(AppConstant.BookingStatus.PENDING);
        bookingDetailDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDetailDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        bookingDetailDao.setFamily(familyDao);
        bookingDetailDao.setFamilyId(123L);
        bookingDetailDao.setIsDeleted(true);
        bookingDetailDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));

        ArrayList<BookingDetailDao> bookingDetailDaoList = new ArrayList<>();
        bookingDetailDaoList.add(bookingDetailDao);
        when(this.bookingDetailRepository.findAll()).thenReturn(bookingDetailDaoList);
        ResponseEntity<Object> actualAllBookingDetails = this.bookingDetailService.getAllBookingDetails();
        assertTrue(actualAllBookingDetails.hasBody());
        assertTrue(actualAllBookingDetails.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualAllBookingDetails.getStatusCode());
        Object data = ((ApiResponse<Object>) actualAllBookingDetails.getBody()).getData();
        assertEquals(1, ((Collection<BookingDetailDto>) data).size());
        ApiResponseStatus status = ((ApiResponse<Object>) actualAllBookingDetails.getBody()).getStatus();
        assertEquals("SUCCESS", status.getCode());
        assertEquals("Success", status.getMessage());
        BookingDetailDto getResult = ((List<BookingDetailDto>) data).get(0);
        assertEquals(AppConstant.BookingStatus.PENDING, getResult.getBookingStatus());
        assertEquals(123L, getResult.getBookingId().longValue());
        assertEquals(123L, getResult.getFamilyId().longValue());
        verify(this.bookingDetailRepository).findAll();
    }

    /**
     * Method under test: {@link BookingDetailService#getAllBookingDetails()}
     */
    @Test
    void getAllBookingDetails_Test3() {
        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
        healthFacilityDao.setCity("Oxford");
        healthFacilityDao.setCreatedAt(null);
        healthFacilityDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityDao.setDistrict("Getting all of Booking Details");
        healthFacilityDao.setFacilityName("Getting all of Booking Details");
        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao.setId(123L);
        healthFacilityDao.setImgUrl("https://example.org/example");
        healthFacilityDao.setIsDeleted(true);
        healthFacilityDao.setOfficeNumber("42");
        healthFacilityDao.setPostalCode(1);
        healthFacilityDao.setProfile(new ProfileDao());
        healthFacilityDao.setProvince("Getting all of Booking Details");
        healthFacilityDao.setScheduleDaoList(new ArrayList<>());
        healthFacilityDao.setStreetName("Getting all of Booking Details");
        healthFacilityDao.setUpdatedAt(null);
        healthFacilityDao.setVillageName("Getting all of Booking Details");

        VaccineTypeDao vaccineTypeDao = new VaccineTypeDao();
        vaccineTypeDao.setCreatedAt(null);
        vaccineTypeDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        vaccineTypeDao.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao.setId(123L);
        vaccineTypeDao.setIsDeleted(true);
        vaccineTypeDao.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao.setUpdatedAt(null);
        vaccineTypeDao.setVaccineName("Getting all of Booking Details");

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

        BookingDao bookingDao = new BookingDao();
        bookingDao.setBookingDate(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao.setBookingPass(1);
        bookingDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        bookingDao.setFamilyDaoList(new ArrayList<>());
        bookingDao.setId(123L);
        bookingDao.setIsDeleted(true);
        bookingDao.setSchedule(scheduleDao);
        bookingDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao.setUser(userDao);

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

        FamilyDao familyDao = new FamilyDao();
        familyDao.setBookingDaoList(new ArrayList<>());
        familyDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        familyDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        familyDao.setDateOfBirth(LocalDate.ofEpochDay(1L));
        familyDao.setEmail("jane.doe@example.org");
        familyDao.setFullName("Dr Jane Doe");
        familyDao.setGender(AppConstant.Gender.LAKI_LAKI);
        familyDao.setId(123L);
        familyDao.setIdCardAddress("42 Main St");
        familyDao.setIsDeleted(true);
        familyDao.setNIK("Getting all of Booking Details");
        familyDao.setPhoneNumber("4105551212");
        familyDao.setProfile(profileDao1);
        familyDao.setResidenceAddress("42 Main St");
        familyDao.setStatusInFamily(AppConstant.FamilyStatus.AYAH);
        familyDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        BookingDetailDao bookingDetailDao = mock(BookingDetailDao.class);
        when(bookingDetailDao.getBookingStatus()).thenReturn(AppConstant.BookingStatus.PENDING);
        when(bookingDetailDao.getBookingId()).thenReturn(123L);
        when(bookingDetailDao.getFamilyId()).thenReturn(123L);
        doNothing().when(bookingDetailDao).setCreatedAt((LocalDateTime) any());
        doNothing().when(bookingDetailDao).setCreatedBy((String) any());
        doNothing().when(bookingDetailDao).setIsDeleted((Boolean) any());
        doNothing().when(bookingDetailDao).setUpdatedAt((LocalDateTime) any());
        doNothing().when(bookingDetailDao).setBooking((BookingDao) any());
        doNothing().when(bookingDetailDao).setBookingId((Long) any());
        doNothing().when(bookingDetailDao).setBookingStatus((AppConstant.BookingStatus) any());
        doNothing().when(bookingDetailDao).setFamily((FamilyDao) any());
        doNothing().when(bookingDetailDao).setFamilyId((Long) any());
        bookingDetailDao.setBooking(bookingDao);
        bookingDetailDao.setBookingId(123L);
        bookingDetailDao.setBookingStatus(AppConstant.BookingStatus.PENDING);
        bookingDetailDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDetailDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        bookingDetailDao.setFamily(familyDao);
        bookingDetailDao.setFamilyId(123L);
        bookingDetailDao.setIsDeleted(true);
        bookingDetailDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));

        ArrayList<BookingDetailDao> bookingDetailDaoList = new ArrayList<>();
        bookingDetailDaoList.add(bookingDetailDao);
        when(this.bookingDetailRepository.findAll()).thenReturn(bookingDetailDaoList);
        ResponseEntity<Object> actualAllBookingDetails = this.bookingDetailService.getAllBookingDetails();
        assertTrue(actualAllBookingDetails.hasBody());
        assertTrue(actualAllBookingDetails.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualAllBookingDetails.getStatusCode());
        Object data = ((ApiResponse<Object>) actualAllBookingDetails.getBody()).getData();
        assertEquals(1, ((Collection<BookingDetailDto>) data).size());
        ApiResponseStatus status = ((ApiResponse<Object>) actualAllBookingDetails.getBody()).getStatus();
        assertEquals("SUCCESS", status.getCode());
        assertEquals("Success", status.getMessage());
        BookingDetailDto getResult = ((List<BookingDetailDto>) data).get(0);
        assertEquals(AppConstant.BookingStatus.PENDING, getResult.getBookingStatus());
        assertEquals(123L, getResult.getBookingId().longValue());
        assertEquals(123L, getResult.getFamilyId().longValue());
        verify(this.bookingDetailRepository).findAll();
        verify(bookingDetailDao).getBookingStatus();
        verify(bookingDetailDao).getBookingId();
        verify(bookingDetailDao).getFamilyId();
        verify(bookingDetailDao).setCreatedAt((LocalDateTime) any());
        verify(bookingDetailDao).setCreatedBy((String) any());
        verify(bookingDetailDao).setIsDeleted((Boolean) any());
        verify(bookingDetailDao).setUpdatedAt((LocalDateTime) any());
        verify(bookingDetailDao).setBooking((BookingDao) any());
        verify(bookingDetailDao).setBookingId((Long) any());
        verify(bookingDetailDao).setBookingStatus((AppConstant.BookingStatus) any());
        verify(bookingDetailDao).setFamily((FamilyDao) any());
        verify(bookingDetailDao).setFamilyId((Long) any());
    }

    /**
     * Method under test: {@link BookingDetailService#updateBookingDetailById(Long, Long, BookingDetailDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void updateBookingDetailById_Test() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at com.backend.vaccinebookingsystem.service.BookingDetailService.updateBookingDetailById(BookingDetailService.java:122)
        //   In order to prevent updateBookingDetailById(Long, Long, BookingDetailDto)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   updateBookingDetailById(Long, Long, BookingDetailDto).
        //   See https://diff.blue/R013 to resolve this issue.

        BookingDetailService bookingDetailService = new BookingDetailService();
        bookingDetailService.updateBookingDetailById(123L, 123L, new BookingDetailDto());
    }

    /**
     * Method under test: {@link BookingDetailService#deleteBookingDetailById(Long, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void deleteBookingDetailById_Test() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at com.backend.vaccinebookingsystem.service.BookingDetailService.deleteBookingDetailById(BookingDetailService.java:150)
        //   In order to prevent deleteBookingDetailById(Long, Long)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   deleteBookingDetailById(Long, Long).
        //   See https://diff.blue/R013 to resolve this issue.

        (new BookingDetailService()).deleteBookingDetailById(123L, 123L);
    }
}

