package com.backend.vaccinebookingsystem.domain.dao;

import com.backend.vaccinebookingsystem.domain.common.BaseDao;
import com.backend.vaccinebookingsystem.domain.common.FacilityVaccineKey;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "FACILITY_VACCINES")
@SQLDelete(sql = "UPDATE FACILITY_VACCINES SET is_deleted = true WHERE facility_id =? AND vaccine_id =?")
@Where(clause = "is_deleted = false")
@IdClass(FacilityVaccineKey.class)
public class FacilityVaccineDao extends BaseDao {

    @Id
    @Column(name = "facility_id", nullable = false)
    private Long facilityId;

    @Id
    @Column(name = "vaccine_id", nullable = false)
    private Long vaccineId;

    @ManyToOne
    @MapsId("facilityId")
    @JoinColumn(name = "facility_id")
    @ToString.Exclude
    private HealthFacilityDao facility;

    @ManyToOne
    @MapsId("vaccineId")
    @JoinColumn(name = "vaccine_id")
    @ToString.Exclude
    private VaccineTypeDao vaccine;

    @Column(name = "stock", nullable = false)
    private Integer stock;
}
