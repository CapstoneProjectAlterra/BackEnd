package com.backend.vaccinebookingsystem.service;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.common.ApiResponse;
import com.backend.vaccinebookingsystem.domain.common.ApiResponseStatus;
import com.backend.vaccinebookingsystem.domain.dao.*;
import com.backend.vaccinebookingsystem.domain.dto.FacilityVaccineDto;
import com.backend.vaccinebookingsystem.repository.HealthFacilityRepository;
import com.backend.vaccinebookingsystem.repository.StockRepository;
import com.backend.vaccinebookingsystem.repository.VaccineTypeRepository;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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

    @Test
    void createStockSuccess_Test() {
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
        healthFacilityDao1.setProfile(profileDao2);
        healthFacilityDao1.setScheduleDaoList(new ArrayList<>());

        VaccineTypeDao vaccineTypeDao = new VaccineTypeDao();
        vaccineTypeDao.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao.setId(123L);
        vaccineTypeDao.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao.setVaccineName("Vaccine Name");

        FacilityVaccineDao facilityVaccineDao = new FacilityVaccineDao();
        facilityVaccineDao.setFacilityId(123L);
        facilityVaccineDao.setFacilityVaccine(healthFacilityDao1);
        facilityVaccineDao.setStock(1);
        facilityVaccineDao.setVaccineFacility(vaccineTypeDao);
        facilityVaccineDao.setVaccineId(123L);

        Optional<FacilityVaccineDao> ofResult1 = Optional.of(facilityVaccineDao);

        ProfileDao profileDao3 = new ProfileDao();
        profileDao3.setFamilyDaoList(new ArrayList<>());
        profileDao3.setHealthFacilityDaoList(new ArrayList<>());
        profileDao3.setRole(AppConstant.ProfileRole.USER);
        profileDao3.setUser(new UserDao());
        profileDao3.setUserId(123L);

        UserDao userDao2 = new UserDao();
        userDao2.setBookingDaoList(new ArrayList<>());
        userDao2.setId(123L);
        userDao2.setName("Name");
        userDao2.setProfile(profileDao3);

        ProfileDao profileDao4 = new ProfileDao();
        profileDao4.setFamilyDaoList(new ArrayList<>());
        profileDao4.setHealthFacilityDaoList(new ArrayList<>());
        profileDao4.setRole(AppConstant.ProfileRole.USER);
        profileDao4.setUser(userDao2);
        profileDao4.setUserId(123L);

        HealthFacilityDao healthFacilityDao2 = new HealthFacilityDao();
        healthFacilityDao2.setFacilityName("Facility Name");
        healthFacilityDao2.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao2.setId(123L);
        healthFacilityDao2.setProfile(profileDao4);
        healthFacilityDao2.setScheduleDaoList(new ArrayList<>());

        VaccineTypeDao vaccineTypeDao1 = new VaccineTypeDao();
        vaccineTypeDao1.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao1.setId(123L);
        vaccineTypeDao1.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao1.setVaccineName("Vaccine Name");

        FacilityVaccineDao facilityVaccineDao1 = new FacilityVaccineDao();
        facilityVaccineDao1.setFacilityId(123L);
        facilityVaccineDao1.setFacilityVaccine(healthFacilityDao2);
        facilityVaccineDao1.setStock(1);
        facilityVaccineDao1.setVaccineFacility(vaccineTypeDao1);
        facilityVaccineDao1.setVaccineId(123L);

        when(stockRepository.save((FacilityVaccineDao) any())).thenReturn(facilityVaccineDao1);
        when(stockRepository.findByFacilityIdAndAndVaccineId((Long) any(), (Long) any())).thenReturn(ofResult1);

        ResponseEntity<Object> actualCreateStockResult = stockService.createStock(new FacilityVaccineDto());

        assertEquals(HttpStatus.OK, actualCreateStockResult.getStatusCode());

        Object data = ((ApiResponse<Object>) actualCreateStockResult.getBody()).getData();

        ApiResponseStatus status = ((ApiResponse<Object>) actualCreateStockResult.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());

        assertEquals(123L, ((FacilityVaccineDto) data).getFacilityId().longValue());
        assertEquals(123L, ((FacilityVaccineDto) data).getVaccineId().longValue());

        verify(stockRepository).save((FacilityVaccineDao) any());
        verify(stockRepository).findByFacilityIdAndAndVaccineId((Long) any(), (Long) any());
    }

    @Test
    void searchStockByIdSuccess_Test() {
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

        FacilityVaccineDao facilityVaccineDao = new FacilityVaccineDao();
        facilityVaccineDao.setFacilityId(123L);
        facilityVaccineDao.setFacilityVaccine(healthFacilityDao);
        facilityVaccineDao.setStock(1);
        facilityVaccineDao.setVaccineFacility(vaccineTypeDao);
        facilityVaccineDao.setVaccineId(123L);

        Optional<FacilityVaccineDao> ofResult = Optional.of(facilityVaccineDao);
        when(stockRepository.findByFacilityIdAndAndVaccineId((Long) any(), (Long) any())).thenReturn(ofResult);

        ResponseEntity<Object> actualSearchStockByIdResult = stockService.searchStockById(123L, 123L);

        assertEquals(HttpStatus.OK, actualSearchStockByIdResult.getStatusCode());

        Object data = ((ApiResponse<Object>) actualSearchStockByIdResult.getBody()).getData();

        ApiResponseStatus status = ((ApiResponse<Object>) actualSearchStockByIdResult.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());
        assertEquals(1, ((FacilityVaccineDto) data).getStock().intValue());
        assertEquals(123L, ((FacilityVaccineDto) data).getFacilityId().longValue());
        assertEquals(123L, ((FacilityVaccineDto) data).getVaccineId().longValue());

        verify(stockRepository).findByFacilityIdAndAndVaccineId((Long) any(), (Long) any());
    }

    @Test
    void getAllStocksSuccess_Test() {
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
        healthFacilityDao.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao.setId(123L);
        healthFacilityDao.setProfile(profileDao);
        healthFacilityDao.setScheduleDaoList(new ArrayList<>());

        VaccineTypeDao vaccineTypeDao = new VaccineTypeDao();
        vaccineTypeDao.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao.setId(123L);
        vaccineTypeDao.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao.setVaccineName("Vaccine Name");

        FacilityVaccineDao facilityVaccineDao = new FacilityVaccineDao();
        facilityVaccineDao.setFacilityId(123L);
        facilityVaccineDao.setFacilityVaccine(healthFacilityDao);
        facilityVaccineDao.setStock(1);
        facilityVaccineDao.setVaccineFacility(vaccineTypeDao);
        facilityVaccineDao.setVaccineId(123L);

        ArrayList<FacilityVaccineDao> facilityVaccineDaoList = new ArrayList<>();
        facilityVaccineDaoList.add(facilityVaccineDao);

        when(stockRepository.findAll()).thenReturn(facilityVaccineDaoList);
        ResponseEntity<Object> actualAllStocks = stockService.getAllStocks();

        assertEquals(HttpStatus.OK, actualAllStocks.getStatusCode());

        Object data = ((ApiResponse<Object>) actualAllStocks.getBody()).getData();
        assertEquals(1, ((Collection<FacilityVaccineDto>) data).size());

        ApiResponseStatus status = ((ApiResponse<Object>) actualAllStocks.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());

        FacilityVaccineDto getResult = ((List<FacilityVaccineDto>) data).get(0);

        assertEquals(1, getResult.getStock().intValue());
        assertEquals(123L, getResult.getFacilityId().longValue());
        assertEquals(123L, getResult.getVaccineId().longValue());

        verify(stockRepository).findAll();
    }

    @Test
    void updateStockByIdSuccess_Test() {
        UserDao userDao = new UserDao();
        userDao.setBookingDaoList(new ArrayList<>());
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

        FacilityVaccineDao facilityVaccineDao = new FacilityVaccineDao();
        facilityVaccineDao.setFacilityId(123L);
        facilityVaccineDao.setFacilityVaccine(healthFacilityDao);
        facilityVaccineDao.setStock(1);
        facilityVaccineDao.setVaccineFacility(vaccineTypeDao);
        facilityVaccineDao.setVaccineId(123L);

        Optional<FacilityVaccineDao> ofResult = Optional.of(facilityVaccineDao);

        ProfileDao profileDao1 = new ProfileDao();
        profileDao1.setFamilyDaoList(new ArrayList<>());
        profileDao1.setHealthFacilityDaoList(new ArrayList<>());
        profileDao1.setRole(AppConstant.ProfileRole.USER);
        profileDao1.setUser(new UserDao());
        profileDao1.setUserId(123L);

        UserDao userDao1 = new UserDao();
        userDao1.setBookingDaoList(new ArrayList<>());
        userDao1.setId(123L);
        userDao1.setName("Name");
        userDao1.setProfile(profileDao1);

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
        healthFacilityDao1.setProfile(profileDao2);
        healthFacilityDao1.setScheduleDaoList(new ArrayList<>());

        VaccineTypeDao vaccineTypeDao1 = new VaccineTypeDao();
        vaccineTypeDao1.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao1.setId(123L);
        vaccineTypeDao1.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao1.setVaccineName("Vaccine Name");

        FacilityVaccineDao facilityVaccineDao1 = new FacilityVaccineDao();
        facilityVaccineDao1.setFacilityId(123L);
        facilityVaccineDao1.setFacilityVaccine(healthFacilityDao1);
        facilityVaccineDao1.setStock(1);
        facilityVaccineDao1.setVaccineFacility(vaccineTypeDao1);
        facilityVaccineDao1.setVaccineId(123L);

        when(stockRepository.save((FacilityVaccineDao) any())).thenReturn(facilityVaccineDao1);
        when(stockRepository.findByFacilityIdAndAndVaccineId((Long) any(), (Long) any())).thenReturn(ofResult);

        ResponseEntity<Object> actualUpdateStockByIdResult = stockService.updateStockById(123L, 123L,
                new FacilityVaccineDto());

        assertEquals(HttpStatus.OK, actualUpdateStockByIdResult.getStatusCode());

        Object data = ((ApiResponse<Object>) actualUpdateStockByIdResult.getBody()).getData();

        ApiResponseStatus status = ((ApiResponse<Object>) actualUpdateStockByIdResult.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());
        assertEquals(123L, ((FacilityVaccineDto) data).getFacilityId().longValue());
        assertEquals(123L, ((FacilityVaccineDto) data).getVaccineId().longValue());

        verify(stockRepository).save((FacilityVaccineDao) any());
        verify(stockRepository).findByFacilityIdAndAndVaccineId((Long) any(), (Long) any());
    }

    @Test
    void deleteStockByIdSuccess_Test() {
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

        FacilityVaccineDao facilityVaccineDao = new FacilityVaccineDao();
        facilityVaccineDao.setFacilityId(123L);
        facilityVaccineDao.setFacilityVaccine(healthFacilityDao);
        facilityVaccineDao.setStock(1);
        facilityVaccineDao.setVaccineFacility(vaccineTypeDao);
        facilityVaccineDao.setVaccineId(123L);

        Optional<FacilityVaccineDao> ofResult = Optional.of(facilityVaccineDao);

        ProfileDao profileDao1 = new ProfileDao();
        profileDao1.setFamilyDaoList(new ArrayList<>());
        profileDao1.setHealthFacilityDaoList(new ArrayList<>());
        profileDao1.setRole(AppConstant.ProfileRole.USER);
        profileDao1.setUser(new UserDao());
        profileDao1.setUserId(123L);

        UserDao userDao1 = new UserDao();
        userDao1.setBookingDaoList(new ArrayList<>());
        userDao1.setId(123L);
        userDao1.setName("Name");
        userDao1.setProfile(profileDao1);

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
        healthFacilityDao1.setProfile(profileDao2);
        healthFacilityDao1.setScheduleDaoList(new ArrayList<>());

        VaccineTypeDao vaccineTypeDao1 = new VaccineTypeDao();
        vaccineTypeDao1.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao1.setId(123L);
        vaccineTypeDao1.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao1.setVaccineName("Vaccine Name");

        FacilityVaccineDao facilityVaccineDao1 = new FacilityVaccineDao();
        facilityVaccineDao1.setFacilityId(123L);
        facilityVaccineDao1.setFacilityVaccine(healthFacilityDao1);
        facilityVaccineDao1.setStock(1);
        facilityVaccineDao1.setVaccineFacility(vaccineTypeDao1);
        facilityVaccineDao1.setVaccineId(123L);

        when(stockRepository.save((FacilityVaccineDao) any())).thenReturn(facilityVaccineDao1);
        when(stockRepository.findByFacilityIdAndAndVaccineId((Long) any(), (Long) any())).thenReturn(ofResult);

        ResponseEntity<Object> actualDeleteStockByIdResult = stockService.deleteStockById(123L, 123L);

        assertEquals(HttpStatus.OK, actualDeleteStockByIdResult.getStatusCode());

        Object data = ((ApiResponse<Object>) actualDeleteStockByIdResult.getBody()).getData();

        ApiResponseStatus status = ((ApiResponse<Object>) actualDeleteStockByIdResult.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());
        assertEquals(0, ((FacilityVaccineDto) data).getStock().intValue());
        assertEquals(123L, ((FacilityVaccineDto) data).getFacilityId().longValue());
        assertEquals(123L, ((FacilityVaccineDto) data).getVaccineId().longValue());

        verify(stockRepository).save((FacilityVaccineDao) any());
        verify(stockRepository).findByFacilityIdAndAndVaccineId((Long) any(), (Long) any());
    }
}

