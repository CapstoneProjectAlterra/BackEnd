package com.backend.vaccinebookingsystem.repository;

import com.backend.vaccinebookingsystem.domain.dao.HealthFacilityImageDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthFacilityImageRepository extends JpaRepository<HealthFacilityImageDao, Long> {

}
