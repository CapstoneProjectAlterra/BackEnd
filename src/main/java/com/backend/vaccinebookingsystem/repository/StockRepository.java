package com.backend.vaccinebookingsystem.repository;

import com.backend.vaccinebookingsystem.domain.common.FacilityVaccineKey;
import com.backend.vaccinebookingsystem.domain.dao.FacilityVaccineDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<FacilityVaccineDao, FacilityVaccineKey> {

    @Query("select f from FacilityVaccineDao f where f.facilityId = ?1 and f.vaccineId = ?2")
    Optional<FacilityVaccineDao> findByFacilityIdAndAndVaccineId(Long facilityId, Long vaccineId);

}
