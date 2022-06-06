package com.backend.vaccinebookingsystem.service;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.common.ApiResponse;
import com.backend.vaccinebookingsystem.domain.dao.HealthFacilityDao;
import com.backend.vaccinebookingsystem.domain.dto.HealthFacilityDto;
import com.backend.vaccinebookingsystem.repository.HealthFacilityRepository;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = HealthFacilityService.class)
class HealthFacilityServiceTest {

    @MockBean
    private HealthFacilityRepository healthFacilityRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private HealthFacilityService healthFacilityService;

    @Test
    void createHealthFacilitySuccess_Test() {
        HealthFacilityDao healthFacilityDao = HealthFacilityDao.builder()
                .id(1L)
                .facilityName("Facility")
                .build();

        HealthFacilityDto facilityDto = HealthFacilityDto.builder()
                .id(1L)
                .facilityName("Facility")
                .build();

        when(modelMapper.map(any(), eq(HealthFacilityDao.class))).thenReturn(healthFacilityDao);
        when(modelMapper.map(any(), eq(HealthFacilityDto.class))).thenReturn(facilityDto);

        ResponseEntity<Object> response = healthFacilityService.createHealthFacility(HealthFacilityDto.builder()
                        .facilityName("Facility")
                .build());

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        HealthFacilityDto healthFacilityDto = (HealthFacilityDto) Objects.requireNonNull(apiResponse).getData();

        assertEquals(1L, healthFacilityDto.getId());
        assertEquals("Facility", healthFacilityDto.getFacilityName());
    }

