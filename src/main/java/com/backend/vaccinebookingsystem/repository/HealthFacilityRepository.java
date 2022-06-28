package com.backend.vaccinebookingsystem.repository;

import com.backend.vaccinebookingsystem.domain.dao.HealthFacilityDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HealthFacilityRepository extends JpaRepository<HealthFacilityDao, Long> {

    Optional<HealthFacilityDao> findHealthFacilityDaoByFacilityName(String facilityName);

}
