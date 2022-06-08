package com.backend.vaccinebookingsystem.service;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.common.ApiResponse;
import com.backend.vaccinebookingsystem.domain.dao.VaccineTypeDao;
import com.backend.vaccinebookingsystem.domain.dto.VaccineTypeDto;
import com.backend.vaccinebookingsystem.repository.VaccineTypeRepository;
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
@SpringBootTest(classes = VaccineTypeService.class)
class VaccineTypeServiceTest {

    @MockBean
    private VaccineTypeRepository vaccineTypeRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private VaccineTypeService vaccineTypeService;

    @Test
    void createVaccineTypeSuccess_Test() {
        VaccineTypeDao vaccineTypeDao = VaccineTypeDao.builder()
                .id(1L)
                .vaccineName("Vaccine")
                .build();

        VaccineTypeDto typeDto = VaccineTypeDto.builder()
                .id(1L)
                .vaccineName("Vaccine")
                .build();

        when(modelMapper.map(any(), eq(VaccineTypeDao.class))).thenReturn(vaccineTypeDao);
        when(modelMapper.map(any(), eq(VaccineTypeDto.class))).thenReturn(typeDto);

        ResponseEntity<Object> response = vaccineTypeService.createVaccineType(VaccineTypeDto.builder()
                .vaccineName("Vaccine")
                .build());

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        VaccineTypeDto vaccineTypeDto = (VaccineTypeDto) Objects.requireNonNull(apiResponse).getData();

        assertEquals(1L, vaccineTypeDto.getId());
        assertEquals("Vaccine", vaccineTypeDto.getVaccineName());
    }

