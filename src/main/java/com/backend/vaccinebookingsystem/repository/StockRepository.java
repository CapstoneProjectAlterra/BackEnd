package com.backend.vaccinebookingsystem.repository;

import com.backend.vaccinebookingsystem.domain.dao.FacilityVaccineDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<FacilityVaccineDao, Long> {
}
