package com.office.officereportingsystem.controller;

import com.office.officereportingsystem.entity.DailyReport;
import com.office.officereportingsystem.entity.Task;
import com.office.officereportingsystem.service.DailyReportService;
import com.office.officereportingsystem.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

//    private final TaskService taskService;
//    private final DailyReportService reportService;

//    @GetMapping("/dashboard")
//    public String userDashboard(Model model, Principal principal) {
//
//        // Tasks assigned to the user
//        List<Task> assignedTasks = taskService.getTasksAssignedToUser(principal.getName());
//        model.addAttribute("tasks", assignedTasks);
//
//        // Recent reports submitted by the user
//        List<DailyReport> recentReports = reportService.getRecentReportsByUser(principal.getName(), 5);
//        model.addAttribute("reports", recentReports);
//
//        return "user/dashboard";
//    }
}
