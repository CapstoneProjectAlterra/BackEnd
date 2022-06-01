package com.backend.vaccinebookingsystem.domain.dao;

import com.backend.vaccinebookingsystem.domain.common.BaseDao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "VACCINE_TYPES")
public class VaccineTypeDao extends BaseDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vaccine_name", nullable = false)
    private String vaccineName;

    @OneToMany(mappedBy = "vaccine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ScheduleDao> scheduleDaoList;

    @OneToMany(mappedBy = "vaccineFacility", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FacilityVaccineDao> facilityVaccineDaoList;
}
