package com.backend.vaccinebookingsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.common.ApiResponse;
import com.backend.vaccinebookingsystem.domain.common.ApiResponseStatus;
import com.backend.vaccinebookingsystem.domain.dao.FacilityVaccineDao;
import com.backend.vaccinebookingsystem.domain.dao.HealthFacilityDao;
import com.backend.vaccinebookingsystem.domain.dao.HealthFacilityImageDao;
import com.backend.vaccinebookingsystem.domain.dao.ProfileDao;
import com.backend.vaccinebookingsystem.domain.dao.UserDao;
import com.backend.vaccinebookingsystem.domain.dao.VaccineTypeDao;
import com.backend.vaccinebookingsystem.domain.dto.FacilityVaccineDto;
import com.backend.vaccinebookingsystem.domain.dto.HealthFacilityDto;
import com.backend.vaccinebookingsystem.domain.dto.HealthFacilityImageDto;
import com.backend.vaccinebookingsystem.domain.dto.ProfileDto;
import com.backend.vaccinebookingsystem.domain.dto.VaccineTypeDto;
import com.backend.vaccinebookingsystem.repository.HealthFacilityRepository;
import com.backend.vaccinebookingsystem.repository.StockRepository;
import com.backend.vaccinebookingsystem.repository.VaccineTypeRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = StockService.class)
class StockServiceTest {
    @MockBean
    private HealthFacilityRepository healthFacilityRepository;

    @MockBean
    private StockRepository stockRepository;

    @Autowired
    private StockService stockService;

    @MockBean
    private VaccineTypeRepository vaccineTypeRepository;

