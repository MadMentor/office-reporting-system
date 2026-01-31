package com.office.officereportingsystem.repository;

import com.office.officereportingsystem.entity.DailyReport;
import com.office.officereportingsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DailyReportRepo extends JpaRepository<DailyReport, Integer> {
    List<DailyReport> findByUser(User user);
    List<DailyReport> findTopNByUserOrderByDateDesc(User user, int n);
    List<DailyReport> findByDate(LocalDate date);
}
