package com.office.officereportingsystem.repository;

import com.office.officereportingsystem.entity.Task;
import com.office.officereportingsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Integer> {
    List<Task> findByAssignedTo(User user);
    List<Task> findByProjectId(Integer projectId);
}
