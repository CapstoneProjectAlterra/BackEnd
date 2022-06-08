package com.backend.vaccinebookingsystem.service;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.common.ApiResponse;
import com.backend.vaccinebookingsystem.domain.common.FacilityVaccineKey;
import com.backend.vaccinebookingsystem.domain.dao.FacilityVaccineDao;
import com.backend.vaccinebookingsystem.domain.dto.FacilityVaccineDto;
import com.backend.vaccinebookingsystem.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = StockService.class)
class StockServiceTest {

    @MockBean
    private StockRepository stockRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private StockService stockService;

    @Test
    void createStockSuccess_Test() {
        FacilityVaccineDao facilityVaccineDao = FacilityVaccineDao.builder()
                .id(FacilityVaccineKey.builder()
                        .facilityId(1L)
                        .vaccineId(1L)
                        .build())
                .stock(100)
                .build();

        FacilityVaccineDto vaccineDto = FacilityVaccineDto.builder()
                .id(FacilityVaccineKey.builder()
                        .facilityId(1L)
                        .vaccineId(1L)
                        .build())
                .stock(100)
                .build();

        when(modelMapper.map(any(), eq(FacilityVaccineDao.class))).thenReturn(facilityVaccineDao);
        when(modelMapper.map(any(), eq(FacilityVaccineDto.class))).thenReturn(vaccineDto);

        ResponseEntity<Object> response = stockService.createStock(FacilityVaccineDto.builder()
                .stock(100)
                .build());

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        FacilityVaccineDto facilityVaccineDto = (FacilityVaccineDto) Objects.requireNonNull(apiResponse).getData();

        assertEquals(100, facilityVaccineDto.getStock());
    }

