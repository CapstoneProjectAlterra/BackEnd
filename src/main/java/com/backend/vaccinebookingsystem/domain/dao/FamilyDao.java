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

    @Column(name = "status_in_family")
    @Enumerated(EnumType.STRING)
    private AppConstant.FamilyStatus statusInFamily;

    @Column(name = "NIK")
    private String NIK;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "gander")
    @Enumerated(EnumType.STRING)
    private AppConstant.Gender gender;

    @Column(name = "place_of_birth")
    private String placeOfBirth;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "residence_address")
    private String residenceAddress;

    @Column(name = "id_card_address")
    private String idCardAddress;

    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "userId")
    private ProfileDao profile;

    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookingDetailDao> bookingDaoList;
}
