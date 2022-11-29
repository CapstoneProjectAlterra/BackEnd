package com.backend.vaccinebookingsystem.domain.common;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class FacilityVaccineKey implements Serializable {

    private Long facilityId;

    private Long vaccineId;
}
