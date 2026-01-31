package com.office.officereportingsystem.service.impl;

import com.office.officereportingsystem.dto.request.TaskCreateRequestDto;
import com.office.officereportingsystem.dto.request.TaskUpdateRequestDto;
import com.office.officereportingsystem.dto.response.TaskResponseDto;
import com.office.officereportingsystem.entity.Project;
import com.office.officereportingsystem.entity.Task;
import com.office.officereportingsystem.entity.User;
import com.office.officereportingsystem.enums.Role;
import com.office.officereportingsystem.exception.AccountNotFoundException;
import com.office.officereportingsystem.exception.TaskNotFoundException;
import com.office.officereportingsystem.repository.ProjectRepo;
import com.office.officereportingsystem.repository.TaskRepo;
import com.office.officereportingsystem.repository.UserRepo;
import com.office.officereportingsystem.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.office.officereportingsystem.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepo taskRepo;
    private final ProjectRepo projectRepo;
    private final UserRepo userRepo;

    @Override
    public void createTask(TaskCreateRequestDto dto, String currentAdmin) {
        User assignedTo = userRepo.findById(dto.getAssignedToId())
                .orElseThrow(() -> new AccountNotFoundException("USER_NOT_FOUND"));

        User admin = userRepo.findByEmail(currentAdmin)
                .orElseThrow(() -> new AccountNotFoundException("ACCOUNT_NOT_FOUND"));

        Project project = null;
        if (dto.getProjectId() != null) {
            project = projectRepo.findById(dto.getProjectId())
                    .orElseThrow(() -> new RuntimeException("PROJECT_NOT_FOUND"));
        }

        Task task = Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .project(project)
                .assignedTo(assignedTo)
                .createdBy(admin)
                .assignedBy(admin)
                .createdAt(LocalDateTime.now())
                .dueDate(dto.getDueDate())
                .status(TaskStatus.NEW)
                .build();

        Task savedTask = taskRepo.save(task);
    }

    @Override
    public TaskResponseDto getTaskById(Integer id) {
        Task task =  taskRepo.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("TASK_NOT_FOUND"));

        return TaskResponseDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .assignedTo(task.getAssignedTo().getId())
                .assignedTo(task.getAssignedTo().getId())
                .build();
    }

    @Override
    public TaskUpdateRequestDto getTaskUpdateRequestById(Integer id) {
        Task task =  taskRepo.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("TASK_NOT_FOUND"));

        return TaskUpdateRequestDto.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .assignedToId(task.getAssignedTo().getId())
                .dueDate(task.getDueDate())
                .projectId(task.getProject().getId())
                .build();
    }


    @Override
    public List<Task> getTasksAssignedToUser(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException("USER_NOT_FOUND"));

        return taskRepo.findByAssignedTo(user);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }

    @Override
    public void updateTask(Integer taskId, TaskUpdateRequestDto dto, String currentAdmin) {
        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new RuntimeException("TASK_NOT_FOUND"));

        User assignedTo = userRepo.findById(dto.getAssignedToId())
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));

        Project project = null;
        if (dto.getProjectId() != null) {
            project = projectRepo.findById(dto.getProjectId())
                    .orElseThrow(() -> new RuntimeException("PROJECT_NOT_FOUND"));
        }

        User admin = userRepo.findByEmail(currentAdmin)
                .orElseThrow(() -> new AccountNotFoundException("USER_NOT_FOUND"));
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setProject(project);
        task.setAssignedTo(assignedTo);
        task.setUpdatedBy(admin.getId());
        task.setAssignedBy(admin);
        if (dto.getDueDate() != null) task.setDueDate(dto.getDueDate());

        taskRepo.save(task);
    }

    @Override
    public void deleteTask(Integer taskId, String currentAdmin) {
        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new RuntimeException("TASK_NOT_FOUND"));
        taskRepo.delete(task);
    }

    @Override
    public void changeTaskStatus(Integer taskId, String username, TaskStatus newStatus) {
        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("TASK_NOT_FOUND"));
        
        User currentUser = userRepo.findByEmail(username)
                .orElseThrow(() -> new AccountNotFoundException("USER_NOT_FOUND"));
        
        TaskStatus currentStatus = task.getStatus();
        
        boolean isAdmin = currentUser.getRole() == Role.ADMIN || currentUser.getRole() == Role.SUPER_ADMIN;

        boolean isAssignee =
                task.getAssignedTo() != null &&
                        task.getAssignedTo().getId().equals(currentUser.getId());

        if (isAdmin) {
            task.setStatus(newStatus);
        } else if (isAssignee) {
            boolean validUserTransition =
                    (currentStatus == TaskStatus.NEW && newStatus == TaskStatus.IN_PROGRESS) ||
                            (currentStatus == TaskStatus.IN_PROGRESS && newStatus == TaskStatus.COMPLETED);

            if (!validUserTransition) {
                throw new IllegalStateException(
                        "You are not allowed to change task from "
                                + currentStatus + " to " + newStatus
                );
            }
            task.setStatus(newStatus);
        } else {
            throw new SecurityException("You are not authorized to update this task");
        }

        task.setUpdatedAt(LocalDateTime.now());
        task.setUpdatedBy(currentUser.getId());

        taskRepo.save(task);
    }

    @Override
    public long getTotalTaskCount() {
        return taskRepo.count();
    }
}
