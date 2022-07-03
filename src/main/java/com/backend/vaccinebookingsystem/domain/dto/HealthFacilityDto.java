package com.backend.vaccinebookingsystem.domain.dto;

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
public class HealthFacilityDto {

    private Long id;

    private String facilityName;

    private String imgUrl;

    private String streetName;

    private String officeNumber;

    private String villageName;

    private String district;

    private String city;

    private String province;

    private Integer postalCode;

    private ProfileDto profile;

}
