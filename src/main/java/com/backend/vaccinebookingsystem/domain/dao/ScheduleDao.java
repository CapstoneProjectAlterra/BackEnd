package com.backend.vaccinebookingsystem.domain.dao;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.common.BaseDao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "SCHEDULES")
public class ScheduleDao extends BaseDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vaccination_date", nullable = false)
    private LocalTime vaccinationDate;

    @Column(name = "operational_hour", nullable = false)
    private LocalTime operationalHour;

    @Column(name = "quota", nullable = false)
    private Integer quota;

    @Column(name = "dose", nullable = false)
    private AppConstant.Dose dose;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookingDao> bookingDaoList;

    @ManyToOne
    @JoinColumn(name = "facility_id", referencedColumnName = "id")
    private HealthFacilityDao facility;

    @ManyToOne
    @JoinColumn(name = "vaccine_id", referencedColumnName = "id")
    private VaccineTypeDao vaccine;
}
