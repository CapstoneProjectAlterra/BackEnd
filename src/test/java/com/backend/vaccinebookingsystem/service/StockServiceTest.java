package com.backend.vaccinebookingsystem.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.common.ApiResponse;
import com.backend.vaccinebookingsystem.domain.common.ApiResponseStatus;
import com.backend.vaccinebookingsystem.domain.dao.FacilityVaccineDao;
import com.backend.vaccinebookingsystem.domain.dao.HealthFacilityDao;
import com.backend.vaccinebookingsystem.domain.dao.ProfileDao;
import com.backend.vaccinebookingsystem.domain.dao.UserDao;
import com.backend.vaccinebookingsystem.domain.dao.VaccineTypeDao;
import com.backend.vaccinebookingsystem.domain.dto.FacilityVaccineDto;
import com.backend.vaccinebookingsystem.repository.HealthFacilityRepository;
import com.backend.vaccinebookingsystem.repository.StockRepository;
import com.backend.vaccinebookingsystem.repository.VaccineTypeRepository;

import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {StockService.class})
@ExtendWith(SpringExtension.class)
class StockServiceTest {
    @MockBean
    private HealthFacilityRepository healthFacilityRepository;

    @MockBean
    private StockRepository stockRepository;

    @Autowired
    private StockService stockService;

    @MockBean
    private VaccineTypeRepository vaccineTypeRepository;

    /**
     * Method under test: {@link StockService#createStock(FacilityVaccineDto)}
     */
    @Test
    void createStockSuccess_Test() {
        VaccineTypeDao vaccineTypeDao = new VaccineTypeDao();
        vaccineTypeDao.setId(123L);
        vaccineTypeDao.setVaccineName("Vaccine Name");

        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
        healthFacilityDao.setId(123L);
        healthFacilityDao.setFacilityName("Facility Name");

        FacilityVaccineDao facilityVaccineDao = new FacilityVaccineDao();
        facilityVaccineDao.setFacilityId(123L);
        facilityVaccineDao.setVaccineId(123L);
        facilityVaccineDao.setStock(1);

        Optional<VaccineTypeDao> ofResult = Optional.of(vaccineTypeDao);
        when(this.vaccineTypeRepository.findById((Long) any())).thenReturn(ofResult);

        Optional<HealthFacilityDao> ofResult1 = Optional.of(healthFacilityDao);
        when(this.healthFacilityRepository.findById((Long) any())).thenReturn(ofResult1);

        when(this.stockRepository.save((FacilityVaccineDao) any())).thenReturn(facilityVaccineDao);

        ResponseEntity<Object> actualCreateStockResult = this.stockService.createStock(new FacilityVaccineDto());

        ApiResponseStatus status = ((ApiResponse<Object>) actualCreateStockResult.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());
        assertEquals(HttpStatus.OK, actualCreateStockResult.getStatusCode());
    }

    /**
     * Method under test: {@link StockService#createStock(FacilityVaccineDto)}
     */
    @Test
    void createStockIsNull_Test() {
        when(this.vaccineTypeRepository.findById((Long) any())).thenReturn(Optional.empty());

        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
        healthFacilityDao.setId(123L);
        healthFacilityDao.setFacilityName("Facility Name");

        VaccineTypeDao vaccineTypeDao = new VaccineTypeDao();
        vaccineTypeDao.setId(123L);
        vaccineTypeDao.setVaccineName("Vaccine Name");

        FacilityVaccineDao facilityVaccineDao = new FacilityVaccineDao();
        facilityVaccineDao.setFacilityId(123L);
        facilityVaccineDao.setStock(1);
        facilityVaccineDao.setVaccineId(123L);

        when(this.stockRepository.save((FacilityVaccineDao) any())).thenReturn(facilityVaccineDao);

        HealthFacilityDao healthFacilityDao1 = new HealthFacilityDao();
        healthFacilityDao1.setFacilityName("Facility Name");
        healthFacilityDao1.setId(123L);

        Optional<HealthFacilityDao> ofResult = Optional.of(healthFacilityDao1);
        when(this.healthFacilityRepository.findById((Long) any())).thenReturn(ofResult);

        ResponseEntity<Object> actualCreateStockResult = this.stockService.createStock(new FacilityVaccineDto());

        assertEquals(HttpStatus.BAD_REQUEST, actualCreateStockResult.getStatusCode());
        assertNull(((ApiResponse<Object>) actualCreateStockResult.getBody()).getData());

        ApiResponseStatus status = ((ApiResponse<Object>) actualCreateStockResult.getBody()).getStatus();
        assertEquals("DATA_NOT_FOUND", status.getCode());
    }

//    @Test
//    void createStockException_Test() {
//        FacilityVaccineDto facilityVaccineDto = FacilityVaccineDto.builder()
//                .facilityId(1L)
//                .vaccineId(1L)
//                .stock(100)
//                .build();
//
//        when(stockRepository.save(any())).thenReturn(NullPointerException.class);
//
//        ResponseEntity<Object> response = stockService.createStock(facilityVaccineDto);
//
//        ApiResponse apiResponse = (ApiResponse) response.getBody();
//
//        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
//    }

    @Test
    void createStockException_Test() {
        FacilityVaccineDao facilityDao = FacilityVaccineDao.builder()
                .facilityId(1L)
                .vaccineId(1L)
                .stock(100)
                .build();

        FacilityVaccineDto vaccineDto = FacilityVaccineDto.builder()
                .facilityId(1L)
                .vaccineId(1L)
                .stock(100)
                .build();

        when(stockRepository.save(any())).thenReturn(facilityDao);

        doThrow(NullPointerException.class).when(stockRepository).save(any());
        assertThrows(Exception.class, () -> stockService.createStock(vaccineDto));
    }

