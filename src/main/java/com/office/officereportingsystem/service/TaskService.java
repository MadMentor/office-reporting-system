package com.office.officereportingsystem.service;

import com.office.officereportingsystem.dto.request.TaskCreateRequestDto;
import com.office.officereportingsystem.dto.request.TaskUpdateRequestDto;
import com.office.officereportingsystem.dto.response.TaskResponseDto;
import com.office.officereportingsystem.entity.Task;
import com.office.officereportingsystem.entity.User;
import com.office.officereportingsystem.enums.TaskStatus;

import java.util.List;

public interface TaskService {
    void createTask(TaskCreateRequestDto dto, String currentAdmin);
    TaskResponseDto  getTaskById(Integer id);
    TaskUpdateRequestDto  getTaskUpdateRequestById(Integer id);
    List<Task> getTasksAssignedToUser(String email);
    List<Task> getAllTasks();
    void   updateTask(Integer taskId, TaskUpdateRequestDto dto, String currentAdmin);
    void deleteTask(Integer taskId, String currentAdmin);
    void changeTaskStatus(Integer taskId, String username, TaskStatus newStatus);
    long getTotalTaskCount();
}

