package com.backend.vaccinebookingsystem.repository;

import com.backend.vaccinebookingsystem.domain.common.FamilyBookingKey;
import com.backend.vaccinebookingsystem.domain.dao.BookingDetailDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingDetailRepository extends JpaRepository<BookingDetailDao, FamilyBookingKey> {

    Optional<BookingDetailDao> findTopByBookingId(Long bookingId);

    Optional<BookingDetailDao> findTopByFamilyId(Long familyId);

    @Query("select b from BookingDetailDao b where b.bookingId = ?1 and b.familyId = ?2")
    Optional<BookingDetailDao> findByBookingIdAndFamilyId(Long bookingId, Long familyId);
}
