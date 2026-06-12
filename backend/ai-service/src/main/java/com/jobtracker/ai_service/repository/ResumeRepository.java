package com.jobtracker.ai_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobtracker.ai_service.model.Resumes;

public interface ResumeRepository extends JpaRepository<Resumes, Long> {

    List<Resumes> findByUserId(long userId);
}