    @Test
    void createVaccineTypeException_Test() {
        VaccineTypeDto typeDto = VaccineTypeDto.builder()
                .id(1L)
                .vaccineName("Vaccine")
                .build();

        when(vaccineTypeRepository.save(any())).thenReturn(NullPointerException.class);

        ResponseEntity<Object> response = vaccineTypeService.createVaccineType(typeDto);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void getVaccineTypeByIdSuccess_Test() {
        VaccineTypeDao vaccineTypeDao = VaccineTypeDao.builder()
                .id(1L)
                .vaccineName("Vaccine")
                .build();

        VaccineTypeDto typeDto = VaccineTypeDto.builder()
                .id(1L)
                .vaccineName("Vaccine")
                .build();

        when(vaccineTypeRepository.findById(anyLong())).thenReturn(Optional.of(vaccineTypeDao));
        when(modelMapper.map(any(), eq(VaccineTypeDto.class))).thenReturn(typeDto);

        ResponseEntity<Object> response = vaccineTypeService.getVaccineTypeById(1L);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    void getVaccineTypeByIdIsNull_Test() {
        VaccineTypeDao vaccineTypeDao = VaccineTypeDao.builder()
                .id(1L)
                .vaccineName("Vaccine")
                .build();

        VaccineTypeDto typeDto = VaccineTypeDto.builder()
                .id(1L)
                .vaccineName("Vaccine")
                .build();

        when(vaccineTypeRepository.findById(anyLong())).thenReturn(Optional.of(vaccineTypeDao));
        when(modelMapper.map(any(), eq(VaccineTypeDto.class))).thenReturn(typeDto);

        ResponseEntity<Object> response = vaccineTypeService.getVaccineTypeById(null);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    void getVaccineTypeIdException_Test() {
        VaccineTypeDao vaccineTypeDao = VaccineTypeDao.builder()
                .id(1L)
                .vaccineName("Vaccine")
                .build();

        when(vaccineTypeRepository.findById(anyLong())).thenReturn(Optional.of(vaccineTypeDao));

        doThrow(NullPointerException.class).when(vaccineTypeRepository).findById(any());
        assertThrows(Exception.class, () -> vaccineTypeService.getVaccineTypeById(1L));
    }

    @Test
    void getAllVaccineTypesSuccess_Test() {
        VaccineTypeDao vaccineTypeDao = VaccineTypeDao.builder()
                .id(1L)
                .vaccineName("Vaccine")
                .build();

        VaccineTypeDto typeDto = VaccineTypeDto.builder()
                .id(1L)
                .vaccineName("Vaccine")
                .build();

        when(vaccineTypeRepository.findAll()).thenReturn(List.of(vaccineTypeDao));
        when(modelMapper.map(any(), eq(VaccineTypeDto.class))).thenReturn(typeDto);

        ResponseEntity<Object> response = vaccineTypeService.getAllVaccineTypes();

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    void getAllVaccineTypesException_Test() {
        when(vaccineTypeRepository.findAll()).thenThrow(NullPointerException.class);

        ResponseEntity<Object> response = vaccineTypeService.getAllVaccineTypes();

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void updateVaccineTypeByIdSuccess_Test() {
        VaccineTypeDao vaccineTypeDao = VaccineTypeDao.builder()
                .id(1L)
                .vaccineName("Vaccine")
                .build();

        VaccineTypeDto typeDto = VaccineTypeDto.builder()
                .id(1L)
                .vaccineName("Vaccine")
                .build();

        when(vaccineTypeRepository.findById(anyLong())).thenReturn(Optional.of(vaccineTypeDao));
        when(modelMapper.map(any(), eq(VaccineTypeDto.class))).thenReturn(typeDto);

        ResponseEntity<Object> response = vaccineTypeService.updateVaccineTypeById(1L, VaccineTypeDto.builder()
                .vaccineName("Updated Vaccine")
                .build());

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    void updateVaccineTypeByIdIsNull_Test() {
        VaccineTypeDao vaccineTypeDao = VaccineTypeDao.builder()
                .id(1L)
                .vaccineName("Vaccine")
                .build();

        VaccineTypeDto typeDto = VaccineTypeDto.builder()
                .id(1L)
                .vaccineName("Vaccine")
                .build();

        when(vaccineTypeRepository.findById(anyLong())).thenReturn(Optional.of(vaccineTypeDao));
        when(modelMapper.map(any(), eq(VaccineTypeDto.class))).thenReturn(typeDto);

        ResponseEntity<Object> response = vaccineTypeService.updateVaccineTypeById(null, null);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    void updateVaccineTypeByIdException_Test() {
        VaccineTypeDao vaccineTypeDao = VaccineTypeDao.builder()
                .id(1L)
                .vaccineName("Vaccine")
                .build();

        when(vaccineTypeRepository.findById(anyLong())).thenReturn(Optional.of(vaccineTypeDao));

        doThrow(NullPointerException.class).when(vaccineTypeRepository).findById(any());
        assertThrows(Exception.class, () -> vaccineTypeService.updateVaccineTypeById(1L, VaccineTypeDto.builder()
                .vaccineName("Updated Vaccine")
                .build()));
    }

    @Test
    void deleteVaccineTypeByIdSuccess_Test() {
        VaccineTypeDao vaccineTypeDao = VaccineTypeDao.builder()
                .id(1L)
                .vaccineName("Vaccine")
                .build();

        when(vaccineTypeRepository.findById(anyLong())).thenReturn(Optional.of(vaccineTypeDao));

        doNothing().when(vaccineTypeRepository).delete(any());

        ResponseEntity<Object> response = vaccineTypeService.deleteVaccineTypeById(1L);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        verify(vaccineTypeRepository, times(1)).delete(any());
    }

    @Test
    void deleteVaccineTypeByIdIsNull_Test() {
        VaccineTypeDao vaccineTypeDao = VaccineTypeDao.builder()
                .id(1L)
                .vaccineName("Vaccine")
                .build();

        when(vaccineTypeRepository.findById(anyLong())).thenReturn(Optional.of(vaccineTypeDao));

        doNothing().when(vaccineTypeRepository).delete(any());

        ResponseEntity<Object> response = vaccineTypeService.deleteVaccineTypeById(null);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    void deleteVaccineTypeByIdException_Test() {
        VaccineTypeDao vaccineTypeDao = VaccineTypeDao.builder()
                .id(1L)
                .vaccineName("Vaccine")
                .build();

        when(vaccineTypeRepository.findById(anyLong())).thenReturn(Optional.of(vaccineTypeDao));

        doThrow(NullPointerException.class).when(vaccineTypeRepository).findById(any());
        assertThrows(Exception.class, () -> vaccineTypeService.deleteVaccineTypeById(1L));
    }

}