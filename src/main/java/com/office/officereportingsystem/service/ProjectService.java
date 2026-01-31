package com.office.officereportingsystem.service;

import com.office.officereportingsystem.dto.request.ProjectCreateRequestDto;
import com.office.officereportingsystem.dto.request.ProjectUpdateRequestDto;
import com.office.officereportingsystem.entity.Project;
import com.office.officereportingsystem.entity.User;

import java.util.List;

public interface ProjectService {
    Project createProject(ProjectCreateRequestDto dto, String currentAdmin);
    List<Project> getAllProjects();
    List<Project> getProjectsByAdmin(Integer adminId);
    ProjectUpdateRequestDto getProjectById(Integer projectId);
    void updateProject(Integer projectId, ProjectUpdateRequestDto dto);
    void deleteProject(Integer projectId);
    long getActiveProjectCount();
}
