package com.backend.vaccinebookingsystem.repository;

import com.backend.vaccinebookingsystem.domain.dao.NewsDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<NewsDao, Long> {
}
