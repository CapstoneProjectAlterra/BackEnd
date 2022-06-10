package com.backend.vaccinebookingsystem.domain.dao;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.common.BaseDao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "SCHEDULES")
@SQLDelete(sql = "UPDATE SCHEDULES SET is_deleted = true WHERE id =?")
@Where(clause = "is_deleted = false")
public class ScheduleDao extends BaseDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vaccination_date", nullable = false)
    private LocalDate vaccinationDate;

    @Column(name = "operational_hour_start", nullable = false)
    private LocalTime operationalHourStart;

    @Column(name = "operational_hour_end", nullable = false)
    private LocalTime operationalHourEnd;

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