    /**
     * Method under test: {@link StockService#searchStockById(Long, Long)}
     */
//    @Test
//    void testSearchStockById() {
//        UserDao userDao = new UserDao();
//        userDao.setBookingDaoList(new ArrayList<>());
//        userDao.setCreatedAt(null);
//        userDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        userDao.setId(123L);
//        userDao.setIsDeleted(true);
//        userDao.setPassword("iloveyou");
//        userDao.setProfile(new ProfileDao());
//        userDao.setUpdatedAt(null);
//        userDao.setUsername("janedoe");
//
//        ProfileDao profileDao = new ProfileDao();
//        profileDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        profileDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        profileDao.setFamilyDaoList(new ArrayList<>());
//        profileDao.setHealthFacilityDaoList(new ArrayList<>());
//        profileDao.setIsDeleted(true);
//        profileDao.setRole(AppConstant.ProfileRole.USER);
//        profileDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        profileDao.setUser(userDao);
//        profileDao.setUserId(123L);
//
//        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
//        healthFacilityDao.setCity("Oxford");
//        healthFacilityDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        healthFacilityDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        healthFacilityDao.setDistrict("District");
//        healthFacilityDao.setFacilityName("Facility Name");
//        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
//        healthFacilityDao.setId(123L);
//        healthFacilityDao.setImgUrl("https://example.org/example");
//        healthFacilityDao.setIsDeleted(true);
//        healthFacilityDao.setOfficeNumber("42");
//        healthFacilityDao.setPostalCode(1);
//        healthFacilityDao.setProfile(profileDao);
//        healthFacilityDao.setProvince("Province");
//        healthFacilityDao.setScheduleDaoList(new ArrayList<>());
//        healthFacilityDao.setStreetName("Street Name");
//        healthFacilityDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        healthFacilityDao.setVillageName("Village Name");
//
//        VaccineTypeDao vaccineTypeDao = new VaccineTypeDao();
//        vaccineTypeDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        vaccineTypeDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        vaccineTypeDao.setFacilityVaccineDaoList(new ArrayList<>());
//        vaccineTypeDao.setId(123L);
//        vaccineTypeDao.setIsDeleted(true);
//        vaccineTypeDao.setScheduleDaoList(new ArrayList<>());
//        vaccineTypeDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        vaccineTypeDao.setVaccineName("Vaccine Name");
//
//        FacilityVaccineDao facilityVaccineDao = new FacilityVaccineDao();
//        facilityVaccineDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        facilityVaccineDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        facilityVaccineDao.setFacilityId(123L);
//        facilityVaccineDao.setFacilityVaccine(healthFacilityDao);
//        facilityVaccineDao.setIsDeleted(true);
//        facilityVaccineDao.setStock(1);
//        facilityVaccineDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        facilityVaccineDao.setVaccineFacility(vaccineTypeDao);
//        facilityVaccineDao.setVaccineId(123L);
//        Optional<FacilityVaccineDao> ofResult = Optional.of(facilityVaccineDao);
//
//        UserDao userDao1 = new UserDao();
//        userDao1.setBookingDaoList(new ArrayList<>());
//        userDao1.setCreatedAt(null);
//        userDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        userDao1.setId(123L);
//        userDao1.setIsDeleted(true);
//        userDao1.setPassword("iloveyou");
//        userDao1.setProfile(new ProfileDao());
//        userDao1.setUpdatedAt(null);
//        userDao1.setUsername("janedoe");
//
//        ProfileDao profileDao1 = new ProfileDao();
//        profileDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        profileDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        profileDao1.setFamilyDaoList(new ArrayList<>());
//        profileDao1.setHealthFacilityDaoList(new ArrayList<>());
//        profileDao1.setIsDeleted(true);
//        profileDao1.setRole(AppConstant.ProfileRole.USER);
//        profileDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        profileDao1.setUser(userDao1);
//        profileDao1.setUserId(123L);
//
//        HealthFacilityDao healthFacilityDao1 = new HealthFacilityDao();
//        healthFacilityDao1.setCity("Oxford");
//        healthFacilityDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        healthFacilityDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        healthFacilityDao1.setDistrict("District");
//        healthFacilityDao1.setFacilityName("Facility Name");
//        healthFacilityDao1.setFacilityVaccineDaoList(new ArrayList<>());
//        healthFacilityDao1.setId(123L);
//        healthFacilityDao1.setImgUrl("https://example.org/example");
//        healthFacilityDao1.setIsDeleted(true);
//        healthFacilityDao1.setOfficeNumber("42");
//        healthFacilityDao1.setPostalCode(1);
//        healthFacilityDao1.setProfile(profileDao1);
//        healthFacilityDao1.setProvince("Province");
//        healthFacilityDao1.setScheduleDaoList(new ArrayList<>());
//        healthFacilityDao1.setStreetName("Street Name");
//        healthFacilityDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        healthFacilityDao1.setVillageName("Village Name");
//
//        VaccineTypeDao vaccineTypeDao1 = new VaccineTypeDao();
//        vaccineTypeDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        vaccineTypeDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        vaccineTypeDao1.setFacilityVaccineDaoList(new ArrayList<>());
//        vaccineTypeDao1.setId(123L);
//        vaccineTypeDao1.setIsDeleted(true);
//        vaccineTypeDao1.setScheduleDaoList(new ArrayList<>());
//        vaccineTypeDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        vaccineTypeDao1.setVaccineName("Vaccine Name");
//
//        FacilityVaccineDao facilityVaccineDao1 = new FacilityVaccineDao();
//        facilityVaccineDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        facilityVaccineDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        facilityVaccineDao1.setFacilityId(123L);
//        facilityVaccineDao1.setFacilityVaccine(healthFacilityDao1);
//        facilityVaccineDao1.setIsDeleted(true);
//        facilityVaccineDao1.setStock(1);
//        facilityVaccineDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        facilityVaccineDao1.setVaccineFacility(vaccineTypeDao1);
//        facilityVaccineDao1.setVaccineId(123L);
//        Optional<FacilityVaccineDao> ofResult1 = Optional.of(facilityVaccineDao1);
//        when(this.stockRepository.findByFacilityId((Long) any())).thenReturn(ofResult);
//        when(this.stockRepository.findByVaccineId((Long) any())).thenReturn(ofResult1);
//        ResponseEntity<Object> actualSearchStockByIdResult = this.stockService.searchStockById(123L, 123L);
//        assertTrue(actualSearchStockByIdResult.hasBody());
//        assertTrue(actualSearchStockByIdResult.getHeaders().isEmpty());
//        assertEquals(HttpStatus.OK, actualSearchStockByIdResult.getStatusCode());
//        Object data = ((ApiResponse<Object>) actualSearchStockByIdResult.getBody()).getData();
//        assertTrue(data instanceof FacilityVaccineDto);
//        ApiResponseStatus status = ((ApiResponse<Object>) actualSearchStockByIdResult.getBody()).getStatus();
//        assertEquals("SUCCESS", status.getCode());
//        assertEquals(1, ((FacilityVaccineDto) data).getStock().intValue());
//        assertEquals(123L, ((FacilityVaccineDto) data).getFacilityId().longValue());
//        assertEquals(123L, ((FacilityVaccineDto) data).getVaccineId().longValue());
//        assertEquals("Success", status.getMessage());
//        verify(this.stockRepository).findByFacilityId((Long) any());
//        verify(this.stockRepository).findByVaccineId((Long) any());
//    }
//
//    /**
//     * Method under test: {@link StockService#searchStockById(Long, Long)}
//     */
//    @Test
//    void testSearchStockById2() {
//        UserDao userDao = new UserDao();
//        userDao.setBookingDaoList(new ArrayList<>());
//        userDao.setCreatedAt(null);
//        userDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        userDao.setId(123L);
//        userDao.setIsDeleted(true);
//        userDao.setPassword("iloveyou");
//        userDao.setProfile(new ProfileDao());
//        userDao.setUpdatedAt(null);
//        userDao.setUsername("janedoe");
//
//        ProfileDao profileDao = new ProfileDao();
//        profileDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        profileDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        profileDao.setFamilyDaoList(new ArrayList<>());
//        profileDao.setHealthFacilityDaoList(new ArrayList<>());
//        profileDao.setIsDeleted(true);
//        profileDao.setRole(AppConstant.ProfileRole.USER);
//        profileDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        profileDao.setUser(userDao);
//        profileDao.setUserId(123L);
//
//        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
//        healthFacilityDao.setCity("Oxford");
//        healthFacilityDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        healthFacilityDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        healthFacilityDao.setDistrict("District");
//        healthFacilityDao.setFacilityName("Facility Name");
//        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
//        healthFacilityDao.setId(123L);
//        healthFacilityDao.setImgUrl("https://example.org/example");
//        healthFacilityDao.setIsDeleted(true);
//        healthFacilityDao.setOfficeNumber("42");
//        healthFacilityDao.setPostalCode(1);
//        healthFacilityDao.setProfile(profileDao);
//        healthFacilityDao.setProvince("Province");
//        healthFacilityDao.setScheduleDaoList(new ArrayList<>());
//        healthFacilityDao.setStreetName("Street Name");
//        healthFacilityDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        healthFacilityDao.setVillageName("Village Name");
//
//        VaccineTypeDao vaccineTypeDao = new VaccineTypeDao();
//        vaccineTypeDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        vaccineTypeDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        vaccineTypeDao.setFacilityVaccineDaoList(new ArrayList<>());
//        vaccineTypeDao.setId(123L);
//        vaccineTypeDao.setIsDeleted(true);
//        vaccineTypeDao.setScheduleDaoList(new ArrayList<>());
//        vaccineTypeDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        vaccineTypeDao.setVaccineName("Vaccine Name");
//        FacilityVaccineDao facilityVaccineDao = mock(FacilityVaccineDao.class);
//        when(facilityVaccineDao.getStock()).thenReturn(1);
//        when(facilityVaccineDao.getFacilityId()).thenReturn(123L);
//        doNothing().when(facilityVaccineDao).setCreatedAt((LocalDateTime) any());
//        doNothing().when(facilityVaccineDao).setCreatedBy((String) any());
//        doNothing().when(facilityVaccineDao).setIsDeleted((Boolean) any());
//        doNothing().when(facilityVaccineDao).setUpdatedAt((LocalDateTime) any());
//        doNothing().when(facilityVaccineDao).setFacilityId((Long) any());
//        doNothing().when(facilityVaccineDao).setFacilityVaccine((HealthFacilityDao) any());
//        doNothing().when(facilityVaccineDao).setStock((Integer) any());
//        doNothing().when(facilityVaccineDao).setVaccineFacility((VaccineTypeDao) any());
//        doNothing().when(facilityVaccineDao).setVaccineId((Long) any());
//        facilityVaccineDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        facilityVaccineDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        facilityVaccineDao.setFacilityId(123L);
//        facilityVaccineDao.setFacilityVaccine(healthFacilityDao);
//        facilityVaccineDao.setIsDeleted(true);
//        facilityVaccineDao.setStock(1);
//        facilityVaccineDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        facilityVaccineDao.setVaccineFacility(vaccineTypeDao);
//        facilityVaccineDao.setVaccineId(123L);
//        Optional<FacilityVaccineDao> ofResult = Optional.of(facilityVaccineDao);
//
//        UserDao userDao1 = new UserDao();
//        userDao1.setBookingDaoList(new ArrayList<>());
//        userDao1.setCreatedAt(null);
//        userDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        userDao1.setId(123L);
//        userDao1.setIsDeleted(true);
//        userDao1.setPassword("iloveyou");
//        userDao1.setProfile(new ProfileDao());
//        userDao1.setUpdatedAt(null);
//        userDao1.setUsername("janedoe");
//
//        ProfileDao profileDao1 = new ProfileDao();
//        profileDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        profileDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        profileDao1.setFamilyDaoList(new ArrayList<>());
//        profileDao1.setHealthFacilityDaoList(new ArrayList<>());
//        profileDao1.setIsDeleted(true);
//        profileDao1.setRole(AppConstant.ProfileRole.USER);
//        profileDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        profileDao1.setUser(userDao1);
//        profileDao1.setUserId(123L);
//
//        HealthFacilityDao healthFacilityDao1 = new HealthFacilityDao();
//        healthFacilityDao1.setCity("Oxford");
//        healthFacilityDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        healthFacilityDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        healthFacilityDao1.setDistrict("District");
//        healthFacilityDao1.setFacilityName("Facility Name");
//        healthFacilityDao1.setFacilityVaccineDaoList(new ArrayList<>());
//        healthFacilityDao1.setId(123L);
//        healthFacilityDao1.setImgUrl("https://example.org/example");
//        healthFacilityDao1.setIsDeleted(true);
//        healthFacilityDao1.setOfficeNumber("42");
//        healthFacilityDao1.setPostalCode(1);
//        healthFacilityDao1.setProfile(profileDao1);
//        healthFacilityDao1.setProvince("Province");
//        healthFacilityDao1.setScheduleDaoList(new ArrayList<>());
//        healthFacilityDao1.setStreetName("Street Name");
//        healthFacilityDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        healthFacilityDao1.setVillageName("Village Name");
//
//        VaccineTypeDao vaccineTypeDao1 = new VaccineTypeDao();
//        vaccineTypeDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        vaccineTypeDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        vaccineTypeDao1.setFacilityVaccineDaoList(new ArrayList<>());
//        vaccineTypeDao1.setId(123L);
//        vaccineTypeDao1.setIsDeleted(true);
//        vaccineTypeDao1.setScheduleDaoList(new ArrayList<>());
//        vaccineTypeDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        vaccineTypeDao1.setVaccineName("Vaccine Name");
//
//        FacilityVaccineDao facilityVaccineDao1 = new FacilityVaccineDao();
//        facilityVaccineDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        facilityVaccineDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        facilityVaccineDao1.setFacilityId(123L);
//        facilityVaccineDao1.setFacilityVaccine(healthFacilityDao1);
//        facilityVaccineDao1.setIsDeleted(true);
//        facilityVaccineDao1.setStock(1);
//        facilityVaccineDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        facilityVaccineDao1.setVaccineFacility(vaccineTypeDao1);
//        facilityVaccineDao1.setVaccineId(123L);
//        Optional<FacilityVaccineDao> ofResult1 = Optional.of(facilityVaccineDao1);
//        when(this.stockRepository.findByFacilityId((Long) any())).thenReturn(ofResult);
//        when(this.stockRepository.findByVaccineId((Long) any())).thenReturn(ofResult1);
//        ResponseEntity<Object> actualSearchStockByIdResult = this.stockService.searchStockById(123L, 123L);
//        assertTrue(actualSearchStockByIdResult.hasBody());
//        assertTrue(actualSearchStockByIdResult.getHeaders().isEmpty());
//        assertEquals(HttpStatus.OK, actualSearchStockByIdResult.getStatusCode());
//        Object data = ((ApiResponse<Object>) actualSearchStockByIdResult.getBody()).getData();
//        assertTrue(data instanceof FacilityVaccineDto);
//        ApiResponseStatus status = ((ApiResponse<Object>) actualSearchStockByIdResult.getBody()).getStatus();
//        assertEquals("SUCCESS", status.getCode());
//        assertEquals(1, ((FacilityVaccineDto) data).getStock().intValue());
//        assertEquals(123L, ((FacilityVaccineDto) data).getFacilityId().longValue());
//        assertEquals(123L, ((FacilityVaccineDto) data).getVaccineId().longValue());
//        assertEquals("Success", status.getMessage());
//        verify(this.stockRepository).findByFacilityId((Long) any());
//        verify(this.stockRepository).findByVaccineId((Long) any());
//        verify(facilityVaccineDao).getStock();
//        verify(facilityVaccineDao).getFacilityId();
//        verify(facilityVaccineDao).setCreatedAt((LocalDateTime) any());
//        verify(facilityVaccineDao).setCreatedBy((String) any());
//        verify(facilityVaccineDao).setIsDeleted((Boolean) any());
//        verify(facilityVaccineDao).setUpdatedAt((LocalDateTime) any());
//        verify(facilityVaccineDao).setFacilityId((Long) any());
//        verify(facilityVaccineDao).setFacilityVaccine((HealthFacilityDao) any());
//        verify(facilityVaccineDao).setStock((Integer) any());
//        verify(facilityVaccineDao).setVaccineFacility((VaccineTypeDao) any());
//        verify(facilityVaccineDao).setVaccineId((Long) any());
//    }
//
//    /**
//     * Method under test: {@link StockService#getAllStocks()}
//     */
//    @Test
//    void testGetAllStocks() {
//        ArrayList<FacilityVaccineDao> facilityVaccineDaoList = new ArrayList<>();
//        when(this.stockRepository.findAll()).thenReturn(facilityVaccineDaoList);
//        ResponseEntity<Object> actualAllStocks = this.stockService.getAllStocks();
//        assertTrue(actualAllStocks.hasBody());
//        assertTrue(actualAllStocks.getHeaders().isEmpty());
//        assertEquals(HttpStatus.OK, actualAllStocks.getStatusCode());
//        assertEquals(facilityVaccineDaoList, ((ApiResponse<Object>) actualAllStocks.getBody()).getData());
//        ApiResponseStatus status = ((ApiResponse<Object>) actualAllStocks.getBody()).getStatus();
//        assertEquals("Success", status.getMessage());
//        assertEquals("SUCCESS", status.getCode());
//        verify(this.stockRepository).findAll();
//    }
//
//    /**
//     * Method under test: {@link StockService#getAllStocks()}
//     */
//    @Test
//    void testGetAllStocks2() {
//        UserDao userDao = new UserDao();
//        userDao.setBookingDaoList(new ArrayList<>());
//        userDao.setCreatedAt(null);
//        userDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        userDao.setId(123L);
//        userDao.setIsDeleted(true);
//        userDao.setPassword("iloveyou");
//        userDao.setProfile(new ProfileDao());
//        userDao.setUpdatedAt(null);
//        userDao.setUsername("janedoe");
//
//        ProfileDao profileDao = new ProfileDao();
//        profileDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        profileDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        profileDao.setFamilyDaoList(new ArrayList<>());
//        profileDao.setHealthFacilityDaoList(new ArrayList<>());
//        profileDao.setIsDeleted(true);
//        profileDao.setRole(AppConstant.ProfileRole.USER);
//        profileDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        profileDao.setUser(userDao);
//        profileDao.setUserId(123L);
//
//        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
//        healthFacilityDao.setCity("Oxford");
//        healthFacilityDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        healthFacilityDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        healthFacilityDao.setDistrict("Getting all of Stocks");
//        healthFacilityDao.setFacilityName("Getting all of Stocks");
//        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
//        healthFacilityDao.setId(123L);
//        healthFacilityDao.setImgUrl("https://example.org/example");
//        healthFacilityDao.setIsDeleted(true);
//        healthFacilityDao.setOfficeNumber("42");
//        healthFacilityDao.setPostalCode(1);
//        healthFacilityDao.setProfile(profileDao);
//        healthFacilityDao.setProvince("Getting all of Stocks");
//        healthFacilityDao.setScheduleDaoList(new ArrayList<>());
//        healthFacilityDao.setStreetName("Getting all of Stocks");
//        healthFacilityDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        healthFacilityDao.setVillageName("Getting all of Stocks");
//
//        VaccineTypeDao vaccineTypeDao = new VaccineTypeDao();
//        vaccineTypeDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        vaccineTypeDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        vaccineTypeDao.setFacilityVaccineDaoList(new ArrayList<>());
//        vaccineTypeDao.setId(123L);
//        vaccineTypeDao.setIsDeleted(true);
//        vaccineTypeDao.setScheduleDaoList(new ArrayList<>());
//        vaccineTypeDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        vaccineTypeDao.setVaccineName("Getting all of Stocks");
//
//        FacilityVaccineDao facilityVaccineDao = new FacilityVaccineDao();
//        facilityVaccineDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        facilityVaccineDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        facilityVaccineDao.setFacilityId(123L);
//        facilityVaccineDao.setFacilityVaccine(healthFacilityDao);
//        facilityVaccineDao.setIsDeleted(true);
//        facilityVaccineDao.setStock(1);
//        facilityVaccineDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        facilityVaccineDao.setVaccineFacility(vaccineTypeDao);
//        facilityVaccineDao.setVaccineId(123L);
//
//        ArrayList<FacilityVaccineDao> facilityVaccineDaoList = new ArrayList<>();
//        facilityVaccineDaoList.add(facilityVaccineDao);
//        when(this.stockRepository.findAll()).thenReturn(facilityVaccineDaoList);
//        ResponseEntity<Object> actualAllStocks = this.stockService.getAllStocks();
//        assertTrue(actualAllStocks.hasBody());
//        assertTrue(actualAllStocks.getHeaders().isEmpty());
//        assertEquals(HttpStatus.OK, actualAllStocks.getStatusCode());
//        Object data = ((ApiResponse<Object>) actualAllStocks.getBody()).getData();
//        assertEquals(1, ((Collection<FacilityVaccineDto>) data).size());
//        ApiResponseStatus status = ((ApiResponse<Object>) actualAllStocks.getBody()).getStatus();
//        assertEquals("SUCCESS", status.getCode());
//        assertEquals("Success", status.getMessage());
//        FacilityVaccineDto getResult = ((List<FacilityVaccineDto>) data).get(0);
//        assertEquals(1, getResult.getStock().intValue());
//        assertEquals(123L, getResult.getFacilityId().longValue());
//        assertEquals(123L, getResult.getVaccineId().longValue());
//        verify(this.stockRepository).findAll();
//    }
//
//    /**
//     * Method under test: {@link StockService#getAllStocks()}
//     */
//    @Test
//    void testGetAllStocks3() {
//        UserDao userDao = new UserDao();
//        userDao.setBookingDaoList(new ArrayList<>());
//        userDao.setCreatedAt(null);
//        userDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        userDao.setId(123L);
//        userDao.setIsDeleted(true);
//        userDao.setPassword("iloveyou");
//        userDao.setProfile(new ProfileDao());
//        userDao.setUpdatedAt(null);
//        userDao.setUsername("janedoe");
//
//        ProfileDao profileDao = new ProfileDao();
//        profileDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        profileDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        profileDao.setFamilyDaoList(new ArrayList<>());
//        profileDao.setHealthFacilityDaoList(new ArrayList<>());
//        profileDao.setIsDeleted(true);
//        profileDao.setRole(AppConstant.ProfileRole.USER);
//        profileDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        profileDao.setUser(userDao);
//        profileDao.setUserId(123L);
//
//        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
//        healthFacilityDao.setCity("Oxford");
//        healthFacilityDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        healthFacilityDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        healthFacilityDao.setDistrict("Getting all of Stocks");
//        healthFacilityDao.setFacilityName("Getting all of Stocks");
//        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
//        healthFacilityDao.setId(123L);
//        healthFacilityDao.setImgUrl("https://example.org/example");
//        healthFacilityDao.setIsDeleted(true);
//        healthFacilityDao.setOfficeNumber("42");
//        healthFacilityDao.setPostalCode(1);
//        healthFacilityDao.setProfile(profileDao);
//        healthFacilityDao.setProvince("Getting all of Stocks");
//        healthFacilityDao.setScheduleDaoList(new ArrayList<>());
//        healthFacilityDao.setStreetName("Getting all of Stocks");
//        healthFacilityDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        healthFacilityDao.setVillageName("Getting all of Stocks");
//
//        VaccineTypeDao vaccineTypeDao = new VaccineTypeDao();
//        vaccineTypeDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        vaccineTypeDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        vaccineTypeDao.setFacilityVaccineDaoList(new ArrayList<>());
//        vaccineTypeDao.setId(123L);
//        vaccineTypeDao.setIsDeleted(true);
//        vaccineTypeDao.setScheduleDaoList(new ArrayList<>());
//        vaccineTypeDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        vaccineTypeDao.setVaccineName("Getting all of Stocks");
//        FacilityVaccineDao facilityVaccineDao = mock(FacilityVaccineDao.class);
//        when(facilityVaccineDao.getStock()).thenReturn(1);
//        when(facilityVaccineDao.getFacilityId()).thenReturn(123L);
//        when(facilityVaccineDao.getVaccineId()).thenReturn(123L);
//        doNothing().when(facilityVaccineDao).setCreatedAt((LocalDateTime) any());
//        doNothing().when(facilityVaccineDao).setCreatedBy((String) any());
//        doNothing().when(facilityVaccineDao).setIsDeleted((Boolean) any());
//        doNothing().when(facilityVaccineDao).setUpdatedAt((LocalDateTime) any());
//        doNothing().when(facilityVaccineDao).setFacilityId((Long) any());
//        doNothing().when(facilityVaccineDao).setFacilityVaccine((HealthFacilityDao) any());
//        doNothing().when(facilityVaccineDao).setStock((Integer) any());
//        doNothing().when(facilityVaccineDao).setVaccineFacility((VaccineTypeDao) any());
//        doNothing().when(facilityVaccineDao).setVaccineId((Long) any());
//        facilityVaccineDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        facilityVaccineDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        facilityVaccineDao.setFacilityId(123L);
//        facilityVaccineDao.setFacilityVaccine(healthFacilityDao);
//        facilityVaccineDao.setIsDeleted(true);
//        facilityVaccineDao.setStock(1);
//        facilityVaccineDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        facilityVaccineDao.setVaccineFacility(vaccineTypeDao);
//        facilityVaccineDao.setVaccineId(123L);
//
//        ArrayList<FacilityVaccineDao> facilityVaccineDaoList = new ArrayList<>();
//        facilityVaccineDaoList.add(facilityVaccineDao);
//        when(this.stockRepository.findAll()).thenReturn(facilityVaccineDaoList);
//        ResponseEntity<Object> actualAllStocks = this.stockService.getAllStocks();
//        assertTrue(actualAllStocks.hasBody());
//        assertTrue(actualAllStocks.getHeaders().isEmpty());
//        assertEquals(HttpStatus.OK, actualAllStocks.getStatusCode());
//        Object data = ((ApiResponse<Object>) actualAllStocks.getBody()).getData();
//        assertEquals(1, ((Collection<FacilityVaccineDto>) data).size());
//        ApiResponseStatus status = ((ApiResponse<Object>) actualAllStocks.getBody()).getStatus();
//        assertEquals("SUCCESS", status.getCode());
//        assertEquals("Success", status.getMessage());
//        FacilityVaccineDto getResult = ((List<FacilityVaccineDto>) data).get(0);
//        assertEquals(1, getResult.getStock().intValue());
//        assertEquals(123L, getResult.getFacilityId().longValue());
//        assertEquals(123L, getResult.getVaccineId().longValue());
//        verify(this.stockRepository).findAll();
//        verify(facilityVaccineDao).getStock();
//        verify(facilityVaccineDao).getFacilityId();
//        verify(facilityVaccineDao).getVaccineId();
//        verify(facilityVaccineDao).setCreatedAt((LocalDateTime) any());
//        verify(facilityVaccineDao).setCreatedBy((String) any());
//        verify(facilityVaccineDao).setIsDeleted((Boolean) any());
//        verify(facilityVaccineDao).setUpdatedAt((LocalDateTime) any());
//        verify(facilityVaccineDao).setFacilityId((Long) any());
//        verify(facilityVaccineDao).setFacilityVaccine((HealthFacilityDao) any());
//        verify(facilityVaccineDao).setStock((Integer) any());
//        verify(facilityVaccineDao).setVaccineFacility((VaccineTypeDao) any());
//        verify(facilityVaccineDao).setVaccineId((Long) any());
//    }
//
//    /**
//     * Method under test: {@link StockService#updateStockById(Long, Long, FacilityVaccineDto)}
//     */
//    @Test
//    void testUpdateStockById() {
//        UserDao userDao = new UserDao();
//        userDao.setBookingDaoList(new ArrayList<>());
//        userDao.setCreatedAt(null);
//        userDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        userDao.setId(123L);
//        userDao.setIsDeleted(true);
//        userDao.setPassword("iloveyou");
//        userDao.setProfile(new ProfileDao());
//        userDao.setUpdatedAt(null);
//        userDao.setUsername("janedoe");
//
//        ProfileDao profileDao = new ProfileDao();
//        profileDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        profileDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        profileDao.setFamilyDaoList(new ArrayList<>());
//        profileDao.setHealthFacilityDaoList(new ArrayList<>());
//        profileDao.setIsDeleted(true);
//        profileDao.setRole(AppConstant.ProfileRole.USER);
//        profileDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        profileDao.setUser(userDao);
//        profileDao.setUserId(123L);
//
//        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
//        healthFacilityDao.setCity("Oxford");
//        healthFacilityDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        healthFacilityDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        healthFacilityDao.setDistrict("District");
//        healthFacilityDao.setFacilityName("Facility Name");
//        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
//        healthFacilityDao.setId(123L);
//        healthFacilityDao.setImgUrl("https://example.org/example");
//        healthFacilityDao.setIsDeleted(true);
//        healthFacilityDao.setOfficeNumber("42");
//        healthFacilityDao.setPostalCode(1);
//        healthFacilityDao.setProfile(profileDao);
//        healthFacilityDao.setProvince("Province");
//        healthFacilityDao.setScheduleDaoList(new ArrayList<>());
//        healthFacilityDao.setStreetName("Street Name");
//        healthFacilityDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        healthFacilityDao.setVillageName("Village Name");
//
//        VaccineTypeDao vaccineTypeDao = new VaccineTypeDao();
//        vaccineTypeDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        vaccineTypeDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        vaccineTypeDao.setFacilityVaccineDaoList(new ArrayList<>());
//        vaccineTypeDao.setId(123L);
//        vaccineTypeDao.setIsDeleted(true);
//        vaccineTypeDao.setScheduleDaoList(new ArrayList<>());
//        vaccineTypeDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        vaccineTypeDao.setVaccineName("Vaccine Name");
//
//        FacilityVaccineDao facilityVaccineDao = new FacilityVaccineDao();
//        facilityVaccineDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        facilityVaccineDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        facilityVaccineDao.setFacilityId(123L);
//        facilityVaccineDao.setFacilityVaccine(healthFacilityDao);
//        facilityVaccineDao.setIsDeleted(true);
//        facilityVaccineDao.setStock(1);
//        facilityVaccineDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        facilityVaccineDao.setVaccineFacility(vaccineTypeDao);
//        facilityVaccineDao.setVaccineId(123L);
//        Optional<FacilityVaccineDao> ofResult = Optional.of(facilityVaccineDao);
//
//        UserDao userDao1 = new UserDao();
//        userDao1.setBookingDaoList(new ArrayList<>());
//        userDao1.setCreatedAt(null);
//        userDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        userDao1.setId(123L);
//        userDao1.setIsDeleted(true);
//        userDao1.setPassword("iloveyou");
//        userDao1.setProfile(new ProfileDao());
//        userDao1.setUpdatedAt(null);
//        userDao1.setUsername("janedoe");
//
//        ProfileDao profileDao1 = new ProfileDao();
//        profileDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        profileDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        profileDao1.setFamilyDaoList(new ArrayList<>());
//        profileDao1.setHealthFacilityDaoList(new ArrayList<>());
//        profileDao1.setIsDeleted(true);
//        profileDao1.setRole(AppConstant.ProfileRole.USER);
//        profileDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        profileDao1.setUser(userDao1);
//        profileDao1.setUserId(123L);
//
//        HealthFacilityDao healthFacilityDao1 = new HealthFacilityDao();
//        healthFacilityDao1.setCity("Oxford");
//        healthFacilityDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        healthFacilityDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        healthFacilityDao1.setDistrict("District");
//        healthFacilityDao1.setFacilityName("Facility Name");
//        healthFacilityDao1.setFacilityVaccineDaoList(new ArrayList<>());
//        healthFacilityDao1.setId(123L);
//        healthFacilityDao1.setImgUrl("https://example.org/example");
//        healthFacilityDao1.setIsDeleted(true);
//        healthFacilityDao1.setOfficeNumber("42");
//        healthFacilityDao1.setPostalCode(1);
//        healthFacilityDao1.setProfile(profileDao1);
//        healthFacilityDao1.setProvince("Province");
//        healthFacilityDao1.setScheduleDaoList(new ArrayList<>());
//        healthFacilityDao1.setStreetName("Street Name");
//        healthFacilityDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        healthFacilityDao1.setVillageName("Village Name");
//
//        VaccineTypeDao vaccineTypeDao1 = new VaccineTypeDao();
//        vaccineTypeDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        vaccineTypeDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        vaccineTypeDao1.setFacilityVaccineDaoList(new ArrayList<>());
//        vaccineTypeDao1.setId(123L);
//        vaccineTypeDao1.setIsDeleted(true);
//        vaccineTypeDao1.setScheduleDaoList(new ArrayList<>());
//        vaccineTypeDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        vaccineTypeDao1.setVaccineName("Vaccine Name");
//
//        FacilityVaccineDao facilityVaccineDao1 = new FacilityVaccineDao();
//        facilityVaccineDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        facilityVaccineDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        facilityVaccineDao1.setFacilityId(123L);
//        facilityVaccineDao1.setFacilityVaccine(healthFacilityDao1);
//        facilityVaccineDao1.setIsDeleted(true);
//        facilityVaccineDao1.setStock(1);
//        facilityVaccineDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        facilityVaccineDao1.setVaccineFacility(vaccineTypeDao1);
//        facilityVaccineDao1.setVaccineId(123L);
//        Optional<FacilityVaccineDao> ofResult1 = Optional.of(facilityVaccineDao1);
//
//        ProfileDao profileDao2 = new ProfileDao();
//        profileDao2.setCreatedAt(null);
//        profileDao2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        profileDao2.setFamilyDaoList(new ArrayList<>());
//        profileDao2.setHealthFacilityDaoList(new ArrayList<>());
//        profileDao2.setIsDeleted(true);
//        profileDao2.setRole(AppConstant.ProfileRole.USER);
//        profileDao2.setUpdatedAt(null);
//        profileDao2.setUser(new UserDao());
//        profileDao2.setUserId(123L);
//
//        UserDao userDao2 = new UserDao();
//        userDao2.setBookingDaoList(new ArrayList<>());
//        userDao2.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        userDao2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        userDao2.setId(123L);
//        userDao2.setIsDeleted(true);
//        userDao2.setPassword("iloveyou");
//        userDao2.setProfile(profileDao2);
//        userDao2.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        userDao2.setUsername("janedoe");
//
//        ProfileDao profileDao3 = new ProfileDao();
//        profileDao3.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        profileDao3.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        profileDao3.setFamilyDaoList(new ArrayList<>());
//        profileDao3.setHealthFacilityDaoList(new ArrayList<>());
//        profileDao3.setIsDeleted(true);
//        profileDao3.setRole(AppConstant.ProfileRole.USER);
//        profileDao3.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        profileDao3.setUser(userDao2);
//        profileDao3.setUserId(123L);
//
//        HealthFacilityDao healthFacilityDao2 = new HealthFacilityDao();
//        healthFacilityDao2.setCity("Oxford");
//        healthFacilityDao2.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        healthFacilityDao2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        healthFacilityDao2.setDistrict("District");
//        healthFacilityDao2.setFacilityName("Facility Name");
//        healthFacilityDao2.setFacilityVaccineDaoList(new ArrayList<>());
//        healthFacilityDao2.setId(123L);
//        healthFacilityDao2.setImgUrl("https://example.org/example");
//        healthFacilityDao2.setIsDeleted(true);
//        healthFacilityDao2.setOfficeNumber("42");
//        healthFacilityDao2.setPostalCode(1);
//        healthFacilityDao2.setProfile(profileDao3);
//        healthFacilityDao2.setProvince("Province");
//        healthFacilityDao2.setScheduleDaoList(new ArrayList<>());
//        healthFacilityDao2.setStreetName("Street Name");
//        healthFacilityDao2.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        healthFacilityDao2.setVillageName("Village Name");
//
//        VaccineTypeDao vaccineTypeDao2 = new VaccineTypeDao();
//        vaccineTypeDao2.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        vaccineTypeDao2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        vaccineTypeDao2.setFacilityVaccineDaoList(new ArrayList<>());
//        vaccineTypeDao2.setId(123L);
//        vaccineTypeDao2.setIsDeleted(true);
//        vaccineTypeDao2.setScheduleDaoList(new ArrayList<>());
//        vaccineTypeDao2.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        vaccineTypeDao2.setVaccineName("Vaccine Name");
//
//        FacilityVaccineDao facilityVaccineDao2 = new FacilityVaccineDao();
//        facilityVaccineDao2.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        facilityVaccineDao2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        facilityVaccineDao2.setFacilityId(123L);
//        facilityVaccineDao2.setFacilityVaccine(healthFacilityDao2);
//        facilityVaccineDao2.setIsDeleted(true);
//        facilityVaccineDao2.setStock(1);
//        facilityVaccineDao2.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        facilityVaccineDao2.setVaccineFacility(vaccineTypeDao2);
//        facilityVaccineDao2.setVaccineId(123L);
//        when(this.stockRepository.save((FacilityVaccineDao) any())).thenReturn(facilityVaccineDao2);
//        when(this.stockRepository.findByFacilityId((Long) any())).thenReturn(ofResult);
//        when(this.stockRepository.findByVaccineId((Long) any())).thenReturn(ofResult1);
//        ResponseEntity<Object> actualUpdateStockByIdResult = this.stockService.updateStockById(123L, 123L,
//                new FacilityVaccineDto());
//        assertTrue(actualUpdateStockByIdResult.hasBody());
//        assertTrue(actualUpdateStockByIdResult.getHeaders().isEmpty());
//        assertEquals(HttpStatus.OK, actualUpdateStockByIdResult.getStatusCode());
//        Object data = ((ApiResponse<Object>) actualUpdateStockByIdResult.getBody()).getData();
//        assertTrue(data instanceof FacilityVaccineDto);
//        ApiResponseStatus status = ((ApiResponse<Object>) actualUpdateStockByIdResult.getBody()).getStatus();
//        assertEquals("SUCCESS", status.getCode());
//        assertNull(((FacilityVaccineDto) data).getStock());
//        assertEquals(123L, ((FacilityVaccineDto) data).getFacilityId().longValue());
//        assertEquals(123L, ((FacilityVaccineDto) data).getVaccineId().longValue());
//        assertEquals("Success", status.getMessage());
//        verify(this.stockRepository).save((FacilityVaccineDao) any());
//        verify(this.stockRepository).findByFacilityId((Long) any());
//        verify(this.stockRepository).findByVaccineId((Long) any());
//    }
//
//    /**
//     * Method under test: {@link StockService#deleteStockById(Long, Long)}
//     */
//    @Test
//    void testDeleteStockById() {
//        UserDao userDao = new UserDao();
//        userDao.setBookingDaoList(new ArrayList<>());
//        userDao.setCreatedAt(null);
//        userDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        userDao.setId(123L);
//        userDao.setIsDeleted(true);
//        userDao.setPassword("iloveyou");
//        userDao.setProfile(new ProfileDao());
//        userDao.setUpdatedAt(null);
//        userDao.setUsername("janedoe");
//
//        ProfileDao profileDao = new ProfileDao();
//        profileDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        profileDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        profileDao.setFamilyDaoList(new ArrayList<>());
//        profileDao.setHealthFacilityDaoList(new ArrayList<>());
//        profileDao.setIsDeleted(true);
//        profileDao.setRole(AppConstant.ProfileRole.USER);
//        profileDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        profileDao.setUser(userDao);
//        profileDao.setUserId(123L);
//
//        HealthFacilityDao healthFacilityDao = new HealthFacilityDao();
//        healthFacilityDao.setCity("Oxford");
//        healthFacilityDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        healthFacilityDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        healthFacilityDao.setDistrict("District");
//        healthFacilityDao.setFacilityName("Facility Name");
//        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
//        healthFacilityDao.setId(123L);
//        healthFacilityDao.setImgUrl("https://example.org/example");
//        healthFacilityDao.setIsDeleted(true);
//        healthFacilityDao.setOfficeNumber("42");
//        healthFacilityDao.setPostalCode(1);
//        healthFacilityDao.setProfile(profileDao);
//        healthFacilityDao.setProvince("Province");
//        healthFacilityDao.setScheduleDaoList(new ArrayList<>());
//        healthFacilityDao.setStreetName("Street Name");
//        healthFacilityDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        healthFacilityDao.setVillageName("Village Name");
//
//        VaccineTypeDao vaccineTypeDao = new VaccineTypeDao();
//        vaccineTypeDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        vaccineTypeDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        vaccineTypeDao.setFacilityVaccineDaoList(new ArrayList<>());
//        vaccineTypeDao.setId(123L);
//        vaccineTypeDao.setIsDeleted(true);
//        vaccineTypeDao.setScheduleDaoList(new ArrayList<>());
//        vaccineTypeDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        vaccineTypeDao.setVaccineName("Vaccine Name");
//
//        FacilityVaccineDao facilityVaccineDao = new FacilityVaccineDao();
//        facilityVaccineDao.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        facilityVaccineDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        facilityVaccineDao.setFacilityId(123L);
//        facilityVaccineDao.setFacilityVaccine(healthFacilityDao);
//        facilityVaccineDao.setIsDeleted(true);
//        facilityVaccineDao.setStock(1);
//        facilityVaccineDao.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        facilityVaccineDao.setVaccineFacility(vaccineTypeDao);
//        facilityVaccineDao.setVaccineId(123L);
//        Optional<FacilityVaccineDao> ofResult = Optional.of(facilityVaccineDao);
//
//        UserDao userDao1 = new UserDao();
//        userDao1.setBookingDaoList(new ArrayList<>());
//        userDao1.setCreatedAt(null);
//        userDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        userDao1.setId(123L);
//        userDao1.setIsDeleted(true);
//        userDao1.setPassword("iloveyou");
//        userDao1.setProfile(new ProfileDao());
//        userDao1.setUpdatedAt(null);
//        userDao1.setUsername("janedoe");
//
//        ProfileDao profileDao1 = new ProfileDao();
//        profileDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        profileDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        profileDao1.setFamilyDaoList(new ArrayList<>());
//        profileDao1.setHealthFacilityDaoList(new ArrayList<>());
//        profileDao1.setIsDeleted(true);
//        profileDao1.setRole(AppConstant.ProfileRole.USER);
//        profileDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        profileDao1.setUser(userDao1);
//        profileDao1.setUserId(123L);
//
//        HealthFacilityDao healthFacilityDao1 = new HealthFacilityDao();
//        healthFacilityDao1.setCity("Oxford");
//        healthFacilityDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        healthFacilityDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        healthFacilityDao1.setDistrict("District");
//        healthFacilityDao1.setFacilityName("Facility Name");
//        healthFacilityDao1.setFacilityVaccineDaoList(new ArrayList<>());
//        healthFacilityDao1.setId(123L);
//        healthFacilityDao1.setImgUrl("https://example.org/example");
//        healthFacilityDao1.setIsDeleted(true);
//        healthFacilityDao1.setOfficeNumber("42");
//        healthFacilityDao1.setPostalCode(1);
//        healthFacilityDao1.setProfile(profileDao1);
//        healthFacilityDao1.setProvince("Province");
//        healthFacilityDao1.setScheduleDaoList(new ArrayList<>());
//        healthFacilityDao1.setStreetName("Street Name");
//        healthFacilityDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        healthFacilityDao1.setVillageName("Village Name");
//
//        VaccineTypeDao vaccineTypeDao1 = new VaccineTypeDao();
//        vaccineTypeDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        vaccineTypeDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        vaccineTypeDao1.setFacilityVaccineDaoList(new ArrayList<>());
//        vaccineTypeDao1.setId(123L);
//        vaccineTypeDao1.setIsDeleted(true);
//        vaccineTypeDao1.setScheduleDaoList(new ArrayList<>());
//        vaccineTypeDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        vaccineTypeDao1.setVaccineName("Vaccine Name");
//
//        FacilityVaccineDao facilityVaccineDao1 = new FacilityVaccineDao();
//        facilityVaccineDao1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        facilityVaccineDao1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
//        facilityVaccineDao1.setFacilityId(123L);
//        facilityVaccineDao1.setFacilityVaccine(healthFacilityDao1);
//        facilityVaccineDao1.setIsDeleted(true);
//        facilityVaccineDao1.setStock(1);
//        facilityVaccineDao1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        facilityVaccineDao1.setVaccineFacility(vaccineTypeDao1);
//        facilityVaccineDao1.setVaccineId(123L);
//        Optional<FacilityVaccineDao> ofResult1 = Optional.of(facilityVaccineDao1);
//        doNothing().when(this.stockRepository).delete((FacilityVaccineDao) any());
//        when(this.stockRepository.findByFacilityId((Long) any())).thenReturn(ofResult);
//        when(this.stockRepository.findByVaccineId((Long) any())).thenReturn(ofResult1);
//        ResponseEntity<Object> actualDeleteStockByIdResult = this.stockService.deleteStockById(123L, 123L);
//        assertTrue(actualDeleteStockByIdResult.hasBody());
//        assertTrue(actualDeleteStockByIdResult.getHeaders().isEmpty());
//        assertEquals(HttpStatus.OK, actualDeleteStockByIdResult.getStatusCode());
//        assertNull(((ApiResponse<Object>) actualDeleteStockByIdResult.getBody()).getData());
//        ApiResponseStatus status = ((ApiResponse<Object>) actualDeleteStockByIdResult.getBody()).getStatus();
//        assertEquals("Success", status.getMessage());
//        assertEquals("SUCCESS", status.getCode());
//        verify(this.stockRepository).findByFacilityId((Long) any());
//        verify(this.stockRepository).findByVaccineId((Long) any());
//        verify(this.stockRepository).delete((FacilityVaccineDao) any());
//    }
}

