package com.backend.vaccinebookingsystem.domain.dao;

import com.backend.vaccinebookingsystem.domain.common.BaseDao;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "FACILITY_IMAGES")
@SQLDelete(sql = "UPDATE FACILITY_IMAGES SET is_deleted = true WHERE facility_id =?")
@Where(clause = "is_deleted = false")
public class HealthFacilityImageDao extends BaseDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facilityId;

    @Lob
    @Column(name = "base64")
    private String base64;

    @Column(name = "content_type")
    private String contentType;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", nullable = false)
    @ToString.Exclude
    private HealthFacilityDao facility;
}
