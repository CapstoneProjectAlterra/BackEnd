package com.backend.vaccinebookingsystem.domain.dao;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.common.BaseDao;
import com.backend.vaccinebookingsystem.domain.common.FamilyBookingKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "BOOKING_DETAIL")
@SQLDelete(sql = "UPDATE BOOKING_DETAIL SET is_deleted = true WHERE id =?")
@Where(clause = "is_deleted = false")
@IdClass(FamilyBookingKey.class)
public class BookingDetailDao extends BaseDao {

    @Id
    @Column(name = "booking_id", nullable = false)
    private Long bookingId;

    @Id
    @Column(name = "family_id", nullable = false)
    private Long familyId;

    @ManyToOne
    @MapsId("familyId")
    @JoinColumn(name = "family_id")
    private FamilyDao family;

    @ManyToOne
    @MapsId("bookingId")
    @JoinColumn(name = "booking_id")
    private BookingDao booking;

    @Column(name = "booking_status", nullable = false)
    private AppConstant.BookingStatus bookingStatus;
}
