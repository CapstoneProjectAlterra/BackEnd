package com.backend.vaccinebookingsystem.domain.dao;

import com.backend.vaccinebookingsystem.domain.common.BaseDao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "HEALTH_FACILITIES")
@SQLDelete(sql = "UPDATE HEALTH_FACILITIES SET is_deleted = true WHERE id =?")
@Where(clause = "is_deleted = false")
public class HealthFacilityDao extends BaseDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "facility_name", nullable = false)
    private String facilityName;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "street_name", nullable = false)
    private String streetName;

    @Column(name = "office_number")
    private String officeNumber;

    @Column(name = "village_name", nullable = false)
    private String villageName;

    @Column(name = "district", nullable = false)
    private String district;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "province", nullable = false)
    private String province;

    @Column(name = "postal_code", nullable = false)
    private Integer postalCode;

    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<ScheduleDao> scheduleDaoList;

    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "userId")
    private ProfileDao profile;

    @OneToMany(mappedBy = "facilityVaccine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FacilityVaccineDao> facilityVaccineDaoList;
}
