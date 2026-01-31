package com.office.officereportingsystem.repository;

import com.office.officereportingsystem.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepo extends JpaRepository<Project, Integer> {
    List<Project> findByCreatedById(Integer userId);
}
