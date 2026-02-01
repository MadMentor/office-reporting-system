package com.office.officereportingsystem.service.impl;

import com.office.officereportingsystem.dto.request.ProjectCreateRequestDto;
import com.office.officereportingsystem.dto.request.ProjectUpdateRequestDto;
import com.office.officereportingsystem.entity.Project;
import com.office.officereportingsystem.exception.AccountNotFoundException;
import com.office.officereportingsystem.exception.ProjectNotFoundException;
import com.office.officereportingsystem.repository.ProjectRepo;
import com.office.officereportingsystem.repository.UserRepo;
import com.office.officereportingsystem.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepo projectRepo;
    private final UserRepo userRepo;

    @Override
    public Project createProject(ProjectCreateRequestDto dto, String currentAdmin) {
        Project project = Project.builder()
                .name(dto.getName().trim())
                .description(dto.getDescription())
                .createdBy(userRepo.findByEmail(currentAdmin)
                        .orElseThrow(() -> new AccountNotFoundException("USER_NOT_FOUND")))
                .createdAt(LocalDateTime.now())
                .build();
        return projectRepo.save(project);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepo.findAll();
    }

    @Override
    public List<Project> getProjectsByAdmin(Integer adminId) {
        return projectRepo.findByCreatedById(adminId);
    }

    @Override
    public ProjectUpdateRequestDto getProjectById(Integer projectId) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new RuntimeException("PROJECT_NOT_FOUND"));

        return ProjectUpdateRequestDto.builder()
                .projectName(project.getName())
                .description(project.getDescription())
                .build();
    }

    @Override
    public void updateProject(Integer projectId, ProjectUpdateRequestDto dto) {

        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException());

        project.setName(dto.getProjectName());
        project.setDescription(dto.getDescription());
        project.setUpdatedAt(LocalDateTime.now());

        projectRepo.save(project);
    }

    @Override
    public void deleteProject(Integer projectId) {

        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException());

        if (!project.getTasks().isEmpty()) {
            throw new IllegalStateException("PROJECT_HAS_ACTIVE_TASKS");
        }

        projectRepo.delete(project);
    }

    @Override
    public long getActiveProjectCount() {
        return projectRepo.count();
    }

}
