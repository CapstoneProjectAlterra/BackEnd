package com.backend.vaccinebookingsystem.service;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.common.ApiResponse;
import com.backend.vaccinebookingsystem.domain.common.ApiResponseStatus;
import com.backend.vaccinebookingsystem.domain.dao.FacilityVaccineDao;
import com.backend.vaccinebookingsystem.domain.dao.HealthFacilityDao;
import com.backend.vaccinebookingsystem.domain.dao.VaccineTypeDao;
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

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void createStockIsNull_Test() {
        when(this.vaccineTypeRepository.findById((Long) any())).thenReturn(Optional.empty());

        FacilityVaccineDao facilityVaccineDao = FacilityVaccineDao.builder()
                .facilityId(123L)
                .vaccineId(123L)
                .stock(1)
                .build();

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
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), status.getCode());
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
        doNothing().when(this.stockRepository).delete((FacilityVaccineDao) any());
        when(this.stockRepository.findTopByFacilityId((Long) any())).thenReturn(ofResult);
        when(this.stockRepository.findTopByVaccineId((Long) any())).thenReturn(ofResult1);

        ResponseEntity<Object> actualDeleteStockByIdResult = this.stockService.deleteStockById(123L, 123L);

        assertEquals(HttpStatus.OK, actualDeleteStockByIdResult.getStatusCode());
        assertNull(((ApiResponse<Object>) actualDeleteStockByIdResult.getBody()).getData());

        ApiResponseStatus status = ((ApiResponse<Object>) actualDeleteStockByIdResult.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());

        verify(this.stockRepository).delete((FacilityVaccineDao) any());
    }

    @Test
    void deleteStockByIdException_Test() {
        FacilityVaccineDao facilityVaccineDao = FacilityVaccineDao.builder()
                .facilityId(123L)
                .vaccineId(123L)
                .stock(1)
                .build();

        Optional<FacilityVaccineDao> ofResult = Optional.of(facilityVaccineDao);

        when(this.stockRepository.findTopByFacilityId(anyLong())).thenReturn(ofResult);

        doThrow(NullPointerException.class).when(stockRepository).findTopByFacilityId(any());
        assertThrows(Exception.class, () -> stockService.deleteStockById(1L, 1L));
    }

}

