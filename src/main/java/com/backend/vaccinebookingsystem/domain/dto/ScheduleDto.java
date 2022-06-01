package com.backend.vaccinebookingsystem.domain.dto;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ScheduleDto {

    private Long id;

    private LocalDate vaccinationDate;

    private LocalTime operationalHour;

    private Integer quota;

    private AppConstant.Dose dose;

}
