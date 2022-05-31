package com.backend.vaccinebookingsystem.domain.dao;

import com.backend.vaccinebookingsystem.domain.common.BaseDao;
import com.backend.vaccinebookingsystem.domain.common.FacilityVaccineKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "FACILITY_VACCINES")
public class FacilityVaccineDao extends BaseDao {

    @EmbeddedId
    private FacilityVaccineKey id;

    @ManyToOne
    @MapsId("facilityId")
    @JoinColumn(name = "facility_id")
    private HealthFacilityDao facilityVaccine;

    @ManyToOne
    @MapsId("vaccineId")
    @JoinColumn(name = "vaccine_id")
    private VaccineTypeDao vaccineFacility;

    @Column(name = "stock", nullable = false)
    private Integer stock;
}
