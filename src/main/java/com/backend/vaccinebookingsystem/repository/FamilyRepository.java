package com.backend.vaccinebookingsystem.repository;

import com.backend.vaccinebookingsystem.domain.dao.FamilyDao;
import com.backend.vaccinebookingsystem.domain.dto.FamilyDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FamilyRepository extends JpaRepository<FamilyDao, Long> {

    Optional<FamilyDao> findTopByNIK(String username);

}
