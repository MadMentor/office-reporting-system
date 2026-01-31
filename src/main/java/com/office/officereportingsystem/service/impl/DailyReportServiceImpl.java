package com.office.officereportingsystem.service.impl;

import com.office.officereportingsystem.dto.request.DailyReportRequestDto;
import com.office.officereportingsystem.entity.DailyReport;
import com.office.officereportingsystem.entity.Project;
import com.office.officereportingsystem.entity.Task;
import com.office.officereportingsystem.entity.User;
import com.office.officereportingsystem.exception.AccountNotFoundException;
import com.office.officereportingsystem.exception.ProjectNotFoundException;
import com.office.officereportingsystem.exception.TaskNotFoundException;
import com.office.officereportingsystem.repository.DailyReportRepo;
import com.office.officereportingsystem.repository.ProjectRepo;
import com.office.officereportingsystem.repository.TaskRepo;
import com.office.officereportingsystem.repository.UserRepo;
import com.office.officereportingsystem.service.DailyReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DailyReportServiceImpl implements DailyReportService {

    private final DailyReportRepo dailyReportRepo;
    private final UserRepo userRepo;
    private final TaskRepo taskRepo;
    private final ProjectRepo projectRepo;

    @Override
    public void submitDailyReport(DailyReportRequestDto dto, String userEmail) {
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new AccountNotFoundException("USER_NOT_FOUND"));

        Task task = taskRepo.findById(dto.getTaskId())
                .orElseThrow(() -> new TaskNotFoundException("TASK_NOT_FOUND"));

        Project project = projectRepo.findById(task.getProject().getId())
                .orElseThrow(() -> new ProjectNotFoundException("PROJECT_NOT_FOUND"));

        DailyReport dailyReport = DailyReport.builder()
                .user(user)
                .task(task)
                .description(dto.getDescription())
                .date(dto.getDate())
                .createdAt(LocalDateTime.now())
                .hoursWorked(dto.getHoursWorked())
                .build();

        dailyReportRepo.save(dailyReport);
    }

    @Override
    public List<DailyReport> getReportsByUser(String userEmail) {
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new AccountNotFoundException("USER_NOT_FOUND"));

        return dailyReportRepo.findByUser(user);
    }

    @Override
    public List<DailyReport> getDailyReports(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("DATE_REQUIRED");
        }

        return dailyReportRepo.findByDate(date);
    }

    @Override
    public List<DailyReport> getRecentReportsByUser(String email, int limit) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException("USER_NOT_FOUND"));
        return dailyReportRepo.findTopNByUserOrderByDateDesc(user, limit);
    }

    @Override
    public long getTodayReportCount() {
        return dailyReportRepo.findByDate(LocalDate.now()).stream().count();
    }

    @Override
    public List<DailyReport> getAllReports() {
        return dailyReportRepo.findAll();
    }
}
