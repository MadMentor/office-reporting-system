package com.office.officereportingsystem.controller;

import com.office.officereportingsystem.entity.DailyReport;
import com.office.officereportingsystem.entity.Task;
import com.office.officereportingsystem.service.DailyReportService;
import com.office.officereportingsystem.service.ProjectService;
import com.office.officereportingsystem.service.TaskService;
import com.office.officereportingsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {

    private final TaskService taskService;
    private final DailyReportService reportService;
    private final UserService userService;
    private final ProjectService projectService;

    @GetMapping
    public String dashboard(Model model, Principal principal, Authentication authentication) {
        // Check user role and redirect accordingly
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String role = authority.getAuthority();

            switch (role) {
                case "ROLE_SUPER_ADMIN":
                    model.addAttribute("adminCount", userService.getAdminCount());
                    model.addAttribute("userCount", userService.getTotalUsersCount());
                    model.addAttribute("projectCount", projectService.getActiveProjectCount());
                    model.addAttribute("pendingTaskCount", taskService.getTotalTaskCount());
                    return "super-admin/dashboard";
                case "ROLE_ADMIN":
                    model.addAttribute("userCount", userService.getTotalUsersCount());
                    model.addAttribute("projectCount", projectService.getActiveProjectCount());
                    model.addAttribute("taskCount", taskService.getTotalTaskCount());
                    model.addAttribute("reportCount", reportService.getTodayReportCount());
                    return "admin/dashboard";
                case "ROLE_USER":
                    List<Task> assignedTasks = taskService.getTasksAssignedToUser(principal.getName());
                    model.addAttribute("tasks", assignedTasks);

                    List<DailyReport> recentReports = reportService.getRecentReportsByUser(principal.getName(), 5);
                    model.addAttribute("reports", recentReports);

                    return "user/dashboard";
            }
        }

        // Default fallback
        return "redirect:/login";
    }
}