    @Test
    void createStock_Test() {
        HealthFacilityImageDao healthFacilityImageDao = new HealthFacilityImageDao();
        healthFacilityImageDao.setBase64("Base64");
        healthFacilityImageDao.setContentType("text/plain");
        healthFacilityImageDao.setCreatedAt(null);
        healthFacilityImageDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityImageDao.setFacility(new HealthFacilityDao());
        healthFacilityImageDao.setFacilityId(123L);
        healthFacilityImageDao.setIsDeleted(true);
        healthFacilityImageDao.setUpdatedAt(null);

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
        healthFacilityDao.setImage(healthFacilityImageDao);
        healthFacilityDao.setIsDeleted(true);
        healthFacilityDao.setOfficeNumber("42");
        healthFacilityDao.setPostalCode(1);
        healthFacilityDao.setProfile(profileDao);
        healthFacilityDao.setProvince("Province");
        healthFacilityDao.setScheduleDaoList(new ArrayList<>());
        healthFacilityDao.setStreetName("Street Name");
        healthFacilityDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao.setVillageName("Village Name");

        HealthFacilityImageDao healthFacilityImageDao1 = new HealthFacilityImageDao();
        healthFacilityImageDao1.setBase64("Base64");
        healthFacilityImageDao1.setContentType("text/plain");
        healthFacilityImageDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityImageDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityImageDao1.setFacility(healthFacilityDao);
        healthFacilityImageDao1.setFacilityId(123L);
        healthFacilityImageDao1.setIsDeleted(true);
        healthFacilityImageDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));

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

        UserDao userDao = new UserDao();
        userDao.setBookingDaoList(new ArrayList<>());
        userDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDao.setEmail("jane.doe@example.org");
        userDao.setId(123L);
        userDao.setIsDeleted(true);
        userDao.setName("Name");
        userDao.setPassword("iloveyou");
        userDao.setProfile(profileDao1);
        userDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDao.setUsername("janedoe");

        ProfileDao profileDao2 = new ProfileDao();
        profileDao2.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        profileDao2.setFamilyDaoList(new ArrayList<>());
        profileDao2.setHealthFacilityDaoList(new ArrayList<>());
        profileDao2.setIsDeleted(true);
        profileDao2.setRole(AppConstant.ProfileRole.USER);
        profileDao2.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao2.setUser(userDao);
        profileDao2.setUserId(123L);

        HealthFacilityDao healthFacilityDao1 = new HealthFacilityDao();
        healthFacilityDao1.setCity("Oxford");
        healthFacilityDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityDao1.setDistrict("District");
        healthFacilityDao1.setFacilityName("Facility Name");
        healthFacilityDao1.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao1.setId(123L);
        healthFacilityDao1.setImage(healthFacilityImageDao1);
        healthFacilityDao1.setIsDeleted(true);
        healthFacilityDao1.setOfficeNumber("42");
        healthFacilityDao1.setPostalCode(1);
        healthFacilityDao1.setProfile(profileDao2);
        healthFacilityDao1.setProvince("Province");
        healthFacilityDao1.setScheduleDaoList(new ArrayList<>());
        healthFacilityDao1.setStreetName("Street Name");
        healthFacilityDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao1.setVillageName("Village Name");
        Optional<HealthFacilityDao> ofResult = Optional.of(healthFacilityDao1);
        when(healthFacilityRepository.findById((Long) any())).thenReturn(ofResult);

        HealthFacilityImageDao healthFacilityImageDao2 = new HealthFacilityImageDao();
        healthFacilityImageDao2.setBase64("Base64");
        healthFacilityImageDao2.setContentType("text/plain");
        healthFacilityImageDao2.setCreatedAt(null);
        healthFacilityImageDao2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityImageDao2.setFacility(new HealthFacilityDao());
        healthFacilityImageDao2.setFacilityId(123L);
        healthFacilityImageDao2.setIsDeleted(true);
        healthFacilityImageDao2.setUpdatedAt(null);

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

        HealthFacilityDao healthFacilityDao2 = new HealthFacilityDao();
        healthFacilityDao2.setCity("Oxford");
        healthFacilityDao2.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityDao2.setDistrict("District");
        healthFacilityDao2.setFacilityName("Facility Name");
        healthFacilityDao2.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao2.setId(123L);
        healthFacilityDao2.setImage(healthFacilityImageDao2);
        healthFacilityDao2.setIsDeleted(true);
        healthFacilityDao2.setOfficeNumber("42");
        healthFacilityDao2.setPostalCode(1);
        healthFacilityDao2.setProfile(profileDao3);
        healthFacilityDao2.setProvince("Province");
        healthFacilityDao2.setScheduleDaoList(new ArrayList<>());
        healthFacilityDao2.setStreetName("Street Name");
        healthFacilityDao2.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao2.setVillageName("Village Name");

        HealthFacilityImageDao healthFacilityImageDao3 = new HealthFacilityImageDao();
        healthFacilityImageDao3.setBase64("Base64");
        healthFacilityImageDao3.setContentType("text/plain");
        healthFacilityImageDao3.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityImageDao3.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityImageDao3.setFacility(healthFacilityDao2);
        healthFacilityImageDao3.setFacilityId(123L);
        healthFacilityImageDao3.setIsDeleted(true);
        healthFacilityImageDao3.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));

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

        UserDao userDao1 = new UserDao();
        userDao1.setBookingDaoList(new ArrayList<>());
        userDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDao1.setEmail("jane.doe@example.org");
        userDao1.setId(123L);
        userDao1.setIsDeleted(true);
        userDao1.setName("Name");
        userDao1.setPassword("iloveyou");
        userDao1.setProfile(profileDao4);
        userDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDao1.setUsername("janedoe");

        ProfileDao profileDao5 = new ProfileDao();
        profileDao5.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao5.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        profileDao5.setFamilyDaoList(new ArrayList<>());
        profileDao5.setHealthFacilityDaoList(new ArrayList<>());
        profileDao5.setIsDeleted(true);
        profileDao5.setRole(AppConstant.ProfileRole.USER);
        profileDao5.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao5.setUser(userDao1);
        profileDao5.setUserId(123L);

        HealthFacilityDao healthFacilityDao3 = new HealthFacilityDao();
        healthFacilityDao3.setCity("Oxford");
        healthFacilityDao3.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao3.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityDao3.setDistrict("District");
        healthFacilityDao3.setFacilityName("Facility Name");
        healthFacilityDao3.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao3.setId(123L);
        healthFacilityDao3.setImage(healthFacilityImageDao3);
        healthFacilityDao3.setIsDeleted(true);
        healthFacilityDao3.setOfficeNumber("42");
        healthFacilityDao3.setPostalCode(1);
        healthFacilityDao3.setProfile(profileDao5);
        healthFacilityDao3.setProvince("Province");
        healthFacilityDao3.setScheduleDaoList(new ArrayList<>());
        healthFacilityDao3.setStreetName("Street Name");
        healthFacilityDao3.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao3.setVillageName("Village Name");

        VaccineTypeDao vaccineTypeDao = new VaccineTypeDao();
        vaccineTypeDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        vaccineTypeDao.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao.setId(123L);
        vaccineTypeDao.setIsDeleted(true);
        vaccineTypeDao.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao.setVaccineName("Vaccine Name");

        FacilityVaccineDao facilityVaccineDao = new FacilityVaccineDao();
        facilityVaccineDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        facilityVaccineDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        facilityVaccineDao.setFacility(healthFacilityDao3);
        facilityVaccineDao.setFacilityId(123L);
        facilityVaccineDao.setIsDeleted(true);
        facilityVaccineDao.setStock(1);
        facilityVaccineDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        facilityVaccineDao.setVaccine(vaccineTypeDao);
        facilityVaccineDao.setVaccineId(123L);

        HealthFacilityDao healthFacilityDao4 = new HealthFacilityDao();
        healthFacilityDao4.setCity("Oxford");
        healthFacilityDao4.setCreatedAt(null);
        healthFacilityDao4.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityDao4.setDistrict("District");
        healthFacilityDao4.setFacilityName("Facility Name");
        healthFacilityDao4.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao4.setId(123L);
        healthFacilityDao4.setImage(new HealthFacilityImageDao());
        healthFacilityDao4.setIsDeleted(true);
        healthFacilityDao4.setOfficeNumber("42");
        healthFacilityDao4.setPostalCode(1);
        healthFacilityDao4.setProfile(new ProfileDao());
        healthFacilityDao4.setProvince("Province");
        healthFacilityDao4.setScheduleDaoList(new ArrayList<>());
        healthFacilityDao4.setStreetName("Street Name");
        healthFacilityDao4.setUpdatedAt(null);
        healthFacilityDao4.setVillageName("Village Name");

        HealthFacilityImageDao healthFacilityImageDao4 = new HealthFacilityImageDao();
        healthFacilityImageDao4.setBase64("Base64");
        healthFacilityImageDao4.setContentType("text/plain");
        healthFacilityImageDao4.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityImageDao4.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityImageDao4.setFacility(healthFacilityDao4);
        healthFacilityImageDao4.setFacilityId(123L);
        healthFacilityImageDao4.setIsDeleted(true);
        healthFacilityImageDao4.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));

        UserDao userDao2 = new UserDao();
        userDao2.setBookingDaoList(new ArrayList<>());
        userDao2.setCreatedAt(null);
        userDao2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDao2.setEmail("jane.doe@example.org");
        userDao2.setId(123L);
        userDao2.setIsDeleted(true);
        userDao2.setName("Name");
        userDao2.setPassword("iloveyou");
        userDao2.setProfile(new ProfileDao());
        userDao2.setUpdatedAt(null);
        userDao2.setUsername("janedoe");

        ProfileDao profileDao6 = new ProfileDao();
        profileDao6.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao6.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        profileDao6.setFamilyDaoList(new ArrayList<>());
        profileDao6.setHealthFacilityDaoList(new ArrayList<>());
        profileDao6.setIsDeleted(true);
        profileDao6.setRole(AppConstant.ProfileRole.USER);
        profileDao6.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        profileDao6.setUser(userDao2);
        profileDao6.setUserId(123L);

        HealthFacilityDao healthFacilityDao5 = new HealthFacilityDao();
        healthFacilityDao5.setCity("Oxford");
        healthFacilityDao5.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao5.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityDao5.setDistrict("District");
        healthFacilityDao5.setFacilityName("Facility Name");
        healthFacilityDao5.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao5.setId(123L);
        healthFacilityDao5.setImage(healthFacilityImageDao4);
        healthFacilityDao5.setIsDeleted(true);
        healthFacilityDao5.setOfficeNumber("42");
        healthFacilityDao5.setPostalCode(1);
        healthFacilityDao5.setProfile(profileDao6);
        healthFacilityDao5.setProvince("Province");
        healthFacilityDao5.setScheduleDaoList(new ArrayList<>());
        healthFacilityDao5.setStreetName("Street Name");
        healthFacilityDao5.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao5.setVillageName("Village Name");

        VaccineTypeDao vaccineTypeDao1 = new VaccineTypeDao();
        vaccineTypeDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        vaccineTypeDao1.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao1.setId(123L);
        vaccineTypeDao1.setIsDeleted(true);
        vaccineTypeDao1.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao1.setVaccineName("Vaccine Name");

        FacilityVaccineDao facilityVaccineDao1 = new FacilityVaccineDao();
        facilityVaccineDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        facilityVaccineDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        facilityVaccineDao1.setFacility(healthFacilityDao5);
        facilityVaccineDao1.setFacilityId(123L);
        facilityVaccineDao1.setIsDeleted(true);
        facilityVaccineDao1.setStock(1);
        facilityVaccineDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        facilityVaccineDao1.setVaccine(vaccineTypeDao1);
        facilityVaccineDao1.setVaccineId(123L);

        Optional<FacilityVaccineDao> ofResult1 = Optional.of(facilityVaccineDao1);
        when(stockRepository.save((FacilityVaccineDao) any())).thenReturn(facilityVaccineDao);
        when(stockRepository.findByFacilityIdAndAndVaccineId((Long) any(), (Long) any())).thenReturn(ofResult1);

        VaccineTypeDao vaccineTypeDao2 = new VaccineTypeDao();
        vaccineTypeDao2.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        vaccineTypeDao2.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao2.setId(123L);
        vaccineTypeDao2.setIsDeleted(true);
        vaccineTypeDao2.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao2.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao2.setVaccineName("Vaccine Name");

        Optional<VaccineTypeDao> ofResult2 = Optional.of(vaccineTypeDao2);
        when(vaccineTypeRepository.findById((Long) any())).thenReturn(ofResult2);

        ResponseEntity<Object> actualCreateStockResult = stockService.createStock(new FacilityVaccineDto());

        assertEquals(HttpStatus.OK, actualCreateStockResult.getStatusCode());

        Object data = ((ApiResponse<Object>) actualCreateStockResult.getBody()).getData();

        ApiResponseStatus status = ((ApiResponse<Object>) actualCreateStockResult.getBody()).getStatus();

        assertEquals(123L, ((FacilityVaccineDto) data).getVaccineId().longValue());
        assertEquals(123L, ((FacilityVaccineDto) data).getFacilityId().longValue());
        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());
        assertNull(((FacilityVaccineDto) data).getStock());

        HealthFacilityDto facility = ((FacilityVaccineDto) data).getFacility();
        assertEquals(1, facility.getPostalCode().intValue());

        VaccineTypeDto vaccine = ((FacilityVaccineDto) data).getVaccine();
        assertEquals(123L, vaccine.getId().longValue());
        assertEquals("Village Name", facility.getVillageName());
        assertEquals("42", facility.getOfficeNumber());
        assertEquals(123L, facility.getId().longValue());
        assertEquals("District", facility.getDistrict());
        assertEquals("Facility Name", facility.getFacilityName());
        assertEquals("Vaccine Name", vaccine.getVaccineName());
        assertEquals("Province", facility.getProvince());
        assertEquals("Street Name", facility.getStreetName());
        assertEquals("Oxford", facility.getCity());

        ProfileDto profile = facility.getProfile();
        assertEquals(AppConstant.ProfileRole.USER, profile.getRole());

        HealthFacilityImageDto image = facility.getImage();
        assertEquals("text/plain", image.getContentType());
        assertEquals(123L, image.getFacilityId().longValue());
        assertEquals(123L, profile.getUserId().longValue());
        assertEquals("Base64", image.getBase64());

        verify(stockRepository).save((FacilityVaccineDao) any());
        verify(stockRepository).findByFacilityIdAndAndVaccineId((Long) any(), (Long) any());
    }

    @Test
    void searchStockById_Test() {
        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
        healthFacilityDao.setCity("Oxford");
        healthFacilityDao.setCreatedAt(null);
        healthFacilityDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityDao.setDistrict("District");
        healthFacilityDao.setFacilityName("Facility Name");
        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao.setId(123L);
        healthFacilityDao.setImage(new HealthFacilityImageDao());
        healthFacilityDao.setIsDeleted(true);
        healthFacilityDao.setOfficeNumber("42");
        healthFacilityDao.setPostalCode(1);
        healthFacilityDao.setProfile(new ProfileDao());
        healthFacilityDao.setProvince("Province");
        healthFacilityDao.setScheduleDaoList(new ArrayList<>());
        healthFacilityDao.setStreetName("Street Name");
        healthFacilityDao.setUpdatedAt(null);
        healthFacilityDao.setVillageName("Village Name");

        HealthFacilityImageDao healthFacilityImageDao = new HealthFacilityImageDao();
        healthFacilityImageDao.setBase64("Base64");
        healthFacilityImageDao.setContentType("text/plain");
        healthFacilityImageDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityImageDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityImageDao.setFacility(healthFacilityDao);
        healthFacilityImageDao.setFacilityId(123L);
        healthFacilityImageDao.setIsDeleted(true);
        healthFacilityImageDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));

        UserDao userDao = new UserDao();
        userDao.setBookingDaoList(new ArrayList<>());
        userDao.setCreatedAt(null);
        userDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDao.setEmail("jane.doe@example.org");
        userDao.setId(123L);
        userDao.setIsDeleted(true);
        userDao.setName("Name");
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

        HealthFacilityDao healthFacilityDao1 = new HealthFacilityDao();
        healthFacilityDao1.setCity("Oxford");
        healthFacilityDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityDao1.setDistrict("District");
        healthFacilityDao1.setFacilityName("Facility Name");
        healthFacilityDao1.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao1.setId(123L);
        healthFacilityDao1.setImage(healthFacilityImageDao);
        healthFacilityDao1.setIsDeleted(true);
        healthFacilityDao1.setOfficeNumber("42");
        healthFacilityDao1.setPostalCode(1);
        healthFacilityDao1.setProfile(profileDao);
        healthFacilityDao1.setProvince("Province");
        healthFacilityDao1.setScheduleDaoList(new ArrayList<>());
        healthFacilityDao1.setStreetName("Street Name");
        healthFacilityDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao1.setVillageName("Village Name");

        VaccineTypeDao vaccineTypeDao = new VaccineTypeDao();
        vaccineTypeDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        vaccineTypeDao.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao.setId(123L);
        vaccineTypeDao.setIsDeleted(true);
        vaccineTypeDao.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao.setVaccineName("Vaccine Name");

        FacilityVaccineDao facilityVaccineDao = new FacilityVaccineDao();
        facilityVaccineDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        facilityVaccineDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        facilityVaccineDao.setFacility(healthFacilityDao1);
        facilityVaccineDao.setFacilityId(123L);
        facilityVaccineDao.setIsDeleted(true);
        facilityVaccineDao.setStock(1);
        facilityVaccineDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        facilityVaccineDao.setVaccine(vaccineTypeDao);
        facilityVaccineDao.setVaccineId(123L);
        Optional<FacilityVaccineDao> ofResult = Optional.of(facilityVaccineDao);
        when(stockRepository.findByFacilityIdAndAndVaccineId((Long) any(), (Long) any())).thenReturn(ofResult);
        ResponseEntity<Object> actualSearchStockByIdResult = stockService.searchStockById(123L, 123L);
        assertTrue(actualSearchStockByIdResult.hasBody());
        assertTrue(actualSearchStockByIdResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualSearchStockByIdResult.getStatusCode());
        Object data = ((ApiResponse<Object>) actualSearchStockByIdResult.getBody()).getData();
        assertTrue(data instanceof FacilityVaccineDto);
        ApiResponseStatus status = ((ApiResponse<Object>) actualSearchStockByIdResult.getBody()).getStatus();
        assertEquals("Success", status.getMessage());
        assertEquals(123L, ((FacilityVaccineDto) data).getVaccineId().longValue());
        assertEquals(123L, ((FacilityVaccineDto) data).getFacilityId().longValue());
        assertEquals("SUCCESS", status.getCode());
        assertEquals(1, ((FacilityVaccineDto) data).getStock().intValue());
        HealthFacilityDto facility = ((FacilityVaccineDto) data).getFacility();
        assertEquals(1, facility.getPostalCode().intValue());
        VaccineTypeDto vaccine = ((FacilityVaccineDto) data).getVaccine();
        assertEquals(123L, vaccine.getId().longValue());
        assertEquals("Village Name", facility.getVillageName());
        assertEquals("42", facility.getOfficeNumber());
        assertEquals(123L, facility.getId().longValue());
        assertEquals("District", facility.getDistrict());
        assertEquals("Facility Name", facility.getFacilityName());
        assertEquals("Vaccine Name", vaccine.getVaccineName());
        assertEquals("Province", facility.getProvince());
        assertEquals("Street Name", facility.getStreetName());
        assertEquals("Oxford", facility.getCity());
        ProfileDto profile = facility.getProfile();
        assertEquals(AppConstant.ProfileRole.USER, profile.getRole());
        HealthFacilityImageDto image = facility.getImage();
        assertEquals("text/plain", image.getContentType());
        assertEquals(123L, image.getFacilityId().longValue());
        assertEquals(123L, profile.getUserId().longValue());
        assertEquals("Base64", image.getBase64());
        verify(stockRepository).findByFacilityIdAndAndVaccineId((Long) any(), (Long) any());
    }

    /**
     * Method under test: {@link StockService#getAllStocks()}
     */
    @Test
    void getAllStocks_Test() {
        ArrayList<FacilityVaccineDao> facilityVaccineDaoList = new ArrayList<>();
        when(stockRepository.findAll()).thenReturn(facilityVaccineDaoList);
        ResponseEntity<Object> actualAllStocks = stockService.getAllStocks();
        assertTrue(actualAllStocks.hasBody());
        assertTrue(actualAllStocks.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualAllStocks.getStatusCode());
        assertEquals(facilityVaccineDaoList, ((ApiResponse<Object>) actualAllStocks.getBody()).getData());
        ApiResponseStatus status = ((ApiResponse<Object>) actualAllStocks.getBody()).getStatus();
        assertEquals("Success", status.getMessage());
        assertEquals("SUCCESS", status.getCode());
        verify(stockRepository).findAll();
    }

    /**
     * Method under test: {@link StockService#getAllStocks()}
     */
    @Test
    void getAllStocks_Test2() {
        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
        healthFacilityDao.setCity("Oxford");
        healthFacilityDao.setCreatedAt(null);
        healthFacilityDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityDao.setDistrict("Getting all of Stocks");
        healthFacilityDao.setFacilityName("Getting all of Stocks");
        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao.setId(123L);
        healthFacilityDao.setImage(new HealthFacilityImageDao());
        healthFacilityDao.setIsDeleted(true);
        healthFacilityDao.setOfficeNumber("42");
        healthFacilityDao.setPostalCode(1);
        healthFacilityDao.setProfile(new ProfileDao());
        healthFacilityDao.setProvince("Getting all of Stocks");
        healthFacilityDao.setScheduleDaoList(new ArrayList<>());
        healthFacilityDao.setStreetName("Getting all of Stocks");
        healthFacilityDao.setUpdatedAt(null);
        healthFacilityDao.setVillageName("Getting all of Stocks");

        HealthFacilityImageDao healthFacilityImageDao = new HealthFacilityImageDao();
        healthFacilityImageDao.setBase64("Getting all of Stocks");
        healthFacilityImageDao.setContentType("text/plain");
        healthFacilityImageDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityImageDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityImageDao.setFacility(healthFacilityDao);
        healthFacilityImageDao.setFacilityId(123L);
        healthFacilityImageDao.setIsDeleted(true);
        healthFacilityImageDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));

        UserDao userDao = new UserDao();
        userDao.setBookingDaoList(new ArrayList<>());
        userDao.setCreatedAt(null);
        userDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDao.setEmail("jane.doe@example.org");
        userDao.setId(123L);
        userDao.setIsDeleted(true);
        userDao.setName("Getting all of Stocks");
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

        HealthFacilityDao healthFacilityDao1 = new HealthFacilityDao();
        healthFacilityDao1.setCity("Oxford");
        healthFacilityDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityDao1.setDistrict("Getting all of Stocks");
        healthFacilityDao1.setFacilityName("Getting all of Stocks");
        healthFacilityDao1.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao1.setId(123L);
        healthFacilityDao1.setImage(healthFacilityImageDao);
        healthFacilityDao1.setIsDeleted(true);
        healthFacilityDao1.setOfficeNumber("42");
        healthFacilityDao1.setPostalCode(1);
        healthFacilityDao1.setProfile(profileDao);
        healthFacilityDao1.setProvince("Getting all of Stocks");
        healthFacilityDao1.setScheduleDaoList(new ArrayList<>());
        healthFacilityDao1.setStreetName("Getting all of Stocks");
        healthFacilityDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao1.setVillageName("Getting all of Stocks");

        VaccineTypeDao vaccineTypeDao = new VaccineTypeDao();
        vaccineTypeDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        vaccineTypeDao.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao.setId(123L);
        vaccineTypeDao.setIsDeleted(true);
        vaccineTypeDao.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao.setVaccineName("Getting all of Stocks");

        FacilityVaccineDao facilityVaccineDao = new FacilityVaccineDao();
        facilityVaccineDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        facilityVaccineDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        facilityVaccineDao.setFacility(healthFacilityDao1);
        facilityVaccineDao.setFacilityId(123L);
        facilityVaccineDao.setIsDeleted(true);
        facilityVaccineDao.setStock(1);
        facilityVaccineDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        facilityVaccineDao.setVaccine(vaccineTypeDao);
        facilityVaccineDao.setVaccineId(123L);

        ArrayList<FacilityVaccineDao> facilityVaccineDaoList = new ArrayList<>();
        facilityVaccineDaoList.add(facilityVaccineDao);
        when(stockRepository.findAll()).thenReturn(facilityVaccineDaoList);
        ResponseEntity<Object> actualAllStocks = stockService.getAllStocks();
        assertTrue(actualAllStocks.hasBody());
        assertTrue(actualAllStocks.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualAllStocks.getStatusCode());
        Object data = ((ApiResponse<Object>) actualAllStocks.getBody()).getData();
        assertEquals(1, ((Collection<FacilityVaccineDto>) data).size());
        ApiResponseStatus status = ((ApiResponse<Object>) actualAllStocks.getBody()).getStatus();
        assertEquals("SUCCESS", status.getCode());
        assertEquals("Success", status.getMessage());
        FacilityVaccineDto getResult = ((List<FacilityVaccineDto>) data).get(0);
        assertEquals(1, getResult.getStock().intValue());
        assertEquals(123L, getResult.getVaccineId().longValue());
        assertEquals(123L, getResult.getFacilityId().longValue());
        VaccineTypeDto vaccine = getResult.getVaccine();
        assertEquals("Getting all of Stocks", vaccine.getVaccineName());
        HealthFacilityDto facility = getResult.getFacility();
        assertEquals("Getting all of Stocks", facility.getVillageName());
        assertEquals("Getting all of Stocks", facility.getStreetName());
        assertEquals("Getting all of Stocks", facility.getProvince());
        assertEquals(1, facility.getPostalCode().intValue());
        assertEquals(123L, vaccine.getId().longValue());
        assertEquals("Getting all of Stocks", facility.getDistrict());
        assertEquals("42", facility.getOfficeNumber());
        assertEquals(123L, facility.getId().longValue());
        assertEquals("Getting all of Stocks", facility.getFacilityName());
        assertEquals("Oxford", facility.getCity());
        HealthFacilityImageDto image = facility.getImage();
        assertEquals("Getting all of Stocks", image.getBase64());
        ProfileDto profile = facility.getProfile();
        assertEquals(123L, profile.getUserId().longValue());
        assertEquals(AppConstant.ProfileRole.USER, profile.getRole());
        assertEquals("text/plain", image.getContentType());
        assertEquals(123L, image.getFacilityId().longValue());
        verify(stockRepository).findAll();
    }

    /**
     * Method under test: {@link StockService#updateStockById(Long, Long, FacilityVaccineDto)}
     */
    @Test
    void updateStockById_Test() {
        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
        healthFacilityDao.setCity("Oxford");
        healthFacilityDao.setCreatedAt(null);
        healthFacilityDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityDao.setDistrict("District");
        healthFacilityDao.setFacilityName("Facility Name");
        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao.setId(123L);
        healthFacilityDao.setImage(new HealthFacilityImageDao());
        healthFacilityDao.setIsDeleted(true);
        healthFacilityDao.setOfficeNumber("42");
        healthFacilityDao.setPostalCode(1);
        healthFacilityDao.setProfile(new ProfileDao());
        healthFacilityDao.setProvince("Province");
        healthFacilityDao.setScheduleDaoList(new ArrayList<>());
        healthFacilityDao.setStreetName("Street Name");
        healthFacilityDao.setUpdatedAt(null);
        healthFacilityDao.setVillageName("Village Name");

        HealthFacilityImageDao healthFacilityImageDao = new HealthFacilityImageDao();
        healthFacilityImageDao.setBase64("Base64");
        healthFacilityImageDao.setContentType("text/plain");
        healthFacilityImageDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityImageDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityImageDao.setFacility(healthFacilityDao);
        healthFacilityImageDao.setFacilityId(123L);
        healthFacilityImageDao.setIsDeleted(true);
        healthFacilityImageDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));

        UserDao userDao = new UserDao();
        userDao.setBookingDaoList(new ArrayList<>());
        userDao.setCreatedAt(null);
        userDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDao.setEmail("jane.doe@example.org");
        userDao.setId(123L);
        userDao.setIsDeleted(true);
        userDao.setName("Name");
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

        HealthFacilityDao healthFacilityDao1 = new HealthFacilityDao();
        healthFacilityDao1.setCity("Oxford");
        healthFacilityDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityDao1.setDistrict("District");
        healthFacilityDao1.setFacilityName("Facility Name");
        healthFacilityDao1.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao1.setId(123L);
        healthFacilityDao1.setImage(healthFacilityImageDao);
        healthFacilityDao1.setIsDeleted(true);
        healthFacilityDao1.setOfficeNumber("42");
        healthFacilityDao1.setPostalCode(1);
        healthFacilityDao1.setProfile(profileDao);
        healthFacilityDao1.setProvince("Province");
        healthFacilityDao1.setScheduleDaoList(new ArrayList<>());
        healthFacilityDao1.setStreetName("Street Name");
        healthFacilityDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao1.setVillageName("Village Name");

        VaccineTypeDao vaccineTypeDao = new VaccineTypeDao();
        vaccineTypeDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        vaccineTypeDao.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao.setId(123L);
        vaccineTypeDao.setIsDeleted(true);
        vaccineTypeDao.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao.setVaccineName("Vaccine Name");

        FacilityVaccineDao facilityVaccineDao = new FacilityVaccineDao();
        facilityVaccineDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        facilityVaccineDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        facilityVaccineDao.setFacility(healthFacilityDao1);
        facilityVaccineDao.setFacilityId(123L);
        facilityVaccineDao.setIsDeleted(true);
        facilityVaccineDao.setStock(1);
        facilityVaccineDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        facilityVaccineDao.setVaccine(vaccineTypeDao);
        facilityVaccineDao.setVaccineId(123L);
        Optional<FacilityVaccineDao> ofResult = Optional.of(facilityVaccineDao);

        HealthFacilityImageDao healthFacilityImageDao1 = new HealthFacilityImageDao();
        healthFacilityImageDao1.setBase64("Base64");
        healthFacilityImageDao1.setContentType("text/plain");
        healthFacilityImageDao1.setCreatedAt(null);
        healthFacilityImageDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityImageDao1.setFacility(new HealthFacilityDao());
        healthFacilityImageDao1.setFacilityId(123L);
        healthFacilityImageDao1.setIsDeleted(true);
        healthFacilityImageDao1.setUpdatedAt(null);

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

        HealthFacilityDao healthFacilityDao2 = new HealthFacilityDao();
        healthFacilityDao2.setCity("Oxford");
        healthFacilityDao2.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityDao2.setDistrict("District");
        healthFacilityDao2.setFacilityName("Facility Name");
        healthFacilityDao2.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao2.setId(123L);
        healthFacilityDao2.setImage(healthFacilityImageDao1);
        healthFacilityDao2.setIsDeleted(true);
        healthFacilityDao2.setOfficeNumber("42");
        healthFacilityDao2.setPostalCode(1);
        healthFacilityDao2.setProfile(profileDao1);
        healthFacilityDao2.setProvince("Province");
        healthFacilityDao2.setScheduleDaoList(new ArrayList<>());
        healthFacilityDao2.setStreetName("Street Name");
        healthFacilityDao2.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao2.setVillageName("Village Name");

        HealthFacilityImageDao healthFacilityImageDao2 = new HealthFacilityImageDao();
        healthFacilityImageDao2.setBase64("Base64");
        healthFacilityImageDao2.setContentType("text/plain");
        healthFacilityImageDao2.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityImageDao2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityImageDao2.setFacility(healthFacilityDao2);
        healthFacilityImageDao2.setFacilityId(123L);
        healthFacilityImageDao2.setIsDeleted(true);
        healthFacilityImageDao2.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));

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

        UserDao userDao1 = new UserDao();
        userDao1.setBookingDaoList(new ArrayList<>());
        userDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDao1.setEmail("jane.doe@example.org");
        userDao1.setId(123L);
        userDao1.setIsDeleted(true);
        userDao1.setName("Name");
        userDao1.setPassword("iloveyou");
        userDao1.setProfile(profileDao2);
        userDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
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

        HealthFacilityDao healthFacilityDao3 = new HealthFacilityDao();
        healthFacilityDao3.setCity("Oxford");
        healthFacilityDao3.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao3.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityDao3.setDistrict("District");
        healthFacilityDao3.setFacilityName("Facility Name");
        healthFacilityDao3.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao3.setId(123L);
        healthFacilityDao3.setImage(healthFacilityImageDao2);
        healthFacilityDao3.setIsDeleted(true);
        healthFacilityDao3.setOfficeNumber("42");
        healthFacilityDao3.setPostalCode(1);
        healthFacilityDao3.setProfile(profileDao3);
        healthFacilityDao3.setProvince("Province");
        healthFacilityDao3.setScheduleDaoList(new ArrayList<>());
        healthFacilityDao3.setStreetName("Street Name");
        healthFacilityDao3.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao3.setVillageName("Village Name");

        VaccineTypeDao vaccineTypeDao1 = new VaccineTypeDao();
        vaccineTypeDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        vaccineTypeDao1.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao1.setId(123L);
        vaccineTypeDao1.setIsDeleted(true);
        vaccineTypeDao1.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao1.setVaccineName("Vaccine Name");

        FacilityVaccineDao facilityVaccineDao1 = new FacilityVaccineDao();
        facilityVaccineDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        facilityVaccineDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        facilityVaccineDao1.setFacility(healthFacilityDao3);
        facilityVaccineDao1.setFacilityId(123L);
        facilityVaccineDao1.setIsDeleted(true);
        facilityVaccineDao1.setStock(1);
        facilityVaccineDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        facilityVaccineDao1.setVaccine(vaccineTypeDao1);
        facilityVaccineDao1.setVaccineId(123L);
        when(stockRepository.save((FacilityVaccineDao) any())).thenReturn(facilityVaccineDao1);
        when(stockRepository.findByFacilityIdAndAndVaccineId((Long) any(), (Long) any())).thenReturn(ofResult);
        ResponseEntity<Object> actualUpdateStockByIdResult = stockService.updateStockById(123L, 123L,
                new FacilityVaccineDto());
        assertTrue(actualUpdateStockByIdResult.hasBody());
        assertTrue(actualUpdateStockByIdResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualUpdateStockByIdResult.getStatusCode());
        Object data = ((ApiResponse<Object>) actualUpdateStockByIdResult.getBody()).getData();
        assertTrue(data instanceof FacilityVaccineDto);
        ApiResponseStatus status = ((ApiResponse<Object>) actualUpdateStockByIdResult.getBody()).getStatus();
        assertEquals("Success", status.getMessage());
        assertEquals(123L, ((FacilityVaccineDto) data).getVaccineId().longValue());
        assertEquals(123L, ((FacilityVaccineDto) data).getFacilityId().longValue());
        assertEquals("SUCCESS", status.getCode());
        assertNull(((FacilityVaccineDto) data).getStock());
        HealthFacilityDto facility = ((FacilityVaccineDto) data).getFacility();
        assertEquals(1, facility.getPostalCode().intValue());
        VaccineTypeDto vaccine = ((FacilityVaccineDto) data).getVaccine();
        assertEquals(123L, vaccine.getId().longValue());
        assertEquals("Village Name", facility.getVillageName());
        assertEquals("42", facility.getOfficeNumber());
        assertEquals(123L, facility.getId().longValue());
        assertEquals("District", facility.getDistrict());
        assertEquals("Facility Name", facility.getFacilityName());
        assertEquals("Vaccine Name", vaccine.getVaccineName());
        assertEquals("Province", facility.getProvince());
        assertEquals("Street Name", facility.getStreetName());
        assertEquals("Oxford", facility.getCity());
        ProfileDto profile = facility.getProfile();
        assertEquals(AppConstant.ProfileRole.USER, profile.getRole());
        HealthFacilityImageDto image = facility.getImage();
        assertEquals("text/plain", image.getContentType());
        assertEquals(123L, image.getFacilityId().longValue());
        assertEquals(123L, profile.getUserId().longValue());
        assertEquals("Base64", image.getBase64());
        verify(stockRepository).save((FacilityVaccineDao) any());
        verify(stockRepository).findByFacilityIdAndAndVaccineId((Long) any(), (Long) any());
    }

    /**
     * Method under test: {@link StockService#deleteStockById(Long, Long)}
     */
    @Test
    void deleteStockById_Test() {
        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
        healthFacilityDao.setCity("Oxford");
        healthFacilityDao.setCreatedAt(null);
        healthFacilityDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityDao.setDistrict("District");
        healthFacilityDao.setFacilityName("Facility Name");
        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao.setId(123L);
        healthFacilityDao.setImage(new HealthFacilityImageDao());
        healthFacilityDao.setIsDeleted(true);
        healthFacilityDao.setOfficeNumber("42");
        healthFacilityDao.setPostalCode(1);
        healthFacilityDao.setProfile(new ProfileDao());
        healthFacilityDao.setProvince("Province");
        healthFacilityDao.setScheduleDaoList(new ArrayList<>());
        healthFacilityDao.setStreetName("Street Name");
        healthFacilityDao.setUpdatedAt(null);
        healthFacilityDao.setVillageName("Village Name");

        HealthFacilityImageDao healthFacilityImageDao = new HealthFacilityImageDao();
        healthFacilityImageDao.setBase64("Base64");
        healthFacilityImageDao.setContentType("text/plain");
        healthFacilityImageDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityImageDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityImageDao.setFacility(healthFacilityDao);
        healthFacilityImageDao.setFacilityId(123L);
        healthFacilityImageDao.setIsDeleted(true);
        healthFacilityImageDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));

        UserDao userDao = new UserDao();
        userDao.setBookingDaoList(new ArrayList<>());
        userDao.setCreatedAt(null);
        userDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDao.setEmail("jane.doe@example.org");
        userDao.setId(123L);
        userDao.setIsDeleted(true);
        userDao.setName("Name");
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

        HealthFacilityDao healthFacilityDao1 = new HealthFacilityDao();
        healthFacilityDao1.setCity("Oxford");
        healthFacilityDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityDao1.setDistrict("District");
        healthFacilityDao1.setFacilityName("Facility Name");
        healthFacilityDao1.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao1.setId(123L);
        healthFacilityDao1.setImage(healthFacilityImageDao);
        healthFacilityDao1.setIsDeleted(true);
        healthFacilityDao1.setOfficeNumber("42");
        healthFacilityDao1.setPostalCode(1);
        healthFacilityDao1.setProfile(profileDao);
        healthFacilityDao1.setProvince("Province");
        healthFacilityDao1.setScheduleDaoList(new ArrayList<>());
        healthFacilityDao1.setStreetName("Street Name");
        healthFacilityDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao1.setVillageName("Village Name");

        VaccineTypeDao vaccineTypeDao = new VaccineTypeDao();
        vaccineTypeDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        vaccineTypeDao.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao.setId(123L);
        vaccineTypeDao.setIsDeleted(true);
        vaccineTypeDao.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao.setVaccineName("Vaccine Name");

        FacilityVaccineDao facilityVaccineDao = new FacilityVaccineDao();
        facilityVaccineDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        facilityVaccineDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        facilityVaccineDao.setFacility(healthFacilityDao1);
        facilityVaccineDao.setFacilityId(123L);
        facilityVaccineDao.setIsDeleted(true);
        facilityVaccineDao.setStock(1);
        facilityVaccineDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        facilityVaccineDao.setVaccine(vaccineTypeDao);
        facilityVaccineDao.setVaccineId(123L);
        Optional<FacilityVaccineDao> ofResult = Optional.of(facilityVaccineDao);

        HealthFacilityImageDao healthFacilityImageDao1 = new HealthFacilityImageDao();
        healthFacilityImageDao1.setBase64("Base64");
        healthFacilityImageDao1.setContentType("text/plain");
        healthFacilityImageDao1.setCreatedAt(null);
        healthFacilityImageDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityImageDao1.setFacility(new HealthFacilityDao());
        healthFacilityImageDao1.setFacilityId(123L);
        healthFacilityImageDao1.setIsDeleted(true);
        healthFacilityImageDao1.setUpdatedAt(null);

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

        HealthFacilityDao healthFacilityDao2 = new HealthFacilityDao();
        healthFacilityDao2.setCity("Oxford");
        healthFacilityDao2.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityDao2.setDistrict("District");
        healthFacilityDao2.setFacilityName("Facility Name");
        healthFacilityDao2.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao2.setId(123L);
        healthFacilityDao2.setImage(healthFacilityImageDao1);
        healthFacilityDao2.setIsDeleted(true);
        healthFacilityDao2.setOfficeNumber("42");
        healthFacilityDao2.setPostalCode(1);
        healthFacilityDao2.setProfile(profileDao1);
        healthFacilityDao2.setProvince("Province");
        healthFacilityDao2.setScheduleDaoList(new ArrayList<>());
        healthFacilityDao2.setStreetName("Street Name");
        healthFacilityDao2.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao2.setVillageName("Village Name");

        HealthFacilityImageDao healthFacilityImageDao2 = new HealthFacilityImageDao();
        healthFacilityImageDao2.setBase64("Base64");
        healthFacilityImageDao2.setContentType("text/plain");
        healthFacilityImageDao2.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityImageDao2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityImageDao2.setFacility(healthFacilityDao2);
        healthFacilityImageDao2.setFacilityId(123L);
        healthFacilityImageDao2.setIsDeleted(true);
        healthFacilityImageDao2.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));

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

        UserDao userDao1 = new UserDao();
        userDao1.setBookingDaoList(new ArrayList<>());
        userDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDao1.setEmail("jane.doe@example.org");
        userDao1.setId(123L);
        userDao1.setIsDeleted(true);
        userDao1.setName("Name");
        userDao1.setPassword("iloveyou");
        userDao1.setProfile(profileDao2);
        userDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
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

        HealthFacilityDao healthFacilityDao3 = new HealthFacilityDao();
        healthFacilityDao3.setCity("Oxford");
        healthFacilityDao3.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao3.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        healthFacilityDao3.setDistrict("District");
        healthFacilityDao3.setFacilityName("Facility Name");
        healthFacilityDao3.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao3.setId(123L);
        healthFacilityDao3.setImage(healthFacilityImageDao2);
        healthFacilityDao3.setIsDeleted(true);
        healthFacilityDao3.setOfficeNumber("42");
        healthFacilityDao3.setPostalCode(1);
        healthFacilityDao3.setProfile(profileDao3);
        healthFacilityDao3.setProvince("Province");
        healthFacilityDao3.setScheduleDaoList(new ArrayList<>());
        healthFacilityDao3.setStreetName("Street Name");
        healthFacilityDao3.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        healthFacilityDao3.setVillageName("Village Name");

        VaccineTypeDao vaccineTypeDao1 = new VaccineTypeDao();
        vaccineTypeDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        vaccineTypeDao1.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao1.setId(123L);
        vaccineTypeDao1.setIsDeleted(true);
        vaccineTypeDao1.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        vaccineTypeDao1.setVaccineName("Vaccine Name");

        FacilityVaccineDao facilityVaccineDao1 = new FacilityVaccineDao();
        facilityVaccineDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        facilityVaccineDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        facilityVaccineDao1.setFacility(healthFacilityDao3);
        facilityVaccineDao1.setFacilityId(123L);
        facilityVaccineDao1.setIsDeleted(true);
        facilityVaccineDao1.setStock(1);
        facilityVaccineDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        facilityVaccineDao1.setVaccine(vaccineTypeDao1);
        facilityVaccineDao1.setVaccineId(123L);
        when(stockRepository.save((FacilityVaccineDao) any())).thenReturn(facilityVaccineDao1);
        when(stockRepository.findByFacilityIdAndAndVaccineId((Long) any(), (Long) any())).thenReturn(ofResult);
        ResponseEntity<Object> actualDeleteStockByIdResult = stockService.deleteStockById(123L, 123L);
        assertTrue(actualDeleteStockByIdResult.hasBody());
        assertTrue(actualDeleteStockByIdResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualDeleteStockByIdResult.getStatusCode());
        Object data = ((ApiResponse<Object>) actualDeleteStockByIdResult.getBody()).getData();
        assertTrue(data instanceof FacilityVaccineDto);
        ApiResponseStatus status = ((ApiResponse<Object>) actualDeleteStockByIdResult.getBody()).getStatus();
        assertEquals("Success", status.getMessage());
        assertEquals(123L, ((FacilityVaccineDto) data).getVaccineId().longValue());
        assertEquals(123L, ((FacilityVaccineDto) data).getFacilityId().longValue());
        assertEquals("SUCCESS", status.getCode());
        assertEquals(0, ((FacilityVaccineDto) data).getStock().intValue());
        HealthFacilityDto facility = ((FacilityVaccineDto) data).getFacility();
        assertEquals(1, facility.getPostalCode().intValue());
        VaccineTypeDto vaccine = ((FacilityVaccineDto) data).getVaccine();
        assertEquals(123L, vaccine.getId().longValue());
        assertEquals("Village Name", facility.getVillageName());
        assertEquals("42", facility.getOfficeNumber());
        assertEquals(123L, facility.getId().longValue());
        assertEquals("District", facility.getDistrict());
        assertEquals("Facility Name", facility.getFacilityName());
        assertEquals("Vaccine Name", vaccine.getVaccineName());
        assertEquals("Province", facility.getProvince());
        assertEquals("Street Name", facility.getStreetName());
        assertEquals("Oxford", facility.getCity());
        ProfileDto profile = facility.getProfile();
        assertEquals(AppConstant.ProfileRole.USER, profile.getRole());
        HealthFacilityImageDto image = facility.getImage();
        assertEquals("text/plain", image.getContentType());
        assertEquals(123L, image.getFacilityId().longValue());
        assertEquals(123L, profile.getUserId().longValue());
        assertEquals("Base64", image.getBase64());
        verify(stockRepository).save((FacilityVaccineDao) any());
        verify(stockRepository).findByFacilityIdAndAndVaccineId((Long) any(), (Long) any());
    }
}

