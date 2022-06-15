package com.backend.vaccinebookingsystem.repository;

import com.backend.vaccinebookingsystem.domain.dao.HealthFacilityDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthFacilityRepository extends JpaRepository<HealthFacilityDao, Long> {

    @Query(value = "SELECT ji FROM HealthFacilityDao ji WHERE UPPER(ji.city) LIKE UPPER(CONCAT('%', :city, '%') ) ")
    List<HealthFacilityDao> findAllByCity(String city);

    @Query(value = "SELECT ji FROM HealthFacilityDao ji WHERE UPPER(ji.province) LIKE UPPER(CONCAT('%', :province, '%') ) ")
    List<HealthFacilityDao> findAllByProvince(String province);

    @Query(value = "SELECT ji FROM HealthFacilityDao ji WHERE UPPER(ji.postalCode) LIKE UPPER(CONCAT('%', :postalCode, '%') ) ")
    List<HealthFacilityDao> findAllByPostalCode(String postalCode);
}
