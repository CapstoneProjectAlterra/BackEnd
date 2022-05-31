package com.backend.vaccinebookingsystem.domain.dto;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FamilyDto {

    private Long id;

    private AppConstant.FamilyStatus statusInFamily;

    private String NIK;

    private String fullName;

    private String email;

    private String phoneNumber;

    private AppConstant.Gander gander;

    private LocalDate dateOfBirth;

    private String residenceAddress;

    private String idCardAddress;
}
