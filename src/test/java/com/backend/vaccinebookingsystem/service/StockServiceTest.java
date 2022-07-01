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
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void createStockAlreadyExistsSuccess_Test() {
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

        ProfileDao profileDao2 = new ProfileDao();
        profileDao2.setFamilyDaoList(new ArrayList<>());
        profileDao2.setHealthFacilityDaoList(new ArrayList<>());
        profileDao2.setRole(AppConstant.ProfileRole.USER);
        profileDao2.setUser(new UserDao());
        profileDao2.setUserId(123L);

        UserDao userDao1 = new UserDao();
        userDao1.setBookingDaoList(new ArrayList<>());
        userDao1.setId(123L);
        userDao1.setName("Name");
        userDao1.setProfile(profileDao2);

        ProfileDao profileDao3 = new ProfileDao();
        profileDao3.setFamilyDaoList(new ArrayList<>());
        profileDao3.setHealthFacilityDaoList(new ArrayList<>());
        profileDao3.setRole(AppConstant.ProfileRole.USER);
        profileDao3.setUser(userDao1);
        profileDao3.setUserId(123L);

        HealthFacilityDao healthFacilityDao1 = new HealthFacilityDao();
        healthFacilityDao1.setFacilityName("Facility Name");
        healthFacilityDao1.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao1.setId(123L);
        healthFacilityDao1.setProfile(profileDao3);
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

        UserDao userDao2 = new UserDao();
        userDao2.setBookingDaoList(new ArrayList<>());
        userDao2.setId(123L);
        userDao2.setName("Name");
        userDao2.setProfile(new ProfileDao());

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

        Optional<FacilityVaccineDao> ofResult1 = Optional.of(facilityVaccineDao1);

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

        HealthFacilityDao healthFacilityDao3 = new HealthFacilityDao();
        healthFacilityDao3.setFacilityName("Facility Name");
        healthFacilityDao3.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao3.setId(123L);
        healthFacilityDao3.setProfile(profileDao5);
        healthFacilityDao3.setScheduleDaoList(new ArrayList<>());

        VaccineTypeDao vaccineTypeDao2 = new VaccineTypeDao();
        vaccineTypeDao2.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao2.setId(123L);
        vaccineTypeDao2.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao2.setVaccineName("Vaccine Name");

        FacilityVaccineDao facilityVaccineDao2 = new FacilityVaccineDao();
        facilityVaccineDao2.setFacilityId(123L);
        facilityVaccineDao2.setFacilityVaccine(healthFacilityDao3);
        facilityVaccineDao2.setStock(1);
        facilityVaccineDao2.setVaccineFacility(vaccineTypeDao2);
        facilityVaccineDao2.setVaccineId(123L);

        Optional<FacilityVaccineDao> ofResult2 = Optional.of(facilityVaccineDao2);
        when(stockRepository.save((FacilityVaccineDao) any())).thenReturn(facilityVaccineDao);
        when(stockRepository.findTopByFacilityId((Long) any())).thenReturn(ofResult1);
        when(stockRepository.findTopByVaccineId((Long) any())).thenReturn(ofResult2);

        VaccineTypeDao vaccineTypeDao3 = new VaccineTypeDao();
        vaccineTypeDao3.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao3.setId(123L);
        vaccineTypeDao3.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao3.setVaccineName("Vaccine Name");

        Optional<VaccineTypeDao> ofResult3 = Optional.of(vaccineTypeDao3);
        when(vaccineTypeRepository.findById((Long) any())).thenReturn(ofResult3);

        ResponseEntity<Object> actualCreateStockResult = stockService.createStock(new FacilityVaccineDto());

        assertEquals(HttpStatus.OK, actualCreateStockResult.getStatusCode());

        Object data = ((ApiResponse<Object>) actualCreateStockResult.getBody()).getData();

        ApiResponseStatus status = ((ApiResponse<Object>) actualCreateStockResult.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());

        assertEquals(123L, ((FacilityVaccineDto) data).getFacilityId().longValue());
        assertEquals(123L, ((FacilityVaccineDto) data).getVaccineId().longValue());

        verify(stockRepository).save((FacilityVaccineDao) any());
        verify(stockRepository).findTopByFacilityId((Long) any());
        verify(stockRepository).findTopByVaccineId((Long) any());
    }

    @Test
    void searchStockByIdSuccess_Test() {
        FacilityVaccineDao facilityVaccineDao = FacilityVaccineDao.builder()
                .facilityId(123L)
                .vaccineId(123L)
                .stock(1)
                .build();

        Optional<FacilityVaccineDao> ofResult = Optional.of(facilityVaccineDao);

        FacilityVaccineDao facilityVaccineDao1 = FacilityVaccineDao.builder()
                .facilityId(123L)
                .vaccineId(123L)
                .stock(1)
                .build();

        Optional<FacilityVaccineDao> ofResult1 = Optional.of(facilityVaccineDao1);

        when(this.stockRepository.findTopByFacilityId(anyLong())).thenReturn(ofResult);
        when(this.stockRepository.findTopByVaccineId(anyLong())).thenReturn(ofResult1);

        ResponseEntity<Object> actualSearchStockByIdResult = this.stockService.searchStockById(123L, 123L);

        Object data = ((ApiResponse<Object>) actualSearchStockByIdResult.getBody()).getData();

        ApiResponseStatus status = ((ApiResponse<Object>) actualSearchStockByIdResult.getBody()).getStatus();

        assertEquals(HttpStatus.OK, actualSearchStockByIdResult.getStatusCode());
        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());
        assertEquals(1, ((FacilityVaccineDto) data).getStock().intValue());
        assertEquals(123L, ((FacilityVaccineDto) data).getFacilityId().longValue());
        assertEquals(123L, ((FacilityVaccineDto) data).getVaccineId().longValue());
    }

    @Test
    void searchStockByIdIsNull_Test() {
        FacilityVaccineDao facilityVaccineDao = FacilityVaccineDao.builder()
                .facilityId(123L)
                .vaccineId(123L)
                .stock(1)
                .build();

        Optional<FacilityVaccineDao> ofResult = Optional.of(facilityVaccineDao);

        FacilityVaccineDao facilityVaccineDao1 = FacilityVaccineDao.builder()
                .facilityId(123L)
                .vaccineId(123L)
                .stock(1)
                .build();

        Optional<FacilityVaccineDao> ofResult1 = Optional.of(facilityVaccineDao1);

        when(this.stockRepository.findTopByFacilityId(anyLong())).thenReturn(ofResult);
        when(this.stockRepository.findTopByVaccineId(anyLong())).thenReturn(ofResult1);

        ResponseEntity<Object> actualSearchStockByIdResult = this.stockService.searchStockById(null, null);

        Object data = ((ApiResponse<Object>) actualSearchStockByIdResult.getBody()).getData();

        ApiResponseStatus status = ((ApiResponse<Object>) actualSearchStockByIdResult.getBody()).getStatus();

        assertEquals(HttpStatus.BAD_REQUEST, actualSearchStockByIdResult.getStatusCode());
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), status.getCode());
    }

    @Test
    void searchStockByIdException_Test() {
        FacilityVaccineDao facilityVaccineDao = FacilityVaccineDao.builder()
                .facilityId(123L)
                .vaccineId(123L)
                .stock(1)
                .build();

        Optional<FacilityVaccineDao> ofResult = Optional.of(facilityVaccineDao);

        when(this.stockRepository.findTopByFacilityId(anyLong())).thenReturn(ofResult);

        doThrow(NullPointerException.class).when(stockRepository).findTopByFacilityId(any());
        assertThrows(Exception.class, () -> stockService.searchStockById(1L, 1L));
    }

    @Test
    void getAllStocksSuccess_Test() {
        VaccineTypeDao vaccineTypeDao = VaccineTypeDao.builder()
                .id(123L)
                .vaccineName("Vaccine Name")
                .build();

        HealthFacilityDao healthFacilityDao = HealthFacilityDao.builder()
                .id(123L)
                .facilityName("Facility Name")
                .build();

        FacilityVaccineDao facilityVaccineDao = FacilityVaccineDao.builder()
                .facilityId(123L)
                .vaccineId(123L)
                .stock(1)
                .build();

        ArrayList<FacilityVaccineDao> facilityVaccineDaoList = new ArrayList<>();
        facilityVaccineDaoList.add(facilityVaccineDao);

        when(this.stockRepository.findAll()).thenReturn(facilityVaccineDaoList);

        ResponseEntity<Object> actualAllStocks = this.stockService.getAllStocks();

        Object data = ((ApiResponse<Object>) actualAllStocks.getBody()).getData();

        ApiResponseStatus status = ((ApiResponse<Object>) actualAllStocks.getBody()).getStatus();
        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());
        assertEquals(HttpStatus.OK, actualAllStocks.getStatusCode());
    }

    @Test
    void getAllStocksException_Test() {
        when(stockRepository.findAll()).thenThrow(NullPointerException.class);

        ResponseEntity<Object> response = stockService.getAllStocks();

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void updateStockByIdSuccess_Test() {
        FacilityVaccineDao facilityVaccineDao = FacilityVaccineDao.builder()
                .facilityId(123L)
                .vaccineId(123L)
                .stock(1)
                .build();

        Optional<FacilityVaccineDao> ofResult = Optional.of(facilityVaccineDao);

        FacilityVaccineDao facilityVaccineDao1 = FacilityVaccineDao.builder()
                .facilityId(123L)
                .vaccineId(123L)
                .stock(1)
                .build();

        Optional<FacilityVaccineDao> ofResult1 = Optional.of(facilityVaccineDao1);

        when(this.stockRepository.findTopByFacilityId((Long) any())).thenReturn(ofResult);
        when(this.stockRepository.findTopByVaccineId((Long) any())).thenReturn(ofResult1);

        ResponseEntity<Object> actualUpdateStockByIdResult = this.stockService.updateStockById(123L, 123L,
                new FacilityVaccineDto());

        Object data = ((ApiResponse<Object>) actualUpdateStockByIdResult.getBody()).getData();

        ApiResponseStatus status = ((ApiResponse<Object>) actualUpdateStockByIdResult.getBody()).getStatus();

        assertEquals(HttpStatus.OK, actualUpdateStockByIdResult.getStatusCode());
        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());
        assertEquals(123L, ((FacilityVaccineDto) data).getFacilityId().longValue());
        assertEquals(123L, ((FacilityVaccineDto) data).getVaccineId().longValue());
    }

    @Test
    void updateStockByIdException_Test() {
        FacilityVaccineDao facilityVaccineDao = FacilityVaccineDao.builder()
                .facilityId(123L)
                .vaccineId(123L)
                .stock(1)
                .build();

        Optional<FacilityVaccineDao> ofResult = Optional.of(facilityVaccineDao);

        when(this.stockRepository.findTopByFacilityId(anyLong())).thenReturn(ofResult);

        doThrow(NullPointerException.class).when(stockRepository).findTopByFacilityId(any());
        assertThrows(Exception.class, () -> stockService.updateStockById(123L, 123L, new FacilityVaccineDto()));
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

        HealthFacilityDao healthFacilityDao1 = new HealthFacilityDao();
        healthFacilityDao1.setFacilityName("Facility Name");
        healthFacilityDao1.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao1.setId(123L);
        healthFacilityDao1.setProfile(profileDao1);
        healthFacilityDao1.setScheduleDaoList(new ArrayList<>());

        VaccineTypeDao vaccineTypeDao1 = new VaccineTypeDao();
        vaccineTypeDao1.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao1.setId(123L);
        vaccineTypeDao1.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao1.setVaccineName("Vaccine Name");

        FacilityVaccineDao facilityVaccineDao1 = new FacilityVaccineDao();
        facilityVaccineDao1.setFacilityId(123L);
        facilityVaccineDao1.setFacilityVaccine(healthFacilityDao1);
        facilityVaccineDao1.setIsDeleted(true);
        facilityVaccineDao1.setStock(1);
        facilityVaccineDao1.setVaccineFacility(vaccineTypeDao1);
        facilityVaccineDao1.setVaccineId(123L);

        Optional<FacilityVaccineDao> ofResult1 = Optional.of(facilityVaccineDao1);

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

        HealthFacilityDao healthFacilityDao2 = new HealthFacilityDao();
        healthFacilityDao2.setFacilityName("Facility Name");
        healthFacilityDao2.setFacilityVaccineDaoList(new ArrayList<>());
        healthFacilityDao2.setId(123L);
        healthFacilityDao2.setProfile(profileDao3);
        healthFacilityDao2.setScheduleDaoList(new ArrayList<>());

        VaccineTypeDao vaccineTypeDao2 = new VaccineTypeDao();
        vaccineTypeDao2.setFacilityVaccineDaoList(new ArrayList<>());
        vaccineTypeDao2.setId(123L);
        vaccineTypeDao2.setScheduleDaoList(new ArrayList<>());
        vaccineTypeDao2.setVaccineName("Vaccine Name");

        FacilityVaccineDao facilityVaccineDao2 = new FacilityVaccineDao();
        facilityVaccineDao2.setFacilityId(123L);
        facilityVaccineDao2.setFacilityVaccine(healthFacilityDao2);
        facilityVaccineDao2.setStock(1);
        facilityVaccineDao2.setVaccineFacility(vaccineTypeDao2);
        facilityVaccineDao2.setVaccineId(123L);

        when(stockRepository.save((FacilityVaccineDao) any())).thenReturn(facilityVaccineDao2);
        when(stockRepository.findTopByFacilityId((Long) any())).thenReturn(ofResult);
        when(stockRepository.findTopByVaccineId((Long) any())).thenReturn(ofResult1);

        ResponseEntity<Object> actualDeleteStockByIdResult = stockService.deleteStockById(123L, 123L);

        assertEquals(HttpStatus.OK, actualDeleteStockByIdResult.getStatusCode());

        ApiResponseStatus status = ((ApiResponse<Object>) actualDeleteStockByIdResult.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());

        verify(stockRepository).save((FacilityVaccineDao) any());
        verify(stockRepository).findTopByFacilityId((Long) any());
        verify(stockRepository).findTopByVaccineId((Long) any());
    }

}

