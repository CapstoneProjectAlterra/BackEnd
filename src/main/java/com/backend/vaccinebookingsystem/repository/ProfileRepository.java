package com.backend.vaccinebookingsystem.repository;

import com.backend.vaccinebookingsystem.domain.dao.ProfileDao;
import com.backend.vaccinebookingsystem.domain.dto.ProfileDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileDao, Long> {

    Optional<ProfileDao> findByUserId(Long userId);
}