    @Test
    void createHealthFacilityException_Test() {
        HealthFacilityDto facilityDto = HealthFacilityDto.builder()
                .id(1L)
                .facilityName("Facility")
                .build();

        when(healthFacilityRepository.save(any())).thenReturn(NullPointerException.class);

        ResponseEntity<Object> response = healthFacilityService.createHealthFacility(facilityDto);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void getHealthFacilityByIdSuccess_Test() {
        HealthFacilityDao healthFacilityDao = HealthFacilityDao.builder()
                .id(1L)
                .facilityName("Facility")
                .build();

        HealthFacilityDto facilityDto = HealthFacilityDto.builder()
                .id(1L)
                .facilityName("Facility")
                .build();

        when(healthFacilityRepository.findById(anyLong())).thenReturn(Optional.of(healthFacilityDao));
        when(modelMapper.map(any(), eq(HealthFacilityDto.class))).thenReturn(facilityDto);

        ResponseEntity<Object> response = healthFacilityService.getHealthFacilityById(1L);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    void getHealthFacilityByIdIsNull_Test() {
        HealthFacilityDao healthFacilityDao = HealthFacilityDao.builder()
                .id(1L)
                .facilityName("Facility")
                .build();

        HealthFacilityDto facilityDto = HealthFacilityDto.builder()
                .id(1L)
                .facilityName("Facility")
                .build();

        when(healthFacilityRepository.findById(anyLong())).thenReturn(Optional.of(healthFacilityDao));
        when(modelMapper.map(any(), eq(HealthFacilityDto.class))).thenReturn(facilityDto);

        ResponseEntity<Object> response = healthFacilityService.getHealthFacilityById(null);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    void getHealthFacilityByIdException_Test() {
        HealthFacilityDao healthFacilityDao = HealthFacilityDao.builder()
                .id(1L)
                .facilityName("Facility")
                .build();

        when(healthFacilityRepository.findById(anyLong())).thenReturn(Optional.of(healthFacilityDao));

        doThrow(NullPointerException.class).when(healthFacilityRepository).findById(any());
        assertThrows(Exception.class, () -> healthFacilityService.getHealthFacilityById(1L));
    }

    @Test
    void getAllHealthFacilitiesSuccess_Test() {
        HealthFacilityDao healthFacilityDao = HealthFacilityDao.builder()
                .id(1L)
                .facilityName("Facility")
                .build();

        HealthFacilityDto facilityDto = HealthFacilityDto.builder()
                .id(1L)
                .facilityName("Facility")
                .build();

        when(healthFacilityRepository.findAll()).thenReturn(List.of(healthFacilityDao));
        when(modelMapper.map(any(), eq(HealthFacilityDto.class))).thenReturn(facilityDto);

        ResponseEntity<Object> response = healthFacilityService.getAllHealthFacilities();

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    void getAllHealthFacilitiesException_Test() {
        when(healthFacilityRepository.findAll()).thenThrow(NullPointerException.class);

        ResponseEntity<Object> response = healthFacilityService.getAllHealthFacilities();

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void updateHealthFacilityByIdSuccess_Test() {
        HealthFacilityDao healthFacilityDao = HealthFacilityDao.builder()
                .id(1L)
                .facilityName("Facility")
                .build();

        HealthFacilityDto facilityDto = HealthFacilityDto.builder()
                .id(1L)
                .facilityName("Facility")
                .build();

        when(healthFacilityRepository.findById(anyLong())).thenReturn(Optional.of(healthFacilityDao));
        when(modelMapper.map(any(), eq(HealthFacilityDto.class))).thenReturn(facilityDto);

        ResponseEntity<Object> response = healthFacilityService.updateHealthFacilityById(1L, HealthFacilityDto.builder()
                        .facilityName("Updated Facility")
                .build());

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    void updateHealthFacilityByIdIsNull_Test() {
        HealthFacilityDao healthFacilityDao = HealthFacilityDao.builder()
                .id(1L)
                .facilityName("Facility")
                .build();

        HealthFacilityDto facilityDto = HealthFacilityDto.builder()
                .id(1L)
                .facilityName("Facility")
                .build();

        when(healthFacilityRepository.findById(anyLong())).thenReturn(Optional.of(healthFacilityDao));
        when(modelMapper.map(any(), eq(HealthFacilityDto.class))).thenReturn(facilityDto);

        ResponseEntity<Object> response = healthFacilityService.updateHealthFacilityById(null, null);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    void updateHealthFacilityByIdException_Test() {
        HealthFacilityDao healthFacilityDao = HealthFacilityDao.builder()
                .id(1L)
                .facilityName("Facility")
                .build();

        when(healthFacilityRepository.findById(anyLong())).thenReturn(Optional.of(healthFacilityDao));

        doThrow(NullPointerException.class).when(healthFacilityRepository).findById(any());
        assertThrows(Exception.class, () -> healthFacilityService.updateHealthFacilityById(1L, HealthFacilityDto.builder()
                .facilityName("Updated Facility")
                .build()));
    }

    @Test
    void deleteHealthFacilityByIdSuccess_Test() {
        HealthFacilityDao healthFacilityDao = HealthFacilityDao.builder()
                .id(1L)
                .facilityName("Facility")
                .build();

        when(healthFacilityRepository.findById(anyLong())).thenReturn(Optional.of(healthFacilityDao));

        doNothing().when(healthFacilityRepository).delete(any());

        ResponseEntity<Object> response = healthFacilityService.deleteHealthFacilityById(1L);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        verify(healthFacilityRepository, times(1)).delete(any());
    }

    @Test
    void deleteHealthFacilityByIdIsNull_Test() {
        HealthFacilityDao healthFacilityDao = HealthFacilityDao.builder()
                .id(1L)
                .facilityName("Facility")
                .build();

        when(healthFacilityRepository.findById(anyLong())).thenReturn(Optional.of(healthFacilityDao));

        doNothing().when(healthFacilityRepository).delete(any());

        ResponseEntity<Object> response = healthFacilityService.deleteHealthFacilityById(null);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    void deleteHealthFacilityByIdException_Test() {
        HealthFacilityDao healthFacilityDao = HealthFacilityDao.builder()
                .id(1L)
                .facilityName("Facility")
                .build();

        when(healthFacilityRepository.findById(anyLong())).thenReturn(Optional.of(healthFacilityDao));

        doThrow(NullPointerException.class).when(healthFacilityRepository).findById(any());
        assertThrows(Exception.class, () -> healthFacilityService.deleteHealthFacilityById(1L));
    }
}