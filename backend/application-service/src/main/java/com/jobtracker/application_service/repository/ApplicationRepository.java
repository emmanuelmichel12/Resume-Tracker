package com.jobtracker.application_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobtracker.application_service.model.Applications;

public interface ApplicationRepository extends JpaRepository<Applications, Long> {
    List<Applications> findByUserId(long userId);
}
