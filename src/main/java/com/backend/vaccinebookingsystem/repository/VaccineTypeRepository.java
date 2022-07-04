package com.backend.vaccinebookingsystem.repository;

import com.backend.vaccinebookingsystem.domain.dao.VaccineTypeDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VaccineTypeRepository extends JpaRepository<VaccineTypeDao, Long> {

    @Query("select v from VaccineTypeDao v where v.vaccineName = ?1")
    Optional<VaccineTypeDao> findByVaccineName(String vaccineName);
}
