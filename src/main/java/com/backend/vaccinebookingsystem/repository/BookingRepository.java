package com.backend.vaccinebookingsystem.repository;

import com.backend.vaccinebookingsystem.domain.dao.BookingDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<BookingDao, Long> {

    @Query("SELECT COUNT(u) FROM BookingDao u WHERE u.schedule.id=:scheduleId")
    Optional<Long> countBookingByScheduleId(Long scheduleId);
}
