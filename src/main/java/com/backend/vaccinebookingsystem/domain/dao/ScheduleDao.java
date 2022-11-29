package com.backend.vaccinebookingsystem.domain.dao;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.common.BaseDao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
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

    @Column(name = "vaccination_date")
    private LocalDate vaccinationDate;

    @Column(name = "operational_hour_start")
    private LocalTime operationalHourStart;

    @Column(name = "operational_hour_end")
    private LocalTime operationalHourEnd;

    @Column(name = "quota")
    private Integer quota;

    @Column(name = "dose")
    @Enumerated(EnumType.STRING)
    private AppConstant.Dose dose;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    private List<BookingDao> bookingDaoList;

    @ManyToOne
    @JoinColumn(name = "facility_id", referencedColumnName = "id")
    @ToString.Exclude
    private HealthFacilityDao facility;

    @ManyToOne
    @JoinColumn(name = "vaccine_id", referencedColumnName = "id")
    @ToString.Exclude
    private VaccineTypeDao vaccine;
}
