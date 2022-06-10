package com.backend.vaccinebookingsystem.service;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.common.ApiResponse;
import com.backend.vaccinebookingsystem.domain.dao.FamilyDao;
import com.backend.vaccinebookingsystem.domain.dto.FamilyDto;
import com.backend.vaccinebookingsystem.repository.FamilyRepository;
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
@SpringBootTest(classes = FamilyService.class)
class FamilyServiceTest {

    @MockBean
    private FamilyRepository familyRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private FamilyService familyService;

    @Test
    void createFamilySuccess_Test() {
        FamilyDao facilityDao = FamilyDao.builder()
                .id(1L)
                .fullName("Patient Name")
                .build();

        FamilyDto familyDto = FamilyDto.builder()
                .id(1L)
                .fullName("Patient Name")
                .build();

        when(modelMapper.map(any(), eq(FamilyDao.class))).thenReturn(facilityDao);
        when(modelMapper.map(any(), eq(FamilyDto.class))).thenReturn(familyDto);

        ResponseEntity<Object> response = familyService.createFamily(FamilyDto.builder()
                .fullName("Patient Name")
                .build());

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        FamilyDto dto = (FamilyDto) Objects.requireNonNull(apiResponse).getData();

        assertEquals(1L, dto.getId());
        assertEquals("Patient Name", dto.getFullName());
    }

    @Test
    void createFamilyException_Test() {
        FamilyDto familyDto = FamilyDto.builder()
                .id(1L)
                .fullName("Patient Name")
                .build();

        when(familyRepository.save(any())).thenReturn(NullPointerException.class);

        ResponseEntity<Object> response = familyService.createFamily(familyDto);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void getFamilyByIdSuccess_Test() {
        FamilyDao facilityDao = FamilyDao.builder()
                .id(1L)
                .fullName("Patient Name")
                .build();

        FamilyDto familyDto = FamilyDto.builder()
                .id(1L)
                .fullName("Patient Name")
                .build();

        when(familyRepository.findById(anyLong())).thenReturn(Optional.of(facilityDao));
        when(modelMapper.map(any(), eq(FamilyDto.class))).thenReturn(familyDto);

        ResponseEntity<Object> response = familyService.getFamilyById(1L);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    void getFamilyByIdIsNull_Test() {
        FamilyDao facilityDao = FamilyDao.builder()
                .id(1L)
                .fullName("Patient Name")
                .build();

        FamilyDto familyDto = FamilyDto.builder()
                .id(1L)
                .fullName("Patient Name")
                .build();

        when(familyRepository.findById(anyLong())).thenReturn(Optional.of(facilityDao));
        when(modelMapper.map(any(), eq(FamilyDto.class))).thenReturn(familyDto);

        ResponseEntity<Object> response = familyService.getFamilyById(null);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    void getFamilyByIdException_Test() {
        FamilyDao facilityDao = FamilyDao.builder()
                .id(1L)
                .fullName("Patient Name")
                .build();

        when(familyRepository.findById(anyLong())).thenReturn(Optional.of(facilityDao));

        doThrow(NullPointerException.class).when(familyRepository).findById(any());
        assertThrows(Exception.class, () -> familyService.getFamilyById(1L));
    }

    @Test
    void getAllFamiliesSuccess_Test() {
        FamilyDao facilityDao = FamilyDao.builder()
                .id(1L)
                .fullName("Patient Name")
                .build();

        FamilyDto familyDto = FamilyDto.builder()
                .id(1L)
                .fullName("Patient Name")
                .build();

        when(familyRepository.findAll()).thenReturn(List.of(facilityDao));
        when(modelMapper.map(any(), eq(FamilyDto.class))).thenReturn(familyDto);

        ResponseEntity<Object> response = familyService.getAllFamilies();

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    void getAllFamiliesException_Test() {
        when(familyRepository.findAll()).thenThrow(NullPointerException.class);

        ResponseEntity<Object> response = familyService.getAllFamilies();

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void updateFamilyByIdSuccess_Test() {
        FamilyDao facilityDao = FamilyDao.builder()
                .id(1L)
                .fullName("Patient Name")
                .build();

        FamilyDto familyDto = FamilyDto.builder()
                .id(1L)
                .fullName("Patient Name")
                .build();

        when(familyRepository.findById(anyLong())).thenReturn(Optional.of(facilityDao));
        when(modelMapper.map(any(), eq(FamilyDto.class))).thenReturn(familyDto);

        ResponseEntity<Object> response = familyService.updateFamilyById(1L, FamilyDto.builder()
                .fullName("Patient Name")
                .build());

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    void updateFamilyByIdIsNull_Test() {
        FamilyDao facilityDao = FamilyDao.builder()
                .id(1L)
                .fullName("Patient Name")
                .build();

        FamilyDto familyDto = FamilyDto.builder()
                .id(1L)
                .fullName("Patient Name")
                .build();

        when(familyRepository.findById(anyLong())).thenReturn(Optional.of(facilityDao));
        when(modelMapper.map(any(), eq(FamilyDto.class))).thenReturn(familyDto);

        ResponseEntity<Object> response = familyService.updateFamilyById(null, null);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    void updateFamilyByIdException_Test() {
        FamilyDao facilityDao = FamilyDao.builder()
                .id(1L)
                .fullName("Patient Name")
                .build();

        when(familyRepository.findById(anyLong())).thenReturn(Optional.of(facilityDao));

        doThrow(NullPointerException.class).when(familyRepository).findById(any());
        assertThrows(Exception.class, () -> familyService.updateFamilyById(1L, FamilyDto.builder()
                .fullName("Patient Name")
                .build()));
    }

    @Test
    void deleteFamilyByIdSuccess_Test() {
        FamilyDao facilityDao = FamilyDao.builder()
                .id(1L)
                .fullName("Patient Name")
                .build();

        when(familyRepository.findById(anyLong())).thenReturn(Optional.of(facilityDao));

        doNothing().when(familyRepository).delete(any());

        ResponseEntity<Object> response = familyService.deleteFamilyById(1L);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        verify(familyRepository, times(1)).delete(any());
    }

    @Test
    void deleteFamilyByIdIsNull_Test() {
        FamilyDao facilityDao = FamilyDao.builder()
                .id(1L)
                .fullName("Patient Name")
                .build();

        when(familyRepository.findById(anyLong())).thenReturn(Optional.of(facilityDao));

        doNothing().when(familyRepository).delete(any());

        ResponseEntity<Object> response = familyService.deleteFamilyById(null);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    void deleteFamilyByIdException_Test() {
        FamilyDao facilityDao = FamilyDao.builder()
                .id(1L)
                .fullName("Patient Name")
                .build();

        when(familyRepository.findById(anyLong())).thenReturn(Optional.of(facilityDao));

        doThrow(NullPointerException.class).when(familyRepository).findById(any());
        assertThrows(Exception.class, () -> familyService.deleteFamilyById(1L));
    }

}