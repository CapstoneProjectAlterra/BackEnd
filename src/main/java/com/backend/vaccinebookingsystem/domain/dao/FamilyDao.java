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
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "FAMILY_DATA")
@SQLDelete(sql = "UPDATE FAMILY_DATA SET is_deleted = true WHERE id =?")
@Where(clause = "is_deleted = false")
public class FamilyDao extends BaseDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status_in_family", nullable = false)
    private AppConstant.FamilyStatus statusInFamily;

    @Column(name = "NIK", nullable = false)
    private String NIK;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "gander", nullable = false)
    private AppConstant.Gender gender;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "residence_address", nullable = false)
    private String residenceAddress;

    @Column(name = "id_card_address", nullable = false)
    private String idCardAddress;

    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "userId")
    private ProfileDao profile;

    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookingDetailDao> bookingDaoList;
}
