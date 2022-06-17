package com.backend.vaccinebookingsystem.service;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.common.ApiResponse;
import com.backend.vaccinebookingsystem.domain.dao.ScheduleDao;
import com.backend.vaccinebookingsystem.domain.dto.ScheduleDto;
import com.backend.vaccinebookingsystem.repository.ScheduleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ScheduleService.class})
@ExtendWith(SpringExtension.class)
class ScheduleServiceTest {
    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ScheduleService scheduleService;

    @Test
    void createScheduleSuccess_Test() {
        ScheduleDao scheduleDao = ScheduleDao.builder()
                .id(1L)
                .quota(100)
                .build();

        ScheduleDto dto = ScheduleDto.builder()
                .id(1L)
                .quota(100)
                .build();

        when(modelMapper.map(any(), eq(ScheduleDao.class))).thenReturn(scheduleDao);
        when(modelMapper.map(any(), eq(ScheduleDto.class))).thenReturn(dto);

        ResponseEntity<Object> response = scheduleService.createSchedule(ScheduleDto.builder()
                .quota(100)
                .build());

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        ScheduleDto data = (ScheduleDto) Objects.requireNonNull(apiResponse).getData();

        assertEquals(1L, data.getId());
        assertEquals(100, data.getQuota());
    }

    @Test
    void createScheduleException_Test() {
        ScheduleDto dto = ScheduleDto.builder()
                .id(1L)
                .quota(100)
                .build();

        when(scheduleRepository.save(any())).thenReturn(NullPointerException.class);

        ResponseEntity<Object> response = scheduleService.createSchedule(dto);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void getScheduleByIdSuccess_Test() {
        ScheduleDao scheduleDao = ScheduleDao.builder()
                .id(1L)
                .quota(100)
                .build();

        ScheduleDto dto = ScheduleDto.builder()
                .id(1L)
                .quota(100)
                .build();

        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.of(scheduleDao));
        when(modelMapper.map(any(), eq(ScheduleDto.class))).thenReturn(dto);

        ResponseEntity<Object> response = scheduleService.getScheduleById(1L);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    void getScheduleByIdIsNull_Test() {
        ScheduleDao scheduleDao = ScheduleDao.builder()
                .id(1L)
                .quota(100)
                .build();

        ScheduleDto dto = ScheduleDto.builder()
                .id(1L)
                .quota(100)
                .build();

        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.of(scheduleDao));
        when(modelMapper.map(any(), eq(ScheduleDto.class))).thenReturn(dto);

        ResponseEntity<Object> response = scheduleService.getScheduleById(null);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    void getScheduleByIdException_Test() {
        ScheduleDao scheduleDao = ScheduleDao.builder()
                .id(1L)
                .quota(100)
                .build();

        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.of(scheduleDao));

        doThrow(NullPointerException.class).when(scheduleRepository).findById(any());
        assertThrows(Exception.class, () -> scheduleService.getScheduleById(1L));
    }

    @Test
    void getAllSchedulesSuccess_Test() {
        ScheduleDao scheduleDao = ScheduleDao.builder()
                .id(1L)
                .quota(100)
                .build();

        ScheduleDto dto = ScheduleDto.builder()
                .id(1L)
                .quota(100)
                .build();

        when(scheduleRepository.findAll()).thenReturn(List.of(scheduleDao));
        when(modelMapper.map(any(), eq(ScheduleDto.class))).thenReturn(dto);

        ResponseEntity<Object> response = scheduleService.getAllSchedules();

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    void getAllSchedulesException_Test() {
        when(scheduleRepository.findAll()).thenThrow(NullPointerException.class);

        ResponseEntity<Object> response = scheduleService.getAllSchedules();

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void updateScheduleByIdSuccess_Test() {
        ScheduleDao scheduleDao = ScheduleDao.builder()
                .id(1L)
                .quota(100)
                .build();

        ScheduleDto dto = ScheduleDto.builder()
                .id(1L)
                .quota(100)
                .build();

        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.of(scheduleDao));
        when(modelMapper.map(any(), eq(ScheduleDto.class))).thenReturn(dto);

        ResponseEntity<Object> response = scheduleService.updateScheduleById(1L, ScheduleDto.builder()
                .quota(100)
                .build());

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    void updateScheduleByIdIsNull_Test() {
        ScheduleDao scheduleDao = ScheduleDao.builder()
                .id(1L)
                .quota(100)
                .build();

        ScheduleDto dto = ScheduleDto.builder()
                .id(1L)
                .quota(100)
                .build();

        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.of(scheduleDao));
        when(modelMapper.map(any(), eq(ScheduleDto.class))).thenReturn(dto);

        ResponseEntity<Object> response = scheduleService.updateScheduleById(null, null);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    void updateScheduleByIdException_Test() {
        ScheduleDao scheduleDao = ScheduleDao.builder()
                .id(1L)
                .quota(100)
                .build();

        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.of(scheduleDao));

        doThrow(NullPointerException.class).when(scheduleRepository).findById(any());
        assertThrows(Exception.class, () -> scheduleService.updateScheduleById(1L, ScheduleDto.builder()
                .quota(100)
                .build()));
    }

    @Test
    void deleteScheduleByIdSuccess_Test() {
        ScheduleDao scheduleDao = ScheduleDao.builder()
                .id(1L)
                .quota(100)
                .build();

        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.of(scheduleDao));

        doNothing().when(scheduleRepository).delete(any());

        ResponseEntity<Object> response = scheduleService.deleteScheduleById(1L);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        verify(scheduleRepository, times(1)).delete(any());
    }

    @Test
    void deleteScheduleByIdIsNull_Test() {
        ScheduleDao scheduleDao = ScheduleDao.builder()
                .id(1L)
                .quota(100)
                .build();

        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.of(scheduleDao));

        doNothing().when(scheduleRepository).delete(any());

        ResponseEntity<Object> response = scheduleService.deleteScheduleById(null);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    void deleteScheduleByIdException_Test() {
        ScheduleDao scheduleDao = ScheduleDao.builder()
                .id(1L)
                .quota(100)
                .build();

        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.of(scheduleDao));

        doThrow(NullPointerException.class).when(scheduleRepository).findById(any());
        assertThrows(Exception.class, () -> scheduleService.deleteScheduleById(1L));
    }
}

