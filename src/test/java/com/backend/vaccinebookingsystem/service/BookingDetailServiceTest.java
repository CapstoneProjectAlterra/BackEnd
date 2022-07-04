package com.backend.vaccinebookingsystem.service;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.common.ApiResponse;
import com.backend.vaccinebookingsystem.domain.common.ApiResponseStatus;
import com.backend.vaccinebookingsystem.domain.dao.*;
import com.backend.vaccinebookingsystem.domain.dto.BookingDetailDto;
import com.backend.vaccinebookingsystem.repository.BookingDetailRepository;
import com.backend.vaccinebookingsystem.repository.BookingRepository;
import com.backend.vaccinebookingsystem.repository.FamilyRepository;
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

    @Test
    void createBookingDetailSuccess_Test() {
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

        FamilyDao familyDao = new FamilyDao();
        familyDao.setBookingDaoList(new ArrayList<>());
        familyDao.setId(123L);
        familyDao.setNIK("NIK");
        familyDao.setProfile(profileDao3);

        BookingDetailDao bookingDetailDao = new BookingDetailDao();
        bookingDetailDao.setBooking(bookingDao);
        bookingDetailDao.setBookingId(123L);
        bookingDetailDao.setBookingStatus(AppConstant.BookingStatus.PENDING);
        bookingDetailDao.setFamily(familyDao);
        bookingDetailDao.setFamilyId(123L);

        when(bookingDetailRepository.save((BookingDetailDao) any())).thenReturn(bookingDetailDao);

        ProfileDao profileDao4 = new ProfileDao();
        profileDao4.setFamilyDaoList(new ArrayList<>());
        profileDao4.setHealthFacilityDaoList(new ArrayList<>());
        profileDao4.setRole(AppConstant.ProfileRole.USER);
        profileDao4.setUser(new UserDao());
        profileDao4.setUserId(123L);

        HealthFacilityDao healthFacilityDao1 = new HealthFacilityDao();
        healthFacilityDao1.setFacilityName("Facility Name");
        healthFacilityDao1.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao1.setId(123L);
        healthFacilityDao1.setProfile(profileDao4);
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

        UserDao userDao3 = new UserDao();
        userDao3.setBookingDaoList(new ArrayList<>());
        userDao3.setId(123L);
        userDao3.setName("Name");
        userDao3.setProfile(new ProfileDao());

        ProfileDao profileDao5 = new ProfileDao();
        profileDao5.setFamilyDaoList(new ArrayList<>());
        profileDao5.setHealthFacilityDaoList(new ArrayList<>());
        profileDao5.setRole(AppConstant.ProfileRole.USER);
        profileDao5.setUser(userDao3);
        profileDao5.setUserId(123L);

        UserDao userDao4 = new UserDao();
        userDao4.setBookingDaoList(new ArrayList<>());
        userDao4.setId(123L);
        userDao4.setName("Name");
        userDao4.setProfile(profileDao5);

        BookingDao bookingDao1 = new BookingDao();
        bookingDao1.setBookingDetailDaos(new ArrayList<>());
        bookingDao1.setBookingPass(1);
        bookingDao1.setId(123L);
        bookingDao1.setSchedule(scheduleDao1);
        bookingDao1.setUser(userDao4);

        Optional<BookingDao> ofResult = Optional.of(bookingDao1);
        when(bookingRepository.findById((Long) any())).thenReturn(ofResult);

        ProfileDao profileDao6 = new ProfileDao();
        profileDao6.setFamilyDaoList(new ArrayList<>());
        profileDao6.setHealthFacilityDaoList(new ArrayList<>());
        profileDao6.setRole(AppConstant.ProfileRole.USER);
        profileDao6.setUser(new UserDao());
        profileDao6.setUserId(123L);

        UserDao userDao5 = new UserDao();
        userDao5.setBookingDaoList(new ArrayList<>());
        userDao5.setId(123L);
        userDao5.setName("Name");
        userDao5.setProfile(profileDao6);

        ProfileDao profileDao7 = new ProfileDao();
        profileDao7.setFamilyDaoList(new ArrayList<>());
        profileDao7.setHealthFacilityDaoList(new ArrayList<>());
        profileDao7.setRole(AppConstant.ProfileRole.USER);
        profileDao7.setUser(userDao5);
        profileDao7.setUserId(123L);

        FamilyDao familyDao1 = new FamilyDao();
        familyDao1.setBookingDaoList(new ArrayList<>());
        familyDao1.setId(123L);
        familyDao1.setNIK("NIK");
        familyDao1.setProfile(profileDao7);

        Optional<FamilyDao> ofResult1 = Optional.of(familyDao1);
        when(familyRepository.findById((Long) any())).thenReturn(ofResult1);

        ResponseEntity<Object> actualCreateBookingDetailResult = bookingDetailService
                .createBookingDetail(new BookingDetailDto());

        assertEquals(HttpStatus.OK, actualCreateBookingDetailResult.getStatusCode());

        Object data = ((ApiResponse<Object>) actualCreateBookingDetailResult.getBody()).getData();

        ApiResponseStatus status = ((ApiResponse<Object>) actualCreateBookingDetailResult.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());

        verify(bookingDetailRepository).save((BookingDetailDao) any());
        verify(bookingRepository).findById((Long) any());
        verify(familyRepository).findById((Long) any());
    }

    @Test
    void searchBookingDetailByIdSuccess_Test() {
        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
        healthFacilityDao.setFacilityName("Facility Name");
        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao.setId(123L);
        healthFacilityDao.setProfile(new ProfileDao());
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

        BookingDao bookingDao = new BookingDao();
        bookingDao.setBookingDetailDaos(new ArrayList<>());
        bookingDao.setBookingPass(1);
        bookingDao.setId(123L);
        bookingDao.setSchedule(scheduleDao);
        bookingDao.setUser(userDao);

        UserDao userDao1 = new UserDao();
        userDao1.setBookingDaoList(new ArrayList<>());
        userDao1.setId(123L);
        userDao1.setName("Name");
        userDao1.setProfile(new ProfileDao());

        ProfileDao profileDao1 = new ProfileDao();
        profileDao1.setFamilyDaoList(new ArrayList<>());
        profileDao1.setHealthFacilityDaoList(new ArrayList<>());
        profileDao1.setRole(AppConstant.ProfileRole.USER);
        profileDao1.setUser(userDao1);
        profileDao1.setUserId(123L);

        FamilyDao familyDao = new FamilyDao();
        familyDao.setBookingDaoList(new ArrayList<>());
        familyDao.setId(123L);
        familyDao.setNIK("NIK");
        familyDao.setProfile(profileDao1);

        BookingDetailDao bookingDetailDao = new BookingDetailDao();
        bookingDetailDao.setBooking(bookingDao);
        bookingDetailDao.setBookingId(123L);
        bookingDetailDao.setBookingStatus(AppConstant.BookingStatus.PENDING);
        bookingDetailDao.setFamily(familyDao);
        bookingDetailDao.setFamilyId(123L);

        Optional<BookingDetailDao> ofResult = Optional.of(bookingDetailDao);
        when(bookingDetailRepository.findByBookingIdAndFamilyId((Long) any(), (Long) any())).thenReturn(ofResult);

        ResponseEntity<Object> actualSearchBookingDetailByIdResult = bookingDetailService.searchBookingDetailById(123L,
                123L);

        assertEquals(HttpStatus.OK, actualSearchBookingDetailByIdResult.getStatusCode());

        Object data = ((ApiResponse<Object>) actualSearchBookingDetailByIdResult.getBody()).getData();

        ApiResponseStatus status = ((ApiResponse<Object>) actualSearchBookingDetailByIdResult.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());
        assertEquals(123L, ((BookingDetailDto) data).getBookingId().longValue());
        assertEquals(123L, ((BookingDetailDto) data).getFamilyId().longValue());

        verify(bookingDetailRepository).findByBookingIdAndFamilyId((Long) any(), (Long) any());
    }

    @Test
    void getAllBookingDetailsSuccess_Test() {
        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
        healthFacilityDao.setFacilityName("Facility Name");
        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao.setId(123L);
        healthFacilityDao.setProfile(new ProfileDao());
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

        BookingDao bookingDao = new BookingDao();
        bookingDao.setBookingDetailDaos(new ArrayList<>());
        bookingDao.setBookingPass(1);
        bookingDao.setId(123L);
        bookingDao.setSchedule(scheduleDao);
        bookingDao.setUser(userDao);

        UserDao userDao1 = new UserDao();
        userDao1.setBookingDaoList(new ArrayList<>());
        userDao1.setId(123L);
        userDao1.setName("Name");
        userDao1.setProfile(new ProfileDao());

        ProfileDao profileDao1 = new ProfileDao();
        profileDao1.setFamilyDaoList(new ArrayList<>());
        profileDao1.setHealthFacilityDaoList(new ArrayList<>());
        profileDao1.setRole(AppConstant.ProfileRole.USER);
        profileDao1.setUser(userDao1);
        profileDao1.setUserId(123L);

        FamilyDao familyDao = new FamilyDao();
        familyDao.setBookingDaoList(new ArrayList<>());
        familyDao.setId(123L);
        familyDao.setNIK("NIK");
        familyDao.setProfile(profileDao1);

        BookingDetailDao bookingDetailDao = new BookingDetailDao();
        bookingDetailDao.setBooking(bookingDao);
        bookingDetailDao.setBookingId(123L);
        bookingDetailDao.setBookingStatus(AppConstant.BookingStatus.PENDING);
        bookingDetailDao.setFamily(familyDao);
        bookingDetailDao.setFamilyId(123L);

        ArrayList<BookingDetailDao> bookingDetailDaoList = new ArrayList<>();
        bookingDetailDaoList.add(bookingDetailDao);

        HealthFacilityDao healthFacilityDao1 = new HealthFacilityDao();
        healthFacilityDao1.setFacilityName("Facility Name");
        healthFacilityDao1.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao1.setId(123L);
        healthFacilityDao1.setPostalCode(1);
        healthFacilityDao1.setProfile(new ProfileDao());
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

        BookingDao bookingDao1 = new BookingDao();
        bookingDao1.setBookingDetailDaos(new ArrayList<>());
        bookingDao1.setBookingPass(1);
        bookingDao1.setId(123L);
        bookingDao1.setSchedule(scheduleDao1);
        bookingDao1.setUser(userDao2);

        UserDao userDao3 = new UserDao();
        userDao3.setBookingDaoList(new ArrayList<>());
        userDao3.setId(123L);
        userDao3.setName("Name");
        userDao3.setProfile(new ProfileDao());

        ProfileDao profileDao3 = new ProfileDao();
        profileDao3.setFamilyDaoList(new ArrayList<>());
        profileDao3.setHealthFacilityDaoList(new ArrayList<>());
        profileDao3.setRole(AppConstant.ProfileRole.USER);
        profileDao3.setUser(userDao3);
        profileDao3.setUserId(123L);

        FamilyDao familyDao1 = new FamilyDao();
        familyDao1.setBookingDaoList(new ArrayList<>());
        familyDao1.setId(123L);
        familyDao1.setNIK("NIK");
        familyDao1.setProfile(profileDao3);

        BookingDetailDao bookingDetailDao1 = new BookingDetailDao();
        bookingDetailDao1.setBooking(bookingDao1);
        bookingDetailDao1.setBookingId(123L);
        bookingDetailDao1.setBookingStatus(AppConstant.BookingStatus.PENDING);
        bookingDetailDao1.setFamily(familyDao1);
        bookingDetailDao1.setFamilyId(123L);

        Optional<BookingDetailDao> ofResult = Optional.of(bookingDetailDao1);

        when(bookingDetailRepository.findByBookingIdAndFamilyId((Long) any(), (Long) any())).thenReturn(ofResult);
        when(bookingDetailRepository.findAll()).thenReturn(bookingDetailDaoList);

        ResponseEntity<Object> actualAllBookingDetails = bookingDetailService.getAllBookingDetails();

        assertEquals(HttpStatus.OK, actualAllBookingDetails.getStatusCode());

        Object data = ((ApiResponse<Object>) actualAllBookingDetails.getBody()).getData();

        assertEquals(1, ((Collection<BookingDetailDto>) data).size());

        ApiResponseStatus status = ((ApiResponse<Object>) actualAllBookingDetails.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());

        verify(bookingDetailRepository).findAll();
        verify(bookingDetailRepository).findByBookingIdAndFamilyId((Long) any(), (Long) any());
    }

    @Test
    void updateBookingDetailByIdSuccess_Test() {
        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
        healthFacilityDao.setFacilityName("Facility Name");
        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao.setId(123L);
        healthFacilityDao.setProfile(new ProfileDao());
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

        BookingDao bookingDao = new BookingDao();
        bookingDao.setBookingDetailDaos(new ArrayList<>());
        bookingDao.setBookingPass(1);
        bookingDao.setId(123L);
        bookingDao.setSchedule(scheduleDao);
        bookingDao.setUser(userDao);

        UserDao userDao1 = new UserDao();
        userDao1.setBookingDaoList(new ArrayList<>());
        userDao1.setId(123L);
        userDao1.setName("Name");
        userDao1.setProfile(new ProfileDao());

        ProfileDao profileDao1 = new ProfileDao();
        profileDao1.setHealthFacilityDaoList(new ArrayList<>());
        profileDao1.setRole(AppConstant.ProfileRole.USER);
        profileDao1.setUser(userDao1);
        profileDao1.setUserId(123L);

        FamilyDao familyDao = new FamilyDao();
        familyDao.setBookingDaoList(new ArrayList<>());
        familyDao.setId(123L);
        familyDao.setNIK("NIK");
        familyDao.setProfile(profileDao1);

        BookingDetailDao bookingDetailDao = new BookingDetailDao();
        bookingDetailDao.setBooking(bookingDao);
        bookingDetailDao.setBookingId(123L);
        bookingDetailDao.setBookingStatus(AppConstant.BookingStatus.PENDING);
        bookingDetailDao.setFamily(familyDao);
        bookingDetailDao.setFamilyId(123L);

        Optional<BookingDetailDao> ofResult = Optional.of(bookingDetailDao);

        ProfileDao profileDao2 = new ProfileDao();
        profileDao2.setFamilyDaoList(new ArrayList<>());
        profileDao2.setHealthFacilityDaoList(new ArrayList<>());
        profileDao2.setRole(AppConstant.ProfileRole.USER);
        profileDao2.setUser(new UserDao());
        profileDao2.setUserId(123L);

        HealthFacilityDao healthFacilityDao1 = new HealthFacilityDao();
        healthFacilityDao1.setFacilityName("Facility Name");
        healthFacilityDao1.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao1.setId(123L);
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

        UserDao userDao2 = new UserDao();
        userDao2.setBookingDaoList(new ArrayList<>());
        userDao2.setId(123L);
        userDao2.setName("Name");
        userDao2.setProfile(new ProfileDao());

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

        BookingDao bookingDao1 = new BookingDao();
        bookingDao1.setBookingDetailDaos(new ArrayList<>());
        bookingDao1.setBookingPass(1);
        bookingDao1.setId(123L);
        bookingDao1.setSchedule(scheduleDao1);
        bookingDao1.setUser(userDao3);

        ProfileDao profileDao4 = new ProfileDao();
        profileDao4.setFamilyDaoList(new ArrayList<>());
        profileDao4.setHealthFacilityDaoList(new ArrayList<>());
        profileDao4.setRole(AppConstant.ProfileRole.USER);
        profileDao4.setUser(new UserDao());
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

        FamilyDao familyDao1 = new FamilyDao();
        familyDao1.setBookingDaoList(new ArrayList<>());
        familyDao1.setId(123L);
        familyDao1.setNIK("NIK");
        familyDao1.setProfile(profileDao5);

        BookingDetailDao bookingDetailDao1 = new BookingDetailDao();
        bookingDetailDao1.setBooking(bookingDao1);
        bookingDetailDao1.setBookingId(123L);
        bookingDetailDao1.setBookingStatus(AppConstant.BookingStatus.PENDING);
        bookingDetailDao1.setFamily(familyDao1);
        bookingDetailDao1.setFamilyId(123L);

        when(bookingDetailRepository.save((BookingDetailDao) any())).thenReturn(bookingDetailDao1);
        when(bookingDetailRepository.findByBookingIdAndFamilyId((Long) any(), (Long) any())).thenReturn(ofResult);

        ResponseEntity<Object> actualUpdateBookingDetailByIdResult = bookingDetailService.updateBookingDetailById(123L,
                123L, new BookingDetailDto());

        assertEquals(HttpStatus.OK, actualUpdateBookingDetailByIdResult.getStatusCode());

        Object data = ((ApiResponse<Object>) actualUpdateBookingDetailByIdResult.getBody()).getData();

        ApiResponseStatus status = ((ApiResponse<Object>) actualUpdateBookingDetailByIdResult.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());
        assertEquals(123L, ((BookingDetailDto) data).getBookingId().longValue());
        assertEquals(123L, ((BookingDetailDto) data).getFamilyId().longValue());

        verify(bookingDetailRepository).save((BookingDetailDao) any());
        verify(bookingDetailRepository).findByBookingIdAndFamilyId((Long) any(), (Long) any());
    }

    @Test
    void deleteBookingDetailByIdSuccess_Test() {
        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
        healthFacilityDao.setFacilityName("Facility Name");
        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao.setId(123L);
        healthFacilityDao.setProfile(new ProfileDao());
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

        BookingDao bookingDao = new BookingDao();
        bookingDao.setBookingDetailDaos(new ArrayList<>());
        bookingDao.setBookingPass(1);
        bookingDao.setId(123L);
        bookingDao.setSchedule(scheduleDao);
        bookingDao.setUser(userDao);

        UserDao userDao1 = new UserDao();
        userDao1.setBookingDaoList(new ArrayList<>());
        userDao1.setId(123L);
        userDao1.setName("Name");
        userDao1.setProfile(new ProfileDao());

        ProfileDao profileDao1 = new ProfileDao();
        profileDao1.setFamilyDaoList(new ArrayList<>());
        profileDao1.setHealthFacilityDaoList(new ArrayList<>());
        profileDao1.setRole(AppConstant.ProfileRole.USER);
        profileDao1.setUser(userDao1);
        profileDao1.setUserId(123L);

        FamilyDao familyDao = new FamilyDao();
        familyDao.setBookingDaoList(new ArrayList<>());
        familyDao.setId(123L);
        familyDao.setNIK("NIK");
        familyDao.setProfile(profileDao1);

        BookingDetailDao bookingDetailDao = new BookingDetailDao();
        bookingDetailDao.setBooking(bookingDao);
        bookingDetailDao.setBookingId(123L);
        bookingDetailDao.setBookingStatus(AppConstant.BookingStatus.PENDING);
        bookingDetailDao.setFamily(familyDao);
        bookingDetailDao.setFamilyId(123L);

        Optional<BookingDetailDao> ofResult = Optional.of(bookingDetailDao);
        doNothing().when(bookingDetailRepository).delete((BookingDetailDao) any());
        when(bookingDetailRepository.findByBookingIdAndFamilyId((Long) any(), (Long) any())).thenReturn(ofResult);

        ResponseEntity<Object> actualDeleteBookingDetailByIdResult = bookingDetailService.deleteBookingDetailById(123L,
                123L);

        assertEquals(HttpStatus.OK, actualDeleteBookingDetailByIdResult.getStatusCode());

        ApiResponseStatus status = ((ApiResponse<Object>) actualDeleteBookingDetailByIdResult.getBody()).getStatus();

        assertEquals("SUCCESS", status.getCode());

        verify(bookingDetailRepository).findByBookingIdAndFamilyId((Long) any(), (Long) any());
        verify(bookingDetailRepository).delete((BookingDetailDao) any());
    }
}

