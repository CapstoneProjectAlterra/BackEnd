package com.backend.vaccinebookingsystem.util;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.common.ApiResponse;
import com.backend.vaccinebookingsystem.domain.common.ApiResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ResponseUtil {

    private ResponseUtil() {
    }

    public static <T> ResponseEntity<Object> build(AppConstant.ResponseCode responseCode, T data, HttpStatus status) {
        return new ResponseEntity<>(build(responseCode, data), status);
    }

    private static <T> ApiResponse<T> build(AppConstant.ResponseCode responseCode, T data) {
        return ApiResponse.<T>builder()
                .status(ApiResponseStatus.builder()
                        .code(responseCode.getCode())
                        .message(responseCode.getMessage())
                        .build())
                .timestamp(LocalDateTime.now())
                .data(data)
                .build();
    }
}
