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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

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

    @Test
    void createBookingDetail_Test() {

        FamilyDao familyDao = FamilyDao.builder()
                .id(123L)
                .NIK("NIK")
                .build();

        Optional<FamilyDao> ofResult = Optional.of(familyDao);
        when(this.familyRepository.findById((Long) any())).thenReturn(ofResult);


        BookingDao bookingDao = BookingDao.builder()
                .id(123L)
                .bookingDate(LocalDateTime.of(1, 1, 1, 1, 1, 1))
                .build();

        Optional<BookingDao> ofResult1 = Optional.of(bookingDao);
        when(this.bookingRepository.findById((Long) any())).thenReturn(ofResult1);

        BookingDetailDao bookingDetailDao = BookingDetailDao.builder()
                .bookingId(123L)
                .bookingStatus(AppConstant.BookingStatus.PENDING)
                .build();

        when(this.bookingDetailRepository.save(any())).thenReturn(bookingDetailDao);

        ResponseEntity<Object> actualCreateBookingDetailResult = this.bookingDetailService
                .createBookingDetail(new BookingDetailDto());

        assertEquals(HttpStatus.OK, actualCreateBookingDetailResult.getStatusCode());

        Object data = ((ApiResponse<Object>) actualCreateBookingDetailResult.getBody()).getData();

        ApiResponseStatus status = ((ApiResponse<Object>) actualCreateBookingDetailResult.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());
    }

    @Test
    void searchBookingDetailById_Test() {
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
        userDao.setPassword("iloveyou");
        userDao.setProfile(profileDao);

        BookingDao bookingDao = new BookingDao();
        bookingDao.setBookingDate(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao.setBookingDetailDaos(new ArrayList<>());
        bookingDao.setId(123L);
        bookingDao.setSchedule(scheduleDao);
        bookingDao.setUser(userDao);

        UserDao userDao1 = new UserDao();
        userDao1.setBookingDaoList(new ArrayList<>());
        userDao1.setId(123L);
        userDao1.setPassword("iloveyou");
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

        HealthFacilityDao healthFacilityDao1 = new HealthFacilityDao();
        healthFacilityDao1.setFacilityName("Facility Name");
        healthFacilityDao1.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao1.setId(123L);
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
        userDao2.setPassword("iloveyou");
        userDao2.setProfile(profileDao2);

        BookingDao bookingDao1 = new BookingDao();
        bookingDao1.setBookingDate(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao1.setBookingDetailDaos(new ArrayList<>());
        bookingDao1.setId(123L);
        bookingDao1.setSchedule(scheduleDao1);
        bookingDao1.setUser(userDao2);

        UserDao userDao3 = new UserDao();
        userDao3.setBookingDaoList(new ArrayList<>());
        userDao3.setId(123L);
        userDao3.setPassword("iloveyou");
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

        Optional<BookingDetailDao> ofResult1 = Optional.of(bookingDetailDao1);

        when(this.bookingDetailRepository.findByBookingId((Long) any())).thenReturn(ofResult);
        when(this.bookingDetailRepository.findByFamilyId((Long) any())).thenReturn(ofResult1);

        ResponseEntity<Object> actualSearchBookingDetailByIdResult = this.bookingDetailService.searchBookingDetailById(123L,
                123L);

        assertEquals(HttpStatus.OK, actualSearchBookingDetailByIdResult.getStatusCode());

        Object data = ((ApiResponse<Object>) actualSearchBookingDetailByIdResult.getBody()).getData();

        ApiResponseStatus status = ((ApiResponse<Object>) actualSearchBookingDetailByIdResult.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());
        assertEquals(AppConstant.BookingStatus.PENDING, ((BookingDetailDto) data).getBookingStatus());
        assertEquals(123L, ((BookingDetailDto) data).getBookingId().longValue());
        assertEquals(123L, ((BookingDetailDto) data).getFamilyId().longValue());
    }

    @Test
    void getAllBookingDetails_Test() {
        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
        healthFacilityDao.setFacilityName("Getting all of Booking Details");
        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao.setId(123L);
        healthFacilityDao.setProfile(new ProfileDao());
        healthFacilityDao.setScheduleDaoList(new ArrayList<>());

        VaccineTypeDao vaccineTypeDao = new VaccineTypeDao();
        vaccineTypeDao.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao.setId(123L);
        vaccineTypeDao.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao.setVaccineName("Getting all of Booking Details");

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
        userDao.setPassword("iloveyou");
        userDao.setProfile(profileDao);

        BookingDao bookingDao = new BookingDao();
        bookingDao.setBookingDate(LocalDateTime.of(1, 1, 1, 1, 1));
        bookingDao.setBookingDetailDaos(new ArrayList<>());
        bookingDao.setId(123L);
        bookingDao.setSchedule(scheduleDao);
        bookingDao.setUser(userDao);

        UserDao userDao1 = new UserDao();
        userDao1.setBookingDaoList(new ArrayList<>());
        userDao1.setId(123L);
        userDao1.setPassword("iloveyou");
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
        familyDao.setNIK("Getting all of Booking Details");
        familyDao.setProfile(profileDao1);

        BookingDetailDao bookingDetailDao = new BookingDetailDao();
        bookingDetailDao.setBooking(bookingDao);
        bookingDetailDao.setBookingId(123L);
        bookingDetailDao.setBookingStatus(AppConstant.BookingStatus.PENDING);
        bookingDetailDao.setFamily(familyDao);
        bookingDetailDao.setFamilyId(123L);

        ArrayList<BookingDetailDao> bookingDetailDaoList = new ArrayList<>();
        bookingDetailDaoList.add(bookingDetailDao);

        when(this.bookingDetailRepository.findAll()).thenReturn(bookingDetailDaoList);

        ResponseEntity<Object> actualAllBookingDetails = this.bookingDetailService.getAllBookingDetails();

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
    }
}

