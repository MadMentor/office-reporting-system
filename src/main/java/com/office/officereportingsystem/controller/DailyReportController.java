package com.office.officereportingsystem.controller;

import com.office.officereportingsystem.dto.request.DailyReportRequestDto;
import com.office.officereportingsystem.service.DailyReportService;
import com.office.officereportingsystem.service.ProjectService;
import com.office.officereportingsystem.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/reports")
@RequiredArgsConstructor
public class DailyReportController {

    private final ProjectService projectService;
    private final DailyReportService reportService;
    private final TaskService taskService;

    @GetMapping("/create")
    public String showReportForm(Model model, Principal principal) {
        model.addAttribute("dailyReportRequest", new DailyReportRequestDto());
        model.addAttribute("projects", projectService.getAllProjects());
        model.addAttribute("tasks", taskService.getTasksAssignedToUser(principal.getName()));
        return "daily-report/create-report";
    }

    @PostMapping("/create")
    public String submitReport(@Valid @ModelAttribute("dailyReportRequest") DailyReportRequestDto dto,
                               BindingResult result,
                               RedirectAttributes redirectAttributes,
                               Principal principal,
                               Model model) {

        if (result.hasErrors()) {
            model.addAttribute("projects", projectService.getAllProjects());
            return "daily-report/create-report";
        }

        reportService.submitDailyReport(dto, principal.getName());
        redirectAttributes.addFlashAttribute("message", "REPORT_SUBMITTED_SUCCESS");
        return "redirect:/reports/mine";
    }

    @GetMapping("/mine")
    public String myReports(Model model, Principal principal) {
        model.addAttribute("reports", reportService.getReportsByUser(principal.getName()));
        return "daily-report/list-reports";
    }

    @GetMapping("/all-reports")
    public String allReports(Model model, Principal principal) {
        model.addAttribute("reports", reportService.getAllReports());
        return "daily-report/list-reports";
    }
}
