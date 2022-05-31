package com.backend.vaccinebookingsystem.domain.dao;

import com.backend.vaccinebookingsystem.domain.common.BaseDao;
import com.backend.vaccinebookingsystem.domain.common.FamilyBookingKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "BOOKING_DETAIL")
public class BookingDetailDao extends BaseDao {

    @EmbeddedId
    private FamilyBookingKey id;

    @ManyToOne
    @MapsId("familyId")
    @JoinColumn(name = "family_id")
    private FamilyDao family;

    @ManyToOne
    @MapsId("bookingId")
    @JoinColumn(name = "booking_id")
    private BookingDao booking;

    @Column(name = "booking_status", nullable = false)
    private String bookingStatus;
}
