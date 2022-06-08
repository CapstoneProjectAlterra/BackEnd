package com.backend.vaccinebookingsystem.domain.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class FacilityVaccineKey implements Serializable {

    @Column(name = "facility_id")
    private Long facilityId;

    @Column(name = "vaccine_id")
    private Long vaccineId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FacilityVaccineKey)) return false;
        FacilityVaccineKey that = (FacilityVaccineKey) o;
        return getFacilityId().equals(that.getFacilityId()) && getVaccineId().equals(that.getVaccineId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFacilityId(), getVaccineId());
    }
}
