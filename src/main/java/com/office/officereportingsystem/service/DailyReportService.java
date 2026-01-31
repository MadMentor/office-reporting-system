package com.office.officereportingsystem.service;

import com.office.officereportingsystem.dto.request.DailyReportRequestDto;
import com.office.officereportingsystem.entity.DailyReport;

import java.time.LocalDate;
import java.util.List;

public interface DailyReportService {
    void submitDailyReport(DailyReportRequestDto dto, String userEmail);
    List<DailyReport> getReportsByUser(String userEmail);
    List<DailyReport> getDailyReports(LocalDate date);
    List<DailyReport> getRecentReportsByUser(String email, int limit);
    long getTodayReportCount();
    List<DailyReport> getAllReports();
}
