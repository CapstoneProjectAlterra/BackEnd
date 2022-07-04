package com.backend.vaccinebookingsystem.domain.dto;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BookingDetailDto {

    private Long bookingId;

    private Long familyId;

    private AppConstant.BookingStatus bookingStatus;

    private FamilyDto family;

    private BookingDto booking;

}