    @Test
    void createStockException_Test() {
        FacilityVaccineDto vaccineDto = FacilityVaccineDto.builder()
                .id(FacilityVaccineKey.builder()
                        .facilityId(1L)
                        .vaccineId(1L)
                        .build())
                .stock(100)
                .build();

        when(stockRepository.save(any())).thenReturn(NullPointerException.class);

        ResponseEntity<Object> response = stockService.createStock(vaccineDto);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void getStockByIdSuccess_Test() {
        FacilityVaccineDao facilityVaccineDao = FacilityVaccineDao.builder()
                .id(FacilityVaccineKey.builder()
                        .facilityId(1L)
                        .vaccineId(1L)
                        .build())
                .stock(100)
                .build();

        FacilityVaccineDto vaccineDto = FacilityVaccineDto.builder()
                .id(FacilityVaccineKey.builder()
                        .facilityId(1L)
                        .vaccineId(1L)
                        .build())
                .stock(100)
                .build();

        when(stockRepository.findById(anyLong())).thenReturn(Optional.of(facilityVaccineDao));
        when(modelMapper.map(any(), eq(FacilityVaccineDto.class))).thenReturn(vaccineDto);

        ResponseEntity<Object> response = stockService.searchStockById(1L);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    void getStockByIdIsNull_Test() {
        FacilityVaccineDao facilityVaccineDao = FacilityVaccineDao.builder()
                .id(FacilityVaccineKey.builder()
                        .facilityId(1L)
                        .vaccineId(1L)
                        .build())
                .stock(100)
                .build();

        FacilityVaccineDto vaccineDto = FacilityVaccineDto.builder()
                .id(FacilityVaccineKey.builder()
                        .facilityId(1L)
                        .vaccineId(1L)
                        .build())
                .stock(100)
                .build();

        when(stockRepository.findById(anyLong())).thenReturn(Optional.of(facilityVaccineDao));
        when(modelMapper.map(any(), eq(FacilityVaccineDto.class))).thenReturn(vaccineDto);

        ResponseEntity<Object> response = stockService.searchStockById(null);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    void getStockByIdException_Test() {
        FacilityVaccineDao facilityVaccineDao = FacilityVaccineDao.builder()
                .id(FacilityVaccineKey.builder()
                        .facilityId(1L)
                        .vaccineId(1L)
                        .build())
                .stock(100)
                .build();

        when(stockRepository.findById(anyLong())).thenReturn(Optional.of(facilityVaccineDao));

        doThrow(NullPointerException.class).when(stockRepository).findById(any());
        assertThrows(Exception.class, () -> stockService.searchStockById(1L));
    }

    @Test
    void getAllStocksSuccess_Test() {
        FacilityVaccineDao facilityVaccineDao = FacilityVaccineDao.builder()
                .id(FacilityVaccineKey.builder()
                        .facilityId(1L)
                        .vaccineId(1L)
                        .build())
                .stock(100)
                .build();

        FacilityVaccineDto vaccineDto = FacilityVaccineDto.builder()
                .id(FacilityVaccineKey.builder()
                        .facilityId(1L)
                        .vaccineId(1L)
                        .build())
                .stock(100)
                .build();

        when(stockRepository.findAll()).thenReturn(List.of(facilityVaccineDao));
        when(modelMapper.map(any(), eq(FacilityVaccineDto.class))).thenReturn(vaccineDto);

        ResponseEntity<Object> response = stockService.getAllStocks();

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
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
                .id(FacilityVaccineKey.builder()
                        .facilityId(1L)
                        .vaccineId(1L)
                        .build())
                .stock(100)
                .build();

        FacilityVaccineDto vaccineDto = FacilityVaccineDto.builder()
                .id(FacilityVaccineKey.builder()
                        .facilityId(1L)
                        .vaccineId(1L)
                        .build())
                .stock(100)
                .build();

        when(stockRepository.findById(anyLong())).thenReturn(Optional.of(facilityVaccineDao));
        when(modelMapper.map(any(), eq(FacilityVaccineDto.class))).thenReturn(vaccineDto);

        ResponseEntity<Object> response = stockService.updateStockById(1L, FacilityVaccineDto.builder()
                .stock(100)
                .build());

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    void updateStockByIdIsNull_Test() {
        FacilityVaccineDao facilityVaccineDao = FacilityVaccineDao.builder()
                .id(FacilityVaccineKey.builder()
                        .facilityId(1L)
                        .vaccineId(1L)
                        .build())
                .stock(100)
                .build();

        FacilityVaccineDto vaccineDto = FacilityVaccineDto.builder()
                .id(FacilityVaccineKey.builder()
                        .facilityId(1L)
                        .vaccineId(1L)
                        .build())
                .stock(100)
                .build();

        when(stockRepository.findById(anyLong())).thenReturn(Optional.of(facilityVaccineDao));
        when(modelMapper.map(any(), eq(FacilityVaccineDto.class))).thenReturn(vaccineDto);

        ResponseEntity<Object> response = stockService.updateStockById(null, null);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    void updateStockByIdException_Test() {
        FacilityVaccineDao facilityVaccineDao = FacilityVaccineDao.builder()
                .id(FacilityVaccineKey.builder()
                        .facilityId(1L)
                        .vaccineId(1L)
                        .build())
                .stock(100)
                .build();

        when(stockRepository.findById(anyLong())).thenReturn(Optional.of(facilityVaccineDao));

        doThrow(NullPointerException.class).when(stockRepository).findById(any());
        assertThrows(Exception.class, () -> stockService.updateStockById(1L, FacilityVaccineDto.builder()
                .stock(100)
                .build()));
    }

    @Test
    void deleteStockByIdSuccess_Test() {
        FacilityVaccineDao facilityVaccineDao = FacilityVaccineDao.builder()
                .id(FacilityVaccineKey.builder()
                        .facilityId(1L)
                        .vaccineId(1L)
                        .build())
                .stock(100)
                .build();

        when(stockRepository.findById(anyLong())).thenReturn(Optional.of(facilityVaccineDao));

        doNothing().when(stockRepository).delete(any());

        ResponseEntity<Object> response = stockService.deleteStockById(1L);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        verify(stockRepository, times(1)).delete(any());
    }

    @Test
    void deleteStockByIdIsNull_Test() {
        FacilityVaccineDao facilityVaccineDao = FacilityVaccineDao.builder()
                .id(FacilityVaccineKey.builder()
                        .facilityId(1L)
                        .vaccineId(1L)
                        .build())
                .stock(100)
                .build();

        when(stockRepository.findById(anyLong())).thenReturn(Optional.of(facilityVaccineDao));

        doNothing().when(stockRepository).delete(any());

        ResponseEntity<Object> response = stockService.deleteStockById(null);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    void deleteStockByIdException_Test() {
        FacilityVaccineDao facilityVaccineDao = FacilityVaccineDao.builder()
                .id(FacilityVaccineKey.builder()
                        .facilityId(1L)
                        .vaccineId(1L)
                        .build())
                .stock(100)
                .build();

        when(stockRepository.findById(anyLong())).thenReturn(Optional.of(facilityVaccineDao));

        doThrow(NullPointerException.class).when(stockRepository).findById(any());
        assertThrows(Exception.class, () -> stockService.deleteStockById(1L));
    }

}